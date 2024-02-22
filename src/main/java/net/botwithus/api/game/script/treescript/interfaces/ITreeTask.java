package net.botwithus.api.game.script.treescript.interfaces;


import net.botwithus.api.game.script.treescript.TreeTask;

/**
 * Represents a task in a tree structure. This is a common interface for
 * all types of tasks.
 */
public interface ITreeTask {

    /**
     * Defines the task to execute.
     */
    void execute();

    /**
     * Defines the task to execute upon success.
     * @return The TreeTask to execute upon success.
     */
    TreeTask successTask();

    /**
     * Conditional argument used to determine use of successTask or failureTask
     * @return The boolean result of the conditional statement
     */
    boolean validate();

    /**
     * Defines the task to execute upon failure.
     * @return The TreeTask to execute upon failure.
     */
    TreeTask failureTask();

    /**
     * Defines if a given TreeTask is a LeafTask or BranchTask
     */
    boolean isLeaf();
}
