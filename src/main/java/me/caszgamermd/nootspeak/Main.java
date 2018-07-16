package me.caszgamermd.nootspeak;

import me.caszgamermd.nootspeak.commands.NootSpeakCommand;
import me.caszgamermd.nootspeak.commands.SquawkCommand;
import me.caszgamermd.nootspeak.listeners.ChatListener;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class Main extends JavaPlugin{

    public void onEnable() {
        // Create Plugin Folder If Missing
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Create Instances
        MessageUtils msgUtils = new MessageUtils(this);
        ConfigUtils cfgUtils = new ConfigUtils(this, msgUtils);
        CooldownUtils cdUtils = new CooldownUtils();

        // Register Commands
        getCommand("squawk").setExecutor(new SquawkCommand(cdUtils, cfgUtils, msgUtils));
        getCommand("ns").setExecutor(new NootSpeakCommand(cfgUtils, msgUtils));

        // Register Listeners
        getServer().getPluginManager().registerEvents(new ChatListener(cfgUtils, msgUtils), this);

        // Load Data Files
        cfgUtils.loadConfig();
//        cfgUtils.setConfig();
        msgUtils.loadMessages();

        // Announce Completed Enable
        getLogger().info("Enabled");
    }
}
