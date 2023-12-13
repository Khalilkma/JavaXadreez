package Chess;

import Chess.piece.Rei;
import Chess.piece.Torre;
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

    private void placeNewPiece(char coluna, int linha, ChessPiece piece) {
        tabuleiro.placePiece(piece, new ChessPosition(coluna, linha).toPosition());
    }

    private void setupInicial(){
        placeNewPiece('b', 6, new Torre(tabuleiro, Cor.BRANCO));
        placeNewPiece('e', 8, new Rei(tabuleiro, Cor.PRETO));
        placeNewPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO));

    }
}
