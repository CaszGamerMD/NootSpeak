package me.caszgamermd.nootspeak.commands;

import java.util.Arrays;
import java.util.List;

import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SquawkCommand implements CommandExecutor {

    private MessageUtils msgUtils;

    public SquawkCommand(MessageUtils messageUtils) {
        msgUtils = messageUtils;
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

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(msgUtils.colorize("&2&l * &b&l" + player.getDisplayName() + " &f" + chat));
        }
        return true;
        // squawk &4R&6A&eI&aN&2B&3O&bW &1P&9A&5R&dT&cY&f!
    }
}
