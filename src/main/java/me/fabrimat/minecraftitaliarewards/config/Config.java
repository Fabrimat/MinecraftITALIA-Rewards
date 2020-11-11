package me.fabrimat.minecraftitaliarewards.config;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    protected YamlConfiguration config;
    protected final MinecraftItaliaRewards plugin;

    public Config(MinecraftItaliaRewards plugin, String fileName) {
        this.plugin = plugin;
        createCustomConfig(fileName);
    }

    protected void createCustomConfig(String fileName) {
        File customConfigFile = new File(plugin.getDataFolder(), fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        config = new YamlConfiguration();
        try {
            config.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    abstract public void reload();

    public String getString(String path) {
        String value = config.getString(path);
        return value != null ? value : path;
    }

    public String getString(String path, String def) {
        String value = config.getString(path);
        return value != null ? value : def;
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public long getLong(String path) {
        return config.getLong(path);
    }

    public long getLong(String path, long def) {
        return config.getLong(path, def);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public double getDouble(String path, long def) {
        return config.getDouble(path, def);
    }

}
