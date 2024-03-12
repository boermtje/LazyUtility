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
        int[] dialogOptions = getDialogOptions(); // Assuming 'graphics' is an instance of SkeletonScriptGraphicsContext
        int interactionCount = 0; // Keep track of the number of interactions

        println("Starting auto-dialog sequence.");

        while (Dialog.isOpen()) {
            println("Attempting to select a dialog option...");
            Execution.delay(random.nextLong(1000,1758));
            boolean selectionMade = Dialog.select(); // Call select() without parameters
            if (!selectionMade) {
                println("No more selections could be made.");
                // When there are no more dialog options to select, interact with the corresponding dialog option
                if (interactionCount < dialogOptions.length && dialogOptions[interactionCount] != 0) {
                    println("Interacting with dialog option: " + dialogOptions[interactionCount]);
                    Execution.delay(random.nextLong(1000,1758));
                    Dialog.interact(String.valueOf(dialogOptions[interactionCount]));
                    interactionCount++; // Move to the next interaction
                    if (interactionCount >= dialogOptions.length) {
                        println("All dialog options have been exhausted.");
                        break; // All dialog options have been exhausted
                    }
                    // Ensure the dialog is still open before attempting to select again
                    if (!Dialog.isOpen()) {
                        println("Dialog closed after interaction.");
                        break;
                    }
                    println("Re-selecting after interaction.");
                    Execution.delay(random.nextLong(1000,1758));
                    Dialog.select(); // Attempt to select the next option after interaction
                } else {
                    // All dialog options have been exhausted or are set to 0, exit the loop
                    println("Exiting dialog loop - either all options are exhausted or the current option is 0.");
                    break;
                }
            }
        }

        if (!Dialog.isOpen()) {
            // Dialog interaction finished or was never open, return to regular processing
            println("Dialog interaction has finished or was never open. Setting bot state to IDLE.");
            setBotState(BotState.IDLE); // Or another appropriate state
        } else {
            println("Dialog interaction complete.");
        }

        return random.nextLong(1000, 3000); // Return some delay before the next action
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
