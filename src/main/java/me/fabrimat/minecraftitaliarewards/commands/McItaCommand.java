package me.fabrimat.minecraftitaliarewards.commands;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
import me.fabrimat.minecraftitaliarewards.vote.VotesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class McItaCommand implements CommandExecutor {

    private final VotesManager votesManager;
    private final ConfigManager configManager;

    public McItaCommand(MinecraftItaliaRewards plugin) {
        this.votesManager = plugin.getVotesManager();
        this.configManager = plugin.getConfigManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) {
            return false;
        }
        if(!sender.hasPermission("mcita.admin")) {
            sender.sendMessage(configManager.getMessageConfig().getString("no-permission"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reset":
                break;
            case "resetall":
                break;
            case "forcecheck":
                votesManager.forceRun();
                break;
        }

        return true;
    }
}
