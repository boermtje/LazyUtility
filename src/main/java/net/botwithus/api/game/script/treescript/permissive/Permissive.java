package net.botwithus.api.game.script.treescript.permissive;

import com.google.common.flogger.FluentLogger;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;

public class Permissive {
    private String name;
    private Callable<Boolean> predicate;
    private Result lastResult = new Result(false);
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    public Permissive(String name, Callable<Boolean> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    @SneakyThrows
    public boolean isMet() {
        try {
            var result = predicate.call();
            log.atInfo().log("[" + Thread.currentThread().getName() + "]: " + "[Permissive] " + name + ": " + result);
            lastResult = new Result(result);
            return result;
        } catch (Exception e) {
            log.atSevere().withCause(e).log("Exception thrown in permissive predicate: " + name);
            lastResult = new Result(false);
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public Result getLastResult() {
        return lastResult;
    }
}
