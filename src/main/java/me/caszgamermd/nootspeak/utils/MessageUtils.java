package me.caszgamermd.nootspeak.utils;

import org.bukkit.ChatColor;

public class MessageUtils {

    public String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
