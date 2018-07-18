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
                sender.sendMessage(msgUtils.colorize("&b/ns &areload &7[config/lang] &b- Reload config/messages file."));
                sender.sendMessage(msgUtils.colorize("&b/ns &5filter &b- Brings up &2NootSpeak &bFilter Help.")); //TODO have a /filter shortcut
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

            //TODO returns null at points
            if (args[0].equalsIgnoreCase("filter")) {
                if (args.length == 1) {
//                    sender.sendMessage(msgUtils.colorize("&b/ns &5filter &elist &b- Lists all filtered words."));
                    sender.sendMessage(msgUtils.colorize("&b/ns &5filter &etoggle &b- enables or disables filter."));
                    sender.sendMessage(msgUtils.colorize("&b/ns &5filter &7[&ccurse&7/&areplace&7] &eadd " + "&7[&fword&7] &b- Adds to the &7[&cBadWords&7/&aReplacements&7] &bfile."));
                    sender.sendMessage(msgUtils.colorize("&b/ns &5filter &7[&ccurse&7/&areplace&7] &eremove " + "&7[&fword&7] &b- Removes from the &7[&cBadWords&7/&aReplacements&7] &bfile."));
                    sender.sendMessage(msgUtils.colorize("&b/ns &5filter &7[&ccurse&7/&areplace&7] &ereload " + "&b- reloads the list."));

                    return true;
                }
//                Disabled, lists will be long and so unneeded, confirm.
//                if (args[1].equalsIgnoreCase("list")) {
//                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.filterList
//                            .replace("{list}", fltrUtils.badWords.toString())));
//                    return true;
//                }

                if (args[1].equalsIgnoreCase("toggle")) {
                    cfgUtils.toggleFilter();
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.filterEnabled
                            .replace("{status}", Boolean.toString(cfgUtils.filterEnabled))));
                    return true;
                }
//              curses section
                if (args[1].equalsIgnoreCase("curse")) {
                    if (args.length == 2) {
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyFile));  // TODO specify action
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("add")) {
                        if (args.length >= 4) {
                            fltrUtils.addBadWord(sender, args[3].toLowerCase());
                            return true;
                        }
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                                .replace("{action}", args[2])));
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("remove")) {
                        if (args.length >= 4) {
                            fltrUtils.removeBadWord(sender, args[3].toLowerCase());
                            return true;
                        }
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                                .replace("{action}", args[2])));
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("reload")) {
                        fltrUtils.reloadBadWords();
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.fileReloaded
                                .replace("{file}", args[1])));
                        return true;
                    }
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownFileName)); // TODO unknown action for CURSE
                    return true;
                }
//              Replacement section
                if (args[1].equalsIgnoreCase("replace")) {
                    if (args.length == 2) {
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyFile));  // TODO specify action
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("add")) {
                        if (args.length >= 4) {
                            fltrUtils.addGoodWord(sender, args[3].toLowerCase());
                            return true;
                        }
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                                .replace("{action}", args[2])));
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("remove")) {
                        if (args.length >= 4) {
                            fltrUtils.removeGoodWord(sender, args[3].toLowerCase());
                            return true;
                        }
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.specifyWord
                                .replace("{action}", args[2])));
                        return true;
                    }

                    if (args[2].equalsIgnoreCase("reload")) {
                        fltrUtils.reloadReplacements();
                        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.fileReloaded
                                .replace("{file}", args[1])));
                        return true;
                    }
                    sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownFileName)); //TODO unknown action for replace
                    return true;
                }

                sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownFileName));
            }

            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownCommand));
            return true;

        }
        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.noPermission));
        return true;
    }
}
