package me.mob.basicimprovements.data;

import me.mob.basicimprovements.BasicImprovements;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class ImprovementStorage {
    public static FileConfiguration data;
    public static File dataFile;

    private static final BasicImprovements pl = BasicImprovements.getInstance();

    public BasicImprovements bi;

    public ImprovementStorage(BasicImprovements bi) {
        this.bi = bi;
        getData();
        loadpW();
        loadhL();
        loadRtpCool();
    }

    public static void getData() {
        if (!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdirs();
        }
        dataFile = new File(pl.getDataFolder(), "data.yml");
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

    public void loadRtpCool() {
        ConfigurationSection section = data.getConfigurationSection("rtpCooldown");
        if (section != null) {
            section.getKeys(false).forEach(key -> {
                Double cooldown = section.getDouble(key);
                Player pKey = Bukkit.getPlayer(UUID.fromString(key));
                pl.rtpCooldown.put(pKey, cooldown);
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

    public void saveRtpCool() {
        if (!pl.rtpCooldown.isEmpty()) {
            if (pl.rtpCooldown.size() > 0) {
                pl.rtpCooldown.forEach((key, value) -> data.set("rtpCooldown." + key.getUniqueId(), value));
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
