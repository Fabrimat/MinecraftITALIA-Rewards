package me.fabrimat.minecraftitaliarewards.config;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final YamlConfiguration config;
    private final MinecraftItaliaRewards plugin;

    private String serverName;
    private Integer minVotes;
    private Integer guiSize;
    private Integer average;
    private Integer maxVotesLimit;
    private List<Reward> rewards;

    private String prefix;
    private String claimYourRewardMessage;
    private String firstOfTheMonth;
    private String thanksForClaimingMessage;
    private String alreadyClaimedMessage;
    private String notEnoughVotesMessage;
    private String waitingForServerMessage;
    private String noPermissionMessage;
    private String limitExceededMessage;
    private String voteErrorMessage;

    private Long ticksAnnouncer;
    private Long ticksChecker;

    public ConfigManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
        this.config = YamlConfiguration.loadConfiguration( new File(plugin.getDataFolder(), "config.yml"));
        this.loadConfig();
    }

    private void loadConfig() {
        this.serverName = config.getString("server-name", "");
        this.minVotes = config.getInt("min-votes");
        this.guiSize = config.getInt("gui-size");
        if (guiSize <= 0 || guiSize % 9 != 0) {
            guiSize = 27;
        }
        this.maxVotesLimit = config.getInt("max-votes-limit");
        this.average = config.getInt("average-votes");
        if(this.average < 1) {
            this.average = 1;
        }

        this.prefix = config.getString("messages.prefix");
        this.claimYourRewardMessage = config.getString("messages.claim-your-reward");
        this.firstOfTheMonth = config.getString("messages.first-of-the-month");
        this.thanksForClaimingMessage = config.getString("messages.thanks-for-claiming");
        this.alreadyClaimedMessage = config.getString("messages.already-claimed");
        this.notEnoughVotesMessage = config.getString("messages.not-enough-votes");
        this.waitingForServerMessage = config.getString("messages.waiting-for-server");
        this.noPermissionMessage = config.getString("messages.no-permission");
        this.limitExceededMessage = config.getString("messages.limit-exceeded");
        this.voteErrorMessage = config.getString("messages.vote-error");

        this.ticksAnnouncer = config.getLong("ticks-announcer");
        this.ticksChecker = config.getLong("ticks-checker");
    }

    public void loadRewards() {
        this.rewards = new ArrayList<>();
        ConfigurationSection rewardSection = config.getConfigurationSection("rewards");
        int i = 1;
        ConfigurationSection tempConfig;
        while(rewardSection != null && (tempConfig = rewardSection.getConfigurationSection(Integer.toString(i))) != null) {
            Material tempMaterial = Material.matchMaterial(tempConfig.getString("icon-ID"));
            if(tempMaterial == null) {
                System.out.println("Unknown material: " + tempConfig.getString("icon-ID"));
                tempMaterial = Material.DIRT;
            }
            String tempName = tempConfig.getString("gui-name");

            int tempAmount = tempConfig.getInt("amount");
            int tempPosition = tempConfig.getInt("position");

            boolean tempRepeat = tempConfig.getBoolean("repeat-command");
            boolean tempEnchant = tempConfig.getBoolean("enchant");

            double incLin = tempConfig.getDouble("linear-increment");
            if(incLin < 0) {
                incLin = 0.0D;
            }
            double incEsp = tempConfig.getDouble("esponential-increment");
            if(incEsp < 0) {
                incEsp = 0.0D;
            }

            int tempMaxAmount = tempConfig.getInt("max-amount");

            List<String> tempLore = tempConfig.getStringList("lore");
            List<String> tempCommands = tempConfig.getStringList("commands");

            rewards.add(new Reward(tempMaterial, tempName, tempAmount, incLin, incEsp, average, tempMaxAmount, plugin.getVotesManager().getVotes(), tempPosition, tempRepeat, tempEnchant, tempLore, tempCommands));
            i++;
        }
    }

    public List<Reward> getRewards(){
        return rewards;
    }

    public String getServerName() {
        return serverName;
    }

    public Integer getMinVotes() {
        if(this.minVotes == 0)
            return 30;
        return minVotes;
    }

    public Integer getAverageVotes() {
        if(this.average == 0)
            return 25;
        return average;
    }

    public Integer getGuiSize() {
        if(this.guiSize == 0)
            return 9;
        return guiSize;
    }

    public Integer getMaxVotesLimit() {
        if(this.maxVotesLimit == 0)
            return 50;
        return maxVotesLimit;
    }

    public String getPrefix() {
        if(this.prefix == null)
            return "&7[&aLaborRewards&7] ";
        return this.prefix;
    }

    public ConfigString getClaimYourRewardMessage() {
        return new ConfigString(this.claimYourRewardMessage);
    }

    public ConfigString getFirstOfTheMonthMessage() {
        return new ConfigString(this.firstOfTheMonth);
    }

    public ConfigString getThanksForClaimingMessage() {
        return new ConfigString(this.thanksForClaimingMessage);
    }

    public ConfigString getAlreadyClaimedMessage() {
        return new ConfigString(this.alreadyClaimedMessage);
    }

    public ConfigString getNotEnoughVotesMessage() {
        return new ConfigString(this.notEnoughVotesMessage);
    }

    public ConfigString getWaitingForServer() {
        return new ConfigString(this.waitingForServerMessage);
    }

    public ConfigString getNoPermissionMessage() {
        return new ConfigString(this.noPermissionMessage);
    }

    public ConfigString getLimitExceededMessage() {
        return new ConfigString(this.limitExceededMessage);
    }

    public ConfigString getVoteErrorMessage() {
        return new ConfigString(this.voteErrorMessage);
    }

    public Long getTicksChecker() {
        if(this.ticksChecker == 0)
            return 6000L;
        return this.ticksChecker;
    }

    public Long getTicksAnnouncer() {
        if(this.ticksAnnouncer == 0)
            return 24000L;
        return this.ticksAnnouncer;
    }


}
