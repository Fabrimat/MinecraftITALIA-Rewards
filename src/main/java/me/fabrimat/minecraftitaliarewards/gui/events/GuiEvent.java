package me.fabrimat.minecraftitaliarewards.gui.events;

import me.fabrimat.minecraftitaliarewards.gui.Gui;
import org.bukkit.entity.Player;

public abstract class GuiEvent {

    private final Gui gui;
    private final Player player;

    public GuiEvent(Gui gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    public Gui getGui() {
        return gui;
    }

    public Player getPlayer() {
        return this.player;
    }

}
