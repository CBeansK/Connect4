package ui;

import core.Connect4;
import player.Connect4ComputerPlayer;

import java.util.Scanner;

public class Connect4TextConsole {

    private static void runGame(){
        Connect4 game = new Connect4(8);

        final char[] PlayerTypes = {'R','B'};

        Scanner input = new Scanner(System.in);

        System.out.println("Would you like to play against another player or AI? (P/C)");
        char gameChoice = input.next().toUpperCase().charAt(0);
        if(gameChoice == 'P'){
            runPvP(game, PlayerTypes, input);
        } else if(gameChoice == 'C'){
            runPvC(game, PlayerTypes, input);
        }
    }

    // runs a player vs. computer instance of the game
    private static void runPvC(Connect4 game, char[] PlayerTypes, Scanner input){
        char player = PlayerTypes[0];
        Connect4ComputerPlayer opponent = new Connect4ComputerPlayer(game, PlayerTypes[1]);

        boolean foundWinner = false;
        while(!foundWinner){
            System.out.println("Please enter a column: (1-8)");
            int col = input.nextInt() - 1;
            // place piece for player
            boolean validMove = game.placePiece(col, player);
            while(!validMove){
                System.out.println("Not a valid choice. Enter a valid column. (1-8)");
                game.printBoard();
                col = input.nextInt() - 1;
                validMove = game.placePiece(col, player);
            }

            foundWinner = game.checkForWin(col, player) == 4;
            if(foundWinner){
                System.out.println("Player won!");
                game.printBoard();
                break;
            }
            // place piece for bot
            col = opponent.previewMoves();
            validMove = opponent.placePiece(col);
            if(!validMove){
                System.out.println("AI opponent failed to place piece");
            }
            foundWinner = game.checkForWin(col, opponent.team()) == 4;
            if(foundWinner){
                System.out.println("AI won!");
                game.printBoard();
                break;
            }

            game.printBoard();
        }
    }

    // runs a pvp instance of the game
    private static void runPvP(Connect4 game, char[] PlayerTypes, Scanner input){
        char player1 = PlayerTypes[0];
        char player2 = PlayerTypes[1];

        boolean foundWinner = false;

        // totally not optimized. will fix soon
        while(!foundWinner){
            System.out.println("Player 1, enter a column: (1-8)");
            int col = input.nextInt() - 1;

            boolean validMove = game.placePiece(col, player1);
            while(!validMove){
                System.out.println("Not a valid choice. Enter a valid column. (1-8)");
                game.printBoard();
                col = input.nextInt() - 1;
                validMove = game.placePiece(col, player1);
            }

            foundWinner = game.checkForWin(col, player1) == 4;
            if(foundWinner){
                System.out.println("Player 1 won!");
                game.printBoard();
                break;
            }

            game.printBoard();

            System.out.println("Player 2, enter a column: (1-8)");
            col = input.nextInt() - 1;

            validMove = game.placePiece(col, player2);
            while(!validMove){
                System.out.println("Not a valid choice. Enter a valid column. (1-8)");
                game.printBoard();
                col = input.nextInt() - 1;
                validMove = game.placePiece(col, player2);
            }

            foundWinner = game.checkForWin(col, player2) == 4;
            if(foundWinner){
                System.out.println("Player 2 won!");
                game.printBoard();
                break;
            }

            game.printBoard();
        }
    }
    public static void main(String[] args){
        runGame();
    }
}
