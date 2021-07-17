package xyz.geik.plugins;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import xyz.geik.plugins.Database.DatabaseQueries;
import xyz.geik.plugins.Utils.DiscordStats;
import xyz.geik.plugins.Utils.MemberStats;
import net.dv8tion.jda.api.entities.*;

public class Listeners extends ListenerAdapter {

	/**
	 * Jda instance
	 * 
	 * @apiNote JDA Builder Object and instance Object
	 */
	public static JDA jda = null;

	/**
	 * init start
	 * 
	 * @since 1.0.0
	 */
	public static void onStart() {

		try
		{
			
			JDABuilder builder = JDABuilder.create(Main.BOT_TOKEN, GatewayIntent.DIRECT_MESSAGE_REACTIONS,
					GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGES,
					GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS,
					GatewayIntent.GUILD_PRESENCES);

			builder.setActivity(Activity.playing(Main.BOT_STATUS));

			builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);

			builder.setBulkDeleteSplittingEnabled(false);

			builder.addEventListeners(new Listeners());

			builder.setAutoReconnect(true);

			jda = builder.build();

			try {
				jda.awaitReady();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("JDA Bağlantısı başarıyla kuruldu!");

		}
		
		catch (LoginException e)
		{

			e.printStackTrace();

		}

	}

	/**
	 * When a user join the guild(Discord server)
	 * This event occure.
	 * 
	 * @since 1.0.0
	 */
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {

		if (e.getMember().getUser().isBot())
			return;

		User user = e.getUser();

		JDA jda = e.getJDA();

		try {

			HashMap<String, DiscordStats> discordStats = DatabaseQueries.getAllDiscordStats();

			List<MemberStats> memberStatList = DatabaseQueries.getAllPlayerLicenses(user.getId());

			List<String> plugin_list = new ArrayList<String>();
			
			PrivateChannel direct_messages = e.getMember().getUser().openPrivateChannel().complete();

			if (memberStatList != null)
			{

				direct_messages.sendMessageEmbeds(foundLicensesEmbed(memberStatList).build()).queue();

				Role customer_role = jda.getRoleById(Main.CUSTOMER_ROLE_ID);

				e.getGuild().addRoleToMember(user.getId(), customer_role).queue();

				for (MemberStats stats : memberStatList) {

					if (plugin_list.contains(stats.getPluginName())) {

						Role exclusive_buyer = jda.getRoleById(Main.EXCLUSIVE_BUYER_ROLE_ID);

						e.getGuild().addRoleToMember(user.getId(), exclusive_buyer).queue();

					} else {

						Role license_role = jda.getRoleById(discordStats.get(stats.getPluginName()).getDiscordRole());

						e.getGuild().addRoleToMember(user.getId(), license_role).queue();

						plugin_list.add(discordStats.get(stats.getPluginName()).getDiscordRole());

					}

				}

			}
			
			else
				direct_messages.sendMessageEmbeds(
						dontHaveLicenseEmbed()
						.build()
						).queue();

		}

		catch (SQLException | IllegalArgumentException e1) {  e1.printStackTrace();  }

	}
	
	/**
	 * Serialize license string
	 * 
	 * @param playerStats
	 * @return
	 */
	private String licenseStringBuilder(final List<MemberStats> memberStatList)
	{
		
		String licenseString = null;

		for (MemberStats stats : memberStatList) {

			if (licenseString == null)
				licenseString = stats.getPluginName();

			else
				licenseString = licenseString + ", " + stats.getPluginName();

		}
		
		return licenseString;
		
	}
	
	/**
	 * When a license could exist for user,
	 * executing that embed builder.
	 * 
	 * @param memberStatList
	 * @return
	 */
	private EmbedBuilder foundLicensesEmbed(final List<MemberStats> memberStatList)
	{
		
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Merhaba!", Main.TITLE_LINK);

		embed.setColor(Color.red);

		embed.setDescription("");

		embed.addField("Sana ait lisans bulundu:", licenseStringBuilder(memberStatList), false);

		embed.addField("Hatırlatma", "İndirmeler sitemizden yapılmaktadır. " + Main.TITLE_LINK, false);

		embed.setFooter(Main.FOOTER_NAME, Main.PHOTO_LINK);

		embed.setThumbnail(Main.PHOTO_LINK);
		
		return embed;
		
	}
	
	/**
	 * When a license couldn't exist for user,
	 * executing that embed builder.
	 * 
	 * @return
	 */
	private EmbedBuilder dontHaveLicenseEmbed()
	{
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setTitle("Merhaba!", Main.TITLE_LINK);

		embed.setColor(Color.red);

		embed.setDescription("");

		embed.addField("Aramıza Hoşgeldin", "Burada aradığını bulabileceğini umuyorum :)", false);

		embed.addField("Hatırlatma", "İndirmeler sitemizden yapılmaktadır. " + Main.TITLE_LINK, false);

		embed.setFooter(Main.FOOTER_NAME, Main.PHOTO_LINK);

		embed.setThumbnail(Main.PHOTO_LINK);
		
		return embed;
		
	}
	
}
