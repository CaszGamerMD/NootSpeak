package me.caszgamermd.nootspeak.listeners;


import me.caszgamermd.nootspeak.NootSpeak;
import me.caszgamermd.nootspeak.utils.BroadcastMsgUtils;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateListener implements Listener {

    private NootSpeak plugin;
    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;
    private MessageUtils msgConfig;

    private final String resourceURL = "https://api.spigotmc.org/legacy/update.php?resource=59045";


    public UpdateListener(NootSpeak pl, ConfigUtils configUtils, BroadcastMsgUtils broadcastMsgUtils) {
        plugin = pl;
        cfgUtils = configUtils;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!cfgUtils.updaterEnabled) {
            return;
        }

        Player player = event.getPlayer();

        if (player.hasPermission("abc.admin")) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    HttpsURLConnection connection = (HttpsURLConnection) new URL(resourceURL).openConnection();
                    String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

                    if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage(msgUtils.colorize(msgConfig.prefix + " " + msgUtils.pluginOutOfDate));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
