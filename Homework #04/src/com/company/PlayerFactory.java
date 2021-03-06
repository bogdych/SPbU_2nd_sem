package com.company;

import com.company.players.AIPlayerGreedy;
import com.company.players.AIPlayerTableLookup;
import com.company.players.Player;
import com.company.players.RealPlayer;
import org.picocontainer.MutablePicoContainer;

import java.util.ServiceLoader;

public class PlayerFactory {
    private final static int RealPlayer = 1;
    private final static int AIPlayerGreedy = 2;
    private final static int AIPlayerTableLookup = 3;
    private final static int AIPlayerRandom = 4;

    public static Player getPlayer(MutablePicoContainer container, int typeOfPlayer, int index) {
        if (typeOfPlayer == RealPlayer) {
            container.addComponent(RealPlayer.class);
            Player player = container.getComponent(RealPlayer.class);
            player.setSymbol(index);
            return player;
        }
        else if (typeOfPlayer == AIPlayerGreedy) {
            Player player = new AIPlayerGreedy();
            player.setSymbol(index);
            return player;
        }
        else if (typeOfPlayer == AIPlayerTableLookup) {
            Player player = new AIPlayerTableLookup();
            player.setSymbol(index);
            return player;
        }
        else if (typeOfPlayer == AIPlayerRandom) {
            Player player = ServiceLoader.load(Player.class).findFirst().get();
            player.setSymbol(index);
            return player;
        }
        else return null;
    }
}
