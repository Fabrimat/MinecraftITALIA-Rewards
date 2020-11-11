package me.fabrimat.minecraftitaliarewards.vote;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.manager.SchedulerManager;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.remote.RemoteManager;

import java.time.LocalDate;

public class VotesManager implements SchedulerManager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;
    private final RemoteManager remoteManager;
    private int value;
    private LocalDate loadDate;

    private final VoteCheckRunner voteCheckRunner;

    public VotesManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.remoteManager = plugin.getRemoteManager();
        this.voteCheckRunner = new VoteCheckRunner(plugin);
        startRunner();
    }

    @Override
    public void reload() {}

    @Override
    public void disable() {
        this.stopRunner();
    }

    public VoteStatus getStatus() {
        if(LocalDate.now().isEqual(loadDate)) {
            if(value >= 0) {
                if(value > configManager.getRewardConfig().getInt("max-value")) {
                    // TODO error message
                    return VoteStatus.ERROR;
                }
                return VoteStatus.ACQUIRED;
            }
            return VoteStatus.FAILURE;
        }
        return VoteStatus.WAITING;
    }

    public void loadVotes() {
        if(remoteManager.isSuccess()) {
            value = remoteManager.getVotesYesterday();
            loadDate = LocalDate.now();
        }
    }

    public boolean isRewardDay() {
        return getValue() >= configManager.getRewardConfig().getInt("min-value");
    }

    @Override
    public void forceRun() {
        this.voteCheckRunner.runTaskAsynchronously(this.plugin);
    }

    @Override
    public void startRunner() {
        this.voteCheckRunner.runTaskTimerAsynchronously(this.plugin, 800L, 10000L);
    }

    @Override
    public void stopRunner() {
        if(!this.voteCheckRunner.isCancelled()) {
            this.voteCheckRunner.cancel();
        }
    }

    public boolean isRunnerActive() {
        return !this.voteCheckRunner.isCancelled();
    }

    public int getValue() {
        return this.value;
    }
}
