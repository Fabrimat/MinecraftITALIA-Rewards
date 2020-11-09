package me.fabrimat.minecraftitaliarewards.votes;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.config.Reward;
import me.fabrimat.minecraftitaliarewards.gui.Gui;
import me.fabrimat.minecraftitaliarewards.remote.RemoteQuery;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VotesManager {

    private final MinecraftItaliaRewards plugin;
    private int votes;
    private LocalDate loadDate;

    private final Gui gui;
    private final VoteCheckRunner voteCheckRunner;
    private final ConfigManager configManager;

    public VotesManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.voteCheckRunner = new VoteCheckRunner(this);
        this.configManager = plugin.getConfigManager();

        this.gui = new Gui(this.configManager.getGuiSize(), this.configManager.getGuiTitle());
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

    private void loadGui() {
        for(Reward reward : configManager.getRewards()) {
            ItemStack guiItem = new ItemStack(reward.getIcon());
            ItemMeta meta = guiItem.getItemMeta();
            meta.setDisplayName(Reward.format(reward, reward.getGuiName()));
            List<String> lore = new ArrayList<>();
            reward.getLore().forEach((value) -> lore.add(Reward.format(reward, value)));
            meta.setLore(lore);
            guiItem.setItemMeta(meta);

            this.gui.addButton(reward.getPosition(), guiItem, (event) -> {
                for(String command: reward.getCommands()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            Reward.format(reward, command
                                    .replaceAll("\\{playerName}", event.player.getName())
                                    .replaceAll("\\{amount}", String.valueOf(reward.getAmount()))));
                }
            });
        }
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
