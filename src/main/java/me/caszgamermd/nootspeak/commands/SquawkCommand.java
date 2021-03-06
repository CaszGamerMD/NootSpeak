package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.utils.ConfigUtils;
import me.caszgamermd.nootspeak.utils.CooldownUtils;
import me.caszgamermd.nootspeak.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SquawkCommand implements CommandExecutor {

    private MessageUtils msgUtils;
    private ConfigUtils cfgUtils;
    private CooldownUtils cdUtils;

    public SquawkCommand(CooldownUtils cooldownUtils, ConfigUtils configUtils, MessageUtils messageUtils) {
        msgUtils = messageUtils;
        cfgUtils = configUtils;
        cdUtils = cooldownUtils;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.mustBePlayer));
            return true;

        }

        Player player = (Player) sender;

        // If The Player Has Permission To Bypass Cooldown
        if (player.hasPermission("nootspeak.squawk.bypass")) {

            if (args.length > 0) {

                List<String> fullMsg = Arrays.asList(args).subList(0, args.length);
                String chat = String.join(" ", fullMsg);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(msgUtils.colorize(cfgUtils.squawkPrefix + " "
                            + cfgUtils.playerColor + player.getDisplayName() + " &c" + cfgUtils.defaultChatColor + chat));
                }

                return true;

            }

            player.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.emptySquawk));
            return true;

        }

        // If The Command Has Args
        if (args.length > 0) {

            long timePast = System.currentTimeMillis() - cdUtils.getCooldownSqk(player.getUniqueId());
            long timeLeft = cfgUtils.squawkCooldown - TimeUnit.MILLISECONDS.toSeconds(timePast);


            // If Its Been Past So Many Seconds, Allow Command Again
            if (TimeUnit.MILLISECONDS.toSeconds(timePast) >= cfgUtils.squawkCooldown) {

                List<String> fullMsg = Arrays.asList(args).subList(0, args.length);
                String chat = String.join(" ", fullMsg);

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(msgUtils.colorize(cfgUtils.squawkPrefix + " "
                            + cfgUtils.playerColor + player.getDisplayName() + " &c" + cfgUtils.defaultChatColor + chat));
                }

                cdUtils.setCooldownSqk(player.getUniqueId(), System.currentTimeMillis());
                return true;

            }

            player.sendMessage(msgUtils.colorize(msgUtils.prefix + " " +
                    msgUtils.squawkCooldown.replace("{time}", String.valueOf(timeLeft))));
            return true;

        }

        player.sendMessage(msgUtils.colorize(msgUtils.prefix + " " + msgUtils.emptySquawk));
        return true;

    }
}

// squawk &4R&6A&eI&aN&2B&3O&bW &1P&9A&5R&dT&cY&f!
