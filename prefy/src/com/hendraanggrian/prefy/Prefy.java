package com.hendraanggrian.prefy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Create an instance of {@link WritablePreferences} or {@link EditablePreferences}
 * and bind its values to JVM fields with this object.
 */
public final class Prefy {
    private static Map<Class<?>, Constructor<PreferencesSaver>> BINDINGS;
    private static PreferencesLogger LOGGER;

    private Prefy() {
    }

    /**
     * Set logging behavior using {@link PreferencesLogger} instance.
     *
     * @param logger custom or pre-determined logger (e.g: {@link PreferencesLogger#SYSTEM}).
     */
    public static void setLogger(PreferencesLogger logger) {
        LOGGER = logger;
    }

    static void info(String format, Object... args) {
        if (LOGGER != null) {
            LOGGER.info(String.format(format, args));
        }
    }

    static void warn(String format, Object... args) {
        if (LOGGER != null) {
            LOGGER.info(String.format(format, args));
        }
    }

    /**
     * Bind fields annotated with {@link BindPreference} with values from this preferences.
     *
     * @param target fields' owner.
     * @return saver instance to apply changes made to the fields.
     * @throws RuntimeException when constructor of binding class cannot be found.
     * @receiver source preferences.
     */
    public static PreferencesSaver bind(ReadablePreferences source, Object target) {
        final Class<?> targetCls = target.getClass();
        final String targetClsName = targetCls.getName();
        info("Looking up binding for %s", targetClsName);
        final Constructor<PreferencesSaver> constructor = findBindingConstructor(targetCls);
        if (constructor == null) {
            info("%s binding not found, returning empty saver.", targetClsName);
            return PreferencesSaver.EMPTY;
        }
        try {
            return constructor.newInstance(source, target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke " + constructor, e);
        } catch (InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof RuntimeException)
                throw (RuntimeException) cause;
            if (cause instanceof Error)
                throw (Error) cause;
            throw new RuntimeException("Unable to create constructor instance.", cause);
        }
    }

    @SuppressWarnings("unchecked")
    static Constructor<PreferencesSaver> findBindingConstructor(Class<?> cls) {
        if (BINDINGS == null) BINDINGS = new WeakHashMap<>();
        Constructor<PreferencesSaver> binding = BINDINGS.get(cls);
        if (binding != null) {
            info("HIT: Cache found in binding weak map.");
            return binding;
        }
        final String clsName = cls.getName();
        if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            info("MISS: Reached framework class. Abandoning search.");
            return null;
        }
        try {
            binding = (Constructor<PreferencesSaver>) cls.getClassLoader()
                .loadClass(clsName + "_PreferencesBinding")
                .getConstructor(ReadablePreferences.class, cls);
            info("HIT: Loaded binding class, caching in weak map.");
        } catch (ClassNotFoundException e) {
            final Class<?> supercls = cls.getSuperclass();
            warn("Not found. Trying superclass %s ...", supercls.getName());
            binding = findBindingConstructor(supercls);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + clsName, e);
        }
        if (binding == null) {
            throw new IllegalStateException("Unable to find preferences binding, " +
                "is `prefy-compiler` correctly installed?");
        }
        BINDINGS.put(cls, binding);
        return binding;
    }
}
