package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class pawn extends ChessPiece {

    public pawn(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Position p = new Position(0, 0);

        if(getCor() == Cor.BRANCO) {
            p.setValues(position.getLinha() - 1, position.getColuna());
            if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() - 2, position.getColuna());
            Position p2 = new Position(position.getLinha() - 1, position.getColuna());
            if (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p) && getTabuleiro().positionExists(p2) && !getTabuleiro().thereIsAPiece(p2)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() - 1, position.getColuna() - 1);
            if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() - 1, position.getColuna() + 1);
            if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        else {
            p.setValues(position.getLinha() + 1, position.getColuna());
            if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() + 2, position.getColuna());
            Position p2 = new Position(position.getLinha() - 1, position.getColuna());
            if (getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p) && getTabuleiro().positionExists(p2) && !getTabuleiro().thereIsAPiece(p2)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() + 1, position.getColuna() - 1);
            if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() + 1, position.getColuna() + 1);
            if(getTabuleiro().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
