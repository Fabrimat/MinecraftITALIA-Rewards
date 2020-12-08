package me.fabrimat.minecraftitaliarewards.vote;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.manager.SchedulerManager;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.remote.RemoteManager;
import org.bukkit.Bukkit;

import java.time.LocalDate;

public class VotesManager implements SchedulerManager {

    private final MinecraftItaliaRewards plugin;
    private ConfigManager configManager;
    private RemoteManager remoteManager;
    private int value;
    private LocalDate loadDate;

    private VoteCheckRunner voteCheckRunner;

    public VotesManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reload() {
        this.configManager = plugin.getConfigManager();
        this.remoteManager = plugin.getRemoteManager();
        if(voteCheckRunner != null) {
            this.stopRunner();
        }
        this.voteCheckRunner = new VoteCheckRunner(plugin);
        startRunner();
    }

    @Override
    public void disable() {
        this.stopRunner();
    }

    public VoteStatus getStatus() {
        if(loadDate != null && LocalDate.now().isEqual(loadDate)) {
            if(value >= 0) {
                if(value > configManager.getRewardConfig().getInt("max-value")) {
                    // TODO error message in console
                    return VoteStatus.ERROR;
                }
                return VoteStatus.ACQUIRED;
            }
            return VoteStatus.FAILURE;
        }
        return VoteStatus.WAITING;
    }

    public void loadVotes() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            remoteManager.update();
            Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.getVotesManager().update();
            });
        });
    }

    public void update() {
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
        if(this.voteCheckRunner != null) {
            this.voteCheckRunner.runTaskAsynchronously(this.plugin);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void startRunner() {
        if(this.voteCheckRunner != null) {
            this.voteCheckRunner.runTaskTimerAsynchronously(
                    this.plugin,
                    100L,
                    this.configManager.getMainConfig().getLong("ticks-checker", 12000L));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void stopRunner() {
        if(this.voteCheckRunner != null && !this.voteCheckRunner.isCancelled()) {
            this.voteCheckRunner.cancel();
        }
    }

    public boolean isRunnerActive() {
        return this.voteCheckRunner != null && !this.voteCheckRunner.isCancelled();
    }

    public int getValue() {
        return this.value;
    }
}
