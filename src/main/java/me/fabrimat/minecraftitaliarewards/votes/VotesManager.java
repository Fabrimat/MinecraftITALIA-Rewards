package me.fabrimat.minecraftitaliarewards.votes;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.interfaces.SchedulerManager;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.remote.RemoteQuery;

import java.time.LocalDate;

public class VotesManager implements SchedulerManager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;
    private int votes;
    private LocalDate loadDate;

    private final VoteCheckRunner voteCheckRunner;

    public VotesManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
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
        if(loadDate.isEqual(LocalDate.now())) {
            if(votes >= 0) {
                if(votes > configManager.getMaxVotesLimit()) {
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
        votes = RemoteQuery.getRemoteVotes();
        loadDate = LocalDate.now();
    }

    public boolean isRewardDay() {
        return getVotes() >= configManager.getMinVotes();
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

    public int getVotes() {
        return this.votes;
    }
}
