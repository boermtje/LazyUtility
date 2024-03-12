package net.botwithus;

import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.imgui.ImGuiWindowFlag;
import net.botwithus.rs3.imgui.NativeInteger;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;

import java.util.ArrayList;

public class SkeletonScriptGraphicsContext extends ScriptGraphicsContext {

    public int[] dialogOptions = new int[9];
    private SkeletonScript script;

    public SkeletonScriptGraphicsContext(ScriptConsole scriptConsole, SkeletonScript script) {
        super(scriptConsole);
        this.script = script;

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
                        // ... existing code ...
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
                        ImGui.EndTabItem();
                    }
                    ImGui.EndTabBar();
                }
                ImGui.End();
            }
        }

    // Call this method to get the options selected by the user
    public void updateDialogOptionsInScript() {
        script.setDialogOptions(dialogOptions);
    }

    @Override
    public void drawOverlay() { super.drawOverlay(); }
}
