package net.botwithus;

import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.queries.results.EntityResultSet;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.events.impl.SkillUpdateEvent;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.*;

import java.util.*;

public class SkeletonScript extends LoopingScript {

    private BotState botState = BotState.IDLE;
    private Random random = new Random();
    private HashMap<String, Integer> priorityObjects;
    private HashMap<String, Area> islands;
    private HashMap<String, Integer> levelRequirements;

    /////////////////////////////////////Botstate//////////////////////////
    enum BotState {
        //define your own states here
        IDLE,
        SKILLING,
        //...
    }

    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        initializeMaps(); // Call to initialize maps
    }

    private void initializeMaps() {
        // Initialization logic for priorityObjects, islands, levelRequirements
        // HashMap for priority objects with their level requirements
        HashMap<String, Integer> priorityObjects = new HashMap<>();
        priorityObjects.put("Undead Soul", 95);
        priorityObjects.put("Living soul", 90);
        priorityObjects.put("Bloody skulls", 83);
        priorityObjects.put("Blood pool", 77);
        priorityObjects.put("Skulls", 65);
        priorityObjects.put("Jumper", 54);
        priorityObjects.put("Shifter", 44);
        priorityObjects.put("Nebula", 40);
        priorityObjects.put("Chaotic cloud", 35);
        priorityObjects.put("Fire storm", 27);
        priorityObjects.put("Fleshy growth", 20);
        priorityObjects.put("Vine", 17);
        priorityObjects.put("Fireball", 14);
        priorityObjects.put("Rock Fragment", 9);
        priorityObjects.put("Water pool", 5);
        priorityObjects.put("Mind storm", 1);
        priorityObjects.put("Cyclone", 1);

        Area.Rectangular Island_1 = new Area.Rectangular(new Coordinate(3989, 6119, 1), new Coordinate(4007, 6119, 1));
        Area.Rectangular Island_16 = new Area.Rectangular(new Coordinate(3990, 6067, 1), new Coordinate(4014, 6041, 1));
        Area.Rectangular Island_5 = new Area.Rectangular(new Coordinate(4125, 6093, 1), new Coordinate(4146, 6068, 1));
        Area.Rectangular Island_23 = new Area.Rectangular(new Coordinate(4191, 6108, 1), new Coordinate(4204, 6085, 1));
        Area.Rectangular Island_13 = new Area.Rectangular(new Coordinate(4325, 6055, 1), new Coordinate(4365, 6037, 1));
        Area.Rectangular Island_29 = new Area.Rectangular(new Coordinate(4371, 6086, 1), new Coordinate(4385, 6070, 1));

        // HashMap for the islands
        HashMap<String, Area> islands = new HashMap<>();
        islands.put("Island_Low_1", Island_1);
        islands.put("Island_Low_16", Island_16);
        islands.put("Island_Mid__5", Island_5);
        islands.put("Island_Mid_23", Island_23);
        islands.put("Island_High_13", Island_13);
        islands.put("Island_High_29", Island_29);

        // HashMap for level requirements
        HashMap<String, Integer> levelRequirements = new HashMap<>();
        levelRequirements.put("Island_Low_1", 1);
        levelRequirements.put("Island_Low_16", 9);
        levelRequirements.put("Island_Mid__5", 33);
        levelRequirements.put("Island_Mid_23", 50);
        levelRequirements.put("Island_High_13", 66);
        levelRequirements.put("Island_High_29", 90);
    }

    @Override
    public void onLoop() {
        //Loops every 100ms by default, to change:
        //this.loopDelay = 500;
        LocalPlayer player = Client.getLocalPlayer();
        if (player == null || Client.getGameState() != Client.GameState.LOGGED_IN || botState == BotState.IDLE) {
            //wait some time so we dont immediately start on login.
            Execution.delay(random.nextLong(3000, 7000));
            return;
        }

        /////////////////////////////////////Botstate//////////////////////////
        switch (botState) {
            case IDLE ->                {println("We're idle!");
                Execution.delay(random.nextLong(1000,3000));}
        }
        checkAndCollectEssence();
        interactWithPriorityObjects(player);
    }

    private void checkAndCollectEssence() {
        println("Checking for Rune Essence");
        if (!hasRune_Essence()) {
            println("No Rune Essence, going to collect");
            Npc Floating_Essence = NpcQuery.newQuery().name("Floating essence").results().nearest();
            if (Floating_Essence != null) {
                println("found");
            }
            Floating_Essence.interact("Collect");
            println("Collecting Essence");
        }
    }

    private void tryInteractWithNearestObject(Area currentIsland, List<String> eligibleObjects, LocalPlayer player) {
        println("Attempting to interact with objects in " + currentIsland);

        // Iterate through eligible objects in decreasing order of priority
        for (String objectName : eligibleObjects) {
            println("Looking for: " + objectName);
            try {
                EntityResultSet<SceneObject> priorityObjectResultSet = SceneObjectQuery.newQuery()
                        .name(objectName)
                        .inside(currentIsland)
                        .results();

                SceneObject nearestObject = priorityObjectResultSet.nearestTo(player.getCoordinate());
                if (nearestObject != null) {
                    println("Found and interacting with: " + nearestObject.getName());
                    if (nearestObject.interact("Siphon")) {
                        Execution.delay(1000); // Wait for the interaction to complete
                        return; // Exit the method after successful interaction
                    }
                } else {
                    println(objectName + " not found in the current island.");
                }
            } catch (Exception e) {
                println("An error occurred while interacting with " + objectName + ": " + e.getMessage());
            }
        }

        println("No eligible objects found for interaction in the current island.");
    }

    private Area determineCurrentIsland(LocalPlayer player) {
        for (Map.Entry<String, Area> entry : islands.entrySet()) {
            if (entry.getValue().contains(player.getCoordinate())) {
                return entry.getValue();
            }
        }
        return null; // Player is not on any known island
    }

    private List<String> getEligibleObjects(LocalPlayer player) {
        int playerLevel = Skills.RUNECRAFTING.getLevel();
        List<String> eligibleObjects = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : priorityObjects.entrySet()) {
            if (playerLevel >= entry.getValue()) { // This ensures objects with level <= playerLevel are included
                eligibleObjects.add(entry.getKey());
            }
        }
        return eligibleObjects;
    }


    private void interactWithPriorityObjects(LocalPlayer player) {
        println("Interacting with priority objects");
        Area currentIsland = determineCurrentIsland(player);
        println("Current Island: " + currentIsland.getArea());
        if (currentIsland != null) {
            List<String> eligibleObjects = getEligibleObjects(player);
            tryInteractWithNearestObject(currentIsland, eligibleObjects, player);
        } else {
            println("Player is not on any known island.");
        }
    }

    private boolean hasRune_Essence() {
        ResultSet<Item> runeScan = InventoryItemQuery.newQuery(93).ids(24227).results();
        Item rune = runeScan.first();
        if (rune != null) {
            return true;
        }
        return false;
    }


    /////////////////STATISTICS////////////////////
    //XP Gain & Level Gain base is set to zero,
    private int xpGained = 0;
    private int levelsGained = 0;
    private long startTime;
    private int xpPerHour;
    private String ttl; // Time to level

    //XP Gain & Level Gain is calculated and added to base
    @Override
    public boolean initialize() {
        startTime = System.currentTimeMillis();
        xpGained = 0;
        levelsGained = 0;

        subscribe(SkillUpdateEvent.class, skillUpdateEvent -> {
            if (skillUpdateEvent.getId() == Skills.RUNECRAFTING.getId()) {
                xpGained += (skillUpdateEvent.getExperience() - skillUpdateEvent.getOldExperience());
                if (skillUpdateEvent.getOldActualLevel() < skillUpdateEvent.getActualLevel()) {
                    levelsGained++;
                }
            }
        });

        return super.initialize();
    }

    public String levelsGained() {
        return levelsGained + " Levels";
    }

    public String xpPerHour() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > startTime) {
            xpPerHour = (int) (xpGained * 3600000.0 / (currentTime - startTime));
        }
        return xpPerHour + " XP/hr";
    }

    public String ttl() {
        if (xpPerHour > 0) {
            int xpToNextLevel = Skills.RUNECRAFTING.getExperienceToNextLevel();
            int totalSeconds = (int) (xpToNextLevel * 3600.0 / xpPerHour);
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            int seconds = totalSeconds % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return "N/A";
    }

    public String timePassed() {
        long currentTime = System.currentTimeMillis();
        long elapsedMillis = currentTime - startTime;
        long hours = elapsedMillis / 3600000;
        long minutes = (elapsedMillis % 3600000) / 60000;
        long seconds = (elapsedMillis % 60000) / 1000;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String xpGained() {
        return xpGained + " XP";
    }



    ////////////////////Botstate/////////////////////
    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }

}
