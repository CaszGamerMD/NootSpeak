package me.caszgamermd.nootspeak.utils;


import me.caszgamermd.nootspeak.NootSpeak;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BroadcastMsgUtils {

    private NootSpeak plugin;
    private MessageUtils msgUtils;
    private FileConfiguration broadcasts;

    public BroadcastMsgUtils(NootSpeak pl) {
        plugin = pl;
    }

    public void loadConfig() {
        plugin.saveResource("broadcasts.yml", false);

        File file = new File(plugin.getDataFolder(), "broadcasts.yml");
        broadcasts = new YamlConfiguration();
        System.out.println("NootSpeak // ABC: default broadcasts saved *don't forget to edit this later*");

        try {
            broadcasts.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("NootSpeak // ABC: broadcasts loaded");
    }

    public FileConfiguration getBCMs() {
        return broadcasts;
    }

    public void handleCastCommand(String JSONCommand, String JSONLink, String displayText, String message) {
        if ((!JSONCommand.equalsIgnoreCase("none")) && (!JSONLink.equalsIgnoreCase("none"))) {
            Bukkit.broadcast("You can not have JSONCommand and JSONLink in the same broadcast!", "abc.admin");
            return;
        }

        if (!JSONCommand.equalsIgnoreCase("none")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TextComponent msg = new TextComponent(msgUtils.colorize(message));
                if (!displayText.equalsIgnoreCase("none")) {
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msgUtils.colorize(displayText)).create()));
                }
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + JSONCommand));
                player.spigot().sendMessage(msg);
            }
        }


        if (!JSONLink.equalsIgnoreCase("none")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TextComponent msg = new TextComponent(msgUtils.colorize(message));
                if (!displayText.equalsIgnoreCase("none")) {
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msgUtils.colorize(displayText)).create()));
                }
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, JSONLink));
                player.spigot().sendMessage(msg);
            }
        }


        if ((JSONCommand.equalsIgnoreCase("none")) && (JSONLink.equalsIgnoreCase("none"))) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TextComponent msg = new TextComponent(msgUtils.colorize(message));
                if (!displayText.equalsIgnoreCase("none")) {
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(msgUtils.colorize(displayText)).create()));
                }
                player.spigot().sendMessage(msg);
            }

        }
    }
}
