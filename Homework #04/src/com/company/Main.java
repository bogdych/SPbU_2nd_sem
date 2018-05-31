package com.company;

import com.company.players.Player;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.util.Scanner;

public class Main {
    private final static int RealPlayer = 1;
    private final static int AIPlayerGreedy = 2;
    private final static int AIPlayerTableLookup = 3;
    private final static int AIPlayerRandom = 4;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MutablePicoContainer container = new DefaultPicoContainer();
        container.addComponent(GameField.class);

        System.out.println("Here's a list of types of players:");
        System.out.println("1. RealPlayer");
        System.out.println("2. Greedy AI");
        System.out.println("3. Table Lookup AI");
        System.out.println("4. Random AI");
        System.out.println("Enter number of type for 1st player");
        int ind1 = getInd(in);
        System.out.println("Enter number of type for 2nd player");
        int ind2 = getInd(in);

        Player[] players = {
                PlayerFactory.getPlayer(container, ind2, 1),
                PlayerFactory.getPlayer(container, ind1, 0)
        };

        GameField game = container.getComponent(GameField.class);
        game.play(players);
    }

    private static int getInd(Scanner in) {
        boolean correctData = false;
        int ind = 1;
        while (!correctData) {
            ind = in.nextInt();
            if (ind >= 0 && ind <= AIPlayerRandom) {
                correctData = true;
            }
            else {
                System.out.println("Incorrect input. Please, try again.");
            }
        }
        return ind;
    }
}