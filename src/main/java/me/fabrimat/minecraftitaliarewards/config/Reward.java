package me.fabrimat.minecraftitaliarewards.config;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import org.bukkit.Material;

import java.util.List;
import java.util.logging.Level;

public class Reward {
    private Material iconID;
    private String friendlyName;
    private int amount;
    private final int position;
    private boolean repeatCommand;
    private boolean enchant;
    private List<String> lore;
    private List<String> commands;

    public Reward(Material icon, String name, Integer baseAmount, Double incLin, Double incEsp, Integer average, Integer maxAmount, Integer votes, Integer position, Boolean amountRepeat, Boolean enchant, List<String> lore, List<String> commands) {
        this.iconID = icon;
        this.friendlyName = name;
        this.amount = baseAmount;
        Double multiplier = (incLin * Math.pow( (Double.valueOf(votes) / Double.valueOf(average)) , (Math.log(incEsp) / Math.log(2)) ) - incLin + 1.0D);
        if(multiplier > 1) {
            this.amount = (int) (Double.valueOf(baseAmount)*multiplier);
        }
        if(this.amount > maxAmount) {
            this.amount = maxAmount;
        }
        if((multiplier <= 1 && votes > 30) || this.amount < baseAmount) {
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, "Errore durante il calcolo della quantità dei voti!");
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Reward: " + name);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - BaseAmount: " + baseAmount.toString());
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Amount: " + this.amount);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Multiplier: " + multiplier.toString());
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Votes: " + votes.toString());
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - IncLin: " + incLin.toString());
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - IncEsp: " + incEsp.toString());
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Average: " + average.toString());

            if(this.amount < baseAmount)
                this.amount = baseAmount;
        }

        this.position = position;
        this.repeatCommand = amountRepeat;
        this.enchant = enchant;
        this.lore = lore;
        this.commands = commands;
    }

    public Material getIcon() {
        return this.iconID;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public List<String> getCommands(){
        return this.commands;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Boolean getRepeat() {
        return this.repeatCommand;
    }

    public Boolean getEnchant() {
        return this.enchant;
    }

    public void setIcon(Material icon) {
        this.iconID = icon;
    }

    public void setFriendlyName(String name) {
        this.friendlyName = name;
    }

    public void setAmount(Integer amount) {
        if(amount > 0)
            this.amount = amount;
        else
            this.amount = 1;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setRepeat(Boolean ar) {
        this.repeatCommand = ar;
    }

    public void setEncahnt(Boolean enc) {
        this.enchant = enc;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}

