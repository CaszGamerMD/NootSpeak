package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.Main;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
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
        // IF the Filter in enabled, proceed.
//        if (!cfgUtils.filterEnabled) {
//            return;
//        }


        Player sender = event.getPlayer();
        String message = event.getMessage();
        String[] words = message.split(" ");
        String outgoingMessage;
        boolean censor = false;
        int counter = 0;

        outgoingMessage = "";

        if (cfgUtils.filterEnabled) { // TODO add a perm bypass
            event.setCancelled(true);
            // checks for bad words:
            for (String messageWord : words) {
                int index = random.nextInt(fltrUtils.replacements.size());       // Randomly Select Replacer Word
                String newWord = fltrUtils.replacements.get(index);

                for (String badWord : fltrUtils.badWords) { // Check If Word Equals Bad Word
                    //check if word is on the list
                    if (messageWord.equalsIgnoreCase(badWord) || messageWord.replaceAll("[^A-Za-z]*", "")
                            .equalsIgnoreCase(badWord)) {
                        messageWord = messageWord.replaceAll("[^A-Za-z]*", "");
                        // Replace the bad word with another word
                        messageWord = messageWord.replaceAll("(?i)\\b" + badWord + "\\b", newWord);
                        counter = (counter + 1);            // count the words replaced, includes duplicates... as intended
                        censor = true;                      // tell loop it has been censored
                        plugin.takeMoney(sender, cfgUtils.swearCost);
                    }
                }
                //noinspection StringConcatenationInLoop
                outgoingMessage = outgoingMessage + messageWord + " ";
            }
        } else {
            for (String messageWord : words) {
                //noinspection StringConcatenationInLoop
                outgoingMessage = outgoingMessage + messageWord + " ";
            }
        }

        if (cfgUtils.playerPingEnabled) { //todo play sound for pinged player
            //todo colorize ONLY the name in message output
            System.out.println("Ping Enabled");
            event.setCancelled(true);
            //check for player name in chat
            String[] wordsPN = outgoingMessage.split(" ");
            outgoingMessage = "";
            // For Every Word In Chat Message
            for (String checkName : wordsPN) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (checkName.equalsIgnoreCase(player.getPlayerListName())) { // checkName = String, String.valueOf(player) = CraftPlayer{name=PLAYERNAME}
                        System.out.println("checking: " + player.getPlayerListName());
                        checkName = checkName.replaceAll("(?i)\\b" + player.getPlayerListName() + "\\b", cfgUtils.playerPingColor + player.getPlayerListName());
                        System.out.println("Matched name: " + checkName);
                        //  player#playSound(player.getLocation(), Sound.(cfgUtils.pingSound), 100, 100);
                    }
                }
                //noinspection StringConcatenationInLoop
                outgoingMessage = outgoingMessage + checkName + " ";
            }
        }

        if (censor) {                      // If String Is Censored
            double totalSwearCost = (cfgUtils.swearCost * counter);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(totalSwearCost);
            sender.sendMessage(msgUtils.colorize("&4[&2Nootopian Chat Police&4]&7: You have been &4charged " +
                    moneyString + " &7for your language."));
            System.out.println(msgUtils.colorize("&7[NLP]: &b" + sender.getPlayerListName() +
                    " &cCharged for Cursing: " + moneyString));
        }

        event.setCancelled(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outgoingMessage);
        }
        System.out.println(sender.getDisplayName() + ": " + outgoingMessage);
    }
}
