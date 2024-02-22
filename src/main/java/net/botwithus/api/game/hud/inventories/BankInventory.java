package net.botwithus.api.game.hud.inventories;

import com.google.common.flogger.FluentLogger;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;

import java.util.function.Function;

public class BankInventory extends Inventory {
    private static final Function<Integer, Integer> OPTION_MAPPER = i -> switch (i) {
        case 2 -> 3;
        case 3 -> 4;
        case 4 -> 5;
        case 5 -> 6;
        case 6 -> 7;
        case 7 -> 8;
        default -> 1;
    };
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    public BankInventory() {
        super(95, 517, 195, OPTION_MAPPER);
    }

    @Override
    public boolean interact(int slot, int option) {
        ResultSet<Item> results = InventoryItemQuery.newQuery(getId()).slots(slot).results();
        Item item = results.first();
        if (item != null) {
            log.atInfo().log("[Inventory#interact(slot, option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            log.atInfo().log("[Inventory#interact(slot, option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }
}
