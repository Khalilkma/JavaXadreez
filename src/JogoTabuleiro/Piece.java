package JogoTabuleiro;


public class Piece {

    protected Position position;
    private Tabuleiro tabuleiro;

    public Piece(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        position = null;
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
