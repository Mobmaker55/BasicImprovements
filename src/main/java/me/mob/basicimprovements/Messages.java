package me.mob.basicimprovements;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum Messages {
    WARP_START("warp.start", "&5Warp started&7. Stay still for &5five &7seconds, and do not take damage."),
    WARP_SUCCESS("warp.success", "You have been warped to &a{0}&7."),
    WARP_NOTFOUND("warp.notfound", "&c&lHEY! &r&7That warp doesn't exist. Make sure you spelled it right, or that it exists!"),
    WARP_UNNAMED("warp.set.unnamed", "&c&lHEY! &r&7Make sure to name the warp!"),
    WARP_WRONGWORLD("warp.wrongworld", "&c&lHEY! &r&7You need to be in world&a {0}&7 to warp here."),
    WARP_SET_SUCCESS("warp.set.success", "&7You have set the location of &a{0}&7 to your current location."),
    WARP_DEL_SUCCESS("warp.del.success", "&7You have removed the warp&a {0}&7."),
    WARP_CANCEL_MOVE("warp.cancel.move", "&c&lHEY! &r&7You moved! Warp cancelled."),
    WARP_CANCEL_DAMAGE("warp.cancel.damage", "&c&lHEY! &r&7You took damage! Warp cancelled."),
    BACK_SUCCESS("back.success", "&7You have been warped to&a your previous location&7."),
    BACK_NOTFOUND("back.notfound", "&c&lHEY! &r&7 There's nowhere for you to return to!"),
    RTP_SUCCESS("rtp.success", "&7You are now at a new&a random location&7."),
    RTP_COOLDOWN("rtp.cooldown", "&cYou can't run this command yet! You still have&6 {0}&c minutes remaining!"),
    HOME_SUCCESS("home.success", "&7You have been warped to&a your home&7."),
    HOME_NOTFOUND("home.notfound", "&c&lHEY! &r&7Make sure to set your home first!"),
    HOME_SET_SUCCESS("home.set.success", "&7You have set the location of&a your home&7."),
    STATISTIC_RETURN("statistic.return","&a{0} &7{1} {2} &a{3} &7{4}."),
    STATISTIC_NOTFOUND("statistic.notfound", "&c&lHEY! &r&7This player does not have this statistic!"),
    PLUGIN_RELOAD("pl.reload", "&aBasicImprovements &7has been reloaded!"),
    PLUGIN_PING("pl.ping", "&a{0} ping is &a{1} &7milliseconds."),
    PLUGIN_NOTPLAYER("pl.notplayer","&c&lHEY! &r&7You are not a player!"),
    PLUGIN_INVALID_PLAYER("pl.badplayer", "&c&lHEY! &r&7That player doesn't exist!"),
    PLUGIN_PREFIX("pl.prefix", "&8[&6&lBroadcast&r&8]&f");

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
        boolean isTampered = false;

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

        YamlConfiguration config = YamlConfiguration.loadConfiguration(messagesFile);
        for (Messages value : Messages.values()) {
            String string = config.getString(value.key);
            if (string == null) {
                isTampered = true;
                config.set(value.key, value.defaultValue);
            }
        }

        if (isTampered) {
            try {
                config.save(messagesFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
