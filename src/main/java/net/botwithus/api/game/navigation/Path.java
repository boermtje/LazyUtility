package net.botwithus.api.game.navigation;

public interface Path {
    State state();

    boolean traverse();

    enum State {
        NOT_STARTED, IN_PROGRESS, STUCK, COMPLETE
    }
}
