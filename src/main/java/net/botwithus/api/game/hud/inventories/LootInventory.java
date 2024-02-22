package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class LootInventory {
    private static final int LOOT_INTERFACE = 1622;
    private static final int LOOT_VARP = 5413;
    private static final int INVENTORY_ID = 773;
    private static final Pattern ALL_PATTERN = Pattern.compile("^(.*)$");
    static final Inventory LOOT_INVENTORY = new Inventory(INVENTORY_ID, LOOT_INTERFACE, 5, i -> i + 1);

    private LootInventory() {
    }

    /**
     * Checks if any items match the given id
     *
     * @param ids the id to check the items against
     * @return true if an item matches the id
     */
    public static boolean contains(int... ids) {
        return LOOT_INVENTORY.contains(ids);
    }

    /**
     * Checks if any items match the given name
     *
     * @param names the name to check the items against
     * @return true if an item matches the name
     */
    public static boolean contains(String... names) {
        return LOOT_INVENTORY.contains(names);
    }

    /**
     * Checks if any items match the given name
     *
     * @param name the name to check the items against
     * @return true if an item matches the name
     */
    public static boolean contains(Pattern name) {
        return LOOT_INVENTORY.contains(name);
    }

    /**
     * Checks if all of the supplied ids match at least one item each
     *
     * @param ids the ids to check the items against
     * @return true if all of the ids have a match
     */
    public static boolean containsAllOf(final int... ids) {
        return LOOT_INVENTORY.containsAllOf(ids);
    }

    /**
     * Checks if all of the supplied names match at least one item each
     *
     * @param names the names to check the items against
     * @return true if all of the names have a match
     */
    public static boolean containsAllOf(final String... names) {
        return LOOT_INVENTORY.containsAllOf(names);
    }

    /**
     * Checks if all of the supplied names match at least one item each
     *
     * @param names the names to check the items against
     * @return true if all of the names have a match
     */
    public static boolean containsAllOf(final Pattern... names) {
        return LOOT_INVENTORY.containsAllOf(names);
    }

    /**
     * Checks if any items don't match the given ids
     *
     * @param ids the ids to check the items against
     * @return true if at least one item doesn't match the ids
     */
//    public static boolean containsAnyExcept(final int... ids) {
//        return LOOT_INVENTORY.containsAnyExcept(ids);
//    }

    /**
     * Checks if any items don't match the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item doesn't match the names
     */
    public static boolean containsAnyExcept(final String... names) {
        return LOOT_INVENTORY.containsAnyExcept(names);
    }

    /**
     * Checks if any items don't match the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item doesn't match the names
     */
    public static boolean containsAnyExcept(final Pattern... names) {
        return LOOT_INVENTORY.containsAnyExcept(names);
    }

    /**
     * Checks if any item matches the given ids
     *
     * @param ids the ids to check the items against
     * @return true if at least one item matches an id
     */
//    public static boolean containsAnyOf(final int... ids) {
//        return LOOT_INVENTORY.containsAnyOf(ids);
//    }

    /**
     * Checks if any item matches the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item matches a name
     */
//    public static boolean containsAnyOf(final String... names) {
//        return LOOT_INVENTORY.containsAnyOf(names);
//    }

    /**
     * Checks if any item matches the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item matches a name
     */
//    public static boolean containsAnyOf(final Pattern... names) {
//        return LOOT_INVENTORY.containsAnyOf(names);
//    }

    /**
     * Checks if all of the items match the given {@link Predicate filter}
     *
     * @param filter the filter to check the items against
     * @return true if all items match the filter
     */
//    public static boolean containsOnly(final Predicate<Item> filter) {
//        return LOOT_INVENTORY.containsOnly(filter);
//    }

    /**
     * Checks if all of the items match the given {@link Predicate filter}s
     *
     * @param filters the filters to check the items against
     * @return true if all items match at least one filter each
     */
//    @SafeVarargs
//    public static boolean containsOnly(final Predicate<Item>... filters) {
//        return LOOT_INVENTORY.containsOnly(filters);
//    }

    /**
     * Checks if all of the items match the given names
     *
     * @param names the filters to check the items against
     * @return true if all items match at least one name each
     */
//    public static boolean containsOnly(final String... names) {
//        return LOOT_INVENTORY.containsOnly(names);
//    }

    /**
     * Checks if all of the items match the given names
     *
     * @param names the filters to check the items against
     * @return true if all items match at least one name each
     */
//    public static boolean containsOnly(final Pattern... names) {
//        return LOOT_INVENTORY.containsOnly(names);
//    }

    /**
     * Gets the total quantity of items
     *
     * @return the total quantity of items
     */
    public static int getQuantity() {
        return LOOT_INVENTORY.getQuantity(ALL_PATTERN);
    }

    /**
     * Gets the total quantity of items matching the ids
     *
     * @param ids the ids to check the items against
     * @return the total quantity of items matching the ids
     */
    public static int getQuantity(final int... ids) {
        return LOOT_INVENTORY.getQuantity(ids);
    }

    /**
     * Gets the total quantity of items matching the names
     *
     * @param names the ids to check the items against
     * @return the total quantity of items matching the names
     */
    public static int getQuantity(final String... names) {
        return LOOT_INVENTORY.getQuantity(names);
    }

    public static boolean close(boolean hotkey) {
        if (!isOpen()) {
            return true;
        }
        final var button = ComponentQuery.newQuery(LOOT_INTERFACE).option("Close").results().first();
        return button != null && button.interact("Close") && Execution.delayUntil(RandomGenerator.nextInt(600, 1800), () -> !LootInventory.isOpen());
    }

    public static Item getItemIn(final int index) {
        return LOOT_INVENTORY.getItems().get(index);
    }

    public static List<Item> getItems() {
        return LOOT_INVENTORY.getItems();
    }

    public static List<Item> getItems(String... names) {
        return getItems().stream().filter(item -> Arrays.stream(names)
                .anyMatch(name -> Objects.equals(name, item.getName()))).toList();
    }

    //TODO: need #getValueOfBit
//    public static boolean isAreaLootEnabled() {
//        return VarManager.getVarPlayerValue(LOOT_VARP).getValueOfBit(1) == 1;
//    }
//
//    public static boolean isEnabled() {
//        return VarManager.getVarPlayerValue(LOOT_VARP).getValueOfBit(0) == 1;
//    }

    public static boolean isOpen() {
        return Interfaces.isOpen(LOOT_INTERFACE);
    }

    public static boolean take(String... names) {
        var items = getItems(names);
        var result = items != null && items.size() > 0 ? getItems(names).get(0) : null;
        return take(result);
    }

    public static boolean take(Item item) {
        if (item == null || !isOpen()) {
            return false;
        }
        int quantity = getQuantity(item.getId());
        var itemComp = ComponentQuery.newQuery(LOOT_INTERFACE).item(item.getId()).results().first();
        return itemComp != null && itemComp.interact(1) && Execution.delayUntil(RandomGenerator.nextInt(1500, 2500), () -> quantity != getQuantity(item.getId()));
//        return LOOT_INVENTORY.interact(item.getName()) && Execution.delayUntil(RandomGenerator.nextInt(1500, 2500), () -> quantity != getQuantity(item.getId()));
    }

    public static boolean lootAll() {
//        var component = ComponentQuery.newQuery(LOOT_INTERFACE).type(Component.Type.TEXT.getOpcode()).text("Loot All", String::contentEquals).results().first();
        var component = ComponentQuery.newQuery(LOOT_INTERFACE).type(0).componentIndex(22).results().first();
        return isOpen() && component != null && component.interact(1) && Execution.delay(RandomGenerator.nextInt(1500, 3000));
//        return MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 106299414) && Execution.delay(RandomGenerator.nextInt(200, 400));
    }

    public static boolean lootCustom() {
        var component = ComponentQuery.newQuery(LOOT_INTERFACE).type(Component.Type.TEXT.getOpcode()).text("Loot Custom", String::contentEquals).results().first();
        if (component != null && isOpen()) {
            int quantity = getQuantity();
            return component.interact("Select") && Execution.delayUntil(RandomGenerator.nextInt(1500, 3000), () -> getQuantity() == quantity);
        }
        return false;
    }
}
