package net.botwithus.api.game.hud;

import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.script.Execution;

public final class Hud {
    private Hud() {
    }

    /**
     * Logs out the player.
     *
     * @return true if the logout was successful, false otherwise
     */
    public static boolean logout() {
        Component logout = ComponentQuery.newQuery(1477).option("Logout").results().first();
        if (logout != null) {
            logout.interact("Logout");
            if (Execution.delayUntil(5000, () -> Interfaces.isOpen(1433))) {
                return MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, (1433 << 16) | 71);
            }
        }
        return false;
    }

    /**
     * Opens the player's backpack.
     *
     * @return true if the backpack was opened, false otherwise
     */
    public static boolean openBackpack() {
        Component component = ComponentQuery.newQuery(1431).option("Open Backpack").results().first();
        return component != null && component.interact("Open Backpack");
    }

    /**
     * Opens the Skills tab in the game interface.
     *
     * @return true if the Skills tab was successfully opened, false otherwise
     */
    public static boolean openSkills() {
        Component component = ComponentQuery.newQuery(1431).option("Open Skills").results().first();
        return component != null && component.interact("Open Skills");
    }

    /**
     * Opens the worn equipment interface.
     *
     * @return true if the interface was opened, false otherwise
     */
    public static boolean openWornEquipment() {
        Component component = ComponentQuery.newQuery(1431).option("Open Worn Equipment").results().first();
        return component != null && component.interact("Open Worn Equipment");
    }
}
