package me.fabrimat.minecraftitaliarewards.announce;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.message.MessageManager;
import me.fabrimat.minecraftitaliarewards.sound.SoundManager;
import me.fabrimat.minecraftitaliarewards.vote.VotesManager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceRunner extends BukkitRunnable {

    private final MinecraftItaliaRewards plugin;
    private final VotesManager votesManager;
    private final DatabaseManager databaseManager;
    private final MessageManager messageManager;
    private final SoundManager soundManager;
    private final ConfigManager configManager;

    public AnnounceRunner(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.votesManager = plugin.getVotesManager();
        this.databaseManager = plugin.getDatabaseManager();
        this.messageManager = plugin.getMessageManager();
        this.soundManager = plugin.getSoundManager();
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public void run() {
        if(votesManager.isRewardDay()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("mcita.reward") &&
                        !databaseManager.isPlayerInVotes(player.getUniqueId().toString())) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        messageManager.sendMessage(player, true,
                                configManager.getMessageConfig().getString("claim-your-reward"));
                        soundManager.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F, 3.0F);
                    });
                }
            }
        }
    }
}