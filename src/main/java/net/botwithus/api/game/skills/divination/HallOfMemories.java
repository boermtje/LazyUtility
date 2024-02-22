//package net.botwithus.api.game.skills.divination;
//
//import net.botwithus.rs3.game.Item;
//import net.botwithus.rs3.queries.ResultSet;
//import net.botwithus.rs3.queries.builders.items.ItemQuery;
//import net.botwithus.rs3.types.ItemType;
//import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
//import net.botwithus.rs3.game.vars.VarManager;
//
//import java.util.List;
//
//public final class HallOfMemories {
//    private HallOfMemories() {}
//
//    /**
//     *
//     *   Checks if the memory jar in the specified slot is full.
//     *
//     *   @param slot the slot of the memory jar to check
//     *   @return true if the memory jar is full, false otherwise
//     */
//    public static boolean isMemoryJarFull(int slot) {
//        return ItemQuery.newQuery(93)
//                .slot(slot)
//                .results()
//                .first()
//                .filter(HallOfMemories::isMemoryJarFull)
//                .isPresent();
//    }
//
//    /**
//     *
//     *   Checks if the given number of Memory Jars are full.
//     *
//     *   @param count The number of Memory Jars to check for.
//     *   @return true if the given number of Memory Jars are full, false otherwise.
//     */
//    public static boolean hasFullJars(int count) {
//        ResultSet<Item> results = ItemQuery.newQuery(93).itemId(42899).results();
//        int c = 0;
//        for (Item item : results) {
//            if(isMemoryJarFull(item)) {
//                c++;
//            }
//            if(c == count) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     *   Returns a list of ItemType objects that are jars.
//     *
//     *   @return a list of ItemType objects that are jars
//     */
//    public static List<ItemType> getJars() {
//        return ConfigManager.getItemTypeByCategory(4320);
//    }
//
//    /**
//     *
//     *   Retrieves a list of ItemType objects from the ConfigManager that are categorized as Memory Storage Bots.
//     *
//     *   @return A List of ItemType objects that are categorized as Memory Storage Bots.
//     */
//    public static List<ItemType> getMemoryStorageBots() {
//        return ConfigManager.getItemTypeByCategory(4322);
//    }
//
//    /**
//     *
//     *   Checks if a Memory Bot has been recovered.
//     *
//     *   @param memoryBot the Memory Bot to check
//     *   @return true if the Memory Bot has been recovered, false otherwise
//     */
//    public static boolean isMemoryBotRecovered(MemoryBotData memoryBot) {
//        ResultSet<Item> results = ItemQuery.newQuery(93).category(4322).results();
//        if(results.isEmpty()) {
//            return false;
//        }
//        for (Item result : results) {
//            if(result.getId() == memoryBot.getItemId()) {
//                int fullAmount = result.getType().getParams().getInt(7176);
//                int amount = VarManager.getInventoryVarbit(93, result.getSlot(), memoryBot.getVarbitId());
//                return amount == fullAmount;
//            }
//        }
//        return false;
//    }
//
//    private static boolean isMemoryJarFull(Item memoryJar) {
//        int fullJar = memoryJar.getType().getParams().getInt(7176);
//        int filledAmount = VarManager.getInventoryVarbit(93, memoryJar.getSlot(), 40679);
//        return filledAmount == fullJar;
//    }
//
//    enum MemoryBotData {
//        LUSTROUS(42902, 40680),
//        BRILLIANT(42903, 40681),
//        RADIANT(42904, 40682),
//        LUMINOUS(42905, 40683),
//        INCANDESCENT(42906, 40684);
//
//        private final int varbitId;
//        private final int itemId;
//        MemoryBotData(int itemId, int varbitId) {
//            this.itemId = itemId;
//            this.varbitId = varbitId;
//        }
//
//        public int getItemId() {
//            return itemId;
//        }
//
//        public int getVarbitId() {
//            return varbitId;
//        }
//    }
//}
