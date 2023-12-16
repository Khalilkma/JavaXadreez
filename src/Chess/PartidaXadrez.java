package Chess;

import Chess.piece.Rei;
import Chess.piece.Torre;
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

        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = tabuleiro.removePiece(source);
        Piece capturedPiece = tabuleiro.removePiece(target);
        tabuleiro.placePiece(p, target);

        if(capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        Piece p = tabuleiro.removePiece(target);
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

    private void placeNewPiece(char coluna, int linha, ChessPiece piece) {
        tabuleiro.placePiece(piece, new ChessPosition(coluna, linha).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void setupInicial(){
        placeNewPiece('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        placeNewPiece('c', 7, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('c', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 7, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 7, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 8, new Torre(tabuleiro, Cor.PRETO));
        placeNewPiece('d', 8, new Rei(tabuleiro, Cor.PRETO));

    }
}
