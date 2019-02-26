package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.NootSpeak;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageUtils {


    private NootSpeak plugin;

    public String prefix;
    public String fileReloaded;
    public String mustBePlayer;
    public String emptySquawk;
    public String squawkCooldown;
    public String pingCooldown;
    public String specifyFile;
    public String specifyAction;
    public String unknownFileName;
    public String filterEnabled;
    public String specifyWord;
    public String unknownCommand;
    public String noPermission;

    //ABC
    public String specifyBC;
    String cantHaveBoth;
    public String msgDoesntExist;
    public String kill;
    public String reboot;
    public String pluginOutOfDate;

    String inList;
    String notInList;
    String wordAdded;
    String wordRemoved;


    public MessageUtils(NootSpeak pl) {
        plugin = pl;
    }

    public String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


    // Message Methods
    public void loadMessages() {
        plugin.saveResource("messages.yml", false);
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = YamlConfiguration.loadConfiguration(file);

        prefix = messages.getString("Prefix");
        specifyFile = messages.getString("Specify-File");
        specifyAction = messages.getString("Specify-Action");
        unknownFileName = messages.getString("Unknown-File-Name");
        fileReloaded = messages.getString("File-Reloaded");
        mustBePlayer = messages.getString("None-Player");
        emptySquawk = messages.getString("No-Squawk-Message");
        squawkCooldown = messages.getString("Squawk-Cooldown-Msg");
        pingCooldown = messages.getString("Ping-Cooldown-Msg");
        filterEnabled = messages.getString("Filter-Enabled");
        specifyWord = messages.getString("Specify-Word");
        unknownCommand = messages.getString("Unknown-Command");
        noPermission = messages.getString("No-Permission");
        inList = messages.getString("Already-In-List");
        notInList = messages.getString("Not-In-List");
        wordAdded = messages.getString("Word-Added");
        wordRemoved = messages.getString("Word-Removed");
        specifyBC = messages.getString("Specify-Broadcast-Msg");
        unknownCommand = messages.getString("Unknown-Command");
        msgDoesntExist = messages.getString("Message-Does-Not-Exist");
        cantHaveBoth = messages.getString("No-JSONCommand-And-JSONLink");
        kill = messages.getString("Kill-Message");
        reboot = messages.getString("Reboot-Message");
    }

    private void saveMessages() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = YamlConfiguration.loadConfiguration(file);

        messages.set("Prefix", prefix);
        messages.set("Specify-File", specifyFile);
        messages.set("Specify-Action", specifyAction);
        messages.set("Unknown-File-Name", unknownFileName);
        messages.set("File-Reloaded", fileReloaded);
        messages.set("None-Player", mustBePlayer);
        messages.set("No-Squawk-Message", emptySquawk);
        messages.set("Squawk-Cooldown-Msg", squawkCooldown);
        messages.set("Ping-Cooldown-Msg", pingCooldown);
        messages.set("Filter-Enabled", filterEnabled);
        messages.set("Specify-Word", specifyWord);
        messages.set("Unknown-Command", unknownCommand);
        messages.set("No-Permission", noPermission);
        messages.set("Already-In-List", inList);
        messages.set("Not-In-List", notInList);
        messages.set("Word-Added", wordAdded);
        messages.set("Word-Removed", wordRemoved);
        messages.getString("Specify-Broadcast-Msg", specifyBC);
        messages.getString("Unknown-Command", unknownCommand);
        messages.getString("Message-Does-Not-Exist", msgDoesntExist);
        messages.getString("No-JSONCommand-And-JSONLink", cantHaveBoth);
        messages.getString("Kill-Message", kill);
        messages.getString("Reboot-Message", reboot);

        try {
            messages.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadLang() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration messages = YamlConfiguration.loadConfiguration(file);

        // Get New Data From Messages
        prefix = messages.getString("Prefix");
        specifyFile = messages.getString("Specify-File");
        unknownFileName = messages.getString("Unknown-File-Name");
        fileReloaded = messages.getString("File-Reloaded");
        mustBePlayer = messages.getString("None-Player");
        emptySquawk = messages.getString("No-Squawk-Message");
        squawkCooldown = messages.getString("Squawk-Cooldown-Msg");
        filterEnabled = messages.getString("Filter-Enabled");
        specifyWord = messages.getString("Specify-Word");
        unknownCommand = messages.getString("Unknown-Command");
        noPermission = messages.getString("No-Permission");
        inList = messages.getString("Already-In-List");
        notInList = messages.getString("Not-In-List");
        wordAdded = messages.getString("Word-Added");
        wordRemoved = messages.getString("Word-Removed");

        // Set Loaded Data To Plugin
        messages.set("Prefix", prefix);
        messages.set("Specify-File", specifyFile);
        messages.set("Unknown-File-Name", unknownFileName);
        messages.set("File-Reloaded", fileReloaded);
        messages.set("None-Player", mustBePlayer);
        messages.set("No-Squawk-Message", emptySquawk);
        messages.set("Squawk-Cooldown-Msg", squawkCooldown);
        messages.set("Filter-Enabled", filterEnabled);
        messages.set("Specify-Word", specifyWord);
        messages.set("Unknown-Command", unknownCommand);
        messages.set("No-Permission", noPermission);
        messages.set("Already-In-List", inList);
        messages.set("Not-In-List", notInList);
        messages.set("Word-Added", wordAdded);
        messages.set("Word-Removed", wordRemoved);

        // Save messages
        saveMessages();
    }
}