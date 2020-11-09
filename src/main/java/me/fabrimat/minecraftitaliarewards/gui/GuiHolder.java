package me.fabrimat.minecraftitaliarewards.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {

    private final Gui gui;

    public GuiHolder(Gui gui) {
        this.gui = gui;
    }

    @Override
    public Inventory getInventory() {
        return gui.inventory;
    }

    public Gui getGUI() {
        return gui;
    }
}
