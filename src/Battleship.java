import java.util.*;
import java.io.*;

public class Battleship {
    public static void main(String[] args) {
        // Initial prompt
        System.out.println("Welcome to Battleship!\n");

        // Setup scanner object to get user input
        Scanner input = new Scanner(System.in); // Create scanner object

        // Setup player boards
        char[][] player1 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };
        char[][] player2 = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };
        char[][] player1TargetHist = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };
        char[][] player2TargetHist = new char[][]{
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'},
        };

        // BOARD SETUP
        // Get player one ship coordinates
        System.out.println("PLAYER 1, ENTER YOUR SHIPS’ COORDINATES.");
        int counter = 1;
        int xCoord;
        int yCoord;
        do {
            System.out.println("Enter ship " + counter + " location:");
            if (input.hasNextInt()) {
                xCoord = input.nextInt();
            } else {
                input.nextLine();
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }
            if (input.hasNextInt()) {
                yCoord = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }

            // Check to see if X/Y coordinates out of bounds
            if ((xCoord > 4 || xCoord < 0) || (yCoord > 4 || yCoord < 0)) {
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }
            // Check if position already has ship
            if (player1[xCoord][yCoord] == '@') {
                System.out.println("You already have a ship there. Choose different coordinates.");
                continue;
            } else {
                player1[xCoord][yCoord] = '@';
            }

            // Increment counter
            counter++;
        } while (counter < 6); // Required do while loop
        printBattleShip(player1);
        // Scroll past player 1's board
        for (int x = 0; x < 100; x++) {
            System.out.println("");
        }
        // Get player two ship coordinates
        System.out.println("PLAYER 2, ENTER YOUR SHIPS’ COORDINATES.");
        counter = 1;
        do {
            System.out.println("Enter ship " + counter + " location:");
            if (input.hasNextInt()) {
                xCoord = input.nextInt();
            } else {
                input.nextLine();
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }
            if (input.hasNextInt()) {
                yCoord = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }

            // Check to see if X/Y coordinates out of bounds
            if ((xCoord > 4 || xCoord < 0) || (yCoord > 4 || yCoord < 0)) {
                System.out.println("Invalid coordinates. Choose different coordinates.");
                continue;
            }
            // Check if position already has ship
            if (player2[xCoord][yCoord] == '@') {
                System.out.println("You already have a ship there. Choose different coordinates.");
                continue;
            } else {
                player2[xCoord][yCoord] = '@';
            }

            // Increment counter
            counter++;
        } while (counter < 6); // Required do while loop
        printBattleShip(player2);
        // Scroll past player 2's board
        for (int x = 0; x < 100; x++) {
            System.out.println("");
        }

        // Play game
        // Hit counters
        int player1HitCount = 0;
        int player2HitCount = 0;
        int[] attackCoord = {-1, -1};
        int playerCoordValid;
        int attackResult;

        // Begin game
        while (player1HitCount < 5 && player2HitCount < 5) {
            // Player 1 turn

            // Validate target coordinates
            playerCoordValid = 0;
            while (playerCoordValid == 0) {
                System.out.println("\nPlayer 1, enter hit row/column:");
                attackCoord = checkCoord(input, player2, player1TargetHist);
                if (attackCoord[0] < 0 || attackCoord[1] < 0) {
                    continue;
                }
                playerCoordValid = 1;
            } // Check if attack coords valid, loop back if not

            // Update boards
            attackResult = updateBoards(player2, player1TargetHist, attackCoord);

            // Print result
            if (attackResult == 0) {
                System.out.println("PLAYER 1 MISSED!");
            } else {
                System.out.println("PLAYER 1 HIT PLAYER 2’s SHIP!");
                player1HitCount++;
            }
            printBattleShip(player1TargetHist);
            // Player 2 turn

            // Validate target coordinates
            playerCoordValid = 0;
            while (playerCoordValid == 0) {
                System.out.println("\nPlayer 2, enter hit row/column:");
                attackCoord = checkCoord(input, player1, player2TargetHist);
                if (attackCoord[0] < 0 || attackCoord[1] < 0) {
                    continue;
                }
                playerCoordValid = 1;
            } // Check if attack coords valid, loop back if not

            // Update boards
            attackResult = updateBoards(player1, player2TargetHist, attackCoord);

            // Print result
            if (attackResult == 0) {
                System.out.println("PLAYER 2 MISSED!");
            }
            else {
                System.out.println("PLAYER 2 HIT PLAYER 1’s SHIP!");
                player2HitCount++;
            }
            printBattleShip(player2TargetHist);
        }

        // End game
        if (player1HitCount == 5) {
            System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!\n");
        } else {
            System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!\n");
        }

        System.out.println("Final boards:\n");
        printBattleShip(player1);
        System.out.println("");
        printBattleShip(player2);
    }

    // Use this method to print game boards to the console.
    private static void printBattleShip(char[][] player) {
        System.out.print("  ");
        for (int row = -1; row < 5; row++) {
            if (row > -1) {
                System.out.print(row + " ");
            }
            for (int column = 0; column < 5; column++) {
                if (row == -1) {
                    System.out.print(column + " ");
                } else {
                    System.out.print(player[row][column] + " ");
                }
            }
            System.out.println("");
        }
    }

    // Method to check if attack coordinates are valid
    private static int[] checkCoord(Scanner input, char[][] player, char[][] playerTargetHistory) {
        int xCoord;
        int yCoord;
        int[] coords;
        if (input.hasNextInt()) {
            xCoord = input.nextInt();
        } else {
            input.nextLine();
            System.out.println("Invalid coordinates. Choose different coordinates.");
            coords = new int[]{-1, 0};
            return coords;
        }
        if (input.hasNextInt()) {
            yCoord = input.nextInt();
            input.nextLine();
        } else {
            input.nextLine();
            System.out.println("Invalid coordinates. Choose different coordinates.");
            coords = new int[]{xCoord, -1};
            return coords;
        }

        // Check to see if X/Y coordinates out of bounds
        if ((xCoord > 4 || xCoord < 0) || (yCoord > 4 || yCoord < 0)) {
            System.out.println("Invalid coordinates. Choose different coordinates.");
            coords = new int[]{-2, -2};
            return coords;
        }
        // Check to see if already attacked position previously
        if (playerTargetHistory[xCoord][yCoord] != '-') {
            System.out.println("You already fired on this spot. Choose different coordinates.");
            coords = new int[]{-3, -3};
            return coords;
        }
        coords = new int[]{xCoord, yCoord};
        return coords;
    }

    private static int updateBoards(char[][] player, char[][] playerTargetHist, int[] attackCoord) {
        switch (player[attackCoord[0]][attackCoord[1]]) {
            case '-':
                player[attackCoord[0]][attackCoord[1]] = 'O';
                playerTargetHist[attackCoord[0]][attackCoord[1]] = 'O';
                return 0;
            case '@':
                player[attackCoord[0]][attackCoord[1]] = 'X';
                playerTargetHist[attackCoord[0]][attackCoord[1]] = 'X';
                return 1;
        }
        return -1;
    }

}