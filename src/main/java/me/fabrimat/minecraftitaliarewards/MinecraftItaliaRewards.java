package me.fabrimat.minecraftitaliarewards;

import me.fabrimat.minecraftitaliarewards.announce.AnnounceManager;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
import me.fabrimat.minecraftitaliarewards.votes.VotesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftItaliaRewards extends JavaPlugin {

    private static MinecraftItaliaRewards instance = null;
    private VotesManager votesManager;
    private ConfigManager configManager;
    private GuiManager guiManager;
    private DatabaseManager databaseManager;
    private AnnounceManager announceManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.databaseManager = new DatabaseManager(this);

        this.guiManager = new GuiManager(this);

        this.votesManager = new VotesManager(this);
        this.announceManager = new AnnounceManager(this);

    }

    @Override
    public void onDisable() {
        this.guiManager.disable();
        this.votesManager.disable();
        this.databaseManager.disable();
        this.configManager.disable();
        this.announceManager.disable();
    }

    public static MinecraftItaliaRewards getInstance() {
        return instance;
    }

    public VotesManager getVotesManager() {
        return votesManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
