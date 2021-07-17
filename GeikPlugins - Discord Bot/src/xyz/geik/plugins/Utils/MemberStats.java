package xyz.geik.plugins.Utils;

public class MemberStats {
	
	private String discord_id;
	
	private String plugin;
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param discord_id
	 * @param plugin
	 */
	public MemberStats(String discord_id, String plugin)
	{
		this.discord_id = discord_id;
		this.plugin = plugin;
	}
	
	public String getDiscordID()
	{
		return this.discord_id;
	}
	public String getPluginName()
	{
		return this.plugin;
	}

}
