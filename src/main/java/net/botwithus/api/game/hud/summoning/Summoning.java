package net.botwithus.api.game.hud.summoning;

import net.botwithus.rs3.game.annotations.Onymous;
import net.botwithus.rs3.game.js5.types.vars.VarDomainType;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.vars.VarManager;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Summoning {
    private static final int MINUTES_REMAINING_VARBIT_ID = 6055, CURRENT_HEALTH_VARBIT_ID = 19034, TOTAL_HEALTH_VARBIT_ID = 27403, VARBIT_CURRENT_SUMMONING_POINTS = 41524;

    private static final int MINUTE_REMAINING_VARP_ID = 1786, HEALTH_VARP_ID = 5194, VARP_CURRENT_LEFT_CLICK_OPTION = 1789, VARP_SPECIAL_MOVE_POINTS = 1787;
    private static final int SCROLL_COUNT_STORED_ON_FAMILIAR_VARBIT_ID = 25412, ACTIVE_FAMILIAR_POUCH_VARBIT_ID = 1831;

    /**
     * Gets the remaining time for the currently summoned familiar (in minutes)
     */
    public static int getMinutesRemaining() {
        var val = VarManager.getVarbitValue(MINUTES_REMAINING_VARBIT_ID);
        return Math.max(val, 0);
    }

    public static int getFamiliarScrollCount() {
        var val = VarManager.getVarbitValue(SCROLL_COUNT_STORED_ON_FAMILIAR_VARBIT_ID);
        return Math.max(val, 0);
    }

    //TODO: Need to replace MiniMenu.doAction call
    public static void triggerSpecial() {
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 43384870);
    }

    public static int getFamiliarPouchId() {
        var val = VarManager.getVarValue(VarDomainType.PLAYER, ACTIVE_FAMILIAR_POUCH_VARBIT_ID);
        return Math.max(val, 0);
    }

    /**
     * Gets the current amount of summoning points available to the local player
     */
    public static int getPoints() {
        var value = VarManager.getVarbitValue(VARBIT_CURRENT_SUMMONING_POINTS);
        if (value >= 0) {
            return value / 10;
        }
        return 0;
    }


    /**
     * Gets the maximum amount of summoning points available to the local player
     */
    public static int getMaximumPoints() {
        return Skills.SUMMONING.getSkill().getLevel() * 10;
    }

    /**
     * Gets the amount of special move points available
     */
    public static int getSpecialMovePoints() {
        return VarManager.getVarValue(VarDomainType.PLAYER, VARP_SPECIAL_MOVE_POINTS);
    }

    public static Set<Familiar> getBeastOfBurdens() {
        return Arrays.stream(Familiar.values()).filter(Familiar::isBeastOfBurden).collect(Collectors.toSet());
    }

    public enum Ingredient implements Onymous {
        WOLF_BONES("Wolf bones"), RAW_CHICKEN("Raw chicken"), SPIDER_CARCASS("Spider carcass"), THIN_SNAIL(
                "Thin snail"), IRON_ORE("Iron ore"), PROBOSCIS("Proboscis"), BUCKET_OF_SAND(
                "Bucket of sand"), BRONZE_CLAW("Bronze claw"), OBSIDIAN_CHARM("Obsidian charm"), RAW_RAT_MEAT(
                "Raw rat meat"), POTATO_CACTUS("Potato cactus"), COMPOST("Compost"), CHINCHOMPA(
                "Chinchompa"), VAMPYRE_DUST("Vampyre dust"), HONEYCOMB("Honeycomb"), WILLOW_LOGS(
                "Willow logs"), RAVAGER_CHARM("Ravager charm"), SHIFTER_CHARM("Shifter charm"), SPINNER_CHARM(
                "Spinner charm"), TORCHER_CHARM("Torcher charm"), BRONZE_BAR("Bronze bar"), MARIGOLDS(
                "Marigolds"), CLEAN_GUAM("Clean guam"), CARVED_EVIL_TURNIP("Carved evil turnip"), COCKATRICE_EGG(
                "Cockatrice egg"), GUTHATRICE_EGG("Guthatrice egg"), SARATRICE_EGG("Saratrice egg"), ZAMATRICE_EGG(
                "Zamatrice egg"), PENGATRICE_EGG("Pengatrice egg"), CORAXATRICE_EGG("Coraxatrice egg"), VULATRICE_EGG(
                "Vulatrice egg"), IRON_BAR("Iron bar"), TINDERBOX("Tinderbox"), GOLD_RING("Gold ring"), RAW_BEEF(
                "Raw beef"), RAW_BIRD_MEAT("Raw bird meat"), ABYSSAL_CHARM("Abyssal charm"), JUG_OF_WATER(
                "Jug of water"), STEEL_BAR("Steel bar"), HARPOON("Harpoon"), GRAAHK_FUR("Graahk fur"), KYATT_FUR(
                "Kyatt fur"), LARUPIA_FUR("Larupia fur"), FISH_BOWL("Fishbowl"), GOAT_HORN_DUST(
                "Goat horn dust"), SNAKE_HIDE("Snake hide"), BAGGED_PLANT_1("Bagged plant 1"), MITHRIL_BAR(
                "Mithril bar"), SWAMP_TOAD("Swamp toad"), TORTOISE_SHELL("Tortoise shell"), RAW_SHARK(
                "Raw shark"), BANANA("Banana"), POT_OF_FLOUR("Pot of flour"), POLAR_KEBBIT_FUR(
                "Polar kebbit fur"), PHOENIX_QUILL("Phoenix quill"), GRANITE_500G("Granite (500g)"), RED_FLOWERS(
                "Red flowers"), ADAMANT_BAR("Adamant bar"), RUBY_HARVEST("Ruby harvest"), TALON_BEAST_CHARM(
                "Talon beast charm"), WILLOW_BRANCH("Willow branch"), FIRE_TALISMAN("Fire talisman"), AIR_TALISMAN(
                "Air talisman"), WATER_TALISMAN("Water talisman"), EARTH_TALISMAN("Earth talisman"), WATER_ORB(
                "Water orb"), DAGANNOTH_HIDE("Dagannoth hide"), SWAMP_LIZARD("Swamp lizard"), RUNE_BAR(
                "Rune bar"), UNICORN_HORN("Unicorn horn"), RAW_RABBIT("Raw rabbit"), IRON_PLATEBODY(
                "Iron platebody"), YAK_HIDE("Yak-hide"), STEEL_PLATEBODY("Steel platebody"), MUSPAH_SPINE(
                "Muspah spine"), LIGHT_CORE("Light core");
        private final String name;

        Ingredient(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Scroll implements Onymous {
        HOWL("Howl scroll"), DREADFOWL_STRIKE("Dreadfowl strike scroll"), EGG_SPAWN("Egg spawn scroll"), SLIME_SPRAY(
                "Slime spray scroll"), STONY_SHELL("Stony shell scroll"), PESTER("Pester scroll"), ELECTRIC_LASH(
                "Electric lash scroll"), VENOM_SHOT("Venom shot scroll"), FIREBALL_ASSAULT(
                "Fireball assault scroll"), CHEESE_FEAST("Cheese feast scroll"), SANDSTORM(
                "Sandstorm scroll"), GENERATE_COMPOST("Generate compost scroll"), EXPLODE(
                "Explode scroll"), VAMPYRE_TOUCH("Vampyre touch scroll"), INSANE_FEROCITY(
                "Insane ferocity scroll"), MULTICHOP("Multichop scroll"), CALL_TO_ARMS(
                "Call to arms scroll"), BRONZE_BULL_RUSH("Bronze bull rush scroll"), UNBURDEN(
                "Unburden scroll"), HERB_CALL("Herbcall scroll"), EVIL_FLAMES("Evil flames scroll"), PETRIFYING_GAZE(
                "Petrifying gaze scroll"), IRON_BULL_RUSH("Iron bull rush scroll"), IMMENSE_HEAT(
                "Immense heat scroll"), THIEVING_FINGERS("Thieving fingers scroll"), BLOOD_DRAIN(
                "Blood drain scroll"), TIRELESS_RUN("Tireless run scroll"), ABYSSAL_DRAIN(
                "Abyssal drain scroll"), DISSOLVE("Dissolve scroll"), STEEL_BULL_RUSH(
                "Steel bull rush scroll"), FISH_RAIN("Fish rain scroll"), GOAD("Goad scroll"), AMBUSH(
                "Ambush scroll"), RENDING("Rending scroll"), DOOM_SPHERE("Doomsphere scroll"), DUST_CLOUD(
                "Dust cloud scroll"), ABYSSAL_STEALTH("Abyssal stealh scroll"), OPHIDIAN_INCUBATION(
                "Oph. incubation scroll"), POISONOUS_BLAST("Poisonous blast scroll"), MITH_BULL_RUSH(
                "Mith bull rush scroll"), TOAD_BARK("Toad bark scroll"), TETSUDO_SCROLL(
                "Testudo scroll"), SWALLOW_WHOLE("Swallow whole scroll"), FRUIT_FALL("Fruitfall scroll"), FAMINE(
                "Famine scroll"), ARCTIC_BLAST("Arctic blast scroll"), RISE_FROM_THE_ASHES(
                "Rise from the ashes scroll"), VOLCANIC_STREAM("Volcanic str. scroll"), CRUSHING_CLAW(
                "Crushing claw scroll"), MANTIS_STRIKE("Mantis strike scroll"), ADDY_BULL_RUSH(
                "Addy bull rush scroll"), INFERNO("Inferno scroll"), DEADLY_CLAW("Deadly claw scroll"), ACORN_MISSILE(
                "Acorn missile scroll"), TITANS_CONSTITUTION("Titan's con. scroll"), REGROWTH(
                "Regrowth scroll"), SPIKE_SHOT("Spike shot scroll"), EBON_THUNDER("Ebon thunder scroll"), SWAMP_PLAGUE(
                "Swamp plague scroll"), RUNE_BULL_RUSH("Rune bull rush scroll"), HEALING_AURA(
                "Healing aura scroll"), BOIL("Boil scroll"), MAGIC_FOCUS("Magic focus scroll"), ESSENCE_SHIPMENT(
                "Essence shipment scroll"), IRON_WITHIN("Iron within scroll"), WINTER_STORAGE(
                "Winter storage scroll"), STEEL_OF_LEGENDS("Steel of legends scroll"), SIPHON_SELF(
                "Siphon self scroll"), ENLIGHTENMENT("Enlightenment scroll");

        private final String name;

        Scroll(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Pouch implements Onymous {
        SPIRIT_WOLF("Spirit wolf pouch", 7), DREADFOWL("Dreadfowl pouch", 8), SPIRIT_SPIDER("Spirit spider pouch",
                                                                                            8), THORNY_SNAIL(
                "Thorny snail pouch", 9), GRANITE_CRAB("Granite crab pouch", 7), SPIRIT_MOSQUITO(
                "Spirit mosquito pouch", 1), DESERT_WYRM("Desert wyrm pouch", 45), SPIRIT_SCORPION(
                "Spirit scorpion pouch", 57), SPIRIT_TZ_KIH("Spirit Tz-Kih pouch", 64), ALBINO_RAT("Albino rat pouch",
                                                                                                   75), SPIRIT_KALPHITE(
                "Spirit kalphite pouch", 51), COMPOST_MOUND("Compost mound pouch", 47), GIANT_CHINCHOMPA(
                "Giant chinchompa pouch", 84), VAMPYRE_BAT("Vampyre bat pouch", 81), HONEY_BADGER("Honey badger pouch",
                                                                                                  84), BEAVER(
                "Beaver pouch", 72), VOID_RAVAGER("Void ravager pouch", 74), VOID_SHIFTER("Void shifter pouch",
                                                                                          74), VOID_SPINNER(
                "Void spinner pouch", 74), VOID_TORCHER("Void torcher pouch", 74), BRONZE_MINOTAUR(
                "Bronze minotaur pouch", 102), BULL_ANT("Bull ant pouch", 11), MACAW("Macaw pouch", 78), EVIL_TURNIP(
                "Evil turnip pouch", 104), SPIRIT_SARATRICE("Spirit saratrice pouch", 88), SPIRIT_PENGATRICE(
                "Spirit pengatrice pouch", 88), SPIRIT_CORAXATRICE("Spirit coraxatrice pouch", 88), SPIRIT_GUTHATRICE(
                "Spirit guthatrice pouch", 88), SPIRIT_COCKATRICE("Spirit cockatrice pouch", 88), SPIRIT_ZAMATRICE(
                "Spirit zamatrice pouch", 88), SPIRIT_VULATRICE("Spirit vulatrice pouch", 88), IRON_MINOTAUR(
                "Iron minotaur pouch", 125), PYRELORD("Pyrelord pouch", 111), MAGPIE("Magpie pouch", 88), BLOATED_LEECH(
                "Bloated leech pouch", 117), SPIRIT_TERRORBIRD("Spirit terrorbird pouch", 12), ABYSSAL_PARASITE(
                "Abyssal parasite pouch", 106), SPIRIT_JELLY("Spirit jelly pouch", 151), IBIS("Ibis pouch",
                                                                                              109), STEEL_MINOTAUR(
                "Steel minotaur pouch", 141), SPIRIT_KYATT("Spirit kyatt pouch", 153), SPIRIT_LARUPIA(
                "Spirit larupia pouch", 155), SPIRIT_GRAAHK("Spirit graahk pouch", 154), KARAMTHULHU_OVERLORD(
                "Karamthulhu overlord pouch", 144), SMOKE_DEVIL("Smoke devil pouch", 141), ABYSSAL_LURKER(
                "Abyssal lurker pouch", 119), SPIRIT_COBRA("Spirit cobra pouch", 116), STRANGER_PLANT(
                "Stranger plant pouch", 128), MITHRIL_MINOTAUR("Mithril minotaur pouch", 152), BARKER_TOAD(
                "Barker toad pouch", 11), WAR_TORTOISE("War tortoise pouch", 1), BUNYIP("Bunyip pouch", 110), FRUIT_BAT(
                "Fruit bat pouch", 130), RAVENOUS_LOCUST("Ravenous locust pouch", 79), ARCTIC_BEAR("Arctic bear pouch",
                                                                                                   14), PHOENIX(
                "Phoenix (familiar) pouch", 165), OBSIDIAN_GOLEM("Obsidian golem pouch", 195), GRANITE_LOBSTER(
                "Granite lobster pouch", 166), PRAYING_MANTIS("Praying mantis pouch", 168), ADAMANT_MINOTAUR(
                "Adamant minotaur pouch", 144), FORGE_REGENT("Forge regent pouch", 141), TALON_BEAST(
                "Talon beast pouch", 174), GIANT_ENT("Giant ent pouch", 124), MOSS_TITAN("Moss titan pouch",
                                                                                         202), ICE_TITAN(
                "Ice titan pouch", 198), FIRE_TITAN("Fire titan pouch", 198), HYDRA("Hydra pouch",
                                                                                    128), NIGHTMARE_MUSPAH(
                "Nightmare muspah pouch", 0, 150), LAVA_TITAN("Lava titan pouch", 219), SPIRIT_DAGANNOTH(
                "Spirit dagannoth pouch", 1), SWAMP_TITAN("Swamp titan pouch", 150), RUNE_MINOTAUR(
                "Rune minotaur pouch", 1), ICE_NIHIL("Ice nihil pouch", 150), BLOOD_NIHIL("Blood nihil pouch",
                                                                                          150), SHADOW_NIHIL(
                "Shadow nihil pouch", 150), SMOKE_NIHIL("Smoke nihil pouch", 150), LIGHT_CREATURE(
                "Light creature pouch", 204), UNICORN_STALLION("Unicorn stallion pouch", 140), GEYSER_TITAN(
                "Geyser titan pouch", 222), WOLPERTINGER("Wolpertinger pouch", 203), ABYSSAL_TITAN(
                "Abyssal titan pouch", 113), IRON_TITAN("Iron titan pouch", 198), PACK_YAK("Pack yak pouch",
                                                                                           211), STEEL_TITAN(
                "Steel titan pouch", 178);
        private final String name;
        private final int shardsNeeded, elderEnergyNeeded;

        Pouch(String name, int shardsNeeded) {
            this(name, shardsNeeded, 0);
        }

        Pouch(String name, int shardsNeeded, int elderEnergyNeeded) {
            this.name = name;
            this.shardsNeeded = shardsNeeded;
            this.elderEnergyNeeded = elderEnergyNeeded;
        }

        /**
         * Gets the amount of spirit shards needed to create this pouch.
         *
         * @return an int
         */
        public int getSpiritShardsNeeded() {
            return shardsNeeded;
        }

        public int getElderEnergyNeeded() {
            return elderEnergyNeeded;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Charm {
        GOLD("Gold charm"), GREEN("Green charm"), CRIMSON("Crimson charm"), BLUE("Blue charm"), ELDER("Elder charm");
        private final String name;

        Charm(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
