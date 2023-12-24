package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class Rei extends ChessPiece {
    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString(){
        return "R";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece)getTabuleiro().piece(position);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Position p = new Position(0, 0);

        //cima
        p.setValues(position.getLinha() - 1, position.getColuna());
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // baixo
        p.setValues(position.getLinha() + 1, position.getColuna());
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // esquerda
        p.setValues(position.getLinha(), position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //direita
        p.setValues(position.getLinha(), position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nw
        p.setValues(position.getLinha() - 1, position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ne
        p.setValues(position.getLinha() - 1, position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sw
        p.setValues(position.getLinha() + 1, position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ne
        p.setValues(position.getLinha() + 1, position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
