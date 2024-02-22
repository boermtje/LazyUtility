package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.scene.entities.characters.player.Player;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.minimenu.actions.ObjectAction;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;

public class AnachroniaTeleMap {
    public static boolean isOpen() {
        return Interfaces.isOpen(71);
    }

    public static void open() {
        var mapObj = SceneObjectQuery.newQuery().name("Orthen teleportation device").results().nearest();
        Player player = Client.getLocalPlayer();
        if (mapObj != null && player != null && player.getCoordinate() != null && mapObj.getCoordinate() != null &&
                player.getCoordinate().getZ() == mapObj.getCoordinate().getZ()) {
            mapObj.interact(ObjectAction.OBJECT1);
        }
    }

    public static void chooseDigsite(int index) {
         MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, index, 4653065);
        Execution.delay(RandomGenerator.nextInt(2000, 4000));
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 4653084);
    }
}
