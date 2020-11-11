package me.fabrimat.minecraftitaliarewards.gui.listener;

import me.fabrimat.minecraftitaliarewards.gui.GuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();

        if(inventory != null && inventory.getHolder() instanceof GuiHolder && inventory.equals(((GuiHolder) inventory.getHolder()).getGUI().getInventory())) {

            ((GuiHolder) inventory.getHolder()).getGUI().onClick(player, event);
        }
    }

}
