package me.caszgamermd.nootspeak;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import me.caszgamermd.nootspeak.utils.MessageUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
// import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

    public class AutoNoot extends org.bukkit.plugin.java.JavaPlugin {
        public final String prefix = getConfig().getString("prefix");
        public final String noPerm = getConfig().getString("noPermission");

        public int chatActivity;
        private File configf;
        private File messagesf;
        private FileConfiguration config;
        private MessageUtils msgUtils;

        public AutoNoot() {}

        public void onEnable()
        {
            Bukkit.getLogger().info("AutoBroadcast is online!");

           // createFiles();
            autobroadcast();

            getCommand("autobroadcaster").setExecutor(new me.caszgamermd.nootspeak.commands.AutoNootCommand(this));

            getServer().getPluginManager().registerEvents(new me.caszgamermd.nootspeak.listeners.ChatListener(this), this);
        }



        public void onDisable()
        {
            Bukkit.getLogger().info("AutoBroadcast is offline!");
        }


        public void autobroadcast()
        {
            long time = getConfig().getInt("Time") * 20;
            final int interval = getConfig().getInt("ChatInterval");

            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
            {

                public void run()
                {
                    int length = ThreadLocalRandom.current().nextInt(getMessagesConfig().getConfigurationSection("messages").getKeys(false).size()) + 1;
                    message = getMessagesConfig().getConfigurationSection("messages").getString(String.valueOf(length) + ".message");
                    JSONCommand = getMessagesConfig().getConfigurationSection("messages").getString(String.valueOf(length) + ".JSONCommand");
                    JSONLink = getMessagesConfig().getConfigurationSection("messages").getString(String.valueOf(length) + ".JSONLink");


                    if (chatActivity >= interval)
                    {
                        chatActivity = 0;

                        if (getConfig().getBoolean("WantHeader") == true)
                        {
                            for (Player player : Bukkit.getOnlinePlayers())
                            {
                                player.sendMessage(msgUtils.colorize(getConfig().getString("Header")));
                            }
                        }



                        if ((!JSONCommand.equalsIgnoreCase("none")) && (!JSONLink.equalsIgnoreCase("none")))
                        {
                            Bukkit.broadcastMessage(msgUtils.colorize("&4&l<< You can not have both JSONCommand and JSONLink enabled! >>"));
                        }
                        else
                        {
                            if (!JSONCommand.equalsIgnoreCase("none"))
                            {
                                for (Player player : Bukkit.getOnlinePlayers())
                                {
                                    TextComponent msg = new TextComponent(msgUtils.colorize(message));
                                    // msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/" + JSONCommand).create()));
                                    msg.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + JSONCommand));
                                    player.spigot().sendMessage(msg);
                                }
                            }


                            if (!JSONLink.equalsIgnoreCase("none"))
                            {
                                for (Player player : Bukkit.getOnlinePlayers())
                                {
                                    TextComponent msg = new TextComponent(msgUtils.colorize(message));
                                    // msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(JSONLink).create())); -- create a config for "display"
                                    msg.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, JSONLink));
                                    player.spigot().sendMessage(msg);
                                }
                            }


                            if ((JSONCommand.equalsIgnoreCase("none")) && (JSONLink.equalsIgnoreCase("none")))
                            {
                                for (Player player : Bukkit.getOnlinePlayers())
                                {
                                    player.sendMessage(msgUtils.colorize(message));
                                }
                            }
                        }




                        if (getConfig().getBoolean("WantFooter") == true)
                        {
                            for (Player player : Bukkit.getOnlinePlayers())
                            {
                                player.sendMessage(msgUtils.colorize(getConfig().getString("Footer"))); } } } } }, 0L, time);
        }



        private FileConfiguration messages;


        private String message;

        private String JSONCommand;

        private String JSONLink;

        public FileConfiguration getMessagesConfig()
        {
            return messages;
        }


        public void saveMessagesConfig()
        {
            try
            {
                messages.save(messagesf);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }



//        private void createFiles()
////        {
////            configf = new File(getDataFolder(), "config.yml");
////            messagesf = new File(getDataFolder(), "messages.yml");
////
////            if (!configf.exists())
////            {
////                configf.getParentFile().mkdirs();
////                saveResource("config.yml", false);
////            }
////
////
////            if (!messagesf.exists())
////            {
////                messagesf.getParentFile().mkdirs();
////                saveResource("messages.yml", false);
////            }
////
////
////            config = new YamlConfiguration();
////            messages = new YamlConfiguration();
////
////            try
////            {
////                config.load(configf);
////                messages.load(messagesf);
////            }
////            catch (IOException e)
////            {
////                e.printStackTrace();
////            }
////            catch (InvalidConfigurationException e)
////            {
////                e.printStackTrace();
////            }
////        }
    }

