package net.botwithus.api.game.hud.prayer;

import net.botwithus.api.util.StringFormatter;
import net.botwithus.rs3.game.js5.types.StructType;
import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.vars.VarManager;

public enum NormalBook implements PrayerAbility {
    THICK_SKIN(14541, 16739),
    ROCK_SKIN(14542, 16739),
    STEEL_SKIN(14543, 16739),
    BURST_OF_STRENGTH(14545,16740),
    SUPERHUMAN_STRENGTH(14546, 16740),
    ULTIMATE_STRENGTH(14547, 16740),
    CLARITY_OF_THOUGHT(14549, 16741),
    IMPROVED_REFLEXES(14550,16741),
    INCREDIBLE_REFLEXES(14551, 16741),
    SHARP_EYE(14553, 16751),
    HAWK_EYE(14554, 16751),
    EAGLE_EYE(14555, 16751),
    UNSTOPPABLE_FORCE(14557, 16753),
    UNRELENTING_FORCE(14558, 16753),
    OVERPOWERING_FORCE(14559, 16753),
    MYSTIC_WILL(14561,16752),
    MYSTIC_LORE(14562, 16752),
    MYSTIC_MIGHT(14563, 16752),
    CHARGE(14565, 16754),
    SUPER_CHARGE(14566, 16754),
    OVERCHARGE(14567, 16754),
    CHIVALRY(14568, 16756),
    PIETY(14569, 16757),
    AUGURY(14570, 16759),
    RIGOUR(14571, 16760),
    RAPID_RESTORE(14572, 16742),
    RAPID_HEAL(14573, 16743),
    RAPID_RENEWAL(14574, 16758),
    PROTECT_ITEM(14575, 16744),
    PROTECT_FROM_MAGIC(14576, 16745),
    PROTECT_FROM_MISSILES(14577, 16746),
    PROTECT_FROM_MELEE(14578, 16747),
    PROTECT_FROM_SUMMONING(14579, 16755),
    RETRIBUTION(14580, 16748),
    REDEMPTION(14581, 16749),
    SMITE(14582, 16750),
    HAND_OF_JUDGEMENT(48361, 53271),
    HAND_OF_FATE(48362, 53271),
    HAND_OF_DOOM(48363, 53271),
    DECAY(48365, 53272),
    HASTENED_DECAY(48366, 53272),
    ACCELERATED_DECAY(48367, 53272),
    SANCTITY(48368, 53273),
    PROTECT_FROM_NECROMANCY(48369, 53274);
    private final int structId;
    private final int varbitId;

    NormalBook(int structId, int varbitId) {
        this.structId = structId;
        this.varbitId = varbitId;
    }

    public StructType getStruct() {
        return ConfigManager.getStructType(structId);
    }

    public int getVarbitValue() {
        return VarManager.getVarbitValue(varbitId);
    }

    public int getLevel() {
        return (int)getStruct().getParams().getOrDefault(2807, -1);
    }

    /**
     * Checks if the object is active.
     *
     * @return true if the object is active, false otherwise
     */
    @Override
    public boolean isActive() {
        return getVarbitValue() == 1;
    }

    @Override
    public boolean interact(int option) {
        int spriteId = (int)getStruct().getParams().getOrDefault(735, -1);
        var component = ComponentQuery.newQuery(1458).spriteId(spriteId).results().first();
        return component != null && component.interact(option);
    }

    @Override
    public String getName() {
        return StringFormatter.toTitleCase(name());
    }
}