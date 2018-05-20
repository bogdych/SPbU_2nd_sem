package com.company;

import java.util.Scanner;

public class Main {
    private final static int RealPlayer = 1;
    private final static int AIPlayerGreedy = 2;
    private final static int AIPlayerTableLookup = 3;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Here's a list of types of players:");
        System.out.println("1. RealPlayer");
        System.out.println("2. Greedy AI");
        System.out.println("3. Table Lookup AI");
        System.out.println("Enter number of type for 1st player");
        int ind1 = getInd(in);
        System.out.println("Enter number of type for 2nd player");
        int ind2 = getInd(in);

        Player[] players = {
                PlayerFactory.getPlayer(ind1, 0),
                PlayerFactory.getPlayer(ind2, 1)
        };
        GameField game = new GameField();
        game.play(players);
    }

    private static int getInd(Scanner in) {
        boolean correctData = false;
        int ind = 1;
        while (!correctData) {
            ind = in.nextInt();
            if (ind >= 0 && ind <= AIPlayerTableLookup) {
                correctData = true;
            }
            else {
                System.out.println("Incorrect input. Please, try again.");
            }
        }
        return ind;
    }
}