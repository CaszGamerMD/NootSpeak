package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.Main;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.NumberFormat;
import java.util.Random;


public class ChatListener implements Listener {

    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;
    private FilterUtils fltrUtils;
    private Main plugin;

    private Random random = new Random();

    public ChatListener(ConfigUtils configUtils, FilterUtils filterUtils, MessageUtils messageUtils, Main pl) {
        cfgUtils = configUtils;
        msgUtils = messageUtils;
        fltrUtils = filterUtils;
        plugin = pl;
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
        int counter = 0;

        // Randomly Select Replacer Word
        int index = random.nextInt(fltrUtils.replacements.size());
        String newWord = fltrUtils.replacements.get(index);


        outgoingMessage = "";

        // For Every Word In Chat Message
        for (String messageWord : words) {
            // Check If Word Equals Bad Word
            for (String badWord : fltrUtils.badWords) {
                //check if word is on the list
                if (messageWord.equalsIgnoreCase(badWord) || messageWord.replaceAll("[^A-Za-z]*", "").equalsIgnoreCase(badWord)) {
                    messageWord = messageWord.replaceAll("[^A-Za-z]*", "");
                    // Replace the bad word with another word
                    messageWord = messageWord.replaceAll("(?i)\\b" + badWord + "\\b", newWord + " ");
                    // count the words replaced, includes duplicates... as intended
                    counter = (counter + 1);
                    //tell loop it has been censored
                    censor = true;
                    // Cancel Original Message Being Sent
                    event.setCancelled(true);
                }
            }
        outgoingMessage = outgoingMessage + messageWord + "";
        }

        // If String Is Censored
        if (censor) {
            // math for total cost TODO 2 places after decimal or NO places after decimal

            double totalSwearCost = (cfgUtils.swearCost * counter);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(totalSwearCost);
            plugin.takeMoney(sender, (totalSwearCost));
            //msgs 1 for alerting money being taken, other is new message     TODO output player message to console as well as the charge.
            sender.sendMessage(msgUtils.colorize("&4[&2Nootopian Language Police&4]&7: You have made &4bad choices&7 and have been &4charged " + moneyString + "&7."));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outgoingMessage);
            }
            return;
        }

        // If String Is Not Censored, Send Message Normally
        event.setMessage(message);
    }
}
