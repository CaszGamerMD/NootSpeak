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
    public String defaultChatColor = "&a";
    public int squawkCooldown = 10;

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
        defaultChatColor = config.getString("Default-Chat-Color", defaultChatColor);
        squawkCooldown = config.getInt( "Squawk-Cooldown", squawkCooldown);

        // Get Filter Section
        filterEnabled = config.getBoolean("Filter-Enabled", filterEnabled);
        badWords = config.getStringList("Bad-Words");

        // Set Squawk Section
        config.set("Squawk-Prefix", squawkPrefix);
        config.set("Display-Name-Color", playerColor);
        config.set("Default-Chat-Color", defaultChatColor);
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
        if (badWords.contains(word)) {
            sender.sendMessage(word + " Already Exists In List!");
            return;
        }
        badWords.add(word);
        plugin.saveConfig();
        sender.sendMessage("Word " + word + " Added");
    }

    public void removeWord(CommandSender sender, String word) {
        if (!badWords.contains(word)) {
            sender.sendMessage(word + " Doesn't Exist!!");
            return;
        }
        badWords.remove(word);
        plugin.saveConfig();
        sender.sendMessage("Word " + word + " Removed");
    }

    public void toggleFilter() {
        filterEnabled = !filterEnabled;
        plugin.saveConfig();
    }
}
