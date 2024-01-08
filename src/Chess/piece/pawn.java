package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import Chess.PartidaXadrez;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class pawn extends ChessPiece {

    private PartidaXadrez partidaXadrez;

    public pawn(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
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

            //en passant white
            if(position.getLinha() == 3){
                Position left = new Position(position.getLinha(), position.getColuna() - 1);
                if(getTabuleiro().positionExists(left) && isThereOpponentPiece(left) && getTabuleiro().piece(left) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[left.getLinha() - 1][left.getColuna()] = true;
                }
                Position right = new Position(position.getLinha(), position.getColuna() + 1);
                if(getTabuleiro().positionExists(right) && isThereOpponentPiece(right) && getTabuleiro().piece(right) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[right.getLinha() - 1][right.getColuna()] = true;
                }
            }
        }
        else {
            p.setValues(position.getLinha() + 1, position.getColuna());
            if(getTabuleiro().positionExists(p) && !getTabuleiro().thereIsAPiece(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(position.getLinha() + 2, position.getColuna());
            Position p2 = new Position(position.getLinha() + 1, position.getColuna());
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

            //en passant black
            if(position.getLinha() == 4){
                Position left = new Position(position.getLinha(), position.getColuna() - 1);
                if(getTabuleiro().positionExists(left) && isThereOpponentPiece(left) && getTabuleiro().piece(left) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[left.getLinha() + 1][left.getColuna()] = true;
                }
                Position right = new Position(position.getLinha(), position.getColuna() + 1);
                if(getTabuleiro().positionExists(right) && isThereOpponentPiece(right) && getTabuleiro().piece(right) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[right.getLinha() + 1][right.getColuna()] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
