package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {

    private Main plugin;

    private String prefix = "[NO2T]";

    public ConfigUtils(Main pl) {
        plugin = pl;
    }

    public void loadConfig() {
        // Create Config File If Missing
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            if (file.createNewFile()) {
                saveConfig();
                plugin.getLogger().info("config.yml created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Otherwise Load Data
        prefix = config.getString("prefix");
    }

    private void saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = new YamlConfiguration();
        config.set("prefix", prefix);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Get New Data From Config
        prefix = config.getString("prefix");

        // Set Loaded Data To Plugin
        config.set("prefix", prefix);

        // Save Config
        saveConfig();
    }
}
