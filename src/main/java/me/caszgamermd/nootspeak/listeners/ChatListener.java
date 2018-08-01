package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.NootSpeak;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChatListener implements Listener {

    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;
    private FilterUtils fltrUtils;
    private NootSpeak plugin;

    private Random random = new Random();

    public ChatListener(ConfigUtils configUtils, FilterUtils filterUtils, MessageUtils messageUtils, NootSpeak pl) {
        cfgUtils = configUtils;
        msgUtils = messageUtils;
        fltrUtils = filterUtils;
        plugin = pl;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        String chatMsg = event.getMessage();
        String[] chatMsgArray = chatMsg.split(" ");
        String fltrMsg;
        String outputMsg;
        String pingedMsg = "";
        boolean censor = false;
        boolean ping = false;
        int counter = 0;
        Player target = null;
        List<Player> pinged = new ArrayList<>();
        //sets output to be what came in.
        outputMsg = chatMsg;

        // Chat Filter/replace
        if (cfgUtils.filterEnabled) {
            if (!sender.hasPermission("nootspeak.filter.bypass")) {
                //resets the filter.
                fltrMsg = "";

                // todo? enable string replace vs **** if/else (cfgUtils.stringReplace) ? totally optional
                for (String chatFltrCheck : chatMsgArray) {
                    //get a random word from list.
                    int index = random.nextInt(fltrUtils.replacements.size());
                    String newWord = fltrUtils.replacements.get(index);

                    for (String badWord : fltrUtils.badWords) {
                        if (chatFltrCheck.equalsIgnoreCase(badWord) || chatFltrCheck
                                .replaceAll("[^A-Za-z]*", "").equalsIgnoreCase(badWord)) {

                            chatFltrCheck = chatFltrCheck.replaceAll("[^A-Za-z]*", "");
                            chatFltrCheck = chatFltrCheck.replaceAll("(?i)\\b" + badWord + "\\b", msgUtils.colorize(newWord));
                            counter = (counter + 1);
                            censor = true;
                            plugin.takeMoney(sender, cfgUtils.swearCost);
                        }
                    }
                    //noinspection StringConcatenationInLoop
                    fltrMsg = fltrMsg + chatFltrCheck + " ";
                }
                //sets output to be filtered MSG.
                outputMsg = fltrMsg;
            }
        }

        // Ping Check
        if (cfgUtils.playerPingEnabled) {
            // TODO: ASSIGN - HAILEY - COOLDOWN
            if (sender.hasPermission("nootspeak.pingplayers")) {
                //now using filtered message scan for names.
                String[] outputMsgArray = outputMsg.split(" ");
                //resets the ping checker.
                pingedMsg = "";

                for (String pingCheck : outputMsgArray) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (pingCheck.equalsIgnoreCase(player.getName())) {

                            target = player;
                            pinged.add(target);
                            //if sender is pinged, ignore.
                            if (target == sender) {return;}

                            pingCheck = pingCheck.replaceAll("(?i)\\b" + target.getName() + "\\b", msgUtils
                                    .colorize(cfgUtils.playerPingColor.replace("{player}", target.getName())));
                            ping = true;
                        }
                    }
                    //noinspection StringConcatenationInLoop
                    pingedMsg = pingedMsg + pingCheck + " ";
                }
            }
        }

        if (censor) {
            //sends total charge message to player/console
            double totalSwearCost = (cfgUtils.swearCost * counter);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String moneyString = formatter.format(totalSwearCost);
            sender.sendMessage(msgUtils.colorize("&4[&2Nootopian Chat Police&4]&7: You have been &4charged " +
                    moneyString + " &7for your language."));
            System.out.println(msgUtils.colorize("&7[NLP]: &b" + sender.getPlayerListName() +
                    " &cCharged for Cursing: " + moneyString));
        }

        if (ping) {
            //if ANY pings are applied, MSGs are out put here.
            event.setCancelled(true);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!pinged.contains(player)) {
                    player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outputMsg);
                } else {
                    player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + pingedMsg);
                    player.playSound(player.getLocation(), Sound.valueOf(cfgUtils.pingSound), 100, 50);
                }
            }
            return;
        }
        //otherwise all MSGs are output here, (Non-)Filtered, to all players/ console.
        event.setCancelled(true);
        System.out.println(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outputMsg);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outputMsg);
        }
    }
}
