package me.fabrimat.minecraftitaliarewards.database;

import me.fabrimat.minecraftitaliarewards.manager.Manager;
import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager implements Manager {

    private final MinecraftItaliaRewards plugin;

    private Database database;

    public DatabaseManager(MinecraftItaliaRewards plugin) {
        this.plugin = plugin;
        this.reload();
    }

    @Override
    public void reload() {
        switch (plugin.getConfigManager().getDatabaseType()) {
            case "MYSQL":
                this.database = new MySQL(plugin);
                break;
            case "SQLITE":
            default:
                this.database = new SQLite(plugin);
                break;
        }
    }

    @Override
    public void disable() {

    }

    public boolean isPlayerInVotes(String UUID) {
        boolean returnValue = false;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = database.getSQLConnection();
            ps = conn.prepareStatement("SELECT COUNT(*)"
                    + "FROM players WHERE uuid = ?;");
            ps.setString(1, UUID);

            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) != 0) {
                    returnValue = true;
                }
            }
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement: ", ex);
            returnValue = true;
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
        return returnValue;
    }

    public void insertPlayer(String UUID, String name, Long timestamp) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = database.getSQLConnection();
            ps = conn.prepareStatement("INSERT INTO players(uuid,name,date) VALUES(?,?,?);");
            ps.setString(1, UUID);
            ps.setString(2, name);
            ps.setLong(3, timestamp);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
    }


    public void purgePlayers() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = database.getSQLConnection();
            ps = conn.prepareStatement("DELETE FROM players;");
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
    }

    public void deletePlayer(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = database.getSQLConnection();
            ps = conn.prepareStatement("DELETE"
                    + " FROM players"
                    + " WHERE name = ?;");
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't execute SQL statement: ", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
            }
        }
    }
}
