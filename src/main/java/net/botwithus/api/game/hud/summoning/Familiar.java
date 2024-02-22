package net.botwithus.api.game.hud.summoning;

import net.botwithus.api.game.hud.Dialog;
import net.botwithus.rs3.game.annotations.Onymous;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.script.Execution;

import java.util.ArrayList;
import java.util.List;

public enum Familiar implements Onymous {
    SPIRIT_WOLF("Spirit Wolf", 1, 26, 150, 1, 6, 3, Summoning.Scroll.HOWL, 0, Summoning.Pouch.SPIRIT_WOLF, Summoning.Charm.GOLD, Summoning.Ingredient.WOLF_BONES), DREADFOWL("Dreadfowl", 1, 26, 160, 4, 4, 3, Summoning.Scroll.DREADFOWL_STRIKE, 0, Summoning.Pouch.DREADFOWL, Summoning.Charm.GOLD, Summoning.Ingredient.RAW_CHICKEN), SPIRIT_SPIDER("Spirit spider", 2, 25, 180, 10, 15, 6, Summoning.Scroll.EGG_SPAWN, 0, Summoning.Pouch.SPIRIT_SPIDER, Summoning.Charm.GOLD, Summoning.Ingredient.SPIDER_CARCASS), THORNY_SNAIL("Thorny snail", 2, 26, 280, 13, 16, 3, Summoning.Scroll.SLIME_SPRAY, 3, Summoning.Pouch.THORNY_SNAIL, Summoning.Charm.GOLD, Summoning.Ingredient.THIN_SNAIL), GRANITE_CRAB("Granite crab", 2, 26, 160, 16, 18, 12, Summoning.Scroll.STONY_SHELL, 0, Summoning.Pouch.GRANITE_CRAB, Summoning.Charm.GOLD, Summoning.Ingredient.IRON_ORE), SPIRIT_MOSQUITO("Spirit mosquito", 2, 32, 430, 17, 12, 3, Summoning.Scroll.PESTER, 0, Summoning.Pouch.SPIRIT_MOSQUITO, Summoning.Charm.GOLD, Summoning.Ingredient.PROBOSCIS), DESERT_WYRM("Desert wyrm", 1, 31, 470, 18, 19, 6, Summoning.Scroll.ELECTRIC_LASH, 0, Summoning.Pouch.DESERT_WYRM, Summoning.Charm.GREEN, Summoning.Ingredient.BUCKET_OF_SAND), SPIRIT_SCORPION("Spirit scorpion", 2, 51, 670, 19, 17, 6, Summoning.Scroll.VENOM_SHOT, 0, Summoning.Pouch.SPIRIT_SCORPION, Summoning.Charm.CRIMSON, Summoning.Ingredient.BRONZE_CLAW), SPIRIT_TZ_KIH("Spirit Tz-Kih", 3, 36, 630, 22, 18, 6, Summoning.Scroll.FIREBALL_ASSAULT, 0, Summoning.Pouch.SPIRIT_TZ_KIH, Summoning.Charm.CRIMSON, Summoning.Ingredient.OBSIDIAN_CHARM), ALBINO_RAT("Albino rat", 3, 37, 680, 23, 22, 6, Summoning.Scroll.CHEESE_FEAST, 0, Summoning.Pouch.ALBINO_RAT, Summoning.Charm.BLUE, Summoning.Ingredient.RAW_RAT_MEAT), SPIRIT_KALPHITE("Spirit kalphite", 3, 39, 770, 25, 22, 6, Summoning.Scroll.SANDSTORM, 6, Summoning.Pouch.SPIRIT_KALPHITE, Summoning.Charm.BLUE, Summoning.Ingredient.POTATO_CACTUS), COMPOST_MOUND("Compost Mound", 6, 37, 930, 28, 24, 12, Summoning.Scroll.GENERATE_COMPOST, 0, Summoning.Pouch.COMPOST_MOUND, Summoning.Charm.GREEN, Summoning.Ingredient.COMPOST), GIANT_CHINCHOMPA("Giant chinchompa", 1, 42, 970, 29, 31, 3, Summoning.Scroll.EXPLODE, 0, Summoning.Pouch.GIANT_CHINCHOMPA, Summoning.Charm.BLUE, Summoning.Ingredient.CHINCHOMPA), VAMPYRE_BAT("Vampyre bat", 4, 44, 1050, 31, 33, 4, Summoning.Scroll.VAMPYRE_TOUCH, 0, Summoning.Pouch.VAMPYRE_BAT, Summoning.Charm.CRIMSON, Summoning.Ingredient.VAMPYRE_DUST), HONEY_BADGER("Honey badger", 4, 45, 1100, 32, 25, 12, Summoning.Scroll.INSANE_FEROCITY, 0, Summoning.Pouch.HONEY_BADGER, Summoning.Charm.CRIMSON, Summoning.Ingredient.HONEYCOMB), BEAVER("Beaver", 4, 0, 0, 33, 27, 3, Summoning.Scroll.MULTICHOP, 0, Summoning.Pouch.BEAVER, Summoning.Charm.GREEN, Summoning.Ingredient.WILLOW_LOGS), VOID_RAVAGER("Void ravager", 4, 46, 1210, 34, 27, 3, Summoning.Scroll.CALL_TO_ARMS, 0, Summoning.Pouch.VOID_RAVAGER, Summoning.Charm.GREEN, Summoning.Ingredient.RAVAGER_CHARM), VOID_SPINNER("Void spinner", 4, 46, 1210, 34, 27, 3, Summoning.Scroll.CALL_TO_ARMS, 0, Summoning.Pouch.VOID_SPINNER, Summoning.Charm.BLUE, Summoning.Ingredient.SHIFTER_CHARM), VOID_SHIFTER("Void shifter", 4, 40, 590, 34, 94, 3, Summoning.Scroll.CALL_TO_ARMS, 0, Summoning.Pouch.VOID_SHIFTER, Summoning.Charm.BLUE, Summoning.Ingredient.SPINNER_CHARM), VOID_TORCHER("Void torcher", 4, 46, 1210, 34, 94, 3, Summoning.Scroll.CALL_TO_ARMS, 0, Summoning.Pouch.VOID_TORCHER, Summoning.Charm.BLUE, Summoning.Ingredient.TORCHER_CHARM), BRONZE_MINOTAUR("Bronze minotaur", 9, 50, 1330, 36, 30, 6, Summoning.Scroll.BRONZE_BULL_RUSH, 0, Summoning.Pouch.BRONZE_MINOTAUR, Summoning.Charm.BLUE, Summoning.Ingredient.BRONZE_BAR), BULL_ANT("Bull ant", 5, 58, 1540, 40, 30, 12, Summoning.Scroll.UNBURDEN, 9, Summoning.Pouch.BULL_ANT, Summoning.Charm.GOLD, Summoning.Ingredient.MARIGOLDS), MACAW("Macaw", 5, 0, 0, 41, 31, 12, Summoning.Scroll.HERB_CALL, 0, Summoning.Pouch.MACAW, Summoning.Charm.GREEN, Summoning.Ingredient.CLEAN_GUAM), EVIL_TURNIP("Evil turnip", 5, 62, 1670, 42, 30, 6, Summoning.Scroll.EVIL_FLAMES, 0, Summoning.Pouch.EVIL_TURNIP, Summoning.Charm.CRIMSON, Summoning.Ingredient.CARVED_EVIL_TURNIP), SPIRIT_COCKATRICE("Spirit cockatrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_COCKATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.COCKATRICE_EGG), SPIRIT_GUTHATRICE("Spirit guthatrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_GUTHATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.GUTHATRICE_EGG), SPIRIT_SARATRICE("Spirit saratrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_SARATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.SARATRICE_EGG), SPIRIT_ZAMATRICE("Spirit zamatrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_ZAMATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.ZAMATRICE_EGG), SPIRIT_PENGATRICE("Spirit pengatrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_PENGATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.PENGATRICE_EGG), SPIRIT_CORAXATRICE("Spirit coraxatrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_CORAXATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.CORAXATRICE_EGG), SPIRIT_VULATRICE("Spirit vulatrice", 6, 64, 1730, 43, 36, 3, Summoning.Scroll.PETRIFYING_GAZE, 0, Summoning.Pouch.SPIRIT_VULATRICE, Summoning.Charm.GREEN, Summoning.Ingredient.VULATRICE_EGG), IRON_MINOTAUR("Iron minotaur", 9, 70, 1930, 46, 37, 6, Summoning.Scroll.IRON_BULL_RUSH, 0, Summoning.Pouch.IRON_MINOTAUR, Summoning.Charm.BLUE, Summoning.Ingredient.IRON_BAR), PYRELORD("Pyrelord", 5, 70, 1930, 46, 32, 6, Summoning.Scroll.IMMENSE_HEAT, 0, Summoning.Pouch.PYRELORD, Summoning.Charm.CRIMSON, Summoning.Ingredient.TINDERBOX), MAGPIE("Magpie", 5, 0, 0, 47, 34, 12, Summoning.Scroll.THIEVING_FINGERS, 0, Summoning.Pouch.MAGPIE, Summoning.Charm.GREEN, Summoning.Ingredient.GOLD_RING), BLOATED_LEECH("Bloated leech", 5, 76, 2110, 49, 34, 6, Summoning.Scroll.BLOOD_DRAIN, 0, Summoning.Pouch.BLOATED_LEECH, Summoning.Charm.CRIMSON, Summoning.Ingredient.RAW_BEEF), SPIRIT_TERRORBIRD("Spirit terrorbird", 6, 62, 2330, 52, 36, 8, Summoning.Scroll.TIRELESS_RUN, 12, Summoning.Pouch.SPIRIT_TERRORBIRD, Summoning.Charm.GOLD, Summoning.Ingredient.RAW_BIRD_MEAT), ABYSSAL_PARASITE("Abyssal parasite", 6, 86, 2340, 54, 30, 6, Summoning.Scroll.ABYSSAL_DRAIN, 7, Summoning.Pouch.ABYSSAL_PARASITE, Summoning.Charm.GREEN, Summoning.Ingredient.ABYSSAL_CHARM), SPIRIT_JELLY("Spirit jelly", 6, 88, 2550, 55, 43, 6, Summoning.Scroll.DISSOLVE, 0, Summoning.Pouch.SPIRIT_JELLY, Summoning.Charm.BLUE, Summoning.Ingredient.JUG_OF_WATER), STEEL_MINOTAUR("Steel minotaur", 9, 90, 2600, 56, 46, 6, Summoning.Scroll.STEEL_BULL_RUSH, 0, Summoning.Pouch.STEEL_MINOTAUR, Summoning.Charm.BLUE, Summoning.Ingredient.STEEL_BAR), IBIS("Ibis", 6, 0, 0, 56, 38, 12, Summoning.Scroll.FISH_RAIN, 0, Summoning.Pouch.IBIS, Summoning.Charm.GREEN, Summoning.Ingredient.HARPOON), SPIRIT_GRAAHK("Spirit graahk", 6, 93, 2680, 57, 49, 3, Summoning.Scroll.GOAD, 0, Summoning.Pouch.SPIRIT_GRAAHK, Summoning.Charm.BLUE, Summoning.Ingredient.GRAAHK_FUR), SPIRIT_KYATT("Spirit kyatt", 6, 93, 2680, 57, 49, 3, Summoning.Scroll.AMBUSH, 0, Summoning.Pouch.SPIRIT_KYATT, Summoning.Charm.BLUE, Summoning.Ingredient.KYATT_FUR), SPIRIT_LARUPIA("Spirit larupia", 6, 93, 2680, 57, 49, 6, Summoning.Scroll.RENDING, 0, Summoning.Pouch.SPIRIT_LARUPIA, Summoning.Charm.BLUE, Summoning.Ingredient.LARUPIA_FUR), KARAMTHULHU_OVERLORD("Karamthulhu overlord", 6, 95, 2760, 58, 44, 3, Summoning.Scroll.DOOM_SPHERE, 0, Summoning.Pouch.KARAMTHULHU_OVERLORD, Summoning.Charm.BLUE, Summoning.Ingredient.FISH_BOWL), SMOKE_DEVIL("Smoke devil", 7, 101, 3000, 61, 48, 6, Summoning.Scroll.DUST_CLOUD, 0, Summoning.Pouch.SMOKE_DEVIL, Summoning.Charm.CRIMSON, Summoning.Ingredient.GOAT_HORN_DUST), ABYSSAL_LURKER("Abyssal lurker", 7, 93, 3080, 62, 41, 20, Summoning.Scroll.ABYSSAL_STEALTH, 7, Summoning.Pouch.ABYSSAL_LURKER, Summoning.Charm.GREEN, Summoning.Ingredient.ABYSSAL_CHARM), SPIRIT_COBRA("Spirit cobra", 7, 105, 3140, 63, 56, 3, Summoning.Scroll.OPHIDIAN_INCUBATION, 0, Summoning.Pouch.SPIRIT_COBRA, Summoning.Charm.CRIMSON, Summoning.Ingredient.SNAKE_HIDE), STRANGER_PLANT("Stranger plant", 7, 107, 3220, 64, 49, 6, Summoning.Scroll.POISONOUS_BLAST, 0, Summoning.Pouch.STRANGER_PLANT, Summoning.Charm.CRIMSON, Summoning.Ingredient.BAGGED_PLANT_1), MITHRIL_MINOTAUR("Mithril minotaur", 9, 112, 3400, 66, 55, 6, Summoning.Scroll.MITH_BULL_RUSH, 0, Summoning.Pouch.MITHRIL_MINOTAUR, Summoning.Charm.BLUE, Summoning.Ingredient.MITHRIL_BAR), BARKER_TOAD("Barker toad", 7, 112, 3400, 66, 8, 6, Summoning.Scroll.TOAD_BARK, 0, Summoning.Pouch.BARKER_TOAD, Summoning.Charm.GOLD, Summoning.Ingredient.SWAMP_TOAD), WAR_TORTOISE("War tortoise", 7, 86, 3480, 67, 43, 20, Summoning.Scroll.TETSUDO_SCROLL, 18, Summoning.Pouch.WAR_TORTOISE, Summoning.Charm.GOLD, Summoning.Ingredient.TORTOISE_SHELL), BUNYIP("Bunyip", 7, 70, 400, 68, 44, 3, Summoning.Scroll.SWALLOW_WHOLE, 0, Summoning.Pouch.BUNYIP, Summoning.Charm.GREEN, Summoning.Ingredient.RAW_SHARK), FRUIT_BAT("Fruit bat", 7, 0, 1000, 69, 45, 6, Summoning.Scroll.FRUIT_FALL, 0, Summoning.Pouch.FRUIT_BAT, Summoning.Charm.GREEN, Summoning.Ingredient.BANANA), RAVENOUS_LOCUST("Ravenous locust", 4, 120, 3700, 70, 24, 12, Summoning.Scroll.FAMINE, 0, Summoning.Pouch.RAVENOUS_LOCUST, Summoning.Charm.CRIMSON, Summoning.Ingredient.POT_OF_FLOUR), ARCTIC_BEAR("Arctic bear", 8, 122, 3810, 71, 28, 6, Summoning.Scroll.ARCTIC_BLAST, 0, Summoning.Pouch.ARCTIC_BEAR, Summoning.Charm.GOLD, Summoning.Ingredient.POLAR_KEBBIT_FUR), PHOENIX("Phoenix", 8, 124, 1530, 72, 30, 12, Summoning.Scroll.RISE_FROM_THE_ASHES, 0, Summoning.Pouch.PHOENIX, Summoning.Charm.CRIMSON, Summoning.Ingredient.PHOENIX_QUILL), OBSIDIAN_GOLEM("Obsidian golem", 8, 126, 4060, 73, 55, 12, Summoning.Scroll.VOLCANIC_STREAM, 0, Summoning.Pouch.OBSIDIAN_GOLEM, Summoning.Charm.BLUE, Summoning.Ingredient.OBSIDIAN_CHARM), GRANITE_LOBSTER("Granite lobster", 8, 129, 4180, 74, 47, 6, Summoning.Scroll.CRUSHING_CLAW, 0, Summoning.Pouch.GRANITE_LOBSTER, Summoning.Charm.CRIMSON, Summoning.Ingredient.GRANITE_500G), PRAYING_MANTIS("Praying mantis", 8, 131, 4280, 75, 69, 6, Summoning.Scroll.MANTIS_STRIKE, 0, Summoning.Pouch.PRAYING_MANTIS, Summoning.Charm.CRIMSON, Summoning.Ingredient.RED_FLOWERS), ADAMANT_MINOTAUR("Adamant minotaur", 9, 133, 4410, 76, 66, 6, Summoning.Scroll.ADDY_BULL_RUSH, 0, Summoning.Pouch.ADAMANT_MINOTAUR, Summoning.Charm.BLUE, Summoning.Ingredient.ADAMANT_BAR), FORGE_REGENT("Forge regent", 9, 133, 4410, 76, 45, 6, Summoning.Scroll.INFERNO, 0, Summoning.Pouch.FORGE_REGENT, Summoning.Charm.GREEN, Summoning.Ingredient.RUBY_HARVEST), TALON_BEAST("Talon beast", 9, 135, 4540, 77, 49, 6, Summoning.Scroll.DEADLY_CLAW, 0, Summoning.Pouch.TALON_BEAST, Summoning.Charm.CRIMSON, Summoning.Ingredient.TALON_BEAST_CHARM), GIANT_ENT("Giant ent", 8, 137, 4670, 78, 49, 6, Summoning.Scroll.ACORN_MISSILE, 0, Summoning.Pouch.GIANT_ENT, Summoning.Charm.GREEN, Summoning.Ingredient.WILLOW_BRANCH), FIRE_TITAN("Fire titan", 9, 139, 4760, 79, 62, 20, Summoning.Scroll.TITANS_CONSTITUTION, 0, Summoning.Pouch.FIRE_TITAN, Summoning.Charm.BLUE, Summoning.Ingredient.FIRE_TALISMAN), ICE_TITAN("Ice titan", 9, 139, 4760, 79, 64, 20, Summoning.Scroll.TITANS_CONSTITUTION, 0, Summoning.Pouch.ICE_TITAN, Summoning.Charm.BLUE, Summoning.Ingredient.AIR_TALISMAN, Summoning.Ingredient.WATER_TALISMAN), MOSS_TITAN("Moss titan", 9, 139, 4760, 79, 58, 20, Summoning.Scroll.TITANS_CONSTITUTION, 0, Summoning.Pouch.MOSS_TITAN, Summoning.Charm.BLUE, Summoning.Ingredient.EARTH_TALISMAN), HYDRA("Hydra", 8, 141, 4900, 80, 49, 6, Summoning.Scroll.REGROWTH, 0, Summoning.Pouch.HYDRA, Summoning.Charm.GREEN, Summoning.Ingredient.WATER_ORB), SPIRIT_DAGANNOTH("Spirit dagannoth", 9, 148, 5280, 83, 57, 6, Summoning.Scroll.SPIKE_SHOT, 0, Summoning.Pouch.SPIRIT_DAGANNOTH, Summoning.Charm.CRIMSON, Summoning.Ingredient.DAGANNOTH_HIDE), LAVA_TITAN("Lava titan", 9, 148, 5280, 83, 61, 4, Summoning.Scroll.EBON_THUNDER, 0, Summoning.Pouch.LAVA_TITAN, Summoning.Charm.BLUE, Summoning.Ingredient.OBSIDIAN_CHARM), SWAMP_TITAN("Swamp titan", 9, 152, 5660, 85, 56, 6, Summoning.Scroll.SWAMP_PLAGUE, 0, Summoning.Pouch.SWAMP_TITAN, Summoning.Charm.CRIMSON, Summoning.Ingredient.SWAMP_LIZARD), RUNE_MINOTAUR("Rune minotaur", 9, 154, 5700, 86, 151, 6, Summoning.Scroll.RUNE_BULL_RUSH, 0, Summoning.Pouch.RUNE_MINOTAUR, Summoning.Charm.BLUE, Summoning.Ingredient.RUNE_BAR), UNICORN_STALLION("Unicorn stallion", 9, 70, 1000, 88, 54, 20, Summoning.Scroll.HEALING_AURA, 0, Summoning.Pouch.UNICORN_STALLION, Summoning.Charm.GREEN, Summoning.Ingredient.UNICORN_HORN), GEYSER_TITAN("Geyser titan", 10, 200, 6100, 89, 69, 6, Summoning.Scroll.BOIL, 0, Summoning.Pouch.GEYSER_TITAN, Summoning.Charm.BLUE, Summoning.Ingredient.WATER_TALISMAN), WOLPERTINGER("Wolpertinger", 10, 210, 6510, 92, 62, 20, Summoning.Scroll.MAGIC_FOCUS, 0, Summoning.Pouch.WOLPERTINGER, Summoning.Charm.CRIMSON, Summoning.Ingredient.RAW_RABBIT, Summoning.Ingredient.WOLF_BONES), ABYSSAL_TITAN("Abyssal titan", 10, 215, 6670, 93, 32, 6, Summoning.Scroll.ESSENCE_SHIPMENT, 7, Summoning.Pouch.ABYSSAL_TITAN, Summoning.Charm.GREEN, Summoning.Ingredient.ABYSSAL_CHARM), IRON_TITAN("Iron titan", 10, 220, 6940, 95, 60, 12, Summoning.Scroll.IRON_WITHIN, 0, Summoning.Pouch.IRON_TITAN, Summoning.Charm.CRIMSON, Summoning.Ingredient.IRON_PLATEBODY), PACK_YAK("Pack yak", 10, 135, 7100, 96, 58, 12, Summoning.Scroll.WINTER_STORAGE, 30, Summoning.Pouch.PACK_YAK, Summoning.Charm.CRIMSON, Summoning.Ingredient.YAK_HIDE), STEEL_TITAN("Steel titan", 10, 230, 7540, 99, 64, 12, Summoning.Scroll.STEEL_OF_LEGENDS, 0, Summoning.Pouch.STEEL_TITAN, Summoning.Charm.CRIMSON, Summoning.Ingredient.STEEL_PLATEBODY), NIGHTMARE_MUSPAH("Nightmare muspah", 10, 0, 0, 81, 58, 20, Summoning.Scroll.SIPHON_SELF, 32, Summoning.Pouch.NIGHTMARE_MUSPAH, Summoning.Charm.ELDER, Summoning.Ingredient.MUSPAH_SPINE), LIGHT_CREATURE("Light creature", 10, 0, 0, 88, 60, 20, Summoning.Scroll.ENLIGHTENMENT, 0, Summoning.Pouch.LIGHT_CREATURE, Summoning.Charm.BLUE, Summoning.Ingredient.LIGHT_CORE);
    private final String name;
    private final Summoning.Scroll scroll;
    private final Summoning.Charm charm;
    private final Summoning.Pouch pouch;
    private final List<Summoning.Ingredient> ingredients;
    private final int requiredLevel;
    private final int summoningCost;
    private final int specialMoveCost;
    private final int combatLevel;
    private final int lifePoints;
    private final int minutesAvailable;
    private final int inventorySize;

