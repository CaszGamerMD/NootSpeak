package me.caszgamermd.nootspeak;

import me.caszgamermd.nootspeak.commands.*;
import me.caszgamermd.nootspeak.listeners.ActivityListener;
import me.caszgamermd.nootspeak.listeners.ChatListener;
import me.caszgamermd.nootspeak.listeners.UpdateListener;
import me.caszgamermd.nootspeak.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class NootSpeak extends JavaPlugin{

    /*
    Vault works fine in the plug but hates Casz' rig,
    leave this message as a reminder its a false error
    */

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
        TrackerUtils trkUtils = new TrackerUtils();
        //abc
        BroadcastMsgUtils bcmUtils = new BroadcastMsgUtils(this);
        Autobroadcaster abc = new Autobroadcaster(bcmUtils, cfgUtils, msgUtils, this);


        // Register Commands
        getCommand("squawk").setExecutor(new SquawkCommand(cdUtils, cfgUtils, msgUtils));
        getCommand("nootspeak").setExecutor(new NootSpeakCommand(cfgUtils, msgUtils));
        getCommand("filter").setExecutor(new FilterCommand(cfgUtils, fltrUtils, msgUtils));
        getCommand("tracker").setExecutor(new TrackerCommand(msgUtils,trkUtils));
        getCommand("autobroadcast").setExecutor(new ABCCommand(abc, bcmUtils, cfgUtils, msgUtils, this));

        // Register Listeners
        getServer().getPluginManager().registerEvents(new ChatListener(cdUtils, cfgUtils, fltrUtils, msgUtils, this), this);
        getServer().getPluginManager().registerEvents(new ActivityListener(abc), this);
        getServer().getPluginManager().registerEvents(new UpdateListener(this, cfgUtils, bcmUtils), this);

        // Load Data Files
        bcmUtils.loadConfig();
        msgUtils.loadMessages();
        cfgUtils.loadConfig();
        fltrUtils.loadBadWords();
        fltrUtils.loadReplacements();

        // Announce Completed Enable
        getLogger().info("Enabled");

        //Start Runnable
        abc.broadcast();
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
        } else{
           double currentBal = economy.getBalance(player);
            economy.withdrawPlayer(player, currentBal);
        }
    }
}
