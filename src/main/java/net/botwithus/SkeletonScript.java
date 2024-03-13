package net.botwithus;

import net.botwithus.api.game.hud.Dialog;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.js5.types.vars.VarDomainType;
import net.botwithus.rs3.game.movement.Movement;
import net.botwithus.rs3.game.movement.NavPath;
import net.botwithus.rs3.game.movement.TraverseEvent;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.characters.player.Player;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.game.Coordinate;

import java.util.*;

public class SkeletonScript extends LoopingScript {

    public Map<String, int[]> getSavedLocationsForGraphicsContext() {
        return savedLocations;
    }
    private Map<String, int[]> savedLocations = new HashMap<>();
    public int gotoX, gotoY, gotoZ;
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
        GOTOXYZ,
        //...
    }

    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.isBackgroundScript = true;
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        this.savedLocations = new HashMap<>(); // Initialize the map
        loadConfiguration(); // Load configuration when the script starts
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
            case GOTOXYZ -> {
                Execution.delay(handleGotoXYZ());
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

    public Coordinate resolveXYZ() {
        return new Coordinate(gotoX, gotoY, gotoZ);
    }

    private long handleGotoXYZ() {
        Coordinate xyz = resolveXYZ();
        println("Navigating to coordinates: " + xyz);
        if (Movement.traverse(NavPath.resolve(xyz).interrupt(event -> botState == BotState.IDLE)) == TraverseEvent.State.FINISHED) {
            println("Traversed to XYZ");
            botState = BotState.IDLE;
        }
        else {
            print(xyz);
            println("Failed to traverse to XYZ");
        }
        return random.nextLong(1000, 3000);
    }

    private long AutoDialog() {
        int[] dialogOptions = getDialogOptions(); // Retrieve dialog options set by the user
        println("User input dialog options: " + Arrays.toString(dialogOptions)); // Log the user input options
        int interactionCount = 0;

        println("Starting auto-dialog sequence.");

        while (Dialog.isOpen()) {
            println("Attempting to select a dialog option...");
            Execution.delay(random.nextLong(1000, 1758));

            // Attempt to make a selection in the dialog to proceed to the next set of options if available
            boolean selectionMade = Dialog.select();
            if (!selectionMade) {
                println("No more selections could be made, or Dialog.select() failed.");
                // If no selection could be made, it could be due to a prompt or the end of the dialog
                // In such cases, attempt to interact with the next dialog option number as set by the user
                if (dialogOptions[interactionCount] > 0) {
                    // Delay to simulate realistic user interaction timing
                    Execution.delay(random.nextLong(1000, 1758));

                    // Try interacting with the dialog option by its number
                    if (interactWithOptionNumber(dialogOptions[interactionCount])) {
                        println("Interacted with dialog option number: " + dialogOptions[interactionCount]);
                    } else {
                        println("Failed to interact with dialog option number: " + dialogOptions[interactionCount] +
                                ". This could be due to an invalid option number or another issue.");
                        // Since interaction failed, set the state to IDLE to avoid getting stuck
                        setBotState(BotState.IDLE);
                        return random.nextLong(1000, 3000);
                    }
                } else {
                    println("Dialog option number is 0 or negative, which is not valid for interaction.");
                    setBotState(BotState.IDLE); // Change state to IDLE
                    break;
                }

                // Increment interactionCount to attempt the next user-set dialog option number
                interactionCount++;

                // Check if the dialog has closed after the interaction
                if (!Dialog.isOpen()) {
                    println("Dialog closed after interaction.");
                    break;
                }
            }

            // Additional check for dialog closure
            if (!Dialog.isOpen()) {
                println("Dialog closed after interaction.");
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


    private boolean interactWithOptionNumber(int optionNumber) {
        // Retrieve the current dialog options
        List<String> options = Dialog.getOptions();

        // Validate the option number
        if (optionNumber <= 0 || optionNumber > options.size()) {
            println("Invalid dialog option number: " + optionNumber);
            return false;
        }

        // Adjust for index (list is 0-indexed but dialog options start at 1)
        String optionText = options.get(optionNumber - 1);

        // Now interact with the dialog using the option text
        boolean interactionSuccess = Dialog.interact(optionText);

        // Output the result of the interaction attempt
        println("Attempted to interact with dialog option " + optionNumber + ": " + optionText +
                ". Success: " + interactionSuccess);

        return interactionSuccess;
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

    // Add getters and setters for savedLocations
    public Map<String, int[]> getSavedLocations() {
        return savedLocations;
    }

    public void setSavedLocations(Map<String, int[]> savedLocations) {
        this.savedLocations = savedLocations;
    }

    ////////////////Save & Load Config/////////////////////
    void loadConfiguration() {
        try {
            String savedLocationsString = configuration.getProperty("savedLocations");
            if (savedLocationsString != null && !savedLocationsString.isEmpty()) {
                String[] locations = savedLocationsString.split(";");
                for (String location : locations) {
                    String[] parts = location.split(":");
                    String name = parts[0];
                    String[] coords = parts[1].split(",");
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);
                    int z = Integer.parseInt(coords[2]);
                    savedLocations.put(name, new int[]{x, y, z});
                    getSavedLocationsForGraphicsContext();
                }
                println("Configuration loaded successfully.");
            }
        } catch (Exception e) {
            println("Error loading configuration: \n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            println("This is a non-fatal error, you can ignore it.");
        }
    }

    void saveConfiguration() {
        try {
            StringBuilder locationsBuilder = new StringBuilder();
            for (Map.Entry<String, int[]> entry : savedLocations.entrySet()) {
                locationsBuilder.append(entry.getKey())
                        .append(":")
                        .append(entry.getValue()[0]).append(",")
                        .append(entry.getValue()[1]).append(",")
                        .append(entry.getValue()[2]).append(";");
            }

            configuration.addProperty("savedLocations", locationsBuilder.toString());
            configuration.save();
            println("Configuration saved successfully.");
        } catch (Exception e) {
            println("Error saving configuration: \n" + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            println("This is a non-fatal error, you can ignore it.");
        }
    }


    ////////////////////Botstate/////////////////////
    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }
}
