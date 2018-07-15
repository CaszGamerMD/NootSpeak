package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageUtils {

    private Main plugin;

    public String prefix = "&1[&dNootSpeak&1]";
    public String fileReloaded = "&d{file} &1Reloaded!";
    public String nonePlayer = "&4You &6must &ebe &aa &2player &3to &1use &9this &5command.";
    public String emptySquawk = "&bWhat the &5Noot &bare doing!!";
    public String cooldownSquawk = "&cSeconds until you may &bSquawk &cagain!";


    public MessageUtils(Main pl) {
        plugin = pl;
    }

    public String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public void loadMessages() {
        // Create messages File If Missing
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = YamlConfiguration.loadConfiguration(file);
        try {
            if (file.createNewFile()) {
                saveMessages();
                plugin.getLogger().info("messages.yml created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Otherwise Load Data
        prefix = messages.getString("Prefix", prefix);
        fileReloaded = messages.getString("File-Reloaded", fileReloaded);
        nonePlayer = messages.getString("None-Player", nonePlayer);
        emptySquawk = messages.getString("No-Squawk-Message", emptySquawk);
        cooldownSquawk = messages.getString("Cooldown-Squawk", cooldownSquawk);
        
    }

    private void saveMessages() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = new YamlConfiguration();

        messages.set("Prefix", prefix);
        messages.set("FileReloaded", fileReloaded);
        messages.set("None-Player", nonePlayer);
        messages.set("No-Squawk-Message", emptySquawk);
        messages.set("Cooldown-Squawk", cooldownSquawk);

        try {
            messages.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadLang() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = YamlConfiguration.loadConfiguration(file);

        // Get New Data From messages
        prefix = messages.getString("prefix");
        fileReloaded = messages.getString("fileReloaded");
        nonePlayer = messages.getString("None-Player");
        emptySquawk = messages.getString("No-Squawk-Message");
        cooldownSquawk = messages.getString("Cooldown-Squawk");

        // Set Loaded Data To Plugin
        messages.set("prefix", prefix);
        messages.set("fileReloaded", fileReloaded);
        messages.set("None-Player", nonePlayer);
        messages.set("No-Squawk-Message", emptySquawk);
        messages.set("Cooldown-Squawk", cooldownSquawk);

        // Save messages
        saveMessages();
    }
}
