//package net.botwithus.api.game.hud.magic;
//
//import net.botwithus.rs3.menu.Interactable;
//import net.botwithus.rs3.menu.types.ComponentAction;
//import net.botwithus.rs3.types.StructType;
//import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
//import net.botwithus.rs3.types.enums.EnumType;
//import net.botwithus.rs3.types.enums.Value;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public final class Spell implements Interactable<ComponentAction> {
//
//    private final StructType struct;
//
//    public Spell(StructType struct) {
//        this.struct = struct;
//    }
//
//    /**
//     * Gets the required level of the item.
//     *
//     * @return The required level of the item.
//     */
//    public int getRequiredLevel() {
//        return this.struct.getInt(2807);
//    }
//
//    /**
//     * Returns the name of the object.
//     *
//     * @return The name of the object.
//     */
//    public String getName() {
//        return this.struct.getString(2974);
//    }
//
//    public String getCategory() {
//        EnumType cats = ConfigManager.getEnumType(6744);
//        Value value = cats.variants.get(struct.getInt(2873));
//        if (value != null) {
//            return value.getStringValue();
//        }
//        return "";
//    }
//
//    /**
//     * Gets the required runes for this spell.
//     *
//     * @return a list of {@link RuneRequirement}s for this spell.
//     */
//    public List<RuneRequirement> getRequiredRunes() {
//        int hasReq = struct.getInt(2806);
//        if (hasReq != 4 && hasReq != 27) {
//            return Collections.emptyList();
//        }
//
//        List<RuneRequirement> runes = new ArrayList<>();
//        if (struct.contains(2898)) {
//            runes.add(new RuneRequirement(Runes.AIR_RUNE, struct.getInt(2898)));
//        }
//        if (struct.contains(2902)) {
//            runes.add(new RuneRequirement(Runes.MIND_RUNE, struct.getInt(2902)));
//        }
//        if (struct.contains(2900)) {
//            runes.add(new RuneRequirement(Runes.WATER_RUNE, struct.getInt(2900)));
//        }
//        if (struct.contains(2899)) {
//            runes.add(new RuneRequirement(Runes.EARTH_RUNE, struct.getInt(2899)));
//        }
//        if (struct.contains(2901)) {
//            runes.add(new RuneRequirement(Runes.FIRE_RUNE, struct.getInt(2901)));
//        }
//        if (struct.contains(2903)) {
//            runes.add(new RuneRequirement(Runes.BODY_RUNE, struct.getInt(2903)));
//        }
//        if (struct.contains(2910)) {
//            runes.add(new RuneRequirement(Runes.COSMIC_RUNE, struct.getInt(2910)));
//        }
//        if (struct.contains(2904)) {
//            runes.add(new RuneRequirement(Runes.CHAOS_RUNE, struct.getInt(2904)));
//        }
//        if (struct.contains(2908)) {
//            runes.add(new RuneRequirement(Runes.ASTRAL_RUNE, struct.getInt(2908)));
//        }
//        if (struct.contains(2909)) {
//            runes.add(new RuneRequirement(Runes.NATURE_RUNE, struct.getInt(2909)));
//        }
//        if (struct.contains(2911)) {
//            runes.add(new RuneRequirement(Runes.LAW_RUNE, struct.getInt(2911)));
//        }
//        if (struct.contains(2905)) {
//            runes.add(new RuneRequirement(Runes.DEATH_RUNE, struct.getInt(2905)));
//        }
//        if (struct.contains(2906)) {
//            runes.add(new RuneRequirement(Runes.BLOOD_RUNE, struct.getInt(2906)));
//        }
//        if (struct.contains(2907)) {
//            runes.add(new RuneRequirement(Runes.SOUL_RUNE, struct.getInt(2907)));
//        }
//        if (struct.contains(2912)) {
//            runes.add(new RuneRequirement(Runes.ARMADYL_RUNE, struct.getInt(2912)));
//        }
//        return runes;
//    }
//
//    @Override
//    public boolean interact(ComponentAction type) {
//
//
//        return false;
//    }
//
//    @Override
//    public boolean interact(int option) {
//        return false;
//    }
//
//
//    public static final class RuneRequirement {
//        private final Runes rune;
//        private final int amount;
//
//        public RuneRequirement(Runes rune, int amount) {
//            this.rune = rune;
//            this.amount = amount;
//        }
//
//        public Runes getRune() {
//            return rune;
//        }
//
//        public int getAmount() {
//            return amount;
//        }
//    }
//}
