package me.caszgamermd.nootspeak.utils;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


public class TrackerUtils {

    private TitleManagerAPI api = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");


    public void trackerEditor(CommandSender sender, String line, String skill) {
        Player player = (Player) sender;
        int index = 9 - Integer.parseInt(line);
        String mcmmoSkill = skill;
        String trackSkill = "%javascript_" + mcmmoSkill + "%";


        trackerChecker(sender);
        api.setScoreboardValueWithPlaceholders(player, index, trackSkill);

    }

    public void trackerClear(CommandSender sender, String line) {

        Player player = (Player) sender;
        int index = 9 - Integer.parseInt(line);
        api.removeScoreboardValue(player, index);
        trackerChecker(sender);
    }

    private void trackerChecker(CommandSender sender) {

        Player player = (Player) sender;
        api.setScoreboardValueWithPlaceholders(player, 9, "&b> &6&lMCMMO Trackers:");

        if (api.getScoreboardValue(player, 8) == null) {
            if (api.getScoreboardValue(player, 7) == null) {
                if (api.getScoreboardValue(player, 6) == null) {
                    api.removeScoreboardValue(player, 9);
                }
            }
        }
    }

}
