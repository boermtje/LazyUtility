package net.botwithus.api.game.world;

import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.util.RandomGenerator;
import net.botwithus.rs3.game.Area;
import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.Travel;

public class Traverse {
    public static boolean to(Coordinate Coordinate) {
        var player = Client.getLocalPlayer();
        if (player == null)
            return false;
        if (player.distanceTo(Coordinate) > 60) {
            return bresenhamWalkTo(Coordinate, false, RandomGenerator.nextInt(38, 68));
        } else {
            return Travel.walkTo(Coordinate);
        }
    }

    public static boolean to(Area area) {
        return to(area.getRandomCoordinate());
    }

    public static boolean bresenhamWalkTo(Coordinate coordinate, boolean minimap, int stepSize) {
        var player = Client.getLocalPlayer();
        if (player == null) {
            return false;
        } else {
            Coordinate currentCoordinate = player.getCoordinate();
            int dx = coordinate.getX() - currentCoordinate.getX();
            int dy = coordinate.getY() - currentCoordinate.getY();
            int distance = (int)Math.hypot(dx, dy);
            if (distance > stepSize) {
                int stepX = currentCoordinate.getX() + dx * stepSize / distance;
                int stepY = currentCoordinate.getY() + dy * stepSize / distance;
                return Travel.walkTo(stepX, stepY, minimap);
            } else {
                return Travel.walkTo(coordinate);
            }
        }
    }
}
