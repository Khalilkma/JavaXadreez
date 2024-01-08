package Chess;

import Chess.piece.*;
import JogoTabuleiro.Piece;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();


    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        setupInicial();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (ChessPiece) tabuleiro.piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return tabuleiro.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testeCheck(jogadorAtual)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode se colocar em check");
        }

        ChessPiece movedPiece = (ChessPiece) tabuleiro.piece(target);

        // promoção
        promoted = null;
        if (movedPiece instanceof pawn) {
            if ((movedPiece.getCor() == Cor.BRANCO && target.getLinha() == 0) || (movedPiece.getCor() == Cor.PRETO && target.getLinha() == 7)) {
                promoted = (ChessPiece)tabuleiro.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }

        check = (testeCheck(opponent(jogadorAtual))) ? true : false;

        if (testCheckMate(opponent(jogadorAtual))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        // en passant
        if (movedPiece instanceof pawn && (target.getLinha() == source.getLinha() - 2 || target.getLinha() == source.getLinha() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("Não tem nenhuma peça para ser promovida");
        }
        if (!type.equals("B") && !type.equals("C") && !type.equals("Q") && !type.equals("T")) {
            throw new InvalidParameterException("Tipo inválido para promoção");
        }

        Position pos = promoted.getChessPosition().toPosition();
        Piece p = tabuleiro.removePiece(pos);
        piecesOnTheBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getCor());
        tabuleiro.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Cor cor) {
        if(type.equals("B")) return new Bishop(tabuleiro, cor);
        if(type.equals("C")) return new Knight(tabuleiro, cor);
        if(type.equals("Q")) return new Queen(tabuleiro, cor);
        return new Torre(tabuleiro, cor);
    }



    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) tabuleiro.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = tabuleiro.removePiece(target);
        tabuleiro.placePiece(p, target);

        if(capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // rock pequeno
        if(p instanceof Rei && target.getColuna() == source.getColuna() + 2) {
            Position sourceT = new Position(source.getLinha(), source.getColuna() + 3);
            Position targetT = new Position(source.getLinha(), source.getColuna() + 1);
            ChessPiece rook = (ChessPiece)tabuleiro.removePiece(sourceT);
            tabuleiro.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // rock grande
        if(p instanceof Rei && target.getColuna() == source.getColuna() - 2) {
            Position sourceT = new Position(source.getLinha(), source.getColuna() - 4);
            Position targetT = new Position(source.getLinha(), source.getColuna() - 1);
            ChessPiece rook = (ChessPiece)tabuleiro.removePiece(sourceT);
            tabuleiro.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        //en passant
        if (p instanceof pawn) {
            if (source.getColuna() != target.getColuna() && capturedPiece == null) {
                Position pawnPosition;
                if(p.getCor() == Cor.BRANCO) {
                    pawnPosition = new Position(target.getLinha() + 1, target.getColuna());
                } else {
                    pawnPosition = new Position(target.getLinha() - 1, target.getColuna());
                }
                capturedPiece = tabuleiro.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece p = (ChessPiece) tabuleiro.removePiece(target);
        p.decreaseMoveCount();
        tabuleiro.placePiece(p, source);

        if (capturedPiece != null) {
            tabuleiro.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // rock pequeno
        if(p instanceof Rei && target.getColuna() == source.getColuna() + 2) {
            Position sourceT = new Position(source.getLinha(), source.getColuna() + 3);
            Position targetT = new Position(source.getLinha(), source.getColuna() + 1);
            ChessPiece rook = (ChessPiece)tabuleiro.removePiece(targetT);
            tabuleiro.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // rock grande
        if(p instanceof Rei && target.getColuna() == source.getColuna() - 2) {
            Position sourceT = new Position(source.getLinha(), source.getColuna() - 4);
            Position targetT = new Position(source.getLinha(), source.getColuna() - 1);
            ChessPiece rook = (ChessPiece)tabuleiro.removePiece(targetT);
            tabuleiro.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        if (p instanceof pawn) {
            if (source.getColuna() != target.getColuna() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)tabuleiro.removePiece(target);

                Position pawnPosition;
                if(p.getCor() == Cor.BRANCO) {
                    pawnPosition = new Position(3, target.getColuna());
                } else {
                    pawnPosition = new Position(4, target.getColuna());
                }
                tabuleiro.placePiece(pawn, pawnPosition);
            }
        }

    }

    private void validateSourcePosition(Position position) {
        if(!tabuleiro.thereIsAPiece(position)){
            throw new ChessException("Não tem uma peça na posição inicial");
        }
        if(jogadorAtual != ((ChessPiece)tabuleiro.piece(position)).getCor()) {
            throw new ChessException("A peça escolhida não é sua");
        }
        if(!tabuleiro.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("Essa peça não pode se mover");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if(!tabuleiro.piece(source).possibleMove(target)) {
            throw new ChessException("A peça escolhida não pode se mover para essa casa");
        }
    }

    private void nextTurn() {
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private Cor opponent(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private ChessPiece rei(Cor cor) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
        for(Piece p : list) {
            if (p instanceof Rei) {
                return(ChessPiece) p;
            }
        }
        throw new IllegalStateException("Não existe o rei da cor " + cor + " no tabuleiro");
    }

    private boolean testeCheck(Cor cor) {
        Position kingPosition = rei(cor).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getCor() == opponent(cor)).collect(Collectors.toList());
        for(Piece p : opponentPieces){
            boolean[][] mat = p.possibleMoves();
            if(mat[kingPosition.getLinha()][kingPosition.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Cor cor) {
        if(!testeCheck(cor)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
        for(Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for(int i = 0; i < tabuleiro.getLinhas(); i++){
                for(int j = 0; j < tabuleiro.getColunas(); j++) {
                    if(mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testeCheck(cor);
                        undoMove(source, target, capturedPiece);
                        if(!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    private void placeNewPiece(char coluna, int linha, ChessPiece piece) {
        tabuleiro.placePiece(piece, new ChessPosition(coluna, linha).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void setupInicial(){
        placeNewPiece('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('b', 1, new Knight(tabuleiro, Cor.BRANCO));
        placeNewPiece('c', 1, new Bishop(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 1, new Queen(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('f', 1, new Bishop(tabuleiro, Cor.BRANCO));
        placeNewPiece('g', 1, new Knight(tabuleiro, Cor.BRANCO));
        placeNewPiece('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('a', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('b', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('c', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('d', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('e', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('f', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('g', 2, new pawn(tabuleiro, Cor.BRANCO, this));
        placeNewPiece('h', 2, new pawn(tabuleiro, Cor.BRANCO, this));

        placeNewPiece('a', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('b', 8, new Knight(tabuleiro, Cor.PRETO));
        placeNewPiece('c', 8, new Bishop(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 8, new Queen(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        placeNewPiece('f', 8, new Bishop(tabuleiro, Cor.PRETO));
        placeNewPiece('g', 8, new Knight(tabuleiro, Cor.PRETO));
        placeNewPiece('h', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('a', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('b', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('c', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('d', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('e', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('f', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('g', 7, new pawn(tabuleiro, Cor.PRETO, this));
        placeNewPiece('h', 7, new pawn(tabuleiro, Cor.PRETO, this));

    }
}
