package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.Main;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
        Player sender = event.getPlayer();
        String message = event.getMessage();
        String[] words = message.split(" ");
        String outgoingMessage;
        boolean censor = false;
        boolean ping = false;
        int counter = 0;
        Player target = null;

        outgoingMessage = "";

        if (cfgUtils.filterEnabled) {
            if (sender.hasPermission("nootspeak.filter.bypass")) {
                event.setMessage(message);
            }

            // Check For Bad Words
            for (String messageWord : words) {

                int index = random.nextInt(fltrUtils.replacements.size());
                String newWord = fltrUtils.replacements.get(index);

                for (String badWord : fltrUtils.badWords) {

                    //check if word is on the list
                    if (messageWord.equalsIgnoreCase(badWord) || messageWord
                            .replaceAll("[^A-Za-z]*", "").equalsIgnoreCase(badWord)) {

                        event.setCancelled(true);

                        messageWord = messageWord.replaceAll("[^A-Za-z]*", "");
                        // Replace the bad word with another word
                        messageWord = messageWord.replaceAll("(?i)\\b" + badWord + "\\b", newWord);
                        counter = (counter + 1);
                        censor = true;
                        plugin.takeMoney(sender, cfgUtils.swearCost);

                    }
                }

                //noinspection StringConcatenationInLoop
                outgoingMessage = outgoingMessage + messageWord + " ";

            }
        }

        if (cfgUtils.playerPingEnabled) {
            //check for player name in chat
            String[] messageWords = outgoingMessage.split(" ");
            outgoingMessage = "";

            // For Every Word In Chat Message

            for (String word : messageWords) {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (word.equalsIgnoreCase(player.getName())) {

                        target = player;
                        event.setCancelled(true);

                        // TODO: COLORIZE PLAYER NAME ONLY FOR PINGED PLAYER

                        System.out.println("checking: " + target.getName());
                        word = word.replaceAll("(?i)\\b" + target.getName() + "\\b", msgUtils
                                .colorize(cfgUtils.playerPingColor.replace("{player}", target.getName())));

                        target.playSound(target.getLocation(), Sound.valueOf(cfgUtils.pingSound), 100, 25);
                        ping = true;

                    }

                }

                //noinspection StringConcatenationInLoop
                outgoingMessage = outgoingMessage + word + " ";

            }

            if (censor) {

                double totalSwearCost = (cfgUtils.swearCost * counter);
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                String moneyString = formatter.format(totalSwearCost);
                sender.sendMessage(msgUtils.colorize("&4[&2Nootopian Chat Police&4]&7: You have been &4charged " +
                        moneyString + " &7for your language."));
                System.out.println(msgUtils.colorize("&7[NLP]: &b" + sender.getPlayerListName() +
                        " &cCharged for Cursing: " + moneyString));

                for (Player player : Bukkit.getOnlinePlayers()) {

                    player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outgoingMessage);

                }

                return;

            }

            if (ping) {

                for (Player recipient : event.getRecipients()) {
                    if (recipient != target) {
                        event.getRecipients().remove(target);
                        recipient.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + message);
                    }
                    target.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outgoingMessage);

                    if (target == recipient) {
                        return;
                    }
                }
                return;
            }

            event.setMessage(message);

            System.out.println(sender.getDisplayName() + ": " + outgoingMessage);

        }
    }
}
