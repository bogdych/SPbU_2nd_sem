package com.company;

public class GameField {
    public char[][] field = {
            {'-', '-', '-'},
            {'-', '-', '-'},
            {'-', '-', '-'}
    };

    private final static int width = 3;
    private final static int height = 3;

    public void play(Player[] players) {
        int[] currMove;
        int turn = 0;
        boolean endCondition = false;

        drawField(field);

        while (!endCondition) {
            System.out.print("\n");
            System.out.print((turn % 2 == 0) ? "1st" : "2nd");
            System.out.println(" player's turn:");
            currMove = players[turn % 2].move(field);
            field[currMove[0]][currMove[1]] = (turn % 2 == 0) ? 'x' : 'o';
            drawField(field);
            endCondition = analyzeField(field);
            turn++;
        }
    }

    public void drawField(char[][] field) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(field[i][j]);
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public boolean analyzeField(char[][] f) {
        if (
                'x' == f[0][0] && 'x' == f[0][1] && 'x' == f[0][2] ||
                'x' == f[1][0] && 'x' == f[1][1] && 'x' == f[1][2] ||
                'x' == f[2][0] && 'x' == f[2][1] && 'x' == f[2][2] ||

                'x' == f[0][0] && 'x' == f[1][0] && 'x' == f[2][0] ||
                'x' == f[0][1] && 'x' == f[1][1] && 'x' == f[2][1] ||
                'x' == f[0][2] && 'x' == f[1][2] && 'x' == f[2][2] ||

                'x' == f[0][0] && 'x' == f[1][1] && 'x' == f[2][2] ||
                'x' == f[0][2] && 'x' == f[1][1] && 'x' == f[2][0]
                ) {
            System.out.println("1st player wins!");
            return true;
        }
        else if (
                'o' == f[0][0] && 'o' == f[0][1] && 'o' == f[0][2] ||
                'o' == f[1][0] && 'o' == f[1][1] && 'o' == f[1][2] ||
                'o' == f[2][0] && 'o' == f[2][1] && 'o' == f[2][2] ||

                'o' == f[0][0] && 'o' == f[1][0] && 'o' == f[2][0] ||
                'o' == f[0][1] && 'o' == f[1][1] && 'o' == f[2][1] ||
                'o' == f[0][2] && 'o' == f[1][2] && 'o' == f[2][2] ||

                'o' == f[0][0] && 'o' == f[1][1] && 'o' == f[2][2] ||
                'o' == f[0][2] && 'o' == f[1][1] && 'o' == f[2][0]
                ) {
            System.out.println("2nd player wins!");
            return true;
        }
        else {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (f[i][j] == '-') {
                        return false;
                    }
                }
            }
            System.out.println("Draw!");
            return true;
        }
    }
}
