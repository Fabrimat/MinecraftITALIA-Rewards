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
    private int votes;
    private LocalDate loadDate;

    private final VoteCheckRunner voteCheckRunner;

    public VotesManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.voteCheckRunner = new VoteCheckRunner(plugin);
    }

    public VoteStatus getStatus() {
        if(loadDate.isEqual(LocalDate.now())) {
            if(votes >= 0) {
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

    public void startRunner() {
        this.voteCheckRunner.runTaskTimerAsynchronously(this.plugin, 1L, 1L);
    }

    public void stopRunner() {
        this.voteCheckRunner.cancel();
    }

    public boolean isRunnerActive() {
        return !this.voteCheckRunner.isCancelled();
    }

    public int getVotes() {
        return this.votes;
    }
}
