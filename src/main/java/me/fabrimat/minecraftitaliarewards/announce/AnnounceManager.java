package me.fabrimat.minecraftitaliarewards.announce;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.manager.SchedulerManager;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;

public class AnnounceManager implements SchedulerManager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;

    private AnnounceRunner announceRunner;

    public AnnounceManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.reload();
    }

    @Override
    public void reload() {
        this.stopRunner();
        this.announceRunner = new AnnounceRunner(plugin);
        this.startRunner();
    }

    @Override
    public void disable() {
    }

    @Override
    public void forceRun() {
        if(this.announceRunner != null) {
            this.announceRunner.runTaskAsynchronously(this.plugin);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void startRunner() {
        if(this.announceRunner != null) {
            this.announceRunner.runTaskTimerAsynchronously(
                    this.plugin,
                    300L,
                    configManager.getMainConfig().getLong("ticks-announcer", 24000L));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void stopRunner() {
        if(this.announceRunner != null && !this.announceRunner.isCancelled()) {
            this.announceRunner.cancel();
        }
    }

    public boolean isRunnerActive() {
        return this.announceRunner != null && !this.announceRunner.isCancelled();
    }

}
