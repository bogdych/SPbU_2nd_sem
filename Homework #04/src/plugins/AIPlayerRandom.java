package plugins;

import com.company.players.Player;

import java.util.ArrayList;
import java.util.Random;

public class AIPlayerRandom extends Player {
    @Override
    public int[] move(char[][] field) {
        ArrayList<int[]> freeCells = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j] == '-') {
                    freeCells.add(new int[] {i, j});
                }
            }
        }
        Random rand = new Random();
        return freeCells.get(rand.nextInt(freeCells.size()));
    }
}
