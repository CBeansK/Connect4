package ui;

import core.Connect4;

import java.util.Scanner;

public class Connect4TextConsole {



    //TODO: add computer player
    private static void runGame(){
        Connect4 game = new Connect4(8);

        final char[] PlayerTypes = {'R','B'};

        Scanner input = new Scanner(System.in);

        // R moves first
        System.out.println("R moves first.");

        // validate input here

        int player = 0;
        boolean validMove;
        boolean foundWinner = false;
        while(!foundWinner){
            System.out.println("Next player: please enter a number (1-8):");
            int row = input.nextInt() - 1;
            validMove = game.placePiece(row, PlayerTypes[player % 2]);
            if (!validMove){
                System.out.println("Not a valid choice. Please enter another number: (1-8)");
                continue;
            }
            foundWinner = game.checkForWin(row, PlayerTypes[player % 2]);
            if (foundWinner){
                System.out.println("Win!");
                game.printBoard();
                return;
            }
            game.printBoard();
            player++;
        }
        // move:
        //  try to place piece (if fails, then ask for row again)
        //  check for win
        //  if win:
        //      print out victor

        game.printBoard();
    }

    public static void main(String[] args){
        runGame();
    }
}
