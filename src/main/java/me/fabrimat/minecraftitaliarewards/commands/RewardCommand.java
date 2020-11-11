package me.fabrimat.minecraftitaliarewards.commands;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
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

    public RewardCommand(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.votesManager = plugin.getVotesManager();
        this.guiManager = plugin.getGuiManager();
        this.configManager = plugin.getConfigManager();
        this.databaseManager = plugin.getDatabaseManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(configManager.getPrefix() + "Command not supported from console");
            return true;
        }

        switch (votesManager.getStatus()) {
            case ERROR:
                sender.sendMessage("a");
                break;
            case FAILURE:
                sender.sendMessage("b");
                break;
            case WAITING:
                sender.sendMessage("c");
                break;
            case ACQUIRED:
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (!databaseManager.isPlayerInVotes(((Player) sender).getUniqueId().toString())) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            if(votesManager.isRewardDay()) {
                                sender.sendMessage("d"); // OK
                                guiManager.getGui().open((Player) sender);
                            } else {
                                sender.sendMessage("e"); // Not enough votes
                            }
                        });
                    } else {
                        Bukkit.getScheduler().runTask(plugin, () -> sender.sendMessage("")); // Already claimed
                    }
                });
                break;
        }

        return true;
    }
}
