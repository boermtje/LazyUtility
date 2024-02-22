package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;

public enum MagicCarpet {
    AL_KHARID(1928, 28),
    NORTH_POLLNIVNEACH(1928, 60),
    SOUTH_POLLNIVNEACH(1928, 68),
    NARDAH(1928, 68),
    BEDABIN_CAMP(1928, 44),
    UZER(1928, 52),
    MENAPHOS(1928, 84),
    SOPHANEM(1928, 92);

    private final int interfaceIndex;
    private final int componentIndex;

    MagicCarpet(int interfaceIndex, int componentIndex) {
        this.interfaceIndex = interfaceIndex;
        this.componentIndex = componentIndex;
    }

    public int getId() {
        return interfaceIndex << 16 | componentIndex;
    }

    //TODO: Update to no longer use MiniMenu.doAction
    public boolean teleport() {
        if (!MagicCarpetNetwork.isOpen()) {
            MagicCarpetNetwork.open();
            Execution.delay(RandomGenerator.nextInt(600, 900));
//            Time.waitUntil(MagicCarpetNetwork::isOpen, RandomGenerator.nextInt(600, 1200), RandomGenerator.nextInt(300, 600));
        }
        if (MagicCarpetNetwork.isOpen()) {
            return MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, getId());
        }
        return false;
    }
}

