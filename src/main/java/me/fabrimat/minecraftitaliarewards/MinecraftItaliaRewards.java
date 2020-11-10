package me.fabrimat.minecraftitaliarewards;

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

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.databaseManager = new DatabaseManager(this);

        this.guiManager = new GuiManager(this);

        this.votesManager = new VotesManager(this);
        this.votesManager.startRunner();

    }

    @Override
    public void onDisable() {
        this.guiManager.getGui().close();
        this.votesManager.stopRunner();
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
