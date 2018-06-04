package com.company.players;

public class AIPlayerGreedy extends Player {
    private final static int win = 1;
    private final static int lose = 2;
    private final static int draw = 3;
    private final static int cont = 4;

    @Override
    public int[] move(char[][] field) {
        updateLocalField(field);
        int[] currSt = new int[2];
        int max = Integer.MIN_VALUE, bI = 0, bJ = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j] == '-') {
                    currSt = analyzeNextTurn(i, j, 0);
                    if (currSt[0] - currSt[1] > max) {
                        max = currSt[0] - currSt[1];
                        bI = i;
                        bJ = j;
                    }
                }
            }
        }
        currSt[0] = bI;
        currSt[1] = bJ;
        return currSt;
    }

    private int[] analyzeNextTurn (int x, int y, int deep) {
        field[x][y] = (deep % 2 == 0) ? mySymbol : oppSymbol;
        int check = analyzeField(
                (deep % 2 == 0) ? mySymbol : oppSymbol,
                (deep % 2 == 0) ? oppSymbol : mySymbol
        );
        int[] res = new int[2];
        if (check == win) {
            res[0] = 1;
            res[1] = 0;
            field[x][y] = '-';
            return res;
        }
        else if (check == lose) {
            res[0] = 0;
            res[1] = 1;
            field[x][y] = '-';
            return res;
        }
        else if (check == draw) {
            res[0] = 0;
            res[1] = 0;
            field[x][y] = '-';
            return res;
        }
        else {
            int[] currSt;
            int[] stat = {0, 0};
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (field[i][j] == '-') {
                        currSt = analyzeNextTurn(i, j, deep + 1);
                        stat[0] += currSt[1];
                        stat[1] += currSt[0];
                    }
                }
            }
            field[x][y] = '-';
            return stat;
        }
    }

    private int analyzeField(char m, char op) {
        if (
                m == field[0][0] && m == field[0][1] && m == field[0][2] ||
                m == field[1][0] && m == field[1][1] && m == field[1][2] ||
                m == field[2][0] && m == field[2][1] && m == field[2][2] ||

                m == field[0][0] && m == field[1][0] && m == field[2][0] ||
                m == field[0][1] && m == field[1][1] && m == field[2][1] ||
                m == field[0][2] && m == field[1][2] && m == field[2][2] ||

                m == field[0][0] && m == field[1][1] && m == field[2][2] ||
                m == field[0][2] && m == field[1][1] && m == field[2][0]
                ) {
            return win;
        }
        else if (
                op == field[0][0] && op == field[0][1] && op == field[0][2] ||
                op == field[1][0] && op == field[1][1] && op == field[1][2] ||
                op == field[2][0] && op == field[2][1] && op == field[2][2] ||

                op == field[0][0] && op == field[1][0] && op == field[2][0] ||
                op == field[0][1] && op == field[1][1] && op == field[2][1] ||
                op == field[0][2] && op == field[1][2] && op == field[2][2] ||

                op == field[0][0] && op == field[1][1] && op == field[2][2] ||
                op == field[0][2] && op == field[1][1] && op == field[2][0]
                ) {
            return lose;
        }
        else {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (field[i][j] == '-') {
                        return cont;
                    }
                }
            }
            return draw;
        }
    }
}
