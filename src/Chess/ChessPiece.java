package Chess;

import JogoTabuleiro.Piece;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

public abstract class ChessPiece extends Piece {

    private Cor cor;
    private int moveCount;

    public ChessPiece(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount(){
        moveCount++;
    }
    public void decreaseMoveCount(){
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position){
        ChessPiece p = (ChessPiece)getTabuleiro().piece(position);
        return p != null && p.getCor() != cor;
    }
}
