package me.fabrimat.minecraftitaliarewards.votes;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.config.Reward;
import me.fabrimat.minecraftitaliarewards.gui.Gui;
import me.fabrimat.minecraftitaliarewards.remote.RemoteQuery;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VotesManager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;
    private int votes;
    private LocalDate loadDate;

    private final VoteCheckRunner voteCheckRunner;

    public VotesManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.voteCheckRunner = new VoteCheckRunner(plugin);
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

    public void forceRun() {
        this.voteCheckRunner.runTaskAsynchronously(this.plugin);
    }

    public void startRunner() {
        this.voteCheckRunner.runTaskTimerAsynchronously(this.plugin, 1L, 1L);
    }

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
