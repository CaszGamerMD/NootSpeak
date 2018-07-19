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

                sender.sendMessage(msgUtils.colorize("&b/ns &areload &7[config/lang] &b- Reload config/messages file."));
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

            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.unknownCommand));
            return true;

        }

        sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.noPermission));
        return true;

    }
}
