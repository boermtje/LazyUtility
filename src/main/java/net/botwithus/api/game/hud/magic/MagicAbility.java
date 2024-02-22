package net.botwithus.api.game.hud.magic;

import net.botwithus.rs3.game.annotations.Interactable;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;

public interface MagicAbility extends Interactable<ComponentAction> {

    boolean isActive();

    boolean isAutoCasting();

}
