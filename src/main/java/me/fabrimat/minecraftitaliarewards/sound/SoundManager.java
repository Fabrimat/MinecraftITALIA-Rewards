package me.fabrimat.minecraftitaliarewards.sound;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.manager.Manager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoundManager implements Manager {

    private final MinecraftItaliaRewards plugin;

    public SoundManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
    }

    // TODO Config option
    public void playSound(CommandSender sender, Sound sound, float volume, float pitch) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public void playSound(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    @Override
    public void reload() {}

    @Override
    public void disable() {}
}
