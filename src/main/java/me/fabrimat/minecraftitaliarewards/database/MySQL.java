package me.fabrimat.minecraftitaliarewards.database;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;
import me.fabrimat.minecraftitaliarewards.config.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class MySQL extends Database {

    public MySQL(MinecraftItaliaRewards plugin) {
        super(plugin);
    }

    @Override
    public Connection getSQLConnection() {
        ConfigManager configManager = plugin.getConfigManager();

        try {
            if(connection!=null && !connection.isClosed()){
                return connection;
            }
            Class.forName("org.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:" +
                            configManager.getMySQLAddress() +
                            "/" + configManager.getMySQLDatabase(),
                    configManager.getMySQLUser(),
                    configManager.getMySQLPassword());
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"MySQL exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the MySQL JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    @Override
    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            String players = "CREATE TABLE IF NOT EXISTS players (\n" // TODO
                    + "    id integer PRIMARY KEY,\n"
                    + "    uuid text NOT NULL,\n"
                    + "    name text NOT NULL,\n"
                    + "    date timestamp NOT NULL\n"
                    + ");";
            s.executeUpdate(players);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

}
