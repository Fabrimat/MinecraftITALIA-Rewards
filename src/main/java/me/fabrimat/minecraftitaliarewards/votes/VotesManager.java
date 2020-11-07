package me.fabrimat.minecraftitaliarewards.votes;

import me.fabrimat.minecraftitaliarewards.remote.RemoteQuery;
import org.bukkit.plugin.Plugin;

import java.time.LocalDate;

public class VotesManager {

    private final Plugin plugin;
    private int votes;
    private LocalDate loadDate;

    private final VoteCheckRunner voteCheckRunner;

    public VotesManager(Plugin plugin) {
        this.plugin = plugin;
        this.voteCheckRunner = new VoteCheckRunner(this);
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
