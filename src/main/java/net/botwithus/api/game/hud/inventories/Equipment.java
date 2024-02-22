package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.util.Regex;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public final class Equipment {

    public static final Inventory EQUIPMENT = new EquipmentInventory();

    private Equipment() {
    }

    /**
     * Gets the item in the specified slot.
     *
     * @param slot The slot to get the item from.
     * @return An {@link Optional} containing the item in the slot, or an empty {@link Optional} if there is no item in the slot.
     */
    public static Item getItemIn(Slot slot) {
        return EQUIPMENT.getSlot(slot.getIndex());
    }

    /**
     * Checks if the given item name is present in the inventory.
     *
     * @param name The name of the item to check for.
     * @return true if the item is present in the inventory, false otherwise.
     */
    public static boolean contains(String name) {
        return !InventoryItemQuery.newQuery(EQUIPMENT.getId()).name(name).results().isEmpty();
    }

    /**
     * Checks if the inventory contains an item with a name matching the given pattern.
     *
     * @param pattern The pattern to match the item name against.
     * @return True if an item with a matching name is present in the inventory, false otherwise.
     */
    public static boolean contains(Pattern pattern) {
        return !InventoryItemQuery.newQuery(EQUIPMENT.getId()).name(pattern).results().isEmpty();
    }

    /**
     * Interacts with the item in the given slot.
     *
     * @param slot   The slot to interact with.
     * @param option The option to interact with.
     * @return True if the interaction was successful, false otherwise.
     */
    public static boolean interact(Slot slot, String option) {
        return EQUIPMENT.interact(slot.getIndex(), option);
    }

    public static boolean interact(Slot slot, Pattern option) {
        return EQUIPMENT.interact(slot.getIndex(), option);
    }

    /**
     * Removes an item from the specified slot.
     *
     * @param slot The slot to unequip from.
     * @return true if the item was successfully unequipped, false otherwise.
     */
    public static boolean unequip(Slot slot) {
        return interact(slot, "Remove");
    }

    public static boolean equip(Slot slot) {
        return interact(slot, Regex.getPatternForExactStrings("Wear", "Wield", "Equip"));
    }

    /**
     * Gets the value of a varbit in the inventory.
     *
     * @param slot     The inventory slot to check.
     * @param varbitId The varbit ID to check.
     * @return The value of the varbit, or -1 if the varbit is not present.
     */
    public static int getVarbitValue(int slot, int varbitId) {
        var item = InventoryItemQuery.newQuery(EQUIPMENT.getId()).slots(slot).results().first();
        return item != null ? item.getVarbitValue(varbitId) :-1;
    }

    /**
     * Represents a slot in the player's equipment.
     */
    public enum Slot {
        HEAD(0, 24431), CAPE(1, 24432), NECK(2, 24433), WEAPON(3, 24434), BODY(4, 24436), SHIELD(5, 24437), LEGS(7, 24438), HANDS(9, 24439), FEET(10, 24440), RING(12, 24435), AMMUNITION(13, 24441), AURA(14, 24442), POCKET(17, 24443);

        private final int index;

        Slot(int index, int textureId) {
            this.index = index;
        }

        public static Slot resolve(int index) {
            return Arrays.stream(values()).filter((slot) -> slot.index == index).findAny().orElse(null);
        }

        public final int getIndex() {
            return this.index;
        }

        @Override
        public String toString() {
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }
}
