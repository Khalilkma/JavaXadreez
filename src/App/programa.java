package App;

import Chess.ChessException;
import Chess.ChessPiece;
import Chess.ChessPosition;
import Chess.PartidaXadrez;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

import java.util.InputMismatchException;
import java.util.Scanner;

public class programa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();

        while(true) {
            try {
                UI.clearScreen();
                UI.printTabuleiro(partidaXadrez.getPieces());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPositon(sc);

                boolean[][] possibleMoves = partidaXadrez.possibleMoves(source);
                UI.clearScreen();
                UI.printTabuleiro(partidaXadrez.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPositon(sc);

                ChessPiece capturedPiece = partidaXadrez.performChessMove(source, target);
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}
