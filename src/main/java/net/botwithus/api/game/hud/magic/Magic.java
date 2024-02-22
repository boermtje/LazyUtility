//package net.botwithus.api.game.hud.magic;
//
//import com.sun.jdi.Value;
//import net.botwithus.rs3.types.EnumType;
//import net.botwithus.rs3.types.StructType;
//import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
//
//import java.util.Objects;
//
//public final class Magic {
//    private Magic() {
//    }
//
//    /**
//     * Retrieves a Spell object from the ConfigManager based on the given spellId.
//     *
//     * @param spellId The id of the spell to retrieve
//     * @return A Spell object with the given spellId
//     * @throws IllegalArgumentException if the given spellId is invalid
//     */
//    public static Spell getSpell(int spellId) {
//        if (spellId < 1) {
//            throw new IllegalArgumentException("Invalid Spell Id");
//        }
//        StructType spellInfo = ConfigManager.getStructType(spellId);
//        return new Spell(spellInfo);
//    }
//
//    /**
//     * Retrieves a {@link Spell} object from the game's configuration files by its name.
//     *
//     * @param name The name of the spell to retrieve
//     * @return The {@link Spell} object associated with the given name
//     * @throws IllegalArgumentException if the given name does not match any spell
//     */
//    public static Spell getSpellByName(String name) {
//        Objects.requireNonNull(name);
//        EnumType spells = ConfigManager.getEnumType(6740);
//        var varValue = spells.outputs().stream().filter(i -> i.getStringValue().equals(name)).findFirst();
//        if (varValue.isEmpty()) {
//            throw new IllegalArgumentException("Unknown spell " + name);
//        }
//
//        StructType spellInfo = ConfigManager.getStructType(varValue.get().getIntegerValue());
//        if (spellInfo.getString(2974).equalsIgnoreCase(name)) {
//            return new Spell(spellInfo);
//        }
//        return null;
//    }
//
//}
