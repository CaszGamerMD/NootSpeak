package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private Main plugin;


    // Squawk
    public String squawkPrefix;
    public String playerColor;
    public int squawkCooldown;
    public String defaultChatColor;

    // Noot Filter
    public boolean filterEnabled;


    public ConfigUtils(Main pl) {
        plugin = pl;

    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        squawkPrefix = config.getString("Squawk.Squawk-Prefix");
        playerColor = config.getString("Squawk.Display-Name-Color");
        defaultChatColor = config.getString("Squawk.Default-ChatColor");
        squawkCooldown = config.getInt("Squawk.Cooldown");
        filterEnabled = config.getBoolean("Filter.Enabled");
        saveConfig();

    }

    private void saveConfig() {
        FileConfiguration config = plugin.getConfig();
        config.set("Squawk.Squawk-Prefix", squawkPrefix);
        config.set("Squawk.Display-Name-Color", playerColor);
        config.set("Squawk.Default_ChatColor", defaultChatColor);
        config.set("Squawk.Cooldown", squawkCooldown);
        config.set("Filter.Enabled", filterEnabled);

        plugin.saveConfig();
    }
    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
        plugin.getConfig();
    }

    public void toggleFilter() {
        filterEnabled = !filterEnabled;
        plugin.getConfig().set("Filter.Enabled", filterEnabled);
        plugin.saveConfig();
    }
}
