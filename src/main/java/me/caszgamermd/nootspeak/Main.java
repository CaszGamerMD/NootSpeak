package me.caszgamermd.nootspeak;

import me.caszgamermd.nootspeak.commands.NootSpeakCommand;
import me.caszgamermd.nootspeak.commands.SquawkCommand;
import me.caszgamermd.nootspeak.listeners.ChatListener;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
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
        ConfigUtils cfgUtils = new ConfigUtils(this);
        CooldownUtils cdUtils = new CooldownUtils();
        FilterUtils fltrUtils = new FilterUtils(this, msgUtils);

        // Register Commands
        getCommand("squawk").setExecutor(new SquawkCommand(cdUtils, cfgUtils, msgUtils));
        getCommand("ns").setExecutor(new NootSpeakCommand(cfgUtils, fltrUtils, msgUtils));

        // Register Listeners
        getServer().getPluginManager().registerEvents(new ChatListener(cfgUtils, fltrUtils, msgUtils), this);

        // Load Data Files
        cfgUtils.loadConfig();
        fltrUtils.loadBadWords();
        fltrUtils.loadReplacements();
        msgUtils.loadMessages();

        // Announce Completed Enable
        getLogger().info("Enabled");
    }
}
