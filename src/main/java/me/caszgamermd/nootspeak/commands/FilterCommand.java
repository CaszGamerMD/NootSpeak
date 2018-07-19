package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.FilterUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FilterCommand implements CommandExecutor {

    private FilterUtils fltrUtils;
    private MessageUtils msgUtils;
    private ConfigUtils cfgUtils;

    public FilterCommand(ConfigUtils configUtils, FilterUtils filterUtils, MessageUtils messageUtils) {
        cfgUtils = configUtils;
        fltrUtils = filterUtils;
        msgUtils = messageUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {

        if (args.length == 0) {

            sender.sendMessage(msgUtils.colorize("&b/ns &5filter &etoggle &b- enables or disables filter."));
            sender.sendMessage(msgUtils.colorize("&b/ns &5filter &7[&ccurse&7/&areplace&7] &eadd "
                    + "&7[&fword&7] &b- Adds to the &7[&cBadWords&7/&aReplacements&7] &bfile."));
            sender.sendMessage(msgUtils.colorize("&b/ns &5filter &7[&ccurse&7/&areplace&7] &eremove "
                    + "&7[&fword&7] &b- Removes from the &7[&cBadWords&7/&aReplacements&7] &bfile."));
            sender.sendMessage(msgUtils.colorize("&b/ns &5filter &7[&ccurse&7/&areplace&7] &ereload "
                    + "&b- reloads the list."));
            return true;

        }

        if (args[0].equalsIgnoreCase("toggle")) {

            cfgUtils.toggleFilter();
            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.filterEnabled
                    .replace("{status}", Boolean.toString(cfgUtils.filterEnabled))));
            return true;

        }

        // Curses File Commands
        if (args[0].equalsIgnoreCase("curse")) {

            if (args.length == 1) {

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyAction.replace("{file}", args[1])));
                return true;

            }

            if (args[1].equalsIgnoreCase("add")) {

                if (args.length >= 3) {

                    fltrUtils.addBadWord(sender, args[2].toLowerCase());
                    return true;

                }

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                        .replace("{action}", args[1])));
                return true;

            }

            if (args[1].equalsIgnoreCase("remove")) {

                if (args.length >= 3) {

                    fltrUtils.removeBadWord(sender, args[2].toLowerCase());
                    return true;

                }

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                        .replace("{action}", args[1])));
                return true;

            }

            if (args[1].equalsIgnoreCase("reload")) {

                fltrUtils.reloadBadWords();
                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.fileReloaded
                        .replace("{file}", args[0])));
                return true;

            }

            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownCommand));
            return true;

        }

        // Replacement File Commands
        if (args[0].equalsIgnoreCase("replace")) {

            if (args.length == 1) {
                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " "
                        + msgUtils.specifyAction.replace("{file}", args[1])));
                return true;

            }

            if (args[1].equalsIgnoreCase("add")) {

                if (args.length >= 3) {

                    fltrUtils.addGoodWord(sender, args[2].toLowerCase());
                    return true;

                }

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                        .replace("{action}", args[1])));
                return true;

            }

            if (args[1].equalsIgnoreCase("remove")) {

                if (args.length >= 3) {

                    fltrUtils.removeGoodWord(sender, args[2].toLowerCase());
                    return true;

                }

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                        .replace("{action}", args[1])));

                return true;

            }

            if (args[1].equalsIgnoreCase("reload")) {

                fltrUtils.reloadReplacements();
                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.fileReloaded
                        .replace("{file}", args[0])));
                return true;

            }

            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownFileName));
            return true;

        }

        return true;

    }

}
