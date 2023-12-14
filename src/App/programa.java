package App;

import Chess.ChessPiece;
import Chess.ChessPosition;
import Chess.PartidaXadrez;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

import java.util.Scanner;

public class programa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();

        while(true) {
            UI.printTabuleiro(partidaXadrez.getPieces());
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = UI.readChessPositon(sc);

            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = UI.readChessPositon(sc);

            ChessPiece capturedPiece = partidaXadrez.performChessMove(source, target);
        }
    }
}
