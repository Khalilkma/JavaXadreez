package Chess;

import JogoTabuleiro.Piece;
import JogoTabuleiro.Tabuleiro;

public class ChessPiece extends Piece {

    private Cor cor;

    public ChessPiece(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }
}
