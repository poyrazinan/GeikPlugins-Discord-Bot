package xyz.geik.plugins.database;

import xyz.geik.plugins.utils.DiscordStats;
import xyz.geik.plugins.utils.MemberStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseQueries {

    public static HashMap<String, DiscordStats> getAllDiscordStats() {
        String SQL_QUERY = "SELECT roleID, dataName, name FROM Products;";
        HashMap<String, DiscordStats> valueTotal = new HashMap<String, DiscordStats>();
        try (Connection con = ConnectionPool.getConnection()) {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String dataName = resultSet.getString("dataName");
                DiscordStats discordObj = new DiscordStats(resultSet.getString("roleID"), dataName, resultSet.getString("name"));
                valueTotal.put(dataName, discordObj);
            }
            resultSet.close();
            pst.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
        return valueTotal;
    }

    public static List<MemberStats> getAllPlayerLicenses(String discord_id) {
        String SQL_QUERY = "SELECT LC.productID, P.name FROM Licenses AS LC INNER JOIN Products AS P ON P.id = LC.productID WHERE discord_id = ?";
        List<MemberStats> valueTotal = new ArrayList<MemberStats>();
        try (Connection con = ConnectionPool.getConnection())
        {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
            pst.setString(1, discord_id);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                valueTotal.add(new MemberStats(discord_id, resultSet.getString("productID"), resultSet.getString("name")));
            }
            resultSet.close();
            pst.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
        return valueTotal;
    }

}