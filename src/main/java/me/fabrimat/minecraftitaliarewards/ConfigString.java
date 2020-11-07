package me.fabrimat.minecraftitaliarewards;

import org.bukkit.ChatColor;

public class ConfigString {

    private final String value;

    public ConfigString(String s) {
        this.value = s;
    }

    public String getRawValue() {
        return value;
    }

    /*public String getFormattedValue() {
        return ChatColor.translateAlternateColorCodes('&', Config.getInstance().getPrefix() + " " + value);
    }*/

    public String toString() {
        return value;
    }

}

