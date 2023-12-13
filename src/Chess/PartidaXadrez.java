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

    private void setupInicial(){
        tabuleiro.placePiece(new Torre(tabuleiro, Cor.BRANCO), new Position(1, 2));
        tabuleiro.placePiece(new Rei(tabuleiro, Cor.PRETO), new Position(4, 0));
        tabuleiro.placePiece(new Rei(tabuleiro, Cor.BRANCO), new Position(4, 7));

    }
}
