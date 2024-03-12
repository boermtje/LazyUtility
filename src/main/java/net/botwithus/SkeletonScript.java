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
                Execution.delay(handleDialogInteraction());
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
        println("Entered AutoDialog method.");

        // Simple check if Dialog is open
        if (Dialog.isOpen()) {
            println("Dialog is open, attempting to select.");
            Execution.delay(random.nextLong(500, 1000)); // Shorter delay for testing

            boolean selectionMade = Dialog.select();
            if (!selectionMade) {
                println("No more selections could be made, or Dialog.select() failed.");
            } else {
                println("Dialog option selected successfully.");
            }
        } else {
            println("Dialog is not open.");
        }
        println("Exiting AutoDialog method, setting bot state to IDLE.");
        setBotState(BotState.IDLE);
        return random.nextLong(1000, 3000);
    }

    private long handleDialogInteraction() {
        // Fetch the available dialog options
        List<String> availableOptions = Dialog.getOptions();
        println("Available options: " + availableOptions);

        // Go through the dialog options set by the user and interact accordingly
        for (int option : dialogOptions) {
            // If the option number is not 0 and within the range of available options
            if (option > 0 && option <= availableOptions.size()) {
                String optionText = availableOptions.get(option - 1); // Adjust for index starting at 0
                println("Selecting dialog option: " + optionText);
                Execution.delay(random.nextLong(500, 750)); // Delay to simulate user interaction timing
                Dialog.interact(optionText); // Interact with the dialog option text

                // Wait for the next set of dialog options to be available, if necessary
                // You might need to implement a custom method for checking dialog change
                waitForDialogChange();
            }
        }
        // After handling all dialog interactions, return to IDLE or another appropriate state
        setBotState(BotState.IDLE);
        return random.nextLong(1000, 3000); // Return some delay before the next action
    }

    // Implement waiting for dialog change based on your specific needs, this is just a placeholder
    private void waitForDialogChange() {
        Execution.delay(1000); // Wait for 1 second for dialog to update
        // You may need more sophisticated logic to check if the dialog has updated
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
