package net.botwithus.api.game.hud.magic;

public enum Runes {
    AIR_RUNE(556, 14909),
    MIND_RUNE(558, 14913),
    WATER_RUNE(555, 14912),
    EARTH_RUNE(557, 14910),
    FIRE_RUNE(554,14911),
    BODY_RUNE(559, 14914),
    COSMIC_RUNE(564, 14918),
    CHAOS_RUNE(562, 14919),
    ASTRAL_RUNE(9075, 14915),
    NATURE_RUNE(561, 14921),
    LAW_RUNE(563, 14922),
    DEATH_RUNE(560, 14916),
    BLOOD_RUNE(565, 14917),
    SOUL_RUNE(566, 14923),
    ARMADYL_RUNE(21773, 14920);
    private final int itemId;
    private final int spriteId;

    Runes(int itemId, int spriteId) {
        this.itemId = itemId;
        this.spriteId = spriteId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getSpriteId() {
        return spriteId;
    }
}
