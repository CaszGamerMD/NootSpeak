package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class NootSpeakCommand implements CommandExecutor {

    private ConfigUtils cfgUtils;
    private MessageUtils msgUtils;
    private FilterUtils fltrUtils;

    public NootSpeakCommand(ConfigUtils configUtils, FilterUtils filterUtils, MessageUtils messageUtils) {
        cfgUtils = configUtils;
        msgUtils = messageUtils;
        fltrUtils = filterUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("nootspeak.admin")) {
            if (args.length == 0) {
                sender.sendMessage("/ns reload [config/lang]- reload config/messages file");
                sender.sendMessage("/ns filter - Brings up ns filter help");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (args.length == 1) {
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyFile));
                    return true;
                }

                if (args[1].equalsIgnoreCase("config")) {
                    cfgUtils.reloadConfig();
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.fileReloaded.replace("{file}", args[1])));
                    return true;
                }

                if (args[1].equalsIgnoreCase("lang")) {
                    msgUtils.reloadLang();
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.fileReloaded.replace("{file}", args[1])));
                    return true;
                }

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownFileName));
            }

            if (args[0].equalsIgnoreCase("filter")) {
                if (args.length == 1) {
                    sender.sendMessage("/ns filter list -  Lists all filtered words");
                    sender.sendMessage("/ns filter add [word] - Adds a word to filter");
                    sender.sendMessage("/ns filter remove [word] - Removes a word from filter");
                    sender.sendMessage("/ns filter toggle - enables or disables filter");
                    return true;
                }

                if (args[1].equalsIgnoreCase("list")) {
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.filterList
                            .replace("{list}", fltrUtils.badWords.toString())));
                    return true;
                }

                if (args[1].equalsIgnoreCase("toggle")) {
                    cfgUtils.toggleFilter();
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.filterEnabled
                            .replace("{status}", Boolean.toString(cfgUtils.filterEnabled))));
                    return true;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (args.length == 3) {
                        fltrUtils.addWord(sender, args[2].toLowerCase());
                        return true;
                    }
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                            .replace("{action}", args[1])));
                    return true;
                }

                if (args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 3) {
                        fltrUtils.removeWord(sender, args[2].toLowerCase());
                        return true;
                    }
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                            .replace("{action}", args[1])));
                    return true;
                }
            }
            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownCommand));
            return true;

        }
        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.noPermission));
        return true;
    }
}
