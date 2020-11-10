package me.fabrimat.minecraftitaliarewards.votes;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class VoteCheckRunner extends BukkitRunnable {

    private final MinecraftItaliaRewards plugin;
    private final VotesManager votesManager;
    private final GuiManager guiManager;

    public VoteCheckRunner(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.votesManager = plugin.getVotesManager();
        this.guiManager = plugin.getGuiManager();
    }

    @Override
    public void run() {
        if(votesManager.getStatus().equals(VoteStatus.WAITING) ||
                votesManager.getStatus().equals(VoteStatus.FAILURE)) {
            votesManager.loadVotes();
            Bukkit.getScheduler().runTask(plugin, guiManager::reload);
        }
    }
}
