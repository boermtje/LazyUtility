package net.botwithus.api.game.hud;

import com.google.common.flogger.FluentLogger;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.input.GameInput;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;

public class EnterXInterface {
    public static boolean isOpen() {
        return Interfaces.isOpen(1603);
    }
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    public static void enterValue(int value) {
        Execution.delay(RandomGenerator.nextInt(100, 300));
        log.atInfo().log("Entering value: " + value);
        try {
            GameInput.setIntInput(value);
        } catch (Throwable e) {
            log.atSevere().withCause(e).log("Failed to enter quantity in the MakeXInterface");
            e.printStackTrace();
        }
        Execution.delay(RandomGenerator.nextInt(100, 300));
    }

    public static boolean isValueEntered(int value) {
        return Integer.parseInt(getEnteredValue()) == value;
    }

    public static String getEnteredValue() {
        return VarManager.getVarcString(2506);
    }
}
