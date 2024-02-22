package net.botwithus.api.game.hud.inventories;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.Items;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.js5.types.InventoryType;
import net.botwithus.rs3.game.js5.types.ItemType;
import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.util.Regex;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Inventory implements Iterable<Item> {
    private final InventoryType type;
    private final int id;

    protected final int interfaceIndex;

    protected final int componentIndex;

    private final Function<Integer, Integer> optionMapper;
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    public Inventory(int id, int interfaceIndex, int componentIndex, Function<Integer, Integer> optionMapper) {
        this.id = id;
        this.interfaceIndex = interfaceIndex;
        this.componentIndex = componentIndex;
        this.type = ConfigManager.getInventoryType(id);
        this.optionMapper = optionMapper;
    }

    public Item getSlot(int slot) {
        return InventoryItemQuery.newQuery(id).slots(slot).results().first();
    }

    public Item getItem(String name) {
        return InventoryItemQuery.newQuery(id).name(name).results().first();
    }

    public Item getItem(Pattern pattern) {
        return InventoryItemQuery.newQuery(id).name(pattern).results().first();
    }

    /**
     * Checks if the inventory is full.
     *
     * @return true if the inventory is full, false otherwise
     */
    public boolean isFull() {
        // Server does not send inventory items on login when empty, which is why this check is necessary.
        ResultSet<Item> results = InventoryItemQuery.newQuery(id).results();
        if (results.isEmpty()) {
            return false;
        }
        return InventoryItemQuery.newQuery(id).ids(-1).results().isEmpty();
    }

    /**
     * Checks if the inventory is empty.
     *
     * @return true if the inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        // Server does not send inventory items on login when empty, which is why this check is necessary.
        ResultSet<Item> results = InventoryItemQuery.newQuery(id).results();
        if (results.isEmpty()) {
            return true;
        }
        return results.stream().allMatch(i -> i.getId() == -1);
    }

    /**
     * Counts free slots in inventory
     *
     * @return the number of free slots
     */
    public int countFreeSlots() {
        ResultSet<Item> results = InventoryItemQuery.newQuery(id).results();
        if (results.isEmpty()) {
            return 28;
        }
        return (int) results.stream().filter(i -> i.getId() == -1).count();
    }

    public boolean contains(String... names) {
        return !InventoryItemQuery.newQuery(id).name(names).results().isEmpty();
    }

    public boolean contains(int... ids) {
        return !InventoryItemQuery.newQuery(id).ids(ids).results().isEmpty();
    }

    public boolean contains(Pattern itemNamePattern) {
        return !InventoryItemQuery.newQuery(id).name(itemNamePattern).results().isEmpty();
    }

    public boolean containsAllOf(String... names) {
        var items = InventoryItemQuery.newQuery(id).name(names).results();
        var itemsSet = new HashSet<>(items.stream().map(Item::getName).distinct().toList());
        return !Arrays.stream(names).map(itemsSet::contains).toList().contains(false);
    }

    // TODO: Need to fix
    public boolean containsAllOf(int... ids) {
        var items = InventoryItemQuery.newQuery(id).ids(ids).results();
        var itemsSet = items.stream().map(Item::getId).collect(Collectors.toUnmodifiableSet());
//        return !Arrays.stream(ids).map(i -> itemsSet.contains(i)).toList().contains(false);
        return false;
    }

    public boolean containsAllOf(Pattern... patterns) {
        var items = InventoryItemQuery.newQuery(id).name(patterns).results();
        var itemsList = items.stream().map(Item::getName).distinct().collect(Collectors.toSet());
        return !itemsList.stream().map(i -> !Arrays.stream(patterns).map(j -> j.matcher(i)).toList().contains(false)).toList().contains(false);
    }

    public boolean containsAnyExcept(String... names) {
        var items = getItems();
        var nameSet = new HashSet<>(Arrays.asList(names));

        for (Item item : items) {
            if (!nameSet.contains(item.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean containsAnyExcept(Pattern... patterns) {
        return !getItems().stream().map(Item::getName).distinct().filter(i -> !Arrays.stream(patterns).map(p -> p.matcher(i).matches()).toList().contains(true)).toList().isEmpty();
    }

    public boolean containsItemByCategory(int... categoryIds) {
        return Arrays.stream(categoryIds).anyMatch(i -> !InventoryItemQuery.newQuery(id).category(i).results().isEmpty());
    }

    public int getCount() {
        return InventoryItemQuery.newQuery(id).results().size();
    }

    public int getCount(String... names) {
        return InventoryItemQuery.newQuery(id).name(names).results().size();
    }

    public int getCount(int... ids) {
        return InventoryItemQuery.newQuery(id).ids(ids).results().size();
    }

    public int getCount(Pattern pattern) {
        //TODO this is confusingly named
        return InventoryItemQuery.newQuery(id).name(pattern).results().size();
    }

    public int getQuantity(String... names) {
        //TODO not just first, but all that are in the inventory
        var item = InventoryItemQuery.newQuery(id).name(names).results().first();
        return item != null ? item.getStackSize() : -1;
    }

    public int getQuantity(int... ids) {
        var item = InventoryItemQuery.newQuery(id).ids(ids).results().first();
        return item != null ? item.getStackSize() : -1;
    }

    public int getQuantity(Pattern itemNamePattern) {
        var item = InventoryItemQuery.newQuery(id).name(itemNamePattern).results().first();
        return item != null ? item.getStackSize() : -1;
    }

    /**
     * Performs an action on an item in the inventory.
     *
     * @param slot   The slot of the item in the inventory.
     * @param option The option to perform on the item.
     * @return True if the action was successful, false otherwise.
     */
    public boolean interact(int slot, Pattern option) {
        Item item = InventoryItemQuery.newQuery(id).slots(slot).results().first();
        if (item == null) {
            return false;
        }
        ItemType type = item.getConfigType();
        if (type == null) {
            return false;
        }
        List<String> options;
        if (id == 94) {
            //TODO check if the indices need to be tweaked
            options = Items.getWornEquipmentOptions(type);
        } else if (id == 95) {
            //TODO these use indices 8 and 9 I believe
            options = Items.getBankOptions(type);
        } else {
            options = item.getConfigType().getBackpackOptions();
        }
        for (int i = 0; i < options.size(); i++) {
            String s = options.get(i);
            if (s != null && option.matcher(s).matches()) {
                return interact(slot, i);
            }
        }
        return false;
    }

    /**
     * Executes an action on the specified slot with the given option.
     *
     * @param slot   The slot to execute the action on.
     * @param option The option to execute the action with.
     * @return True if the action was successful, false otherwise.
     */
    public boolean interact(int slot, String option) {
        return interact(slot, Regex.getPatternForExactString(option));
    }

    /**
     * Executes an action on the item in the specified slot.
     *
     * @param slot   The slot to execute the action on.
     * @param option The option to execute.
     * @return True if the action was successful, false otherwise.
     */
    public boolean interact(int slot, int option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(id).slots(slot).results();
        Item item = results.first();
        if (item != null) {
//            log.atInfo().log("[Inventory#interact(slot="+slot+", option="+option+")]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(item.getId()).componentIndex(componentIndex).withOptionMapper(optionMapper).results();
//            log.atInfo().log("[Inventory#interact(slot="+slot+", option="+option+")]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }

    /**
     * Executes an action with the given name.
     *
     * @param name The name of the action to execute.
     * @return true if the action was executed successfully, false otherwise.
     */
    public boolean interact(String name) {
        return interact(name, "", String::contentEquals, (s, c) -> true);
    }

    /**
     * Executes an action on an item in the inventory.
     *
     * @param name   The name of the item to perform the action on.
     * @param option The option to perform the action with.
     * @return True if the action was successful, false otherwise.
     */
    public boolean interact(String name, int option) {
        Item item = InventoryItemQuery.newQuery(id).name(name).results().first();
        return item != null && interact(item.getSlot(), option);
    }

    /**
     * Performs an action based on the given name and option.
     *
     * @param name   The name of the action to perform.
     * @param option The option to use for the action.
     * @return True if the action was successful, false otherwise.
     */
    public boolean interact(String name, String option) {
        return interact(name, option, String::contentEquals, String::contentEquals);
    }

    public boolean interact(Pattern namePattern, String option) {
        Item item = InventoryItemQuery.newQuery(id).name(namePattern).results().first();

        return item != null && interact(item.getSlot(), option);
    }

    public boolean interact(Pattern namePattern, int option) {
        Item item = InventoryItemQuery.newQuery(id).name(namePattern).results().first();

        return item != null && interact(item.getSlot(), option);
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (Item result : InventoryItemQuery.newQuery(id).results()) {
//            log.atInfo().log("[Inventory#getItems] Processing item:");
//            log.atInfo().log("[Inventory#getItems]     " + this.id);
//            log.atInfo().log("[Inventory#getItems]     " + result.getSlot());
//            log.atInfo().log("[Inventory#getItems]     " + result.getId());
//            log.atInfo().log("[Inventory#getItems]     " + result.getStackSize());
            if (result.getId() > -1) {
//                items.add(ItemPool.createItem(this.id, result.getSlot(), result.getId(), result.getStackSize()));
                items.add(result);
            }
        }
        return items;
    }

    public List<Item> getItemsWithOptions(String option) {
        var idsWithOption = ComponentQuery.newQuery(interfaceIndex).option(option).componentIndex(componentIndex).results().stream().mapToInt(Component::getItemId);
        return InventoryItemQuery.newQuery(id).ids(idsWithOption.toArray()).results().stream().toList();
    }

    /**
     * Executes an action on an item.
     *
     * @param name       The name of the item to perform the action on.
     * @param option     The option of the item to perform the action on.
     * @param namepred   The predicate to use to compare the item name.
     * @param optionpred The predicate to use to compare the item option.
     * @return true if the action was successful, false otherwise.
     */
    public boolean interact(String name, String option, BiFunction<String, CharSequence, Boolean> namepred, BiFunction<String, CharSequence, Boolean> optionpred) {
        Item item = InventoryItemQuery.newQuery(id).name(name, namepred).results().first();
        if (item != null) {
            List<String> options;
            if (id == 94) {
                options = Items.getWornEquipmentOptions(item.getConfigType());
            } else {
                options = Items.getBankOptions(item.getConfigType());
            }
            for (int j = 0; j < options.size(); j++) {
                String s = options.get(j);
                if (optionpred.apply(s, option)) {
                    int optionIndex = j;
                    try {
                        var result = ComponentQuery.newQuery(interfaceIndex).item(item.getId()).componentIndex(componentIndex).results().first();
                        return result != null && result.interact(optionMapper.apply(optionIndex));
                    } catch (Exception e) {
                        log.atSevere().withCause(e).log("ComponentQuery Exception for { interfaceIndex: " + interfaceIndex + " }, { itemId: " + item.getId() + " }, { componentIndex: " + componentIndex + " }");
                        return false;
                    }

                }
            }
        }
        return false;
    }

    /**
     * Returns the id of the inventory.
     *
     * @return the id of the inventory
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the value of a varbit in the inventory.
     *
     * @param slot     The slot of the inventory.
     * @param varbitId The varbit id.
     * @return The value of the varbit.
     */
    public int getVarbitValue(int slot, int varbitId) {
        var item = InventoryItemQuery.newQuery(id).slots(slot).results().first();
        return item != null ? item.getVarbitValue(varbitId) : Integer.MIN_VALUE;
    }

    @Override
    public Iterator<Item> iterator() {
        return getItems().iterator();
    }

}
