package net.botwithus;

import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.imgui.ImGuiWindowFlag;
import net.botwithus.rs3.imgui.NativeInteger;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;

import java.util.ArrayList;

public class SkeletonScriptGraphicsContext extends ScriptGraphicsContext {

    private SkeletonScript script;

    public SkeletonScriptGraphicsContext(ScriptConsole scriptConsole, SkeletonScript script) {
        super(scriptConsole);
        this.script = script;
    }

    @Override
    public void drawSettings() {
        if (ImGui.Begin("LazyRuneSpan", ImGuiWindowFlag.None.getValue())) {
            if (ImGui.BeginTabBar("My bar", ImGuiWindowFlag.None.getValue())) {
                if (ImGui.BeginTabItem("Settings", ImGuiWindowFlag.None.getValue())) {
                    ImGui.Text("Welcome to my script!");
                    ImGui.Text("My scripts state is: " + script.getBotState());
                    if (ImGui.Button("Go to marker")) {
                        //button has been clicked
                        script.setBotState(SkeletonScript.BotState.GOTOMARKER);
                    }
                    ImGui.SameLine();
                    if (ImGui.Button("Stop")) {
                        //has been clicked
                        script.setBotState(SkeletonScript.BotState.IDLE);
                    }
                    ImGui.EndTabItem();
                }
            }
            ImGui.End();
        }
    }

    @Override
    public void drawOverlay() { super.drawOverlay(); }
}
