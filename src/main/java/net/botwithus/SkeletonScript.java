package net.botwithus;

import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.events.impl.ChatMessageEvent;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.queries.results.EntityResultSet;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.Entity;
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.events.impl.SkillUpdateEvent;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SkeletonScript extends LoopingScript {

    private BotState botState = BotState.IDLE;
    private Random random = new Random();
    private SkeletonScriptGraphicsContext sgc;

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
    }

    @Override
    public void onLoop(LocalPlayer player) {
        // Ensure the player is in a valid state to perform actions
        if (Client.getLocalPlayer() == null || Client.getGameState() != Client.GameState.LOGGED_IN) {
            return;
        }

        //Undead Soul >= 95 , Living soul >= 90, Bloody Skulls >= 83, Blood Pool >= 77, Skulls >= 65, Jumper >= 54, Shifter >= 44, Nebula >= 40, Chaotic Cloud >= 35, Fire Storm >= 27, Fleshy Growth >= 20, Vine >= 17, Fireball >= 14, Rock Fragment >= 9, Water Pool >= 5, Mind Storm >= 1, Cyclone >= 1
        //Write an hashmap for priority objects with their level requirements
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

        //Write code to indicate coordinates of the islands
        Area Island_1 = new Area(3989, 6119, 1, 4007, 6119, 1);
        Area Island_16 = new Area(3990, 6067, 1, 4014, 6041, 1);
        Area Island_5 = new Area(4125, 6093, 1, 4146, 6068, 1);
        Area Island_23 = new Area(4191, 6108, 1, 4204, 6085, 1);
        Area Island_13 = new Area(4325, 6055, 1, 4365, 6037, 1);
        Area Island_29 = new Area(4371, 6086, 1, 4385, 6070, 1);

        //Write an hashmap for the islands
        HashMap<String, Area> islands = new HashMap<>();
        islands.put("Island_Low_1", Island_1);
        islands.put("Island_Low_16", Island_16);
        islands.put("Island_Mid__5", Island_5);
        islands.put("Island_Mid_23", Island_23);
        islands.put("Island_High_13", Island_13);
        islands.put("Island_High_29", Island_29);

        //Write an hashmap for level requirements
        HashMap<String, Integer> levelRequirements = new HashMap<>();
        levelRequirements.put("Island_Low_1", 1);
        levelRequirements.put("Island_Low_16", 9);
        levelRequirements.put("Island_Mid__5", 33);
        levelRequirements.put("Island_Mid_23", 50);
        levelRequirements.put("Island_High_13", 66);
        levelRequirements.put("Island_High_29", 90);

        //Write a for loop to check the level requirements
        for (Map.Entry<String, Integer> entry : levelRequirements.entrySet()) {
            if (Skills.RUNECRAFTING.getLevel() >= entry.getValue()) {
                println("Farming on " + entry.getKey());
            } else {
                println("Not farming on " + entry.getKey());
            }
        }

        //use the hashmap to check if the player is on the island
        for (Map.Entry<String, Area> entry : islands.entrySet()) {
            if (entry.getValue().contains(player.getCoordinate())) {
                println("On " + entry.getKey());
            } else {
                println("Not on " + entry.getKey());
            }
        }

        // Query for priority objects in the area
        EntityResultSet<SceneObject> priorityObject = SceneObjectQuery.newQuery()
                .name(priorityObjects)
                .inside(islands)
                .results();

        // Interact with the nearest priority object
        SceneObject nearestObject = priorityObject.nearestTo(Client.getLocalPlayer().getCoordinate());
        if (nearestObject != null) {
            if (nearestObject.interact("Siphon")) { // Replace "Interact option" with the actual option
                println("Interacting with: " + nearestObject.getName());
                Execution.delay(1000); // Wait for the interaction to complete
            }
        }
    }

    @Override
    public void onLoop() {
        //Loops every 100ms by default, to change:
        //this.loopDelay = 500;
        LocalPlayer player = Client.getLocalPlayer();
        if (player == null || Client.getGameState() != Client.GameState.LOGGED_IN || botState == BotState.IDLE) {
            //wait some time so we dont immediately start on login.
            Execution.delay(random.nextLong(3000,7000));
            return;
        }

        /////////////////////////////////////Botstate//////////////////////////
        switch (botState) {
            case IDLE -> {
                //do nothing
                println("We're idle!");
                Execution.delay(random.nextLong(1000,3000));
            }
            case SKILLING -> {
                //do some code that handles your skilling
                Execution.delay(handleSkilling(player));
            }
        }
    }

    //Make a initialize method to move the areas into
    //a hashmap and to check if the player is on the island
    private void initialize(LocalPlayer player) {



    /////////////////////Skilling + Eating + Backpack Full = Idle////////////////////////
    private long handleSkilling(LocalPlayer player){
            if (!hasRune_Essence()) {
                println("No Rune Essence, going to collect");
                Npc Floating_Essence = NpcQuery.newQuery().name("Floating essence").results().nearest();
                if (Floating_Essence != null) {
                    println("found");
                }
                Floating_Essence.interact("Collect");
                println("Collecting Essence");
            }


            //use the hashmap to check if the player is on the island
            for (Map.Entry<String, Area> entry : islands.entrySet()) {
                if (entry.getValue().contains(player.getCoordinate())) {
                    println("On " + entry.getKey());
                } else {
                    println("Not on " + entry.getKey());
                }
            }

            //If the player is above level 90, farm on Island 29
            //If the player is above level 66, farm on Island 13
            //If the player is above level 50, farm on Island 23
            //If the player is above level 33, farm on Island 5
            //If the player is above level 9, farm on Island 16
            //If the player is under level 9, farm on Island 1

        }










//        if (Skills.RUNECRAFTING.getLevel() <= 9) {
//            println("Under level 9, farming on Island 1");
//            if (isOnIsland1) {
//                println("On Island 1");
//                SceneObject targetObject = null;
//
//                if (player.getAnimationId() == -1) {
//                    println("Not yet collecting");
//                    if (Skills.RUNECRAFTING.getLevel() >= 9) {
//                        SceneObject rockFragment = SceneObjectQuery.newQuery().name("Rock fragment").inside(Island_1).results().nearestTo(player);
//                        if (rockFragment != null) {
//                            targetObject = rockFragment;
//                        }
//                        if (rockFragment == null) {
//                            println("Rock Fragment not found or not in Island_1.");
//                        }
//                    }
//                    if (targetObject == null || Skills.RUNECRAFTING.getLevel() >= 5) {
//                       SceneObject waterPool = SceneObjectQuery.newQuery().name("Water pool").inside(Island_1).results().nearestTo(player);
//                        if (waterPool != null) {
//                            targetObject = waterPool;
//                        }
//                        if (waterPool == null) {
//                            println("Water pool not found or not in Island_1.");
//                        }
//                    }
//                    if (targetObject == null) {
//                        println("Checking for Cyclone or Mind Storm.");
//                        SceneObject cyclone = SceneObjectQuery.newQuery().name("Cyclone").inside(Island_1).results().nearestTo(player);
//                        SceneObject mindStorm = SceneObjectQuery.newQuery().name("Mind storm").inside(Island_1).results().nearestTo(player);
//
//                        if (cyclone != null) {
//                            targetObject = cyclone;
//                        } else if (mindStorm != null) {
//                            targetObject = mindStorm;
//                        }
//                    }
//
//                    if (targetObject != null) {
//                        println("Interacting with " + targetObject.getName());
//                        targetObject.interact("Siphon");
//                    } else {
//                        println("No suitable SceneObject found on Island 1.");
//                    }
//                } else {
//                    Execution.delay(1000);
//                    println("Already Collecting");
//                }
//            }
//            else{
//                    println("Not on Island 1. Moving to Island 1.");
//                    // Code to navigate to Island_1
//                    // ...
//                }
//            if (player.getAnimationId() == 16596 && isOnIsland1) {
//                Execution.delay(1000); // Delay when the player's animation ID is not 16596
//                }
//        }
        return random.nextLong(1500, 3000);
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
