package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class Bishop extends ChessPiece {
    public Bishop(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Position p = new Position(0, 0);

        //nw
        p.setValues(position.getLinha() - 1, position.getColuna() - 1);
        while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p. getLinha() - 1, p.getColuna()- 1);
        }

        if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //ne
        p.setValues(position.getLinha() - 1, position.getColuna() + 1);
        while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() - 1, p.getColuna() + 1);
        }

        if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //se
        p.setValues(position.getLinha() + 1, position.getColuna() + 1);
        while(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() + 1, p.getColuna() + 1);
        }

        if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //sw
        p.setValues(position.getLinha() + 1, position.getColuna() - 1);
        while (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValues(p.getLinha() + 1, p.getColuna() - 1);
        }

        if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
