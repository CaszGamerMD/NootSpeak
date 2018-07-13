package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {

    private Main plugin;

    public String squawkPrefix = "&2&l *";
    public String playerColor = "&b&l";
    public int squawkCooldown = 60;

    public ConfigUtils(Main pl) {
        plugin = pl;
    }

    public void loadConfig() {
        // Create Config File If Missing
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection squawk = config.getConfigurationSection("Squawk");
        try {
            if (file.createNewFile()) {
                saveConfig();
                plugin.getLogger().info("config.yml created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Otherwise Load Data

        squawkPrefix = squawk.getString("SquawkPrefix", squawkPrefix);
        playerColor = squawk.getString("DisplayNameColor", playerColor);

    }

    private void saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = new YamlConfiguration();
        ConfigurationSection squawk = config.createSection("Squawk");

        //set data
        squawk.set("SquawkPrefix", squawkPrefix);
        squawk.set("DisplayNameColor", playerColor);
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
        squawkPrefix = config.getString("SquawkPrefix");
        playerColor = config.getString("DisplayNameColor");

        // Set Loaded Data To Plugin
        config.set("SquawkPrefix", squawkPrefix);
        config.set("DisplayNameColor", playerColor);

        // Save Config
        saveConfig();
    }
}
