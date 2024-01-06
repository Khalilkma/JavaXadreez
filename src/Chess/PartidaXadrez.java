package Chess;

import Chess.piece.*;
import JogoTabuleiro.Piece;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

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

        if(testeCheck(jogadorAtual)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode se colocar em check");
        }

        check = (testeCheck(opponent(jogadorAtual))) ? true : false;

        if(testCheckMate(opponent(jogadorAtual))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
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
        placeNewPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        placeNewPiece('f', 1, new Bishop(tabuleiro, Cor.BRANCO));
        placeNewPiece('g', 1, new Knight(tabuleiro, Cor.BRANCO));
        placeNewPiece('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('a', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('b', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('c', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('f', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('g', 2, new pawn(tabuleiro, Cor.BRANCO));
        placeNewPiece('h', 2, new pawn(tabuleiro, Cor.BRANCO));

        placeNewPiece('a', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('b', 8, new Knight(tabuleiro, Cor.PRETO));
        placeNewPiece('c', 8, new Bishop(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 8, new Rei(tabuleiro, Cor.PRETO));
        placeNewPiece('f', 8, new Bishop(tabuleiro, Cor.PRETO));
        placeNewPiece('g', 8, new Knight(tabuleiro, Cor.PRETO));
        placeNewPiece('h', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('a', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('b', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('c', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('f', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('g', 7, new pawn(tabuleiro, Cor.PRETO));
        placeNewPiece('h', 7, new pawn(tabuleiro, Cor.PRETO));

    }
}
