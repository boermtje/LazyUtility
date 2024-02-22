package net.botwithus.api.game.hud.prayer;

import net.botwithus.api.util.StringFormatter;
import net.botwithus.rs3.game.js5.types.StructType;
import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.vars.VarManager;

public enum AncientBook implements PrayerAbility {
    BERSERKER(14592, 16766),
    DEFLECT_SUMMONING(14593, 16767),
    DEFLECT_MAGIC(14594, 16768),
    DEFLECT_MISSILES(14595, 16769),
    DEFLECT_MELEE(14596, 16770),
    LEECH_ATTACK(14597, 16771),
    LEECH_RANGED(14598, 16772),
    LEECH_RANGE_STRENGTH(14599, 16781),
    LEECH_MAGIC(14600, 16773),
    LEECH_MAGIC_STRENGTH(14601, 16782),
    LEECH_DEFENCE(14602, 16774),
    LEECH_STRENGTH(14603, 16775),
    LEECH_ENERGY(14604, 16776),
    LEECH_ADRENALINE(14605, 16777),
    WRATH(14606, 16778),
    SOUL_SPLIT(14607, 16779),
    TURMOIL(14608, 16780),
    FORTITUDE(32272, 29065),
    TORMENT(14609, 16784),
    LIGHT_FORM(32273, 29066),
    ANGUISH(14610, 16783),
    DARK_FORM(32274, 29067),
    SOUL_LINK(32275, 29068),
    TEAMWORK_PROTECTION(32276, 29069),
    SUPERHEAT_FORM(32278, 29071),
    MALEVOLENCE(35360, 34866),
    DESOLATION(35361, 34867),
    AFFLICTION(35362, 34868),
    PROTECT_ITEM_CURSE(14583, 16761),
    SAP_WARRIOR(14584, 16762),
    SAP_RANGER(14585, 16763),
    SAP_RANGE_STRENGTH(14586, 16786),
    SAP_MAGE(14587, 16764),
    SAP_MAGIC_STRENGTH(14588, 16785),
    SAP_SPIRIT(14589, 16765),
    SAP_DEFENCE(14590, 16788),
    SAP_STRENGTH(14591, 16787),
    CHRONICLE_ATTRACTION(35362, 49330),
    SAP_NECROMANCY_ATTACK(48370, 53275),
    SAP_NECROMANCY_STRENGTH(48371, 53276),
    LEECH_NECROMANCY_ATTACK(48373, 53277),
    LEECH_NECROMANCY_STRENGTH(48374, 53278),
    SORROW(48375, 53279),
    RUINATION(48376, 53280),
    DEFLECT_NECROMANCY(48372, 53281);

    private final int structId;
    private final int varbitId;

    AncientBook(int structId, int varbitId) {
        this.structId = structId;
        this.varbitId = varbitId;
    }

    public int getStructId() {
        return structId;
    }

    public StructType getStruct() {
        return ConfigManager.getStructType(structId);
    }

    public int getVarbitId() {
        return varbitId;
    }

    public int getVarbitValue() {
        return VarManager.getVarbitValue(varbitId);
    }

    public int getLevel() {
        return (int)getStruct().getParams().getOrDefault(2807, -1);
    }

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
