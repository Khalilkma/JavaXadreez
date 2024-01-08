package Chess.piece;

import Chess.ChessPiece;
import Chess.Cor;
import Chess.PartidaXadrez;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public class Rei extends ChessPiece {

    private PartidaXadrez partidaXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getTabuleiro().piece(position);
        return p == null || p.getCor() != getCor();
    }

    private boolean testRockCastling(Position position) {
        ChessPiece p = (ChessPiece) getTabuleiro().piece(position);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Position p = new Position(0, 0);

        // Cima
        p.setValues(position.getLinha() - 1, position.getColuna());
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Baixo
        p.setValues(position.getLinha() + 1, position.getColuna());
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Esquerda
        p.setValues(position.getLinha(), position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Direita
        p.setValues(position.getLinha(), position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NW
        p.setValues(position.getLinha() - 1, position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NE
        p.setValues(position.getLinha() - 1, position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SW
        p.setValues(position.getLinha() + 1, position.getColuna() - 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SE
        p.setValues(position.getLinha() + 1, position.getColuna() + 1);
        if (getTabuleiro().positionExists(p) && canMove(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Special move castling
        if (getMoveCount() == 0 && !partidaXadrez.getCheck()) {
            // Rock pequeno
            Position posT1 = new Position(position.getLinha(), position.getColuna() + 3);
            if (testRockCastling(posT1)) {
                Position p1 = new Position(position.getLinha(), position.getColuna() + 1);
                Position p2 = new Position(position.getLinha(), position.getColuna() + 2);

                if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null) {
                    mat[position.getLinha()][position.getColuna() + 2] = true;
                }
            }

            // Rock grande
            Position posT2 = new Position(position.getLinha(), position.getColuna() - 4);
            if (testRockCastling(posT2)) {
                Position p1 = new Position(position.getLinha(), position.getColuna() - 1);
                Position p2 = new Position(position.getLinha(), position.getColuna() - 2);
                Position p3 = new Position(position.getLinha(), position.getColuna() - 3);

                if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null && getTabuleiro().piece(p3) == null) {
                    mat[position.getLinha()][position.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }
}
