package me.fabrimat.minecraftitaliarewards.utility;

import org.bukkit.ChatColor;

public class TextUtils {

    public static String parseTextColor(String text) {
        // TODO 1.16 RGB
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
