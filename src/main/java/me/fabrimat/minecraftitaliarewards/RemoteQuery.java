package me.fabrimat.minecraftitaliarewards;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteQuery {

    private static String httpsRequest() throws PrimaryThreadException {
        if(Bukkit.isPrimaryThread()) {
            throw new PrimaryThreadException("httpsRequest() can't be called on the primary thread.");
        }
        
        try {
            URL url = new URL("https://www.minecraft-italia.it/v5/server-info/" +
                    MinecraftItaliaRewards.getInstance().getConfigManager().getServerName());
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
        return "{}";
    }

    public static Integer getRemoteVotes() {
        JsonObject obj = new JsonObject();
        try {
            obj = (JsonObject) new JsonParser().parse(httpsRequest());
        } catch (JsonSyntaxException | PrimaryThreadException e) {
            e.printStackTrace();
        }
        try {
            return obj.getAsJsonPrimitive("votesYesterday").getAsInt();
        } catch (Exception  e) {
            return -1;
        }
    }


}
