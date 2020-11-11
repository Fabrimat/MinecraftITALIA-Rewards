package me.fabrimat.minecraftitaliarewards.config;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import org.bukkit.Material;

import java.util.List;
import java.util.logging.Level;

public class Reward {
    private Material iconID;
    private String guiName;
    private int amount;
    private final int position;
    private boolean repeatCommand;
    private boolean enchant;
    private List<String> lore;
    private List<String> commands;

    public Reward(Material icon,
                  String name,
                  int baseAmount,
                  double incLin,
                  double incExp,
                  double average,
                  int maxAmount,
                  int votes,
                  int position,
                  boolean amountRepeat,
                  boolean enchant,
                  List<String> lore,
                  List<String> commands) {
        this.iconID = icon;
        this.guiName = name;
        this.amount = baseAmount;
        double multiplier = (incLin *
                Math.pow( ((double) votes / average) , (Math.log(incExp) / Math.log(2)) ) -
                incLin + 1.0D);
        if(multiplier > 1) {
            this.amount = (int) ((double) baseAmount *multiplier);
        }
        if(this.amount > maxAmount) {
            this.amount = maxAmount;
        }
        if((multiplier <= 1 && votes > 30) || this.amount < baseAmount) {
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, "Errore durante il calcolo della quantità dei voti!");
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Reward: " + name);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - BaseAmount: " + baseAmount);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Amount: " + this.amount);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Multiplier: " + multiplier);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Votes: " + votes);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - IncLin: " + incLin);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - IncExp: " + incExp);
            MinecraftItaliaRewards.getInstance().getLogger().log(Level.INFO, " - Average: " + average);

            this.amount = Math.max(this.amount, baseAmount);
        }

        this.position = position;
        this.repeatCommand = amountRepeat;
        this.enchant = enchant;
        this.lore = lore;
        this.commands = commands;
    }

    private double getMultiplier(double incLin, double incExp, int votes, double average) {
        average = Math.max(average, Double.MIN_VALUE);
        incLin = Math.max(incLin, 0);
        incExp = Math.max(incExp, 1);
        votes = Math.max(votes, 0);

        return (incLin *
                Math.pow( ((double) votes / average) , (Math.log(incExp) / Math.log(2)) ) -
                incLin + 1.0D);
    }

    public Material getIcon() {
        return this.iconID;
    }

    public String getGuiName() {
        return this.guiName;
    }

    public int getAmount() {
        return this.amount;
    }

    public List<String> getCommands(){
        return this.commands;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public int getPosition() {
        return this.position;
    }

    public boolean getRepeat() {
        return this.repeatCommand;
    }

    public boolean getEnchant() {
        return this.enchant;
    }

    public void setIcon(Material icon) {
        this.iconID = icon;
    }

    public void setGuiName(String name) {
        this.guiName = name;
    }

    public void setAmount(int amount) {
        this.amount = Math.max(amount, 1);
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setRepeat(boolean ar) {
        this.repeatCommand = ar;
    }

    public void setEnchant(boolean enc) {
        this.enchant = enc;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public static String format(Reward reward, String from) {
        from = from.replaceAll("\\{amount}", String.valueOf(reward.getAmount()));

        return from;
    }
}

