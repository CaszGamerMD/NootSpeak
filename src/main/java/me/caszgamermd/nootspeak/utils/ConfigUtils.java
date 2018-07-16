package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private Main plugin;
    private MessageUtils msgUtils;

    // Squawk
    public String squawkPrefix;
    public String playerColor;
    public int squawkCooldown;

    // Noot Filter
    public boolean filterEnabled;
    public List<String> badWords = new ArrayList<>();

    public ConfigUtils(Main pl, MessageUtils messageUtils) {
        plugin = pl;
        msgUtils = messageUtils;
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

    private void saveConfig() {
        FileConfiguration config = plugin.getConfig();
        config.set("Squawk.Squawk-Prefix", squawkPrefix);
        config.set("Squawk.Display-Name-Color", playerColor);
        config.set("Squawk.Cooldown", squawkCooldown);
        config.set("Filter.Enabled", filterEnabled);
        config.set("Filter.Bad-Words", badWords);
        plugin.saveConfig();
    }
    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
        plugin.getConfig();
    }

    public void addWord(CommandSender sender, String word) {
        if (badWords.contains(word)) {
            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.inList
                    .replace("{word}", word)));
            return;
        }
        badWords.add(word);
        saveConfig();
        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.wordAdded
                .replace("{word}", word)));
    }

    public void removeWord(CommandSender sender, String word) {
        if (!badWords.contains(word)) {
            sender.sendMessage(msgUtils.colorize(msgUtils.notInList.replace("{word}", word)));
            return;
        }
        badWords.remove(word);
        saveConfig();
        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + msgUtils.wordRemoved
                .replace("{word}", word)));
    }

    public void toggleFilter() {
        filterEnabled = !filterEnabled;
        plugin.getConfig().set("Filter.Enabled", filterEnabled);
        plugin.saveConfig();
    }
}
