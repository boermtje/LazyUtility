package net.botwithus;

import net.botwithus.api.game.hud.Dialog;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.js5.types.vars.VarDomainType;
import net.botwithus.rs3.game.movement.Movement;
import net.botwithus.rs3.game.movement.NavPath;
import net.botwithus.rs3.game.movement.TraverseEvent;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.characters.player.Player;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.imgui.NativeInteger;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.game.Area;
import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.*;
import net.botwithus.rs3.util.RandomGenerator;
import java.util.*;

public class SkeletonScript extends LoopingScript {

    private int[] dialogOptions; // Add this field

    // Add methods to set and get dialogOptions
    public void setDialogOptions(int[] dialogOptions) {
        this.dialogOptions = dialogOptions;
    }

    public int[] getDialogOptions() {
        return dialogOptions;
    }
    private BotState botState = BotState.IDLE;
    private Random random = new Random();

    /////////////////////////////////////Botstate//////////////////////////
    enum BotState {
        //define your own states here
        IDLE,
        GOTOMARKER,
        AUTODIALOG,

        //...
    }

    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
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
            case IDLE -> {
                println("We're idle!");
                Execution.delay(random.nextLong(1000, 3000));
            }
            case GOTOMARKER -> {
                Execution.delay(GoToMarker());
            }
            case AUTODIALOG -> {
                Execution.delay(AutoDialog());
            }
        }
    }

    public Coordinate resolvePlayerCoords() {
        Player localPlayer = Client.getLocalPlayer();
        Coordinate coordinates = localPlayer.getCoordinate();
        int x = coordinates.getX();
        int y = coordinates.getY();
        int z = coordinates.getZ();
        return new Coordinate(x, y, z);
    }

    public Coordinate resolveMarker() {
        int tileHash = VarManager.getVarValue(VarDomainType.PLAYER, 2807);
        int x = (tileHash >> 14) & 0x3fff;
        int y = tileHash & 0x3fff;
        int z = (tileHash >> 28) & 0x3;
        return new Coordinate(x, y, z);
    }

    private long AutoDialog() {
        int[] dialogOptions = getDialogOptions(); // Retrieve dialog options
        int interactionCount = 0;
        long startTime = System.currentTimeMillis(); // Record the start time
        long timeout = 10000; // 10 seconds timeout

        println("Starting auto-dialog sequence.");

        while (Dialog.isOpen() && (System.currentTimeMillis() - startTime) < timeout) {
            println("Attempting to select a dialog option...");
            Execution.delay(random.nextLong(1000, 1758));

            boolean selectionMade = Dialog.select(); // Select without parameters
            if (!selectionMade) {
                println("No more selections could be made.");

                if (interactionCount < dialogOptions.length && dialogOptions[interactionCount] != 0) {
                    println("Interacting with dialog option: " + dialogOptions[interactionCount]);
                    Execution.delay(random.nextLong(1000, 1758));
                    Dialog.interact(String.valueOf(dialogOptions[interactionCount]));
                    interactionCount++; // Move to next interaction
                } else {
                    println("Exiting dialog loop - either all options are exhausted or current option is 0.");
                    setBotState(BotState.IDLE); // Change state to IDLE
                    break;
                }
            }

            // Additional check for dialog closure
            if (!Dialog.isOpen()) {
                println("Dialog closed after interaction.");
                setBotState(BotState.IDLE); // Change state to IDLE
                break;
            }

            // Timeout check
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                println("AutoDialog timeout reached. Exiting.");
                setBotState(BotState.IDLE); // Change state to IDLE
                break;
            }
        }

        // Additional post-loop handling
        if (!Dialog.isOpen() && interactionCount < dialogOptions.length) {
            println("Dialog closed unexpectedly. Changing to IDLE state.");
            setBotState(BotState.IDLE); // Change state to IDLE
        } else if (!Dialog.isOpen()) {
            println("Dialog interaction complete. Changing to IDLE state.");
            setBotState(BotState.IDLE); // Change state to IDLE
        }

        return random.nextLong(1000, 3000);
    }

    private long GoToMarker() {
        Coordinate marker = resolveMarker();
        println(marker);
        if (Movement.traverse(NavPath.resolve(marker).interrupt(event -> botState == BotState.IDLE)) == TraverseEvent.State.FINISHED) {
            println("Traversed to marker");
            botState = BotState.IDLE;
        }
        else {
            println("Failed to traverse to marker");
        }
        return random.nextLong(1000, 3000);
    }


    ////////////////////Botstate/////////////////////
    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }
}
