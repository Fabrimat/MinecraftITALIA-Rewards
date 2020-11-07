package me.fabrimat.minecraftitaliarewards;

import org.bukkit.scheduler.BukkitRunnable;

public class VoteCheckRunner extends BukkitRunnable {

    private VotesManager votesManager;

    public VoteCheckRunner(VotesManager votesManager) {
        this.votesManager = votesManager;
    }

    @Override
    public void run() {
        if(votesManager.getStatus().equals(VoteStatus.WAITING) ||
                votesManager.getStatus().equals(VoteStatus.FAILURE)) {
            votesManager.loadVotes();
        }
    }
}
