//package net.botwithus.api.game.skills.slayer;
//
//import com.sun.jdi.Value;
//import net.botwithus.rs3.types.EnumType;
//import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
//import net.botwithus.rs3.game.vars.VarManager;
//
//public final class Slayer {
//
//    private Slayer() {}
//
//    /**
//     *
//     *   Gets the current reaper assignment.
//     *   @return The current reaper assignment, or "none" if none is active.
//     */
//    public String getReaperAssignment() {
//        int taskId = VarManager.getVarbitValue(22901);
//        EnumType tasks = ConfigManager.getEnumType(9197);
//        Value value = tasks.variants.get(taskId);
//        if(value != null) {
//            return value.getStringValue();
//        }
//        return null;
//    }
//
//    /**
//     *
//     *   Gets the remaining reaper kills for the current task.
//     *
//     *   @return The remaining reaper kills for the current task.
//     */
//    public static int getRemainingReaperKills() {
//        return VarManager.getVarbitValue(22902);
//    }
//
//}
