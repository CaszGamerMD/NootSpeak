package me.caszgamermd.nootspeak.commands;

import me.caszgamermd.nootspeak.AutoNoot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class AutoNootCommand implements CommandExecutor{
    AutoNoot pl;

    public AutoNootCommand(AutoNoot plugin)
    {
        pl = plugin;
    }



    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player)sender;

        if (label.equalsIgnoreCase("autobroadcaster"))
        {
            if (args.length == 0)
            {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &ehttps://www.spigotmc.org/resources/autobroadcaster.49662/"));

                return true;
            }


            if (args.length >= 1)
            {
                if (args[0].equalsIgnoreCase("reload"))
                {
                    if (p.hasPermission("abc.reload"))
                    {
                        pl.reloadConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &bPlugin has been reloaded!"));

                        return true;
                    }


                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " " + pl.noPerm));

                    return true;
                }


                if (args[0].equalsIgnoreCase("add"))
                {
                    if (p.hasPermission("abc.add"))
                    {
                        if (args.length > 1)
                        {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < args.length; i++) {
                                sb.append(args[i]).append(' ');
                            }

                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &bAdded '" + sb.toString() + "' to the config!"));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &cGo to the messages.yml configuration file to edit JSON materials!"));

                            int length = pl.getMessagesConfig().getConfigurationSection("messages").getKeys(false).size() + 1;
                            pl.getMessagesConfig().set("messages." + String.valueOf(length) + ".message", sb.toString());
                            pl.getMessagesConfig().set("messages." + String.valueOf(length) + ".JSONCommand", "none");
                            pl.getMessagesConfig().set("messages." + String.valueOf(length) + ".JSONLink", "none");
                            pl.saveMessagesConfig();
                        }
                        else
                        {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &cPlease add a message!"));
                        }


                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " " + pl.noPerm));
                    }

                }
                else if (args[0].equalsIgnoreCase("set"))
                {
                    if (args.length >= 2)
                    {
                        if (args[1].equalsIgnoreCase("time"))
                        {
                            if (p.hasPermission("abc.set.time"))
                            {
                                if (args.length == 3)
                                {
                                    if (org.apache.commons.lang.math.NumberUtils.isNumber(args[2]))
                                    {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &bTime has been set to '" + args[2].toString() + "!'"));

                                        pl.getConfig().set("Time", Integer.valueOf(args[2]));
                                        pl.saveConfig();
                                        pl.reloadConfig();
                                    }
                                    else
                                    {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &bTime must be a number!"));
                                    }


                                }
                                else {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &b/autobroadcaster set time <seconds>"));
                                }


                            }
                            else {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " " + pl.noPerm));
                            }

                        }


                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &b/autobroadcaster set time <seconds>"));
                    }


                }
                else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.prefix + " &cUsage: /autobroadcaster <reload, add, set>"));
                }
            }
        }




        return true;
    }
}
