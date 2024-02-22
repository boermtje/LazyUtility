package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.script.ScriptConsole;

import java.util.function.Function;
import java.util.regex.Pattern;

public class BackpackInventory extends Inventory {
    private static final Function<Integer, Integer> OPTION_MAPPER = i -> switch (i) {
        case 2 -> 3;
        case 3 -> 4;
        case 4 -> 5;
        case 5 -> 6;
        case 6 -> 7;
        case 7 -> 8;
        default -> 1;
    };

    public BackpackInventory() {
        super(93, 1473, 5, OPTION_MAPPER);
    }

    @Override
    public boolean interact(String name, int option) {
        ScriptConsole.println("[xApi][BackpackInventory#interact(String name, int option)]: Searching for: " + name);
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).name(name).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][BackpackInventory#interact(String name, int option)]: Found item: " + item.getName());
            ScriptConsole.println("[xApi][BackpackInventory#interact(String name, int option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][BackpackInventory#interact(String name, int option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    @Override
    public boolean interact(Pattern name, int option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).name(name).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][BackpackInventory#interact(Pattern name, int option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][BackpackInventory#interact(Pattern name, int option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    @Override
    public boolean interact(String name, String option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).name(name).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][BackpackInventory#interact(String name, String option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][BackpackInventory#interact(String name, String option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    @Override
    public boolean interact(int slot, int option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][BackpackInventory#interact(int slot, int option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][BackpackInventory#interact(int slot, int option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    @Override
    public boolean interact(int slot, String option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot).results();
        Item item = results.first();
        if (item != null) {
            ScriptConsole.println("[xApi][BackpackInventory#interact(int slot, String option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            ScriptConsole.println("[xApi][BackpackInventory#interact(int slot, String option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

}