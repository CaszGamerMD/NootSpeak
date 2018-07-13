package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageUtils {

    private Main plugin;

    private String prefix = "[NootSpeak]";

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
        prefix = messages.getString("prefix");
    }

    private void saveMessages() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = new YamlConfiguration();
        messages.set("prefix", prefix);
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

        // Set Loaded Data To Plugin

        // Save messages
        saveMessages();
    }
}
