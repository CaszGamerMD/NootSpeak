package me.caszgamermd.nootspeak;

import java.io.File;
import java.util.logging.Logger;

import me.caszgamermd.nootspeak.commands.SquawkCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener{

    //Colorize Components
    private String colorize(String string) {
        String colored = ChatColor.translateAlternateColorCodes('&', string);
        return colored;
    }

    //Config labels
    FileConfiguration config;
    File cfile;

    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        //Config-Data Folder
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        cfile = new File(getDataFolder(),"config.yml");


        //Commands
        getCommand("squawk").setExecutor(new SquawkCommands());

        //Enable Complete
        logger.info(colorize("&2" + pdfFile.getName() + " has been enabled &b(v." + pdfFile.getVersion() + ")"));
        logger.info(colorize("&4Step 1 Complete!...Ish"));
    }



    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("nootcomms reload")) {
            config = YamlConfiguration.loadConfiguration(cfile);
            sender.sendMessage(colorize("&2NootSpeak &bReloaded."));
            return true;
        }
        return false;
    }




    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        logger.info(colorize("&2" + pdfFile.getName() + " has been disabled &b(v." + pdfFile.getVersion() + ")"));
    }

}