    Familiar(String name, int summoningCost, int combatLevel, int lifePoints, int requiredLevel, int minutesAvailable, int specialMoveCost, Summoning.Scroll scroll, int inventorySpace, Summoning.Pouch pouch, Summoning.Charm charm, Summoning.Ingredient ingredientA) {
        this(name, summoningCost, combatLevel, lifePoints, requiredLevel, minutesAvailable, specialMoveCost, scroll, inventorySpace, pouch, charm, ingredientA, null);
    }

    Familiar(String name, int summoningCost, int combatLevel, int lifePoints, int requiredLevel, int minutesAvailable, int specialMoveCost, Summoning.Scroll scroll, int inventorySpace, Summoning.Pouch pouch, Summoning.Charm charm, Summoning.Ingredient ingredientA, Summoning.Ingredient ingredientB) {
        this.name = name;
        this.summoningCost = summoningCost;
        this.combatLevel = combatLevel;
        this.lifePoints = lifePoints;
        this.requiredLevel = requiredLevel;
        this.minutesAvailable = minutesAvailable;
        this.specialMoveCost = specialMoveCost;
        this.scroll = scroll;
        this.inventorySize = inventorySpace;
        this.pouch = pouch;
        this.charm = charm;
        this.ingredients = new ArrayList<>(2);
        this.ingredients.add(ingredientA);
        if (ingredientB != null) {
            this.ingredients.add(ingredientB);
        }
    }

