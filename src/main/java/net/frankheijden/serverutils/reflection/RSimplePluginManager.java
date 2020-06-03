package net.frankheijden.serverutils.reflection;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.*;

import static net.frankheijden.serverutils.reflection.ReflectionUtils.FieldParam.fieldOf;
import static net.frankheijden.serverutils.reflection.ReflectionUtils.VersionParam.ALL_VERSIONS;
import static net.frankheijden.serverutils.reflection.ReflectionUtils.get;
import static net.frankheijden.serverutils.reflection.ReflectionUtils.getAllFields;

public class RSimplePluginManager {

    private static Class<?> simplePluginManagerClass;
    private static Map<String, Field> fields;

    static {
        try {
            simplePluginManagerClass = SimplePluginManager.class;
            fields = getAllFields(simplePluginManagerClass,
                    fieldOf("plugins", ALL_VERSIONS),
                    fieldOf("lookupNames", ALL_VERSIONS));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Plugin> getPlugins(Object manager) throws IllegalAccessException {
        return (List<Plugin>) fields.get("plugins").get(manager);
    }

    @SuppressWarnings("unchecked")
    public static void removeLookupName(Object manager, String name) throws IllegalAccessException {
        Map<String, Plugin> lookupNames = (Map<String, Plugin>) get(fields, manager, "lookupNames");
        if (lookupNames == null) return;
        lookupNames.remove(name.replace(' ', '_'));
        lookupNames.remove(name.replace(' ', '_').toLowerCase(Locale.ENGLISH)); // Paper
    }
}
