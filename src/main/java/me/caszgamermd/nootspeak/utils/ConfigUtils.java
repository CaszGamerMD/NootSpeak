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

    //Squawk
    public String squawkPrefix = "&2&l *";
    public String playerColor = "&b&l";
    public int squawkCooldown = 30;

    //AutoNoot
    public int timer = 300;
    public String castType = "&b&l";
    public String autoNootPrefix = "&b[&2Noot&aCast&b]";


    public ConfigUtils(Main pl) {
        plugin = pl;
    }

    public void loadConfig() {
        // Create Config File If Missing
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection squawk = config.getConfigurationSection("Squawk");
        ConfigurationSection autonoot = config.getConfigurationSection("AutoNoot");
        try {
            if (file.createNewFile()) {
                saveConfig(); //freezes here
                plugin.getLogger().info(msgUtils.colorize("&aNew config.yml created."));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Otherwise Load Data
        //Squawk Section
        squawkPrefix = squawk.getString("SquawkPrefix", squawkPrefix);
        playerColor = squawk.getString("DisplayNameColor", playerColor);
        squawkCooldown = squawk.getInt( "SquawkCooldown", squawkCooldown);

        //AutoNoot Section
        timer = autonoot.getInt("Timer", timer);
        castType = autonoot.getString("CastingType", castType);
        autoNootPrefix = autonoot.getString("AutoNootPrefix", autoNootPrefix);

    }

    private void saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = new YamlConfiguration();
        ConfigurationSection squawk = config.createSection("Squawk");
        ConfigurationSection autonoot = config.createSection("AutoNoot");

        //Squawk Section
        squawk.set("SquawkPrefix", squawkPrefix);
        squawk.set("DisplayNameColor", playerColor);
        squawk.set("SquawkCooldown", squawkCooldown);

        //AutoNoot Section
        autonoot.set("Timer", timer);
        autonoot.set("CastingType", castType);
        autonoot.set("AutoNootPrefix", autoNootPrefix);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection squawk = config.getConfigurationSection("Squawk");
        ConfigurationSection autonoot = config.getConfigurationSection("AutoNoot");

        // Get New Data From Config

        //Squawk Section
        squawkPrefix = squawk.getString("SquawkPrefix");
        playerColor = squawk.getString("DisplayNameColor");
        squawkCooldown = squawk.getInt( "SquawkCooldown");

        //AutoNoot Section
        timer = autonoot.getInt("Timer", timer);
        castType = autonoot.getString("CastingType", castType);
        autoNootPrefix = autonoot.getString("AutoNootPrefix", autoNootPrefix);

        // Set Loaded Data To Plugin
        //Squawk Section
        squawk.set("SquawkPrefix", squawkPrefix);
        squawk.set("DisplayNameColor", playerColor);
        squawk.set("SquawkCooldown", squawkCooldown);

        //AutoNoot Section
        autonoot.set("Timer", timer);
        autonoot.set("CastingType", castType);
        autonoot.set("AutoNootPrefix", autoNootPrefix);

        // Save Config
        saveConfig();
        plugin.getLogger().info(msgUtils.colorize("&aConfiguration Reloaded."));
    }
}
