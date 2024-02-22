package net.botwithus.api.game.navigation;

import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;

public interface PathBuilder {
    @Nullable
    default Path build(Coordinate destination, PathOption... options) {
        return build(LocalPlayer.LOCAL_PLAYER.getCoordinate(), destination, options);
    }

    @Nullable
    Path build(Coordinate start, Coordinate destination, PathOption... options);
}
