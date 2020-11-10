package me.fabrimat.minecraftitaliarewards.gui;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.config.Reward;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private final ConfigManager configManager;
    private Gui gui;

    public GuiManager(MinecraftItaliaRewards plugin) {
        this.configManager = plugin.getConfigManager();
        this.reload();
    }

    public void reload() {
        if(this.gui != null) {
            this.gui.close();
        }
        this.gui = new Gui();
        this.loadGui();
    }

    private void loadGui() {
        this.gui.setRows(configManager.getGuiRows());
        this.gui.setTitle(configManager.getGuiTitle());
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
    public Gui getGui() {
        return gui;
    }
}
