package me.mob.basicimprovements.basicimprovements.basicimprovements;

import org.bukkit.Location;
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


    @Override
    public void onEnable() {
        instanceClasses();
        improvementStorage = new ImprovementStorage(this);

        this.getCommand("warp").setExecutor(new ImprovementSetTP());
        this.getCommand("setwarp").setExecutor(new ImprovementSetTP());
        this.getCommand("delwarp").setExecutor(new ImprovementSetTP());
        this.getCommand("home").setExecutor(new ImprovementSetTP());
        this.getCommand("sethome").setExecutor(new ImprovementSetTP());
        this.getCommand("back").setExecutor(new ImprovementSetTP());
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
