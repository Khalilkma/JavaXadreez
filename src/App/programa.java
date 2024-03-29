package App;

import Chess.ChessException;
import Chess.ChessPiece;
import Chess.ChessPosition;
import Chess.PartidaXadrez;
import JogoTabuleiro.Position;
import JogoTabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class programa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<ChessPiece> captured = new ArrayList<>();

        while(!partidaXadrez.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(partidaXadrez, captured);
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

                if(capturedPiece != null){
                    captured.add(capturedPiece);
                }
                if(partidaXadrez.getPromoted() != null) {
                    System.out.print("Escolha a peça para promover (B/C/Q/T): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("C") && !type.equals("Q") && !type.equals("T")) {
                        System.out.print("Valor inválido! Escolha a peça para promover (B/C/Q/T): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    partidaXadrez.replacePromotedPiece(type);
                }

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(partidaXadrez, captured);
    }
}
