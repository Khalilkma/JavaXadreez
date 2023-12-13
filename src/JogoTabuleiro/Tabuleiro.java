package JogoTabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Piece[][] pieces;

    public Tabuleiro(int linhas, int colunas) {
        if(linhas < 1 || colunas < 1){
            throw new TabuleiroException("Erro criando tabuleiro: é necessário que haja pelo menos uma linha e uma coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pieces = new Piece[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }


    public int getColunas() {
        return colunas;
    }


    public Piece piece(int linha, int coluna){
        if(!positionExistis(linha, coluna)){
            throw new TabuleiroException("Posição fora do tabuleiro");
        }
        return pieces[linha][coluna];
    }

    public Piece piece(Position position){
        if(!positionExists(position)){
            throw new TabuleiroException("Posição fora do tabuleiro");
        }
        return pieces[position.getLinha()][position.getColuna()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new TabuleiroException("Já tem uma peça na posição" + position);
        }
        pieces[position.getLinha()][position.getColuna()] = piece;
        piece.position = position;
    }

    private boolean positionExistis(int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean positionExists(Position position){
        return positionExistis(position.getLinha(), position.getColuna());
    }

    public boolean thereIsAPiece(Position position){
        if(!positionExists(position)){
            throw new TabuleiroException("Posição fora do tabuleiro");
        }
        return piece(position) != null;
    }
}
