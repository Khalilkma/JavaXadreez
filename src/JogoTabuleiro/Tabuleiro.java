package JogoTabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Piece[][] pieces;

    public Tabuleiro(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        pieces = new Piece[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public Piece piece(int linhas, int colunas){
        return pieces[linhas][colunas];
    }

    public Piece piece(Position position){
        return pieces[position.getLinha()][position.getColuna()];
    }
}
