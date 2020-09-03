package me.mob.basicimprovements.basicimprovements.basicimprovements;

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
        loadl();
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

    public void loadl() {
        ConfigurationSection section = data.getConfigurationSection("locations");
        if (section != null) {
            section.getKeys(false).forEach(key -> {
                Location loc = section.getLocation(key);
                pl.pwarps.put(key, loc);
            });
        }
    }
}
