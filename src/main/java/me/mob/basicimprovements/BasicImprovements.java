package me.mob.basicimprovements;

import me.mob.basicimprovements.data.ImprovementStorage;
import me.mob.basicimprovements.teleports.TPCommands;
import me.mob.basicimprovements.teleports.TPEvents;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class BasicImprovements extends JavaPlugin {

    public static BasicImprovements getInstance;
    public ImprovementStorage improvementStorage;

    public HashMap<String, Location> publicWarps = new HashMap<>();
    public HashMap<String, Location> homeLocations = new HashMap<>();
    public HashMap<String, UUID> offlinePlayers = new HashMap<>();
    public HashMap<String, Location> backLoc = new HashMap<>();
    public HashMap<Player, Integer> warpTasks = new HashMap<>();
    public boolean Econ;


    @Override
    public void onEnable() {
        instanceClasses();
        improvementStorage = new ImprovementStorage(this);
        Econ = getServer().getPluginManager().isPluginEnabled("BasicEcon");

        this.getCommand("warp").setExecutor(new TPCommands());
        this.getCommand("setwarp").setExecutor(new TPCommands());
        this.getCommand("delwarp").setExecutor(new TPCommands());
        this.getCommand("home").setExecutor(new TPCommands());
        this.getCommand("sethome").setExecutor(new TPCommands());
        this.getCommand("back").setExecutor(new TPCommands());
        this.getCommand("ping").setExecutor(new ImprovementCommands());
        this.getCommand("playtime").setExecutor(new ImprovementCommands());
        getServer().getPluginManager().registerEvents(new TPEvents(), this);
    }

    public void instanceClasses() {
        getInstance = this;
    }


    @Override
    public void onDisable() {
        improvementStorage.savepW();
        improvementStorage.savehL();
    }
}
