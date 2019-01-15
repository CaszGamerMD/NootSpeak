package me.caszgamermd.nootspeak.utils;


import me.caszgamermd.nootspeak.NootSpeak;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Autobroadcaster {

    public int chatActivity;
    private NootSpeak plugin;
    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;
    private BroadcastMsgUtils bcmUtils;

    public Map<Integer, String> randomSelector = new HashMap<>();
    public int taskID;

    public Autobroadcaster(BroadcastMsgUtils broadcastMsgUtils, ConfigUtils configUtils, MessageUtils messageUtils, NootSpeak pl) {
        plugin = pl;
        bcmUtils = broadcastMsgUtils;
        cfgUtils = configUtils;
        msgUtils = messageUtils;
    }

    public void broadcast() {
        ConfigurationSection msgSection = bcmUtils.getBCMs().getConfigurationSection("messages");

        taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            int i = 0;

            for (String s : msgSection.getKeys(false)) {
                randomSelector.put(i, s);
                i += 1;
            }

            String path = randomSelector.get(ThreadLocalRandom.current().nextInt(0, randomSelector.size()));

            String message = msgSection.getString(path + ".Message");
            String JSONCommand = msgSection.getString(path + ".JSONCommand");
            String JSONLink = msgSection.getString(path + ".JSONLink");
            String displayText = msgSection.getString(path + ".Display-Text");

            final int interval = cfgUtils.chatInterveral;

            if (chatActivity >= interval) {
                chatActivity = 0;

                if ((!JSONCommand.equalsIgnoreCase("none")) && (!JSONLink.equalsIgnoreCase("none"))) {

                    Bukkit.broadcast(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.cantHaveBoth), "abc.admin");
                    return;
                }
                System.out.println(message);
                if (!JSONCommand.equalsIgnoreCase("none")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        TextComponent msg = new TextComponent(msgUtils.colorize("" + message));
                        if (displayText.equalsIgnoreCase("none")) {
                            return;
                        }
                        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msgUtils.colorize(displayText)).create()));
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + JSONCommand));
                        player.spigot().sendMessage(msg);
                    }
                }

                if (!JSONLink.equalsIgnoreCase("none")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        TextComponent msg = new TextComponent(msgUtils.colorize("" + message));
                        if (displayText.equalsIgnoreCase("none")) {
                            return;
                        }
                        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msgUtils.colorize(displayText)).create()));
                        msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, JSONLink));
                        player.spigot().sendMessage(msg);
                    }
                }

                if ((JSONCommand.equalsIgnoreCase("none")) && (JSONLink.equalsIgnoreCase("none"))) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        TextComponent msg = new TextComponent(msgUtils.colorize("" + message));
                        if (displayText.equalsIgnoreCase("none")) {
                            return;
                        }
                        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msgUtils.colorize(displayText)).create()));
                        player.spigot().sendMessage(msg);
                    }
                }
            }
        }, 0L, cfgUtils.broadcastInterval * 20);
    }
}
