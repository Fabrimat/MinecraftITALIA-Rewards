package me.fabrimat.minecraftitaliarewards.gui.event;

import me.fabrimat.minecraftitaliarewards.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiClickEvent extends GuiEvent {

    public final int slot;
    public final ItemStack cursor, clickedItem;
    public final ClickType clickType;
    public final InventoryClickEvent event;

    public GuiClickEvent(Gui gui, Player player, InventoryClickEvent event, int slot) {
        super(gui, player);
        this.slot = slot;
        this.cursor = event.getCursor();
        Inventory clicked = event.getClickedInventory();
        this.clickedItem = clicked == null ? null : clicked.getItem(event.getSlot());
        this.clickType = event.getClick();
        this.event = event;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getCursor() {
        return cursor;
    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }

    public ClickType getClickType() {
        return clickType;
    }

}
