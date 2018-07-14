package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class NootSpeakCommand implements CommandExecutor {

    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;

    public NootSpeakCommand(ConfigUtils configUtils, MessageUtils messageUtils) {
        cfgUtils = configUtils;
        msgUtils = messageUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("nootspeak.admin")) {
            if (args.length == 0) {
                sender.sendMessage("/ns reload - (reload config/messages)");
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (args.length == 1) {
                    sender.sendMessage(msgUtils.colorize("&cSpecify File To Reload"));
                }

                if (args[1].equalsIgnoreCase("config")) {
                    cfgUtils.reloadConfig();
                }

                if (args[1].equalsIgnoreCase("lang")) {
                    msgUtils.reloadLang();

                }
            }

//            if (args[0].equalsIgnoreCase("squawk")) {
//                if (args.length == 1) {
//                    sender.sendMessage("&cSpecify &bprefix&c, &bplayercolor&c, or &bcooldown&c.");
//                }
//
//                if (args[1].equalsIgnoreCase("prefix")) {
//                    getConfig().set("SquawkPrefix");
//                }
//
//                if (args[1].equalsIgnoreCase("playercolor")) {
//                    getConfig().set("DisplayNameColor");
//
//                }
//
//                if (args[1].equalsIgnoreCase("cooldown")) {
//                    getConfig().set("SquawkCooldown");
//
//                }
//            }
        }
        return true;
    }
}
