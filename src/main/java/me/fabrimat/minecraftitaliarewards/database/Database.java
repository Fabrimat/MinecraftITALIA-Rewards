package me.fabrimat.minecraftitaliarewards.database;

import me.fabrimat.minecraftitaliarewards.MinecraftItaliaRewards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database {

    MinecraftItaliaRewards plugin;
    Connection connection;
    public Database(MinecraftItaliaRewards instance){
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = connection.prepareStatement("SELECT * FROM players;");
            rs = ps.executeQuery();
            close(ps,rs);

            ps = connection.prepareStatement("SELECT * FROM votes;");
            rs = ps.executeQuery();
            close(ps,rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }

    public void close(PreparedStatement ps,ResultSet rs){
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection: ", ex);
        }
    }
}
