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

    private BotState botState = BotState.IDLE;
    private SkeletonScriptGraphicsContext graphics;
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
        int[] dialogOptions = graphics.getDialogOptions(); // Assuming sgc is an instance of SkeletonScriptGraphicsContext
        int dialogIndex = 0; // Start from the first dialog option
        int interactionCount = 0; // Keep track of the number of interactions

        while (Dialog.isOpen()) {
            int result = Dialog.select(dialogIndex);
            if (result < 0) {
                // When the dialog selection returns negative, interact with the corresponding dialog option
                // and then reset the index to continue with the next dialog interaction
                if (dialogOptions[interactionCount] != 0) { // If the dialog option is not 0
                    Dialog.interact(String.valueOf(dialogOptions[interactionCount]));
                    interactionCount++; // Move to the next interaction
                    if (interactionCount >= dialogOptions.length) {
                        break; // All dialog options have been exhausted
                    }
                }
                dialogIndex = 0; // Reset index after an interaction
            } else {
                dialogIndex++; // Move to the next dialog option if possible
            }
        }

        if (!Dialog.isOpen()) {
            // Dialog interaction finished or was never open, return to regular processing
            setBotState(BotState.IDLE); // Or another appropriate state
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
