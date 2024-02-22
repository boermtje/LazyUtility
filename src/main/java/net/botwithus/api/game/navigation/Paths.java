package net.botwithus.api.game.navigation;

import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

@ApiStatus.Experimental
public class Paths {
    private static final ConcurrentHashMap<Class<? extends Path>, Class<? extends PathBuilder>> builders = new ConcurrentHashMap<>() {
        {
            //put(StatelessGlobalPath.class, FastGlobalPathBuilder2.class);
        }
    };

    public static PathBuilder builder(Class<? extends Path> type) {
        Class<? extends PathBuilder> pathbuilderClass = builders.get(type);
        if (pathbuilderClass == null) {
            System.err.println("Cannot find a pathbuilder class for " + type.getName());
            return null;
        }
        Constructor<? extends PathBuilder> constructor;
        try {
            constructor = pathbuilderClass.getConstructor();
        } catch (NoSuchMethodException e) {
            System.err.println("Cannot find a no-arg pathbuilder constructor");
            return null;
        }
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("Failed to construct the pathbuilder");
            return null;
        }
    }

    public static void register(Class<? extends Path> pathType, Class<? extends PathBuilder> builderType) {
        builders.put(pathType, builderType);
    }
}
