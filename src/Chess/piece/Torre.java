package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import JogoTabuleiro.Tabuleiro;

public class Torre extends ChessPiece {
    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return String.valueOf('♖');
    }
}
