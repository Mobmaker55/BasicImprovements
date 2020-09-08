package me.mob.basicimprovements.data;

import me.mob.basicimprovements.BasicImprovements;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ImprovementStorage {
    public static FileConfiguration data;
    public static File dataFile;

    private static final BasicImprovements pl = BasicImprovements.getInstance;

    public BasicImprovements bi;

    public ImprovementStorage(BasicImprovements bi) {
        this.bi = bi;
        getData();
        loadpW();
        loadhL();
    }

    public static void getData() {
        if (!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdirs();
        }
        dataFile = new File("plugins/BasicImprovements/data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void loadpW() {
        ConfigurationSection section = data.getConfigurationSection("publicWarps");
        if (section != null) {
            section.getKeys(false).forEach(key -> {
                Location loc = section.getLocation(key);
                pl.publicWarps.put(key, loc);
            });
        }
    }

    public void loadhL() {
        ConfigurationSection section = data.getConfigurationSection("homeLocations");
        if (section != null) {
            section.getKeys(false).forEach(key -> {
                Location loc = section.getLocation(key);
                pl.homeLocations.put(key, loc);
            });
        }
    }

    public void savepW() {
        if (!pl.publicWarps.isEmpty()) {
            if (pl.publicWarps.size() > 0) {
                pl.publicWarps.forEach((key, value) -> data.set("publicWarps." + key, value));
                try {
                    data.save(dataFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void savehL() {
        if (!pl.homeLocations.isEmpty()) {
            if (pl.homeLocations.size() > 0) {
                pl.homeLocations.forEach((key, value) -> data.set("homeLocations." + key, value));
                try {
                    data.save(dataFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
