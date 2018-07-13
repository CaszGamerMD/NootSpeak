package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NootSpeakCommand implements CommandExecutor {

    private ConfigUtils cfgUtils;

    public NootSpeakCommand(ConfigUtils configUtils) {
        cfgUtils = configUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("nootspeak.admin")) {
            if (args.length == 0) {
                sender.sendMessage("/ns reload - (reload config/messages)");
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (args.length == 1) {
                    sender.sendMessage("Specify File To Reload");
                }

                if (args[1].equalsIgnoreCase("config")) {
                    cfgUtils.reloadConfig();
                }
            }
        }
        return true;
    }
}
