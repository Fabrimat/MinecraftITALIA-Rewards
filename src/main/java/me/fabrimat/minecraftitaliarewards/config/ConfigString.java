package me.fabrimat.minecraftitaliarewards.config;

import org.bukkit.ChatColor;

public class ConfigString {

    private final ConfigManager configManager;
    private final String value;

    public ConfigString(ConfigManager configManager, String s) {
        this.configManager = configManager;
        this.value = s;
    }

    public String getRawValue() {
        return value;
    }

    public String getFormattedValue() {
        return ChatColor.translateAlternateColorCodes('&', configManager.getPrefix() + " " + value);
    }

    public String toString() {
        return value;
    }

}

