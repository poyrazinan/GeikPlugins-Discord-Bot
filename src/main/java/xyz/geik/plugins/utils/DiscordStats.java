package xyz.geik.plugins.utils;

public class DiscordStats {

    private String discordRole;
    private String dataName;
    private String productName;

    public DiscordStats(String discordRole, String data_name, String productName)
    {
        this.discordRole = discordRole;
        this.dataName = data_name;
        this.productName = productName;
    }

    public String getDiscordRole()
    {
        return this.discordRole;
    }

    public String getDataName()
    {
        return this.dataName;
    }

    public String getProductName()
    {
        return this.productName;
    }

}