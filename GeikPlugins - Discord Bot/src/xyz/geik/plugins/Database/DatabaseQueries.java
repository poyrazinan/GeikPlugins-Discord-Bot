package xyz.geik.plugins.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.geik.plugins.Utils.DiscordStats;
import xyz.geik.plugins.Utils.MemberStats;

public class DatabaseQueries {
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @return
	 * @throws SQLException
	 * @apiNote Player stats catcher
	 */
	public static HashMap<String, DiscordStats> getAllDiscordStats() throws SQLException
	{
		
		String SQL_QUERY = "SELECT discord_role, data_name, name FROM Products;";
		
		HashMap<String, DiscordStats> valueTotal = new HashMap<String, DiscordStats>();
		
        try (Connection con = ConnectionPool.getConnection())
        {
        	
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            ResultSet resultSet = pst.executeQuery();
            
            while (resultSet.next())
            {
            	
            	String dataname = resultSet.getString("data_name");
            	
            	DiscordStats discordObj = new DiscordStats(resultSet.getString("discord_role"), dataname, resultSet.getString("name"));
            	
            	valueTotal.put(dataname, discordObj);
            	
            }
            
            resultSet.close();
            
            pst.close();
        }
        
        return valueTotal;
	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @apiNote Get player all licenses
	 * @param discord_id
	 * @return
	 * @throws SQLException
	 */
	public static List<MemberStats> getAllPlayerLicenses(String discord_id) throws SQLException
	{
		
		String SQL_QUERY = "SELECT plugin FROM plugin_licenses WHERE discord_id = ?";
		
        List<MemberStats> valueTotal = new ArrayList<MemberStats>();
        
        try (Connection con = ConnectionPool.getConnection())
        {
        	
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, discord_id);
            
            ResultSet resultSet = pst.executeQuery();
            
            while (resultSet.next())
            {
            	
            	valueTotal.add(new MemberStats(discord_id, resultSet.getString("plugin")));
            	
            }
            
            resultSet.close();
            
            pst.close();
        }
        
        return valueTotal;
	}

}