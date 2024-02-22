//package net.botwithus.api.game.hud;
//
//import net.botwithus.api.game.hud.prayer.Prayer;
//
//public enum InterfaceMode {
//    MODERN(new InterfaceManager()) {
//        @Override
//        public void defineCommonInterfaces() {
//            defineInterface(Prayer.class, "quick_prayer_toggle", 1430);
//        }
//    }, LEGACY(new InterfaceManager()) {
//        @Override
//        public void defineCommonInterfaces() {
//            defineInterface(Prayer.class, "quick_prayer_toggle", 1505);
//        }
//    };
//
//    private final InterfaceManager manager;
//
//    InterfaceMode(InterfaceManager manager) {
//        this.manager = manager;
//    }
//
//    public abstract void defineCommonInterfaces();
//
//    public void defineInterface(Class<?> clazz, int interfaceIndex) {
//        manager.defineInterface(clazz, interfaceIndex);
//    }
//
//    public void defineInterface(Class<?> clazz, String name, int interfaceIndex) {
//        manager.defineInterface(clazz, name, interfaceIndex);
//    }
//
//    public void overrideInterface(Class<?> clazz, int interfaceIndex) {
//        manager.overrideInterface(clazz, interfaceIndex);
//    }
//
//    public void overrideInterface(Class<?> clazz, String name, int interfaceIndex) {
//        manager.overrideInterface(clazz, name, interfaceIndex);
//    }
//
//    public static InterfaceType getInterface(Class<?> clazz) {
//        if (Interface.isLegacyMode()) {
//            return LEGACY.manager.getInterface(clazz);
//        } else {
//            return MODERN.manager.getInterface(clazz);
//        }
//    }
//
//    public static InterfaceType getInterface(Class<?> clazz, String name) {
//        if (Interface.isLegacyMode()) {
//            return LEGACY.manager.getInterface(clazz, name);
//        } else {
//            return MODERN.manager.getInterface(clazz, name);
//        }
//    }
//}
