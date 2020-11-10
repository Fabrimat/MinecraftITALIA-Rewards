package me.fabrimat.minecraftitaliarewards.gui;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.fabrimat.minecraftitaliarewards.gui.events.GuiClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Gui {

    protected Inventory inventory;

    protected String title;
    protected int rows;

    protected final Map<Integer, Clickable> buttons = new HashMap<>();;
    protected final Map<Integer, ItemStack> items = new HashMap<>();;

    public Gui() {
        this.rows = 3;
        this.title = "";
    }

    public Gui(int rows, String title) {
        this.rows = rows;
        this.title = title;
    }

    @NotNull
    public List<Player> getPlayers() {
        return inventory == null ? Collections.emptyList()
                : inventory.getViewers().stream()
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .collect(Collectors.toList());
    }

    public boolean isOpen() {
        return !(inventory != null && inventory.getViewers().isEmpty());
    }

    public void close() {
        if(inventory != null) {
            inventory.getViewers().stream()
                    .filter(e -> e instanceof Player)
                    .map(e -> (Player) e)
                    .collect(Collectors.toList())
                    .forEach(Player::closeInventory);
        }
    }

    public void addButton(int slot, @Nullable ItemStack item, @Nullable Clickable clickable) {
        this.addItem(slot, item);
        this.buttons.put(slot, clickable);
    }

    private void addItem(int slot, ItemStack item) {
        this.items.put(slot, item);
    }

    public void createInventory() {
        this.inventory = Bukkit.createInventory(new GuiHolder(this), rows * 9, title);

        this.loadInventory();
    }

    public void loadInventory() {
        this.inventory.clear();

        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            this.inventory.setItem(entry.getKey(), entry.getValue());
        }
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void onClick(@NotNull Player player, @NotNull InventoryClickEvent event) {
        final int cell = event.getSlot();
        this.buttons.get(cell).onClick(new GuiClickEvent(this, player, event, cell));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<Integer, Clickable> getButtons() {
        return buttons;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