    public Summoning.Scroll getScroll() {
        return scroll;
    }

    public Summoning.Pouch getPouch() {
        return pouch;
    }

    public Summoning.Charm getCharm() {
        return charm;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getSummoningCost() {
        return summoningCost;
    }

    public int getSpecialMoveCost() {
        return specialMoveCost;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getCombatLevel() {
        return combatLevel;
    }

    public int getMinutesAvailable() {
        return minutesAvailable;
    }

    public boolean isBeastOfBurden() {
        return inventorySize > 0;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Summoning.Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum FamiliarOption {
        FOLLOWER_DETAILS(0, "Follower details", "Follower Details"), SPECIAL_MOVE(1, "Special move", "Cast"), ATTACK(2, "Attack", "Attack"), CALL_FOLLOWER(3, "Call follower", "Call Follower"), DISMISS_FOLLOWER(4, "Dismiss follower", "Dismiss") {
            @Override
            public boolean select() {
                return Dialog.hasOption("Yes.") && Execution.delayUntil(2000, () -> !Dialog.hasOption("Yes.")) && Dialog.interact("Yes.");
            }
        }, TAKE_BOB(5, "Take BoB", "Take BoB"), RENEW_FAMILIAR(6, "Renew familiar", "Renew Familiar"), INTERACT(7, "Interact", "Interact");
        private final int leftClickValue;
        private final String text;
        private final String action;

        FamiliarOption(int leftClickValue, String text, String action) {
            this.leftClickValue = leftClickValue;
            this.text = text;
            this.action = action;
        }

        /**
         * Selects the option from the necessary component.
         *
         * @return
         */
        public boolean select() {
            var ic1 = ComponentQuery.newQuery(1430).option(action).results().first();
            var ic2 = ComponentQuery.newQuery(1506).option(action).results().first();
            if (ic1 == null || !ic1.interact(action)) {
                if (ic2 != null) {
                    return ic2.interact(action);
                }
            }
            return false;
        }
    }
}