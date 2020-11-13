package me.fabrimat.minecraftitaliarewards.remote;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;
import me.fabrimat.minecraftitaliarewards.exception.PrimaryThreadException;
import me.fabrimat.minecraftitaliarewards.manager.Manager;
import org.bukkit.Bukkit;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteManager implements Manager {

    private final MinecraftItaliaRewards plugin;
    private final ConfigManager configManager;

    private boolean success;
    private int position;
    private int votesTotal;
    private int votesYesterday;

    public RemoteManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public void reload() {
        success = false;
        position = 0;
        votesTotal = 0;
        votesYesterday = 0;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::update);
    }

    @Override
    public void disable() {}

    private String httpsRequest() throws PrimaryThreadException {
        if(Bukkit.isPrimaryThread()) {
            throw new PrimaryThreadException("httpsRequest() can't be called on the primary thread.");
        }
        
        try {
            URL url = new URL("https://api.minecraft-italia.it/v5/server-info/" +
                    configManager.getMainConfig().getString("server-name"));
            Bukkit.broadcastMessage(url.toString());
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            HttpsURLConnection.setFollowRedirects(true);
            con.setConnectTimeout(15 * 1000);
            con.setRequestMethod("GET");
            con.setUseCaches(false);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder output = new StringBuilder();
            String out;
            while ((out = br.readLine()) != null) {
                output.append(out);
            };
            br.close();
            return output.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ignored) {}
        return null;
    }

    public void update() {
        JsonObject obj = new JsonObject();
        boolean success;
        try {
            String response = httpsRequest();
            if(response != null) {
                obj = (JsonObject) new JsonParser().parse(response);
                success = true;
            } else {
                success = false;
            }
        } catch (JsonSyntaxException | PrimaryThreadException e) {
            e.printStackTrace();
            success = false;
        }
        boolean finalSuccess = success;
        JsonObject finalObj = obj;
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.broadcastMessage(""+ finalSuccess);
            this.success = finalSuccess;
            if(finalSuccess) {
                position = finalObj.getAsJsonPrimitive("position").getAsInt();
                votesTotal = finalObj.getAsJsonPrimitive("votesTotal").getAsInt();
                votesYesterday = finalObj.getAsJsonPrimitive("votesYesterday").getAsInt();
            }
        });
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPosition() {
        return position;
    }

    public int getVotesTotal() {
        return votesTotal;
    }

    public int getVotesYesterday() {
        return votesYesterday;
    }
}
