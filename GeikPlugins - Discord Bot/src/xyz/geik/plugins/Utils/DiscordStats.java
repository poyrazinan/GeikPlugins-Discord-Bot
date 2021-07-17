package xyz.geik.plugins.Utils;

public class DiscordStats {
	
	private String discordRole;
	
	private String data_name;
	
	private String productName;
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param discordRole
	 * @param data_name
	 * @param productName
	 */
	public DiscordStats(String discordRole, String data_name, String productName)
	{
		this.discordRole = discordRole;
		this.data_name = data_name;
		this.productName = productName;
	}
	
	public String getDiscordRole()
	{
		return this.discordRole;
	}
	
	public String getDataName()
	{
		return this.data_name;
	}
	
	public String getProductName()
	{
		return this.productName;
	}

}
