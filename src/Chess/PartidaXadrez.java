package Chess;

import Chess.piece.Rei;
import Chess.piece.Torre;
import JogoTabuleiro.Piece;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        setupInicial();
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

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = tabuleiro.removePiece(source);
        Piece capturedPiece = tabuleiro.removePiece(target);
        tabuleiro.placePiece(p, target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if(!tabuleiro.thereIsAPiece(position)){
            throw new ChessException("Não tem uma peça na posição inicial");
        }
    }

    private void placeNewPiece(char coluna, int linha, ChessPiece piece) {
        tabuleiro.placePiece(piece, new ChessPosition(coluna, linha).toPosition());
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
