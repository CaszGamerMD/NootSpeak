package me.caszgamermd.nootspeak.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class SquawkCommands implements CommandExecutor {

    private String colorize(String string) {
        String colored = ChatColor.translateAlternateColorCodes('&', string);
        return colored;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }


        List<String> fullMsg = Arrays.asList(args).subList(0, args.length);
        String chat = String.join(" ", fullMsg);
        System.out.println(chat);
        Player player = (Player) sender;
        //        String squawkPrefix = getConfig().getString("prefix");
        //        String squawkName = getConfig().getString("name");


        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(colorize("&2&l * &b&l" + player.getDisplayName() + " &f" + chat));
        };

        return true;
// /squawk &4R&6A&eI&aN&2B&3O&bW &1P&9A&5R&dT&cY&f!
    }



}
