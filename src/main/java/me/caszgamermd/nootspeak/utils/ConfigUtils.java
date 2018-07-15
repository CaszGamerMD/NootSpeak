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

    // Noot Filter
    public boolean filterEnabled;
    public List<String> badWords = new ArrayList<>();

    public ConfigUtils(Main pl) {
        plugin = pl;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        squawkPrefix = config.getString("Squawk.Squawk-Prefix");
        playerColor = config.getString("Squawk.Display-Name-Color");
        squawkCooldown = config.getInt("Squawk.Cooldown");
        filterEnabled = config.getBoolean("Filter.Enabled");
        badWords = config.getStringList("Filter.Bad-Words");
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
        plugin.getConfig();
    }

    public void addWord(CommandSender sender, String word) {
        if (badWords.contains(word)) {
            sender.sendMessage(word + " Already Exists In List!");
            return;
        }
        badWords.add(word);
        plugin.getConfig().set("Filter.Bad-Words", badWords);
        plugin.saveConfig();
        sender.sendMessage("Word " + word + " Added");
    }

    public void removeWord(CommandSender sender, String word) {
        if (!badWords.contains(word)) {
            sender.sendMessage(word + " Doesn't Exist!!");
            return;
        }
        badWords.remove(word);
        plugin.getConfig().set("Filter.Bad-Words", badWords);
        plugin.saveConfig();
        sender.sendMessage("Word " + word + " Removed");
    }

    public void toggleFilter() {
        filterEnabled = !filterEnabled;
        plugin.getConfig().set("Filter.Enabled", filterEnabled);
        plugin.saveConfig();
    }
}
