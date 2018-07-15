package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    private Main plugin;

    //Squawk
    public String squawkPrefix = "&2&l *";
    public String playerColor = "&b&l";
    public int squawkCooldown = 30;



    public ConfigUtils(Main pl) {
        plugin = pl;
    }

    public void setConfig() {
        FileConfiguration config = plugin.getConfig();
        // Squawk Section
        squawkPrefix = config.getString("SquawkPrefix", squawkPrefix);
        playerColor = config.getString("DisplayNameColor", playerColor);
        squawkCooldown = config.getInt( "SquawkCooldown", squawkCooldown);


        // Set Squawk Section
        config.set("SquawkPrefix", squawkPrefix);
        config.set("DisplayNameColor", playerColor);
        config.set("SquawkCooldown", squawkCooldown);

        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        setConfig();
        plugin.getConfig();
    }
}
