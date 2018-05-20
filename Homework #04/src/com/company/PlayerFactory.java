package com.company;

public class PlayerFactory {
    private final static int RealPlayer = 1;
    private final static int AIPlayerGreedy = 2;
    private final static int AIPlayerTableLookup = 3;

    public static Player getPlayer(int typeOfPlayer, int index) {
        if (typeOfPlayer == RealPlayer) {
            return new RealPlayer(index);
        }
        else if (typeOfPlayer == AIPlayerGreedy) {
            return new AIPlayerGreedy(index);
        }
        else /*if (typeOfPlayer == 3)*/ {
            return new AIPlayerTableLookup(index);
        }
    }
}
