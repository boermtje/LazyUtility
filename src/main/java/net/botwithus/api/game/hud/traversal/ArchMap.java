package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.minimenu.actions.ObjectAction;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Execution;

import java.util.Locale;

enum Digsite {
    KHARID_ET(0), EVERLIGHT(1), INFERNAL_SOURCE(2), STORMGUARD(3), WARFORGE(4), ORTHEN(5), SENNTISTEN(6), ARCH_GUILD(-1);

    private final int mapId;

    Digsite(int mapId) {
        this.mapId = mapId;
    }

    public int getMapId() {
        return mapId;
    }

    @Override
    public String toString() {
        return this.name().substring(0, 1).toUpperCase(Locale.ROOT) + this.name().substring(1).toLowerCase(Locale.ROOT).replace('_', ' ').replace('$', '-');
    }
}

public class ArchMap {
    public static boolean isOpen() {
        return Interfaces.isOpen(667);
    }

    public static void open() {
        var mapObj = SceneObjectQuery.newQuery().name("Dig sites map").results().nearest();
        var player = Client.getLocalPlayer();
        if (mapObj != null && player != null && player.getCoordinate() != null && mapObj.getCoordinate() != null && player.getCoordinate().getZ() == mapObj.getCoordinate().getZ()) {
            mapObj.interact(ObjectAction.OBJECT1);
        }
    }

    public static void chooseDigsite(Digsite digsite) {
        chooseDigsite(digsite.getMapId());
    }

    public static void chooseDigsite(int mapId) {
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, mapId, 43712523);
        Execution.delay(800);
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 43712535);
    }
}

