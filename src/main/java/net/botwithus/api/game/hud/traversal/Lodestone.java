package net.botwithus.api.game.hud.traversal;

import com.google.common.flogger.FluentLogger;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;
import net.botwithus.rs3.game.vars.VarManager;

public enum Lodestone {
    AL_KHARID(71565323, 28),
    ANACHRONIA(71565337, 44270),
    ARDOUGNE(71565324, 29),
    ASHDALE(71565346, 35), // TODO Need Varbit to know if they're available or not
    BANDIT_CAMP(71565321, 9482),
    BURTHORPE(71565325, 30),
    CANIFIS(71565339, 18523),
    CATHERBY(71565326, 31),
    CITY_OF_UM(71565348, 35), // TODO Need Varbit to know if they're available or not
    DRAYNOR_VILLAGE(71565327, 32),
    EDGEVILLE(71565328, 33),
    EAGLES_PEAK(71565340, 18524),
    FALADOR(71565329, 34),
    FORT_FORINTHRY(71565335, 35), // TODO Need Varbit to know if they're available or not
    FREMENNIK_PROVINCE(71565341, 18525),
    KARAMJA(71565342, 18526),
    LUMBRIDGE(71565330, 35),
    LUNAR_ISLE(71565322, 9482),
    MENAPHOS(71565336, 35), // TODO Need Varbit to know if they're available or not
    OOGLOG(71565343, 18527),
    PORT_SARIM(71565331, 36),
    PRIFDDINAS(71565347, 24967),
    SEERS_VILLAGE(71565332, 37),
    TAVERLY(71565333, 38),
    TIRANNWN(71565344, 18528),
    VARROCK(71565334, 39),
    WILDERNESS_CRATER(71565345, 18529),
    YANILLE(71565338, 40);

    private final int interactId;
    private final int varbitId;
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    Lodestone(int interactId, int varbitId) {
        this.interactId = interactId;
        this.varbitId = varbitId;
    }

    //TODO: Update to no longer use MiniMenu.doAction
        public boolean teleport() {
        var player = Client.getLocalPlayer();
        if (player == null) {
            return false;
        }
//        var coordinate = player.getCoordinate();
        boolean validate = !LodestoneNetwork.isOpen();
        log.atInfo().log("[Lodestone] LodestoneNetworkIsNotOpen: " + validate);
        if (validate) {
            LodestoneNetwork.open();
            Execution.delay(RandomGenerator.nextInt(600, 900));
        }
        if (MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, interactId)) {
            int wax = VarManager.getVarbitValue(28623);
            int quick = VarManager.getVarbitValue(28622);
            if (quick == 1 && wax > 0) {
                Execution.delay(RandomGenerator.nextInt(4500, 6500));
            } else {
                Execution.delay(RandomGenerator.nextInt(12000, 14000));
            }
            return true;
        } else {
            return false;
        }
//        Time.waitUntil(() -> {
//            var newCoordinate = ApexPlayer.getPosition();
//            return coordinate != null && newCoordinate != null && !coordinate.equals(newCoordinate);
//        }, RandomGenerator.nextInt(12000, 14000), 1000);
    }

    public boolean isAvailable() {
        var result = VarManager.getVarbitValue(varbitId);
        switch (this) {
            case LUNAR_ISLE -> {
                return result >= 100;
            }
            case BANDIT_CAMP -> {
                return result >= 15;
            }
        }
        return result == 1;
    }
}
