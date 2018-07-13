package me.caszgamermd.nootspeak;

import me.caszgamermd.nootspeak.commands.NootSpeakCommand;
import me.caszgamermd.nootspeak.commands.SquawkCommand;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{

    public void onEnable() {
        // Create Plugin Folder If Missing
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Create Instances
        MessageUtils msgUtils = new MessageUtils(this);
        ConfigUtils cfgUtils = new ConfigUtils(this);
        CooldownUtils cdUtils = new CooldownUtils();

        // Register Commands
        getCommand("squawk").setExecutor(new SquawkCommand(cfgUtils, cdUtils, msgUtils));
        getCommand("nootspeak").setExecutor(new NootSpeakCommand(cfgUtils, msgUtils));

        // Load Data Files
        cfgUtils.loadConfig();

        getLogger().info("Enabled");
    }
}
