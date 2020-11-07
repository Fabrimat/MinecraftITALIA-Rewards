package me.fabrimat.minecraftitaliarewards;

import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.votes.VotesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftItaliaRewards extends JavaPlugin {

    private static MinecraftItaliaRewards instance = null;
    private VotesManager votesManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);

        this.votesManager = new VotesManager(this);
        this.votesManager.startRunner();

    }

    @Override
    public void onDisable() {

    }

    public static MinecraftItaliaRewards getInstance() {
        return instance;
    }

    public VotesManager getVotesManager() {
        return votesManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
