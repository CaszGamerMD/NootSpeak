package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {

    private Main plugin;
    private MessageUtils msgUtils;

    public String squawkPrefix = "&2&l *";
    public String playerColor = "&b&l";
    public int squawkCooldown = 30;

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
                plugin.getLogger().info(msgUtils.colorize("&aNew config.yml created."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Otherwise Load Data
        //Squawk Configs
        squawkPrefix = squawk.getString("SquawkPrefix", squawkPrefix);
        playerColor = squawk.getString("DisplayNameColor", playerColor);
        squawkCooldown = squawk.getInt( "SquawkCooldown", squawkCooldown);

    }

    private void saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = new YamlConfiguration();
        ConfigurationSection squawk = config.createSection("Squawk");

        //set data
        squawk.set("SquawkPrefix", squawkPrefix);
        squawk.set("DisplayNameColor", playerColor);
        squawk.set("SquawkCooldown", squawkCooldown);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection squawk = config.createSection("Squawk");

        // Get New Data From Config
        squawkPrefix = squawk.getString("SquawkPrefix");
        playerColor = squawk.getString("DisplayNameColor");
        squawkCooldown = squawk.getInt( "SquawkCooldown");

        // Set Loaded Data To Plugin
        squawk.set("SquawkPrefix", squawkPrefix);
        squawk.set("DisplayNameColor", playerColor);
        squawk.set("SquawkCooldown", squawkCooldown);

        // Save Config
        saveConfig();
        plugin.getLogger().info(msgUtils.colorize("&aConfiguration Reloaded."));
    }
}
