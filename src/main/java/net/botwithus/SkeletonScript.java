package net.botwithus;

import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.events.impl.ChatMessageEvent;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
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

    private Area Island_1 = new Area.Rectangular(new Coordinate(3989,6095,0), new Coordinate(4007,6119,0));

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
        if (!hasRune_Essence()){
            println("No Rune Essence, going to collect");
            Npc Floating_Essence = NpcQuery.newQuery().name("Floating essence").results().nearest();
            if (Floating_Essence != null) {println("found");}
            Floating_Essence.interact("Collect");
            println("Collecting Essence");
            return random.nextLong(1500, 3000);}
        if (Skills.RUNECRAFTING.getLevel() <9){
            if (Island_1 != null){
            println("On Island 1");
                if (Skills.RUNECRAFTING.getLevel() <1){

                }
                if (Skills.RUNECRAFTING.getLevel() <5){

                }
                if (Skills.RUNECRAFTING.getLevel() <9){
                }
            }
            //else traverse to Island_1
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
