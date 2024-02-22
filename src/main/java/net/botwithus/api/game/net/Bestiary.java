package net.botwithus.api.game.net;

import com.google.gson.*;
import net.botwithus.api.util.collection.PairList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Bestiary {
    private static final String BASE_ADDRESS = "http://services.runescape.com/m=itemdb_rs/bestiary/";
    private static final String BEAST_DATA_ADDRESS = BASE_ADDRESS + "beastData.json?beastid=";
    private static final String BEAST_SEARCH_ADDRESS = BASE_ADDRESS + "beastSearch.json?term=";
    private static final String AREA_BEASTS_ADDRESS = BASE_ADDRESS + "areaBeasts.json?identifier=";
    private static final String SLAYER_CATEGORY_BEASTS_ADDRESS = BASE_ADDRESS + "slayerBeasts.json?identifier=";
    private static final String SLAYER_CATEGORY_NAME_TO_IDS_ADDRESS = BASE_ADDRESS + "slayerCatNames.json";

    private Bestiary() {
    }

    /**
     * Gets the beast with the given id
     */
    public static Beast lookupById(int id) {
        try {
            URL url = URI.create(BEAST_DATA_ADDRESS + id).toURL();
            String response = readUrlContent(url);
            if (!response.isEmpty()) {
                return new Beast(response);
            }
        } catch (IOException | JsonSyntaxException ignored) {
        }
        return null;
    }

    /**
     * Gets a list of all Beast's with the given name
     */
    public static List<Beast> lookupByName(String name) {
        try {
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            var url = URI.create(BEAST_SEARCH_ADDRESS + encodedName).toURL();
            String response = readUrlContent(url);
            List<Integer> ids = new ArrayList<>();
            for (JsonElement element : JsonParser.parseString(response).getAsJsonArray()) {
                if (element.isJsonObject()) {
                    int value = element.getAsJsonObject().get("value").getAsInt();
                    String label = element.getAsJsonObject().get("label").getAsString();
                    if (name.equals(label)) {
                        ids.add(value);
                    }
                }
            }
            List<Beast> beasts = new ArrayList<>(ids.size());
            for (int id : ids) {
                Beast beast = lookupById(id);
                if (beast != null) {
                    beasts.add(beast);
                }
            }
            return beasts;
        } catch (IOException | JsonSyntaxException ignored) {
            return Collections.emptyList();
        }
    }

    /**
     * Gets a list of all beasts in the given area
     *
     * @param area
     * @return
     */
    public static List<Beast> lookupByArea(String area) {
        try {
            String encodedArea = URLEncoder.encode(area, StandardCharsets.UTF_8);
            URL url = URI.create(AREA_BEASTS_ADDRESS + encodedArea).toURL();
            String response = readUrlContent(url);
            List<Integer> ids = new ArrayList<>();
            for (JsonElement element : JsonParser.parseString(response).getAsJsonArray()) {
                if (element.isJsonObject()) {
                    int value = element.getAsJsonObject().get("value").getAsInt();
                    ids.add(value);
                }
            }
            List<Beast> beasts = new ArrayList<>(ids.size());
            for (int id : ids) {
                Beast beast = lookupById(id);
                if (beast != null) {
                    beasts.add(beast);
                }
            }
            return beasts;
        } catch (IOException | JsonSyntaxException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Gets a list of all beasts within the given slayer category
     */
    public static List<Beast> lookupBySlayerCategory(int slayerCategoryId) {
        try {
            URL url = URI.create(SLAYER_CATEGORY_BEASTS_ADDRESS + slayerCategoryId).toURL();
            String response = readUrlContent(url);
            List<Integer> ids = new ArrayList<>();
            for (JsonElement element : JsonParser.parseString(response).getAsJsonArray()) {
                if (element.isJsonObject()) {
                    int value = element.getAsJsonObject().get("value").getAsInt();
                    ids.add(value);
                }
            }
            List<Beast> beasts = new ArrayList<>(ids.size());
            for (int id : ids) {
                Beast beast = lookupById(id);
                if (beast != null) {
                    beasts.add(beast);
                }
            }
            return beasts;
        } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Gets a list of all beasts within the given slayer category
     */
    public static List<Beast> lookupBySlayerCategory(String slayerCategory) {
        try {
            URL url = URI.create(SLAYER_CATEGORY_NAME_TO_IDS_ADDRESS).toURL();
            String response = readUrlContent(url);
            Map<String, Integer> categories = new HashMap<>();
            for (Map.Entry<String, JsonElement> element : JsonParser.parseString(
                    response).getAsJsonObject().entrySet()) {
                categories.put(element.getKey(), element.getValue().getAsInt());
            }
            Integer slayerCategoryId = categories.get(slayerCategory);
            if (slayerCategoryId == null) {
                return null;
            }
            return lookupBySlayerCategory(slayerCategoryId);
        } catch (IOException | JsonSyntaxException e) {
            return Collections.emptyList();
        }
    }

    private static String readUrlContent(URL url) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return content.toString();
    }

    public static class Beast {
        final boolean poisonous;
        private final String name;
        private final int id;
        private final String description;
        private final boolean attackable;
        private final boolean aggressive;
        private final double xp;
        private final int size;
        private final boolean members;
        private final List<String> areas = new ArrayList<>();
        private final PairList<String, Integer> animations = new PairList<>(2);
        private String weakness;
        private int lifepoints;
        private int level;
        private int defence;
        private int attack;
        private int magic;
        private int ranged;
        private int slayerLevel;
        private String slayercat;

        public Beast(String json) {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            this.name = root.get("name").getAsString();
            this.id = root.get("id").getAsInt();
            this.description = root.get("description").getAsString();
            JsonElement current = root.get("weakness");
            if (current != null) {
                this.weakness = current.getAsString();
            }
            this.attackable = root.get("attackable").getAsBoolean();
            this.aggressive = root.get("aggressive").getAsBoolean();
            this.poisonous = root.get("poisonous").getAsBoolean();
            this.xp = Double.parseDouble(root.get("xp").getAsString());
            current = root.get("lifepoints");
            if (current != null) {
                this.lifepoints = current.getAsInt();
            }
            current = root.get("level");
            if (current != null) {
                this.level = current.getAsInt();
            }
            current = root.get("defence");
            if (current != null) {
                this.defence = current.getAsInt();
            }
            current = root.get("attack");
            if (current != null) {
                this.attack = current.getAsInt();
            }
            current = root.get("magic");
            if (current != null) {
                this.magic = current.getAsInt();
            }
            current = root.get("ranged");
            if (current != null) {
                this.ranged = current.getAsInt();
            }
            current = root.get("slayerlevel");
            if (current != null) {
                this.slayerLevel = current.getAsInt();
            }
            this.size = root.get("size").getAsInt();
            this.members = root.get("members").getAsBoolean();
            current = root.get("slayercat");
            if (current != null) {
                this.slayercat = current.getAsString();
            }
            JsonObject animations = root.get("animations").getAsJsonObject();
            animations.entrySet().forEach(
                    animation -> this.animations.add(animation.getKey(), animation.getValue().getAsInt()));
            JsonArray areas = root.get("areas").getAsJsonArray();
            for (JsonElement element : areas) {
                this.areas.add(element.getAsString());
            }
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getWeakness() {
            return weakness;
        }

        public boolean isAttackable() {
            return attackable;
        }

        public boolean isAggressive() {
            return aggressive;
        }

        public boolean isPoisonous() {
            return poisonous;
        }

        public double getXp() {
            return xp;
        }

        public int getLifepoints() {
            return lifepoints;
        }

        public int getCombatLevel() {
            return level;
        }

        public int getDefenceLevel() {
            return defence;
        }

        public int getAttackLevel() {
            return attack;
        }

        public int getMagicLevel() {
            return magic;
        }

        public int getRangedLevel() {
            return ranged;
        }

        public int getSlayerLevel() {
            return slayerLevel;
        }

        public boolean isMembersOnly() {
            return members;
        }

        public String getSlayerCategory() {
            return slayercat;
        }

        public List<String> getLocations() {
            return areas;
        }

        public PairList<String, Integer> getAnimations() {
            return animations;
        }

        @Override
        public String toString() {
            return "Beast{" + "slayercat='" + slayercat + '\'' + ", name='" + name + '\'' + ", id=" + id + ", description='" + description + '\'' + ", weakness='" + weakness + '\'' + ", attackable=" + attackable + ", aggressive=" + aggressive + ", poisonous=" + poisonous + ", xp=" + xp + ", lifepoints=" + lifepoints + ", level=" + level + ", defence=" + defence + ", attack=" + attack + ", magic=" + magic + ", ranged=" + ranged + ", slayerLevel=" + slayerLevel + ", size=" + size + ", members=" + members + '}';
        }
    }
}
