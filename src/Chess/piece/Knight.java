package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class Knight extends ChessPiece {
    public Knight(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "C";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece)getTabuleiro().piece(position);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Position p = new Position(0, 0);

        p.setValues(position.getLinha() - 1, position.getColuna() - 2);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() - 2, position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() - 2, position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() - 1, position.getColuna() + 2);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() + 1, position.getColuna() + 2);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() + 2, position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() + 2, position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(position.getLinha() + 1, position.getColuna() - 2);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
