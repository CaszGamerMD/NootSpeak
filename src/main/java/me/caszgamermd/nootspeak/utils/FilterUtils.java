package me.caszgamermd.nootspeak.utils;

import me.caszgamermd.nootspeak.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FilterUtils {

    private Main plugin;
    private MessageUtils msgUtils;


    public List<String> badWords = new ArrayList<>();
    public List<String> replacements = new ArrayList<>();

    public FilterUtils(Main pl, MessageUtils messageUtils) {
        plugin = pl;
        msgUtils = messageUtils;

    }
// Add/remove Bad words only
    public void addWord(CommandSender sender, String word) {
        if (badWords.contains(word)) {
            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.inList
                    .replace("{word}", word)));
            return;
        }
        badWords.add(word);
        saveBadWords();
        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.wordAdded
                .replace("{word}", word)));
    }

    public void removeWord(CommandSender sender, String word) {
        if (!badWords.contains(word)) {
            sender.sendMessage(msgUtils.colorize(msgUtils.notInList.replace("{word}", word)));
            return;
        }
        badWords.remove(word);
        saveBadWords();
        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + msgUtils.wordRemoved
                .replace("{word}", word)));
    }


// load-save-reload bad words/replacements
    public void loadBadWords() {
        plugin.saveResource("badwords.yml", false);
        File file = new File(plugin.getDataFolder(), "badwords.yml");
        FileConfiguration badWordList = YamlConfiguration.loadConfiguration(file);
        badWords = badWordList.getStringList("Bad-Words");

    }

    public void loadReplacements() {
        plugin.saveResource("replacements.yml", false);
        File file = new File(plugin.getDataFolder(), "replacements.yml");
        FileConfiguration replacementsList = YamlConfiguration.loadConfiguration(file);
        replacements = replacementsList.getStringList("Replacements");

    }


    private void saveBadWords() {
        File file = new File(plugin.getDataFolder(), "badwords.yml");
        FileConfiguration badWordList = YamlConfiguration.loadConfiguration(file);
        badWords = badWordList.getStringList("Bad-Words");
        try {
            badWordList.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveReplacements() {
        File file = new File(plugin.getDataFolder(), "replacements.yml");
        FileConfiguration replacementsList = YamlConfiguration.loadConfiguration(file);
        replacements = replacementsList.getStringList("Replacements");
        try {
            replacementsList.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void reloadBadWords() {
        File file = new File(plugin.getDataFolder(), "badwords.yml");
        FileConfiguration badWordList = YamlConfiguration.loadConfiguration(file);
        badWords = badWordList.getStringList("Bad-Words");
        badWordList.set("Bad-Words", badWords);


        saveBadWords();
    }

    public void reloadreplacements() {
        File file = new File(plugin.getDataFolder(), "replacements.yml");
        FileConfiguration replacementsList = YamlConfiguration.loadConfiguration(file);
        replacements = replacementsList.getStringList("Replacements");
        replacementsList.set("Replacement", replacements);

        saveReplacements();
    }
}

