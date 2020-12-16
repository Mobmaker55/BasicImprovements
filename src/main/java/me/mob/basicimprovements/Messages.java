package me.mob.basicimprovements;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum Messages {
    WARP_START("warp.start", "&5Warp started&7. Stay still for &5five &7seconds, and do not take damage."),
    WARP_SUCCESS("warp.success", "You have been warped to &a{0}&7."),
    WARP_NOTFOUND("warp.notfound", "&c&lHEY! &r&7That warp doesn't exist. Make sure you spelled it right, or that it exists!"),
    WARP_SET_SUCCESS("warp.set.success", "&7You have set the location of &a{0}&7 to your current location."),
    WARP_DEL_SUCCESS("warp.del.success", "&7You have removed the warp&a {0}&7."),
    WARP_CANCEL_MOVE("warp.cancel.move", "&c&lHEY! &r&7You moved! Warp cancelled."),
    WARP_CANCEL_DAMAGE("warp.cancel.damage", "&c&lHEY! &r&7You took damage! Warp cancelled."),
    WARP_UNNAMED("warp.set.unnamed", "&c&lHEY! &r&7Make sure to name the warp!"),
    HOME_SUCCESS("home.success", "&7You have been warped to§a your home§7."),
    HOME_NOTFOUND("home.notfound", "&c&lHEY! &r&7Make sure to set your home first!"),
    HOME_SET_SUCCESS("home.set.success", "&7You have set the location of §ayour home§7.");

    private final String key;
    private final String defaultValue;

    Messages(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String get() {
        if (messages == null) color(defaultValue);
        String message = messages.getString(key);
        if (message == null || message.isEmpty()) return color(defaultValue);
        return color(message);
    }

    public String get(String... args) {
        String get = get();
        int argNum = 0;
        for (String arg : args) {
            get = get.replace("{" + argNum + "}", arg);
            argNum++;
        }

        return get;
    }

    private static YamlConfiguration messages = null;

    protected static void reloadLanguage() {
        File messagesFile = new File(BasicImprovements.getInstance().getDataFolder(), "messages.yml");
        boolean isNew = !messagesFile.exists();

        if (isNew) {
            try {
                if (messagesFile.createNewFile()) {
                    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(messagesFile);
                    for (Messages message : Messages.values()) {
                        configuration.set(message.key, message.defaultValue);
                    }
                    configuration.save(messagesFile);
                } else {
                    System.out.println("Unable to create messages, defaulting to preset.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to create messages (see above error message), defaulting to preset.");
                return;
            }
        }

        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
