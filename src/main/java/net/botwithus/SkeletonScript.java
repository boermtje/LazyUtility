package net.botwithus;

import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.events.impl.ChatMessageEvent;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
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

import java.util.Random;

public class SkeletonScript extends LoopingScript {

    private BotState botState = BotState.IDLE;
    private Random random = new Random();


    /////////////////////////////////////Botstate//////////////////////////
    enum BotState {
        //define your own states here
        IDLE,
        SKILLING,
        //...
    }

    private Area Island_1 = new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1));

    /////////////////////////////////////ChatMessage Stunned + No Food//////////////////////////
    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);

//        // Subscribe to ChatMessageEvent
//        subscribe(ChatMessageEvent.class, chatMessageEvent -> {
//            // Idles when stunned
//            if (chatMessageEvent.getMessage().contains("stunned")) {
//                Execution.delay(4000); // Idle for 4 seconds
//                println("Got hit, Idle");
//            }
//        });
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

    /////////////////////Skilling + Eating + Backpack Full = Idle////////////////////////
    private long handleSkilling(LocalPlayer player) {
        if (!hasRune_Essence()) {
            println("No Rune Essence, going to collect");
            Npc Floating_Essence = NpcQuery.newQuery().name("Floating essence").results().nearest();
            if (Floating_Essence != null) {
                println("found");
            }
            Floating_Essence.interact("Collect");
            println("Collecting Essence");
        }

        boolean isOnIsland1 = Island_1.contains(player.getCoordinate());

//Use an hashmap to store the coordinates of the islands and check if the player is on the island
//        HashMap<String, Area> islands = new HashMap<>();
//        islands.put("Island_1", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_2", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_3", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_4", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_5", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_6", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_7", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_8", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));
//        islands.put("Island_9", new Area.Rectangular(new Coordinate(3989,6095,1), new Coordinate(4007,6119,1)));

        //use the hashmap to check if the player is on the island
//        for (Map.Entry<String, Area> entry : islands.entrySet()) {
//            if (entry.getValue().contains(player.getCoordinate())) {
//                println("On " + entry.getKey());
//            } else {
//                println("Not on " + entry.getKey());
//            }
//        }

        //Use an hashmap for target objects
//        HashMap<String, SceneObject> targetObjects = new HashMap<>();
//        targetObjects.put("Rock Fragment", SceneObjectQuery.newQuery().name("Rock fragment").inside(Island_1).results().nearestTo(player));
//        targetObjects.put("Water Pool", SceneObjectQuery.newQuery().name("Water pool").inside(Island_1).results().nearestTo(player));
//        targetObjects.put("Cyclone", SceneObjectQuery.newQuery().name("Cyclone").inside(Island_1).results().nearestTo(player));
//        targetObjects.put("Mind Storm", SceneObjectQuery.newQuery().name("Mind storm").inside(Island_1).results().nearestTo(player));

        //Use the hashmap to interact with the target object
//        for (Map.Entry<String, SceneObject> entry : targetObjects.entrySet()) {
//            if (entry.getValue() != null) {
//                println("Interacting with " + entry.getKey());
//                entry.getValue().interact("Siphon");
//            } else {
//                println(entry.getKey() + " not found or not in Island_1.");
//            }
//        }

        //If the player is under level 9, farm on Island 1
        //If the player is on Island 1, collect from the Rock Fragment, Water Pool, Cyclone or Mind Storm
        //If the player is not on Island 1, move to Island 1
        //If the player is already collecting, delay for 1 second

        //If the player's animation ID is not 16596, delay for 1 second
        //If the player's animation ID is 16596 and the player is on Island 1, delay for 1 second

        if (Skills.RUNECRAFTING.getLevel() <= 9) {
            println("Under level 9, farming on Island 1");
            if (isOnIsland1) {
                println("On Island 1");
                SceneObject targetObject = null;

                if (player.getAnimationId() == -1) {
                    println("Not yet collecting");
                    if (Skills.RUNECRAFTING.getLevel() >= 9) {
                        SceneObject rockFragment = SceneObjectQuery.newQuery().name("Rock fragment").inside(Island_1).results().nearestTo(player);
                        if (rockFragment != null) {
                            targetObject = rockFragment;
                        }
                        if (rockFragment == null) {
                            println("Rock Fragment not found or not in Island_1.");
                        }
                    }
                    if (targetObject == null || Skills.RUNECRAFTING.getLevel() >= 5) {
                       SceneObject waterPool = SceneObjectQuery.newQuery().name("Water pool").inside(Island_1).results().nearestTo(player);
                        if (waterPool != null) {
                            targetObject = waterPool;
                        }
                        if (waterPool == null) {
                            println("Water pool not found or not in Island_1.");
                        }
                    }
                    if (targetObject == null) {
                        println("Checking for Cyclone or Mind Storm.");
                        SceneObject cyclone = SceneObjectQuery.newQuery().name("Cyclone").inside(Island_1).results().nearestTo(player);
                        SceneObject mindStorm = SceneObjectQuery.newQuery().name("Mind storm").inside(Island_1).results().nearestTo(player);

                        if (cyclone != null) {
                            targetObject = cyclone;
                        } else if (mindStorm != null) {
                            targetObject = mindStorm;
                        }
                    }

                    if (targetObject != null) {
                        println("Interacting with " + targetObject.getName());
                        targetObject.interact("Siphon");
                    } else {
                        println("No suitable SceneObject found on Island 1.");
                    }
                } else {
                    Execution.delay(1000);
                    println("Already Collecting");
                }
            }
            else{
                    println("Not on Island 1. Moving to Island 1.");
                    // Code to navigate to Island_1
                    // ...
                }
            if (player.getAnimationId() == 16596 && isOnIsland1) {
                Execution.delay(1000); // Delay when the player's animation ID is not 16596
                }
        }
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
