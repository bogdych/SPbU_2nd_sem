package com.company;

public class AIPlayerTableLookup extends Player {
    private int[][] preferredMoves = {
            {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2},
            {0, 1}, {1, 0}, {1, 2}, {2, 1}};

    public AIPlayerTableLookup(int index) {
        super(index);
    }

    @Override
    public int[] move(char[][] field) {
        for (int[] move : preferredMoves) {
            if (field[move[0]][move[1]] == '-') {
                return move;
            }
        }
        assert false : "No empty cell?!";
        return null;
    }
}