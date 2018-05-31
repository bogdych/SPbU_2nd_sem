package com.company.players;

public abstract class Player {
    protected char mySymbol;
    protected char oppSymbol;

    protected final static int width = 3;
    protected final static int height = 3;

    public char[][] field = {
            {'-', '-', '-'},
            {'-', '-', '-'},
            {'-', '-', '-'}
    };

    public void setSymbol(int index) {
        if (index % 2 == 0) {
            mySymbol = 'x';
            oppSymbol = 'o';
        }
        else {
            mySymbol = 'o';
            oppSymbol = 'x';
        }
    }

    protected void updateLocalField (char[][] f) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = f[i][j];
            }
        }
    }

    public abstract int[] move(char[][] field);
}
