package me.fabrimat.minecraftitaliarewards.announce;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.interfaces.SchedulerManager;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;

public class AnnounceManager implements SchedulerManager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;

    private final AnnounceRunner announceRunner;

    public AnnounceManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.announceRunner = new AnnounceRunner(plugin);
        this.startRunner();
    }

    @Override
    public void reload() {}

    @Override
    public void disable() {
        this.stopRunner();
    }

    @Override
    public void forceRun() {
        this.announceRunner.runTaskAsynchronously(this.plugin);
    }

    @Override
    public void startRunner() {
        this.announceRunner.runTaskTimerAsynchronously(this.plugin, 800L, 10000L);
    }

    @Override
    public void stopRunner() {
        if(!this.announceRunner.isCancelled()) {
            this.announceRunner.cancel();
        }
    }

    public boolean isRunnerActive() {
        return !this.announceRunner.isCancelled();
    }

}
