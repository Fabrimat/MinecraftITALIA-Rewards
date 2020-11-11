package me.fabrimat.minecraftitaliarewards.votes;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class VoteCheckRunner extends BukkitRunnable {

    private final MinecraftItaliaRewards plugin;
    private final VotesManager votesManager;
    private final GuiManager guiManager;
    private final DatabaseManager databaseManager;

    public VoteCheckRunner(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.votesManager = plugin.getVotesManager();
        this.guiManager = plugin.getGuiManager();
        this.databaseManager = plugin.getDatabaseManager();
    }

    @Override
    public void run() {
        switch (votesManager.getStatus()) {
            case WAITING:
                databaseManager.purgePlayers();
            case FAILURE:
            case ERROR:
                votesManager.loadVotes();
                Bukkit.getScheduler().runTask(plugin, guiManager::reload);
            default:
                break;

        }
    }
}
