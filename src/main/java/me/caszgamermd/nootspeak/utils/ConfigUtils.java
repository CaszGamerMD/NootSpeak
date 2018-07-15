package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private Main plugin;

    // Squawk
    public String squawkPrefix = "&2&l *";
    public String playerColor = "&b&l";
    public int squawkCooldown = 30;

    // Noot Filter
    public boolean filterEnabled = true;
    public List<String> badWords = new ArrayList<>();


    public ConfigUtils(Main pl) {
        plugin = pl;
    }

    public void setConfig() {
        FileConfiguration config = plugin.getConfig();
        // Get Squawk Section
        squawkPrefix = config.getString("Squawk-Prefix", squawkPrefix);
        playerColor = config.getString("Display-Name-Color", playerColor);
        squawkCooldown = config.getInt( "Squawk-Cooldown", squawkCooldown);

        // Get Filter Section
        filterEnabled = config.getBoolean("Filter-Enabled", filterEnabled);
        badWords = config.getStringList("Bad-Words");

        // Set Squawk Section
        config.set("Squawk-Prefix", squawkPrefix);
        config.set("Display-Name-Color", playerColor);
        config.set("Squawk-Cooldown", squawkCooldown);

        // Set Filter Section
        config.set("Filter-Enabled", filterEnabled);
        config.set("Bad-Words", badWords);

        // Save Config
        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        setConfig();
        plugin.getConfig();
    }

    public void addWord(CommandSender sender, String word) {
        if (!badWords.contains(word)) {
            badWords.add(word);
            plugin.saveConfig();
            return;
        }
        sender.sendMessage(word + " Already Exists In List!");
    }

    public void removeWord(CommandSender sender, String word) {
        if (badWords.contains(word)) {
            badWords.remove(word);
            plugin.saveConfig();
            return;
        }
        sender.sendMessage(word + " Doesn't Exist In List!");
    }

    public void toggleFilter() {
        filterEnabled = !filterEnabled;
        plugin.saveConfig();
    }
}
