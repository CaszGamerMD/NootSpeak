package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.NootSpeak;
import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
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
import java.util.concurrent.TimeUnit;


public class ChatListener implements Listener {

    private ConfigUtils cfgUtils;
    private CooldownUtils cdUtils;
    private MessageUtils msgUtils;
    private FilterUtils fltrUtils;
    private NootSpeak plugin;

    private Random random = new Random();

    public ChatListener(CooldownUtils cooldownUtils, ConfigUtils configUtils, FilterUtils filterUtils, MessageUtils messageUtils, NootSpeak pl) {
        cdUtils = cooldownUtils;
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
                            //todo Adjust, make money taken from player add to a var: moneyTaken, lotto charge part-1
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
        if (cfgUtils.playerPingEnabled) { //is pinging on, server wide

            Player playerPingCD = sender;
            long timePast = System.currentTimeMillis() - cdUtils.getCooldownPing(playerPingCD.getUniqueId());
            long timeLeft = cfgUtils.pingCooldown - TimeUnit.MILLISECONDS.toSeconds(timePast);

            if (sender.hasPermission("nootspeak.pingplayers")) {

                //now using filtered message scan for names.
                String[] outputMsgArray = outputMsg.split(" ");
                //resets the ping checker.
                pingedMsg = "";

                for (String pingCheck : outputMsgArray) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (pingCheck.equalsIgnoreCase(player.getName())) {

                            target = player;

                            if (target == sender) { //player tried to ping self
                                pingCheck = pingCheck.replace("(?i)\\b" + target.getName() + "\\b", "&f" + target.getName() + "&f");
                                break;
                                //moves on

                            } else {

                                if (!sender.hasPermission("nootspeak.ping.bypass") &&
                                        TimeUnit.MILLISECONDS.toSeconds(timePast) <= cfgUtils.pingCooldown) {
                                    //player doesnt have ping CD bypass & time past is less than CD rate

                                    //todo send as a bossbar msg?
                                    playerPingCD.sendMessage(msgUtils.colorize(msgUtils.prefix + " " +
                                            msgUtils.pingCooldown.replace("{time}", String.valueOf(timeLeft))));

                                } else {

                                    pinged.add(target);
                                    pingCheck = pingCheck.replaceAll("(?i)\\b" + target.getName() + "\\b",
                                            msgUtils.colorize(cfgUtils.playerPing.replace("{player}", target.getName()) + "&f"));
                                    ping = true;
                                    cdUtils.setCooldownPing(playerPingCD.getUniqueId(), System.currentTimeMillis());
                                }
                            }
                        }
                    }
                    //noinspection StringConcatenationInLoop
                    pingedMsg = pingedMsg + pingCheck + " ";

                }

                if (censor) {
                    //sends total charge message to player/console
                    double totalSwearCost = (cfgUtils.swearCost * counter);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    String moneyString = formatter.format(totalSwearCost);
                    sender.sendMessage(msgUtils.colorize("&4[&2Swear Jar&4]&7: You have been &4charged " +
                            moneyString + " &7for your language."));
                    // sender.sendMessage(msgUtils.colorize("&4[&2Swear Jar&4]&7: This Charge has been added to the Current Lottery. &a " + LottoNew + " &4is the current pot size."));
                    System.out.println(msgUtils.colorize("&7[NLP]: &b" + sender.getPlayerListName() +
                            " &cCharged for Cursing: " + moneyString));
                    //todo take percentage of moneyTaken and add to lotto Part-2
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
                    System.out.println(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outputMsg);
                    return;
                }

                //otherwise all MSGs are output here, (Non-)Filtered, to all players/ console.
                event.setCancelled(true);
                System.out.println(sender.getDisplayName() + ": " + outputMsg);
                for (
                        Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(sender.getDisplayName() + msgUtils.colorize("&7: &f") + outputMsg);
                }
            }
        }
    }
}