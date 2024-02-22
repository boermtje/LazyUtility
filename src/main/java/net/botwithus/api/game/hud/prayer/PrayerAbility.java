package net.botwithus.api.game.hud.prayer;

public sealed interface PrayerAbility permits AncientBook, NormalBook {

    boolean isActive();

    boolean interact(int option);

    String getName();
}
