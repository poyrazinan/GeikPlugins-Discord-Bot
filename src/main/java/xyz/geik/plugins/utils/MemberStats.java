package xyz.geik.plugins.utils;

public class MemberStats {

    private String discordId;

    private String pluginId;

    private String pluginName;

    /**
     * @author Geik
     * @since 1.0.0
     * @param discordId
     * @param pluginId
     */
    public MemberStats(String discordId, String pluginId, String pluginName)
    {
        this.discordId = discordId;
        this.pluginId = pluginId;
        this.pluginName = pluginName;
    }

    public String getDiscordID()
    {
        return this.discordId;
    }
    public String getPluginId()
    {
        return this.pluginId;
    }
    public String getPluginName() { return this.pluginName; }

}
