package net.botwithus.api.game.hud.summoning;

import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;

import java.util.List;

public class BeastOfBurden {
    public static ResultSet<Item> getItems() {
        return InventoryItemQuery.newQuery(530).results();
    }

    public static List<Item> getPopulatedItems() {
        return getItems().stream().filter(i -> i.getId() > 0).toList();
    }
}
