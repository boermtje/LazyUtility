package net.botwithus.api.game.script.treescript;

import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.script.TickingScript;
import net.botwithus.rs3.script.config.ScriptConfig;

/**
 * An abstract implementation of a TreeScript that extends from the Script class. This class is designed
 * to facilitate the use of a tree-based task structure within the context of a script. It holds a reference
 * to a root task that acts as an entry point to the task tree. The task tree is traversed in the onLoop method.
 * <p>
 * Subclasses must provide the root task through the implementation of getRootTask method. The root task should
 * be a BranchTask that serves as the starting point of the task tree.
 * <p>
 * A TreeScript may be used for structuring complex or dynamic scripts, where the script behavior may need
 * to change depending on various conditions (represented by the validation of tasks in the tree).
 */
public abstract class TreeTickingScript extends TickingScript {
    private BranchTask rootTask;

    /**
     * Constructs a new TreeScript instance.
     *
     * @param name             The name of the script.
     * @param scriptConfig     The script configuration associated with this script.
     * @param scriptDefinition The script definition associated with this script.
     */
    public TreeTickingScript(String name, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(name, scriptConfig, scriptDefinition);
    }

    /**
     * Retrieves the root task of the task tree associated with this script. This method is
     * abstract and must be implemented by subclasses to return the appropriate root task.
     * This is the entry point to the task tree that the script will execute.
     *
     * @return The root BranchTask of the task tree.
     */
    public abstract BranchTask getRootTask();


    /**
     * Called in each iteration of the script's main loop. If the root task is already initialized,
     * it traverses the task tree from the root. If the root task is null, it's initialized by calling
     * the getRootTask() method.
     * <p>
     * The traversal strategy depends on the tree task structure, and whether tasks validate or not.
     * Non-leaf tasks provide subsequent tasks upon their validation success or failure. Leaf tasks
     * get executed when reached.
     */
    @Override
    public void onTick(LocalPlayer localPlayer) {
        if (rootTask != null) {
            TreeTask.traverse(this, rootTask);
        } else {
            rootTask = getRootTask();
        }
    }
}
