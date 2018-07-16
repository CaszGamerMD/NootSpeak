package me.caszgamermd.nootspeak;

import me.caszgamermd.nootspeak.commands.NootSpeakCommand;
import me.caszgamermd.nootspeak.commands.SquawkCommand;
import me.caszgamermd.nootspeak.listeners.ChatListener;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class Main extends JavaPlugin{

    private static Economy economy = null;

    public void onEnable() {
        // Create Plugin Folder If Missing
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Check For Vault
        setupEconomy();
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
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
        getServer().getPluginManager().registerEvents(new ChatListener(cfgUtils, fltrUtils, msgUtils, this), this);

        // Load Data Files
        cfgUtils.loadConfig();
        fltrUtils.loadBadWords();
        fltrUtils.loadReplacements();
        msgUtils.loadMessages();

        // Announce Completed Enable
        getLogger().info("Enabled");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public void takeMoney(OfflinePlayer player, double amount) {
        if (economy.has(player, amount)) {
            economy.withdrawPlayer(player, amount);
        }

    }
}
