package xyz.geik.plugins.utils;

import java.io.IOException;
import java.util.List;

import xyz.geik.plugins.Listeners;
import xyz.geik.plugins.Main;
import xyz.geik.plugins.utils.socket.SocketServer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class ConsoleCommands {

    public ConsoleCommands(List<String> tokens) throws InterruptedException, IOException {
        final String command = tokens.get(0);
        if (command.equalsIgnoreCase("satinalim") && tokens.size() == 3) {
            final String discord_id = tokens.get(1);
            final String role = tokens.get(2);
            JDA jda = Listeners.jda;
            if (jda == null)
                return;

            Guild guild = jda.getGuilds().get(0);
            jda.awaitReady();
            User user = jda.getUserById(discord_id);
            if (user == null)
                return;

            else {
                final Role customer = jda.getRoleById(Main.CUSTOMER_ROLE_ID);
                guild.addRoleToMember(user.getId(), customer).queue();

                final Role license_role = jda.getRoleById(role);
                guild.addRoleToMember(user.getId(), license_role).queue();

                final PrivateChannel pc = user.openPrivateChannel().complete();
                pc.sendMessageEmbeds(licenseRegisteredEmbed(license_role.getName()).build()).queue();
            }
        }
        else if (command.equalsIgnoreCase("stop")) {
            SocketServer.listenSock.close();
            System.exit(1);
        }
    }

    public EmbedBuilder licenseRegisteredEmbed(final String roleName) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(Main.BUY_TITLE, Main.TITLE_LINK);
        eb.setColor(Main.BUY_COLOR);
        eb.setDescription("");
        eb.addField(Main.BUY_FIRST_FIELD, Main.BUY_FIRST_FIELD_DESC.replace("%license%", roleName),
                false);
        eb.addField(Main.BUY_SECOND_FIELD, Main.BUY_SECOND_FIELD_DESC.replace("%link%", Main.TITLE_LINK),
                false);
        eb.setFooter(Main.FOOTER_NAME, Main.PHOTO_LINK);
        eb.setThumbnail(Main.PHOTO_LINK);
        return eb;
    }
}