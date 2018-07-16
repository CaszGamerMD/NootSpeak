package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;

    public ChatListener(ConfigUtils configUtils, MessageUtils messageUtils) {
        cfgUtils = configUtils;
        msgUtils = messageUtils;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!cfgUtils.filterEnabled) {
            return;
        }

        Player sender = event.getPlayer();
        String message = event.getMessage();
        String[] words = message.split(" ");
        String outgoingMessage;
        boolean censor = false;

        outgoingMessage = message.toLowerCase();

        for (String messageWord : words) {
            for (String word : cfgUtils.badWords) {
                if (messageWord.equalsIgnoreCase(word)) {
                    int length = word.length();
                    StringBuilder builder = new StringBuilder();
                    while (length > 0) {
                        builder.append('*');
                        length--;
                    }
                    String newMsg = builder.toString();
                    outgoingMessage = outgoingMessage.replace(word, newMsg);
                    censor = true;
                    event.setCancelled(true);
                    Bukkit.getLogger().info(sender.getName() + ": " + message);
                }
            }
        }

        if (censor) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: ") + outgoingMessage);
            }
            return;
        }

        event.setMessage(message);
    }
}
