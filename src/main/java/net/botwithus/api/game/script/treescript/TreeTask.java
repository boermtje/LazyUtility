package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.script.treescript.interfaces.ITreeTask;
import net.botwithus.api.game.script.treescript.permissive.Result;
import net.botwithus.rs3.script.Script;

/**
 * Represents a task in a tree structure. This is an abstract base class for
 * all types of tasks.
 */
public abstract class TreeTask implements ITreeTask {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    protected Result latestValidate = new Result(false);

    private Script script;
    private String desc = "";

    public TreeTask(Script script) {
        this.script = script;
    }

    public TreeTask(Script script, String desc) {
        this.script = script;
        this.desc = desc;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void execute();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask successTask();

    /** {@inheritDoc} */
    @Override
    public abstract boolean validate();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask failureTask();

    /** {@inheritDoc} */

    @Override
    public abstract boolean isLeaf();

    public String getDesc() {
        return desc;
    }

    public Script getScript() {
        return script;
    }

    /**
     * Traverses the given tree of tasks, executing the appropriate task
     * based on the result of validation.
     * @param treeTask The root task of the tree to traverse.
     */
    public static void traverse(Script script, TreeTask treeTask) {
        var validate = treeTask.validate();
        treeTask.setLatestValidate(validate);
        if (!treeTask.isLeaf()) {
            var msg = "[TreeTaskBranch] " + (treeTask.getDesc().length() > 0 ? treeTask.getDesc() : treeTask.getClass().getSimpleName()) + ": " + validate;
            log.atInfo().log(msg);
            script.println(msg);
            traverse(script, validate ? treeTask.successTask() : treeTask.failureTask());
        } else {
            treeTask.execute();
        }
    }

    public Result getLatestValidate() {
        return latestValidate;
    }

    public void setLatestValidate(boolean lastLoopValidate) {
        this.latestValidate = new Result(lastLoopValidate);
    }
}
