package me.fabrimat.minecraftitaliarewards.gui.events;

import me.fabrimat.minecraftitaliarewards.gui.Gui;
import org.bukkit.entity.Player;

public abstract class GuiEvent {

    public final Gui gui;
    public final Player player;

    public GuiEvent(Gui gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

}
