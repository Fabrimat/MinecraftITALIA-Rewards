package me.fabrimat.minecraftitaliarewards.config;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.utility.TextUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RewardConfig extends Config {

    public RewardConfig(MinecraftItaliaRewards plugin) {
        super(plugin, "rewards.yml");
    }

    public List<Reward> getRewards() {
        List<Reward> rewards = new ArrayList<>();
        ConfigurationSection rewardSection = config.getConfigurationSection("rewards");
        int i = 1;
        ConfigurationSection tempConfig;
        while(rewardSection != null && (tempConfig = rewardSection.getConfigurationSection(Integer.toString(i))) != null) {
            Material tempMaterial = Material.matchMaterial(tempConfig.getString("icon-ID"));
            if(tempMaterial == null) {
                System.out.println("Unknown material: " + tempConfig.getString("icon-ID"));
                tempMaterial = Material.DIRT;
            }
            String tempName = TextUtils.parseTextColor(tempConfig.getString("gui-name"));

            int tempAmount = tempConfig.getInt("amount");
            int tempPosition = tempConfig.getInt("position");

            boolean tempRepeat = tempConfig.getBoolean("repeat-command");
            boolean tempEnchant = tempConfig.getBoolean("enchant");

            double incLin = tempConfig.getDouble("linear-increment");
            if(incLin < 0) {
                incLin = 0.0D;
            }
            double incEsp = tempConfig.getDouble("exponential-increment");
            if(incEsp < 0) {
                incEsp = 0.0D;
            }

            int tempMaxAmount = tempConfig.getInt("max-amount");

            List<String> tempLore = tempConfig.getStringList("lore");
            for (ListIterator<String> j = tempLore.listIterator(); j.hasNext(); ) {
                j.set(TextUtils.parseTextColor(j.next()));
            }

            List<String> tempCommands = tempConfig.getStringList("commands");

            rewards.add(new Reward(tempMaterial, tempName, tempAmount, incLin, incEsp, getDouble("average"),
                    tempMaxAmount, plugin.getVotesManager().getValue(), tempPosition, tempRepeat, tempEnchant, tempLore, tempCommands));
            i++;
        }
        return rewards;
    }
}
