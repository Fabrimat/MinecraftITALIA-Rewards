package me.fabrimat.minecraftitaliarewards.announce;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.vote.VotesManager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceRunner extends BukkitRunnable {

    private final MinecraftItaliaRewards plugin;
    private final VotesManager votesManager;
    private final DatabaseManager databaseManager;

    public AnnounceRunner(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.votesManager = plugin.getVotesManager();
        this.databaseManager = plugin.getDatabaseManager();
    }

    @Override
    public void run() {
        if(votesManager.isRewardDay()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("") && !databaseManager.isPlayerInVotes(player.getUniqueId().toString())) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        player.sendMessage(""); // TODO
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 3.0F);
                    });
                }
            }
        }
    }
}