package me.mob.basicimprovements.basicimprovements.basicimprovements;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class BasicImprovements extends JavaPlugin {

    public static BasicImprovements getInstance;
    public ImprovementStorage improvementStorage;

    public HashMap<String, Location> pwarps = new HashMap<>();


    @Override
    public void onEnable() {
        instanceClasses();
        improvementStorage = new ImprovementStorage(this);
    }

    public void instanceClasses() {
        getInstance = this;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
