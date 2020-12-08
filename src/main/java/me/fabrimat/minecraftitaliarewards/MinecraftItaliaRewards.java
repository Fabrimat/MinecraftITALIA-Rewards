package me.fabrimat.minecraftitaliarewards;

import me.fabrimat.minecraftitaliarewards.announce.AnnounceManager;
import me.fabrimat.minecraftitaliarewards.commands.McItaCommand;
import me.fabrimat.minecraftitaliarewards.commands.RewardCommand;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.database.DatabaseManager;
import me.fabrimat.minecraftitaliarewards.gui.GuiManager;
import me.fabrimat.minecraftitaliarewards.gui.listener.InventoryListener;
import me.fabrimat.minecraftitaliarewards.message.MessageManager;
import me.fabrimat.minecraftitaliarewards.remote.RemoteManager;
import me.fabrimat.minecraftitaliarewards.sound.SoundManager;
import me.fabrimat.minecraftitaliarewards.vote.VotesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftItaliaRewards extends JavaPlugin {

    private static MinecraftItaliaRewards instance = null;
    private VotesManager votesManager;
    private ConfigManager configManager;
    private GuiManager guiManager;
    private DatabaseManager databaseManager;
    private AnnounceManager announceManager;
    private RemoteManager remoteManager;
    private SoundManager soundManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.databaseManager = new DatabaseManager(this);

        this.guiManager = new GuiManager(this);

        this.votesManager = new VotesManager(this);
        this.announceManager = new AnnounceManager(this);
        this.messageManager = new MessageManager(this);
        this.soundManager = new SoundManager(this);
        this.remoteManager = new RemoteManager(this);

        this.votesManager.reload();
        this.guiManager.reload();

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getCommand("premio").setExecutor(new RewardCommand(this));
        getCommand("mcita").setExecutor(new McItaCommand(this));

    }

    @Override
    public void onDisable() {
        this.guiManager.disable();
        this.votesManager.disable();
        this.databaseManager.disable();
        this.configManager.disable();
        this.announceManager.disable();
    }

    public void reload() {
        this.configManager.reload();
        this.databaseManager.reload();
        this.announceManager.reload();
        this.messageManager.reload();
        this.soundManager.reload();
        this.remoteManager.reload();
        this.votesManager.reload();
        this.guiManager.reload();
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

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public RemoteManager getRemoteManager() {
        return remoteManager;
    }
}
