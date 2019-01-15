package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.utils.MessageUtils;
import me.caszgamermd.nootspeak.utils.TrackerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TrackerCommand implements CommandExecutor {

    private MessageUtils msgUtils;
    private TrackerUtils trkUtils;
    private List<String> line = Arrays.asList("1", "2", "3");
    private List<String> mcmmoSkillList = Arrays.asList("acrobactics", "alchemy", "archery", "axes", "excavation",
            "herbalism", "fishing", "mining", "repair", "swords", "taming", "unarmed", "woodcutting");

    public TrackerCommand(MessageUtils messageUtils, TrackerUtils trackerUtils) {
        msgUtils = messageUtils;
        trkUtils = trackerUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (Bukkit.getServer().getPluginManager().getPlugin("TitleManager") == null) {
            sender.sendMessage(msgUtils.colorize("&cServer Doesn't have &bTitleManager&c, NootTracker Disabled."));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(msgUtils.colorize("&3[NootTracker]: &b/tracker set [1-3] [MCMMO Skill]"));
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args.length <= 2) {
                sender.sendMessage(msgUtils.colorize("&3[NootTracker]: &b/tracker set [1-3] [MCMMO Skill] "));
                return true;
            }

            if (args.length == 3) {
                if (args[1].equalsIgnoreCase("0")) {
                    trkUtils.trackerChecker(sender);
                    return true;
                }

                for (String lineCheck : line) {
                    if (args[1].equalsIgnoreCase(lineCheck)) {
                        for (String skillCheck : mcmmoSkillList) {
                            if (args[2].equalsIgnoreCase(skillCheck)) {

                                trkUtils.trackerEditor(sender, args[1], args[2]);
                                sender.sendMessage(msgUtils.colorize("&3[NootTracker]: &bLine " + args[1] + " has been set to track: " + args[2]));
                                return true;
                            }
                        }
                        if (args[2].equalsIgnoreCase("clear")) {
                            trkUtils.trackerClear(sender, args[1]);
                            return true;
                        }
                        sender.sendMessage(msgUtils.colorize("&3[NootTracker]: &cYou Need a Valid &6MCMMO Skill&c, check &b/mcstats."));
                        return true;
                    }
                }
                sender.sendMessage(msgUtils.colorize("&3[NootTracker]: &cYou Need a Valid Line Number, [1-3]."));
                return true;
            }
            return true;
        }
        return false;
    }
}

