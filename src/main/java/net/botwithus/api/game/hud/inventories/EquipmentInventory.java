package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.script.ScriptConsole;

import java.util.function.Function;

public class EquipmentInventory extends Inventory {
    private static final Function<Integer, Integer> OPTION_MAPPER = i -> switch (i) {
        case 2 -> 3;
        case 3 -> 4;
        case 4 -> 5;
        case 5 -> 6;
        case 6 -> 7;
        case 7 -> 8;
        default -> 1;
    };

    public EquipmentInventory() {
        super(94, 1464, 15, OPTION_MAPPER);
    }

    public boolean interact(Equipment.Slot slot, int option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot.getIndex()).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    public boolean interact(Equipment.Slot slot, String option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot.getIndex()).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    /**
     * Interacts with a slot (as its in Integer from Equipment.Slot) and an integer interact option
     * @param slot   The slot to execute the action on.
     * @param option The option to execute.
     * @return
     */
    @Override
    public boolean interact(int slot, int option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    /**
     * Interacts with an equipment slot (as its Integer from Equipment.Slot), and a string interact option on it.
     * @param slot   The slot to execute the action on.
     * @param option The option to execute the action with.
     * @return
     */
    @Override
    public boolean interact(int slot, String option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][EquipmentInventory#interact(slot, option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

}