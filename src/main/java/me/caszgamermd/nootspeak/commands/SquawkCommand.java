package me.caszgamermd.nootspeak.commands;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SquawkCommand implements CommandExecutor {

    private MessageUtils msgUtils;
    private ConfigUtils cfgUtils;
    private CooldownUtils cdUtils;

    public SquawkCommand(ConfigUtils configUtils, CooldownUtils cooldownUtils, MessageUtils messageUtils) {
        msgUtils = messageUtils;
        cfgUtils = configUtils;
        cdUtils = cooldownUtils;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        Player player = (Player) sender;

        long timePast = System.currentTimeMillis() - cdUtils.getCooldown(player.getUniqueId());
        long timeLeft = cfgUtils.squawkCooldown - TimeUnit.MILLISECONDS.toSeconds(timePast);
        if (TimeUnit.MILLISECONDS.toSeconds(timePast) >= cfgUtils.squawkCooldown) {
            List<String> fullMsg = Arrays.asList(args).subList(0, args.length);
            String chat = String.join(" ", fullMsg);
            System.out.println(chat);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(msgUtils.colorize(cfgUtils.squawkPrefix + cfgUtils.squawkPrefix + player.getDisplayName() + " &f" + chat));
            }
            cdUtils.setCooldown(player.getUniqueId(), System.currentTimeMillis());
            return true;
        }
        player.sendMessage(msgUtils.colorize("&4" + timeLeft + " &cseconds before you can use this feature again."));
        return true;
    }
}

// squawk &4R&6A&eI&aN&2B&3O&bW &1P&9A&5R&dT&cY&f!
