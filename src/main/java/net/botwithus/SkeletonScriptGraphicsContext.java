package net.botwithus;

import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.imgui.ImGuiWindowFlag;
import net.botwithus.rs3.imgui.NativeInteger;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SkeletonScriptGraphicsContext extends ScriptGraphicsContext {


    // Single-element arrays to hold integer values for ImGui input
    private String xInputText = "0";
    private String yInputText = "0";
    private String zInputText = "0";
    // New fields for storing XYZ coordinates and saved locations
    private NativeInteger xInput = new NativeInteger(0);
    private NativeInteger yInput = new NativeInteger(0);
    private NativeInteger zInput = new NativeInteger(0);
    private StringBuilder saveName = new StringBuilder(64); // StringBuilder for mutable text
    private Map<String, int[]> savedLocations = new HashMap<>(); // Map to store named locations
    public int[] dialogOptions = new int[9];
    private SkeletonScript script;
    // Buffer for the location name input, with a reasonable size for input
    private final int NAME_BUFFER_SIZE = 64;
    private byte[] nameBuffer = new byte[NAME_BUFFER_SIZE];

    class MutableInt {
        public int value;

        public MutableInt(int value) {
            this.value = value;
        }
    }

    public SkeletonScriptGraphicsContext(ScriptConsole scriptConsole, SkeletonScript script) {
        super(scriptConsole);
        this.script = script;
        // Initialize arrays with current values
    }

        @Override
        public void drawSettings () {
            if (ImGui.Begin("LazyUtility", ImGuiWindowFlag.None.getValue())) {
                if (ImGui.BeginTabBar("My bar", ImGuiWindowFlag.None.getValue())) {
                    if (ImGui.BeginTabItem("Navigator", ImGuiWindowFlag.None.getValue())) {
                        ImGui.Text("My scripts state is: " + script.getBotState());
                        if (ImGui.Button("Go to marker")) {
                            //button has been clicked
                            script.setBotState(SkeletonScript.BotState.GOTOMARKER);
                        }
                        ImGui.SameLine();
                        if (ImGui.Button("Interrupt Traversal")) {
                            //has been clicked
                            script.setBotState(SkeletonScript.BotState.IDLE);
                        }
                        ImGui.Text("Marker " + script.resolveMarker());
                        ImGui.Text("Player " + script.resolvePlayerCoords());



                        // Text input fields for X, Y, and Z coordinates
                        xInputText = ImGui.InputText("X", xInputText);
                        yInputText = ImGui.InputText("Y", yInputText);
                        zInputText = ImGui.InputText("Z", zInputText);

                        // Parse integers from text inputs
                        try {
                            int x = Integer.parseInt(xInputText);
                            int y = Integer.parseInt(yInputText);
                            int z = Integer.parseInt(zInputText);

                            // Update the NativeInteger fields
                            xInput.set(x);
                            yInput.set(y);
                            zInput.set(z);

                            // "Go To" button logic
                            if (ImGui.Button("Go To")) {
                                script.setBotState(SkeletonScript.BotState.GOTOXYZ);
                                script.gotoX = x;
                                script.gotoY = y;
                                script.gotoZ = z;
                            }
                        } catch (NumberFormatException e) {
                            // Handle invalid number formats
                            System.err.println("Invalid input: X, Y, and Z values must be integers.");
                        }

                        // Text input for the save name
                        String nameInput = saveName.toString();
                        ImGui.InputText("Name", nameInput);
                        saveName.setLength(0); // Clear the StringBuilder
                        saveName.append(nameInput); // Append the new input from ImGui


                        if (ImGui.Button("Save")) {
                            // Only save if there is a name entered
                            if (saveName.length() > 0) {
                                savedLocations.put(saveName.toString(), new int[]{xInput.get(), yInput.get(), zInput.get()});
                                saveName.setLength(0); // Clear after saving
                            }
                        }

                        // Displaying saved locations as buttons
                        for (Map.Entry<String, int[]> entry : savedLocations.entrySet()) {
                            if (ImGui.Button(entry.getKey())) {
                                // Load and use the saved XYZ coordinates
                                int[] coords = entry.getValue();
                                script.gotoX = coords[0];
                                script.gotoY = coords[1];
                                script.gotoZ = coords[2];
                                script.setBotState(SkeletonScript.BotState.GOTOXYZ);
                            }
                        }
                        ImGui.EndTabItem();
                    }



                    if (ImGui.BeginTabItem("Auto Dialog", ImGuiWindowFlag.None.getValue())) {
                        ImGui.Text("My scripts state is: " + script.getBotState());
                        if (ImGui.Button("Auto Dialog")) {
                            //has been clicked
                            script.setBotState(SkeletonScript.BotState.AUTODIALOG);
                        }
                        ImGui.SameLine();
                        if (ImGui.Button("Stop")) {
                            //has been clicked
                            script.setBotState(SkeletonScript.BotState.IDLE);
                        }
                        for (int i = 0; i < dialogOptions.length; i++) {
                            int dialogOption = dialogOptions[i]; // Temp copy for modification

                            ImGui.Text("Dialog option " + (i + 1) + ": ");
                            ImGui.SameLine();

                            if (ImGui.Button("-##" + i)) {
                                dialogOption = Math.max(0, dialogOption - 1); // Decrease with minimum limit
                            }

                            ImGui.SameLine();
                            ImGui.Text(String.valueOf(dialogOption)); // Display the current value
                            ImGui.SameLine();

                            if (ImGui.Button("+##" + i)) {
                                dialogOption = Math.min(10, dialogOption + 1); // Increase with maximum limit
                            }

                            dialogOptions[i] = dialogOption; // Update the actual array with the new value
                        }
                        // Update the script's dialogOptions with the changes made through the GUI
                        updateDialogOptionsInScript();
                        ImGui.EndTabItem();
                    }
                    ImGui.EndTabBar();
                }
                ImGui.End();
            }
        }

    // Helper method to extract a string from a ByteBuffer
    private String extractString(ByteBuffer buffer) {
        byte[] bytes;
        int oldPosition = buffer.position();
        buffer.flip();
        if (buffer.hasRemaining()) {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        } else {
            bytes = new byte[0];
        }
        buffer.position(oldPosition);
        return new String(bytes, StandardCharsets.UTF_8).trim();
    }

    // Call this method to get the options selected by the user
    public void updateDialogOptionsInScript() {
        script.setDialogOptions(dialogOptions);
    }

    @Override
    public void drawOverlay() { super.drawOverlay(); }
}
