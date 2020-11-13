package me.fabrimat.minecraftitaliarewards.commands;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
import me.fabrimat.minecraftitaliarewards.message.MessageManager;
import me.fabrimat.minecraftitaliarewards.vote.VotesManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardCommand implements CommandExecutor {

    private final MinecraftItaliaRewards plugin;

    private final VotesManager votesManager;
    private final GuiManager guiManager;
    private final ConfigManager configManager;
    private final DatabaseManager databaseManager;
    private final MessageManager messageManager;

    public RewardCommand(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.votesManager = plugin.getVotesManager();
        this.guiManager = plugin.getGuiManager();
        this.configManager = plugin.getConfigManager();
        this.databaseManager = plugin.getDatabaseManager();
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            this.messageManager.sendMessage(sender, true, "Command not supported from console");
            return true;
        }

        switch (votesManager.getStatus()) {
            case ERROR:
                messageManager.sendMessage(sender, true, configManager.getMessageConfig().getString("vote-error"));
                break;
            case FAILURE:
                messageManager.sendMessage(sender, true, configManager.getMessageConfig().getString("limit-exceeded"));
                break;
            case WAITING:
                messageManager.sendMessage(sender, true, configManager.getMessageConfig().getString("waiting-for-server"));
                break;
            case ACQUIRED:
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (!databaseManager.isPlayerInVotes(((Player) sender).getUniqueId().toString())) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            if(votesManager.isRewardDay()) {
                                guiManager.getGui().open((Player) sender);
                            } else {
                                messageManager.sendMessage(sender, true, configManager.getMessageConfig().getString("not-enough-votes"));
                            }
                        });
                    } else {
                        Bukkit.getScheduler().runTask(plugin, () ->
                                messageManager.sendMessage(
                                        sender,
                                        true,
                                        configManager.getMessageConfig()
                                                .getString("already-claimed")));
                    }
                });
                break;
        }

        return true;
    }
}
