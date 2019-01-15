package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.NootSpeak;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public String pingSound;
    private NootSpeak plugin;

    // Squawk
    public String squawkPrefix;
    public String playerColor;
    public int squawkCooldown;
    public String defaultChatColor;

    // Noot Filter
    public boolean filterEnabled;
    public boolean playerPingEnabled;
    public String playerPingColor;
    public double swearCost;

    //ABC
    long broadcastInterval;
    int chatInterveral;
    public boolean updaterEnabled;
    public String filesReloaded;


    public ConfigUtils(NootSpeak pl) {
        plugin = pl;
    }

    // Config Methods
    public void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        squawkPrefix = config.getString("Squawk.Squawk-Prefix");
        playerColor = config.getString("Squawk.Display-Name-Color");
        defaultChatColor = config.getString("Squawk.Default-ChatColor");
        squawkCooldown = config.getInt("Squawk.Cooldown");
        filterEnabled = config.getBoolean("Filter.Enabled");
        playerPingEnabled = config.getBoolean("Filter.PlayerPingEnable");
        playerPingColor = config.getString("Filter.PingedPlayerColor");
        pingSound = config.getString("Filter.PingSound");
        swearCost = config.getDouble("Filter.Swear-Cost");

        //ABC
        broadcastInterval = config.getLong("AutoBroadcast.Broadcast-Interval");
        chatInterveral = config.getInt("AutoBroadcast.Chat-Interval");
        filesReloaded = config.getString("AutoBroadcast.Files-Reloaded-Msg");

        saveConfig();

    }
    //TODO player ping sound effect
    //TODO player ping color for pinged player only?
    //TODO permission based?

    private void saveConfig() {
        FileConfiguration config = plugin.getConfig();
        config.set("Squawk.Squawk-Prefix", squawkPrefix);
        config.set("Squawk.Display-Name-Color", playerColor);
        config.set("Squawk.Default-ChatColor", defaultChatColor);
        config.set("Squawk.Cooldown", squawkCooldown);
        config.set("Filter.Enabled", filterEnabled);
        config.set("Filter.PlayerPingEnable", playerPingEnabled);
        config.set("Filter.PingedPlayerColor", playerPingColor);
        config.set("Filter.PingSound", pingSound);
        config.set("Filter.Swear-Cost", swearCost);

        //abc
        config.set("AutoBroadcast.Broadcast-Interval", broadcastInterval);
        config.set("AutoBroadcast.Chat-Interval", chatInterveral);
        config.set("AutoBroadcast.Files-Reloaded-Msg", filesReloaded);

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