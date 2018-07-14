package me.caszgamermd.nootspeak.listeners;

import me.caszgamermd.nootspeak.AutoNoot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{

        AutoNoot pl;

        public ChatListener(AutoNoot plugin)
        {
            pl = plugin;
        }


        @EventHandler
        public void onPlayerChat(AsyncPlayerChatEvent event)
        {
            Player player = event.getPlayer();

            pl.chatActivity += 1;
        }
    }

