package me.mob.basicimprovements;

import me.mob.basicimprovements.chats.MessageCommands;
import me.mob.basicimprovements.data.ImprovementStorage;
import me.mob.basicimprovements.statistics.PlaytimeCommand;
import me.mob.basicimprovements.statistics.StatisticCommand;
import me.mob.basicimprovements.teleports.TPCommands;
import me.mob.basicimprovements.teleports.TPEvents;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class BasicImprovements extends JavaPlugin {

    private static BasicImprovements INSTANCE;
    public static BasicImprovements getInstance() {
        return INSTANCE;
    }

    public ImprovementStorage improvementStorage;

    public HashMap<String, Location> publicWarps = new HashMap<>();
    public HashMap<String, Location> homeLocations = new HashMap<>();
    public HashMap<String, Location> backLoc = new HashMap<>();
    public HashMap<Player, Integer> warpTasks = new HashMap<>();
    public HashMap<String, Double> tpCooldown = new HashMap<>();

    public boolean Econ;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        Messages.reloadLanguage();
        improvementStorage = new ImprovementStorage(this);
        Econ = getServer().getPluginManager().isPluginEnabled("BasicEcon");
        if (getConfig().getBoolean("warps")) {
            this.getCommand("warp").setExecutor(new TPCommands());
            this.getCommand("setwarp").setExecutor(new TPCommands());
            this.getCommand("delwarp").setExecutor(new TPCommands());
            this.getCommand("back").setExecutor(new TPCommands());
        }
        if (getConfig().getBoolean("home")) {
            this.getCommand("home").setExecutor(new TPCommands());
            this.getCommand("sethome").setExecutor(new TPCommands());
            this.getCommand("back").setExecutor(new TPCommands());
        }
        this.getCommand("ping").setExecutor(new ImprovementCommands());
        this.getCommand("playtime").setExecutor(new PlaytimeCommand());
        this.getCommand("randomtp").setExecutor(new TPCommands());
        this.getCommand("broadcast").setExecutor(new MessageCommands());
        this.getCommand("basicreload").setExecutor(new ImprovementCommands());
        this.getCommand("statistic").setExecutor(new StatisticCommand());
        getServer().getPluginManager().registerEvents(new TPEvents(), this);
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void onDisable() {
        improvementStorage.savepW();
        improvementStorage.savehL();
    }
}
