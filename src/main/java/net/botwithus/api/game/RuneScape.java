package net.botwithus.api.game;

import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.script.Execution;

public class RuneScape {
    public static boolean isLoggedIn() {
        return Client.getGameState() == Client.GameState.LOGGED_IN && Client.getLocalPlayer() != null;
    }

    public static boolean exitToLobby() {
        return MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, 7, 93782016) &&
                Execution.delay(600) &&
                MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 93913156);
    }
}
