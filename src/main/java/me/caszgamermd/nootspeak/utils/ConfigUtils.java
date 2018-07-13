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
 //       ConfigurationSection squawk = config.getConfigurationSection("Squawk");
        try {
            if (file.createNewFile()) {
                saveConfig();
                plugin.getLogger().info("config.yml created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Otherwise Load Data

        squawkPrefix = config.getString("SquawkPrefix", squawkPrefix);
        playerColor = config.getString("DisplayNameColor", playerColor);
        squawkCooldown = config.getInt( "SquawkCooldown", squawkCooldown);

    }

    private void saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = new YamlConfiguration();
 //       ConfigurationSection squawk = config.createSection("Squawk");

        //set data
        config.set("SquawkPrefix", squawkPrefix);
        config.set("DisplayNameColor", playerColor);
        config.set("SquawkCooldown", squawkCooldown);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        //       ConfigurationSection squawk = config.createSection("Squawk");

        // Get New Data From Config
        squawkPrefix = config.getString("SquawkPrefix");
        playerColor = config.getString("DisplayNameColor");
        squawkCooldown = config.getInt( "SquawkCooldown");

        // Set Loaded Data To Plugin
        config.set("SquawkPrefix", squawkPrefix);
        config.set("DisplayNameColor", playerColor);
        config.set("SquawkCooldown", squawkCooldown);

        // Save Config
        saveConfig();
    }
}
