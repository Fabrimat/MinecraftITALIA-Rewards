package me.fabrimat.minecraftitaliarewards.message;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.manager.Manager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageManager implements Manager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;

    public MessageManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public void sendMessage(CommandSender sender, boolean prefix, String message) {

        if (message == null || message.equals("")) return;

        // TODO Placeholders

        if(prefix) {
            message = configManager.getMessageConfig().getString("prefix") + message;
        }

        if (sender instanceof Player) {
            if (message.contains("\n") || message.contains("\\n")) {
                List<String> messages = new ArrayList<>();

                message = message.replace("\\n", "\n");

                for (String messageList : message.split("\n")) {
                    messages.add(ChatColor.translateAlternateColorCodes('&', messageList));
                }

                sender.sendMessage(messages.toArray(new String[0]));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        } else {
            if (message.contains("\n") || message.contains("\\n")) {
                List<String> messages = new ArrayList<>();

                message = message.replace("\\n", "\n");

                for (String messageList : message.split("\n")) {
                    messages.add(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', messageList)));
                }

                sender.sendMessage(messages.toArray(new String[0]));
            } else {
                sender.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message)));
            }
        }
    }

    @Override
    public void reload() {}

    @Override
    public void disable() {}
}
