package com.company;

import java.util.Scanner;

public class RealPlayer extends Player {
    public RealPlayer(int index) {
        super(index);
    }

    @Override
    public int[] move(char[][] f) {
        int[] coordinates = getCoordinates();
        updateLocalField(field);
        return coordinates;
    }

    private int[] getCoordinates() {
        Scanner in = new Scanner(System.in);
        boolean correctData = false;
        int[] coords = new int[2];
        System.out.println("Enter 2 number from 0 to 3.");
        while (!correctData) {
            coords[0] = in.nextInt() - 1;
            coords[1] = in.nextInt() - 1;
            if ((coords[0] >= 0 && coords[0] < height) || (coords[1] >= 0 && coords[1] < height)) {
                correctData = true;
            }
            else {
                System.out.println("Incorrect input. Please, try again.");
            }
        }
        return coords;
    }

}
