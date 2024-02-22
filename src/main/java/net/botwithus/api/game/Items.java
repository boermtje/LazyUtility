package net.botwithus.api.game;

import net.botwithus.rs3.game.js5.types.ItemType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Items {
    @NotNull
    public static List<String> getWornEquipmentOptions(ItemType config) {
        String[] wornActions = new String[8];
        String option = config.getStringParam(528);
        if (option != null) {
            wornActions[0] = option;
        }
        option = config.getStringParam(529);
        if (option != null) {
            wornActions[1] = option;
        }
        option = config.getStringParam(530);
        if (option != null) {
            wornActions[2] = option;
        }
        option = config.getStringParam(531);
        if (option != null) {
            wornActions[3] = option;
        }
        option = config.getStringParam(1211);
        if (option != null) {
            wornActions[4] = option;
        }
        option = config.getStringParam(6712);
        if (option != null) {
            wornActions[5] = option;
        }
        option = config.getStringParam(6713);
        if (option != null) {
            wornActions[6] = option;
        }
        option = config.getStringParam(6714);
        if (option != null) {
            wornActions[7] = option;
        }
        return Arrays.asList(wornActions);
    }

    public static List<String> getBankOptions(ItemType config) {
        List<String> options = new ArrayList<>(2);
        String firstOption = config.getStringParam(1264);
        if (firstOption != null) {
            //Passed as option 8
            options.add(0, firstOption);
        }
        String secondOption = config.getStringParam(1265);
        if (secondOption != null) {
            //Passed as option 9
            options.add(1, secondOption);
        }
        return options;
    }
}
