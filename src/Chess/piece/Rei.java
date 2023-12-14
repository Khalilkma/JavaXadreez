package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import JogoTabuleiro.Tabuleiro;

public class Rei extends ChessPiece {
    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "K";
    }
}
