package xyz.geik.plugins;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import xyz.geik.plugins.database.DatabaseQueries;
import xyz.geik.plugins.utils.DiscordStats;
import xyz.geik.plugins.utils.MemberStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.entities.*;

public class Listeners extends ListenerAdapter {

    public static JDA jda = null;

    public static void onStart() throws Exception {
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
        jda.awaitReady();
        System.out.println("JDA Bağlantısı başarıyla kuruldu!");
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if (e.getMember().getUser().isBot())
            return;
        User user = e.getUser();
        JDA jda = e.getJDA();
        HashMap<String, DiscordStats> discordStats = DatabaseQueries.getAllDiscordStats();
        List<MemberStats> memberStatList = DatabaseQueries.getAllPlayerLicenses(user.getId());
        PrivateChannel directMessage = e.getMember().getUser().openPrivateChannel().complete();
        if (memberStatList != null)
        {
            directMessage.sendMessageEmbeds(foundLicensesEmbed(memberStatList).build()).queue();
            Role customerRole = jda.getRoleById(Main.CUSTOMER_ROLE_ID);
            e.getGuild().addRoleToMember(user.getId(), customerRole).queue();
            for (MemberStats stats : memberStatList) {
                Role licenseRole = jda.getRoleById(discordStats.get(stats.getPluginName()).getDiscordRole());
                e.getGuild().addRoleToMember(user.getId(), licenseRole).queue();
            }
        }
    }

    private String licenseStringBuilder(final List<MemberStats> memberStatList) {
        String licenseString = memberStatList.stream()
                .map(memberStats -> (memberStats.getPluginName())).collect(Collectors.toList()).toString();
        return licenseString;
    }

    private EmbedBuilder foundLicensesEmbed(final List<MemberStats> memberStatList) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(Main.JOIN_TITLE, Main.TITLE_LINK);
        embed.setColor(Main.JOIN_COLOR);
        embed.setDescription("");
        embed.addField(Main.JOIN_FIRST_FIELD, Main.JOIN_FIRST_FIELD_DESC
                .replace("%licenses%", licenseStringBuilder(memberStatList)), false);
        embed.addField(Main.JOIN_SECOND_FIELD, Main.JOIN_SECOND_FIELD_DESC.replace("%link%", Main.TITLE_LINK)
                , false);
        embed.setFooter(Main.FOOTER_NAME, Main.PHOTO_LINK);
        embed.setThumbnail(Main.PHOTO_LINK);
        return embed;
    }
}