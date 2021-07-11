import java.util.*;
import java.io.*;

public class Battleship {
    public static void main(String[] args) {
        // Initial prompt
        System.out.println("Welcome to Battleship!\n");

        // Setup scanner object to get user input
        Scanner input = new Scanner(System.in); // Create scanner object

        // Setup player boards
        char[][] player1 = new char[][] {
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
        };
        char[][] player2 = new char[][] {
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-'},
        };

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
        // Get player two ship coordinates
        System.out.println("\nPLAYER 2, ENTER YOUR SHIPS’ COORDINATES.");
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
            if (player2[xCoord + 1][yCoord + 1] == '@') {
                System.out.println("You already have a ship there. Choose different coordinates.");
                continue;
            } else {
                player2[xCoord + 1][yCoord + 1] = '@';
            }

            // Increment counter
            counter++;
        } while (counter < 6); // Required do while loop
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

}