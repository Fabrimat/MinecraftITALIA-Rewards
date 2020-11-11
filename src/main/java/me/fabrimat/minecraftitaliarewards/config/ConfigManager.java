package me.fabrimat.minecraftitaliarewards.config;

import me.fabrimat.minecraftitaliarewards.manager.Manager;
import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;

public class ConfigManager implements Manager {

    private final MinecraftItaliaRewards plugin;
    private final MessageConfig messageConfig;
    private final RewardConfig rewardConfig;
    private final MainConfig mainConfig;

    public ConfigManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;

        this.mainConfig = new MainConfig(plugin);
        this.rewardConfig = new RewardConfig(plugin);
        this.messageConfig = new MessageConfig(plugin);
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public RewardConfig getRewardConfig() {
        return rewardConfig;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    @Override
    public void reload() {
        mainConfig.reload();
        messageConfig.reload();
        rewardConfig.reload();
    }

    @Override
    public void disable() {

    }
}
