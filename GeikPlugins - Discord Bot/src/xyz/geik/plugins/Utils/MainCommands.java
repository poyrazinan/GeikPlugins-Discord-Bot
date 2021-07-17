package xyz.geik.plugins.Utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import xyz.geik.plugins.Listeners;
import xyz.geik.plugins.Main;
import xyz.geik.plugins.Utils.Socket.SocketServer;

public class MainCommands {

	/**
	 * Main commands construct
	 * 
	 * @param tokens
	 * @throws InterruptedException
	 */
	public MainCommands(List<String> tokens) throws InterruptedException {

		if (tokens.get(0).equalsIgnoreCase("satinalim")) {

			if (tokens.size() == 3) {

				String discord_id = tokens.get(1);

				String role = tokens.get(2);

				JDA jda = Listeners.jda;

				if (jda == null)
					return;

				Guild guild = jda.getGuildById(Main.GUILD_ID);

				jda.awaitReady();

				User user = jda.getUserById(discord_id);

				if (user == null)
					return;

				else {

					Role customer = jda.getRoleById(Main.CUSTOMER_ROLE_ID);

					guild.addRoleToMember(user.getId(), customer).queue();

					Role license_role = jda.getRoleById(role);

					guild.addRoleToMember(user.getId(), license_role).queue();

					PrivateChannel pc = user.openPrivateChannel().complete();

					pc.sendMessageEmbeds(licenseRegisteredEmbed(license_role.getName()).build()).queue();

				}

			}

		}

		else if (tokens.get(0).equalsIgnoreCase("stop")) {

			try {
				SocketServer.listenSock.close();
			} catch (IOException e) {
			}

			System.exit(1);

		}

	}

	/**
	 * When a license registered for a discord user, this embed fires to the member
	 * via direct messages.
	 * 
	 * @return
	 */
	public EmbedBuilder licenseRegisteredEmbed(final String role_name) {

		EmbedBuilder eb = new EmbedBuilder();

		eb.setTitle("Merhaba!", Main.TITLE_LINK);

		eb.setColor(Color.GREEN);

		eb.setDescription("");

		eb.addField("Satın alımın için teşekkürler", role_name + " alıp bizi desteklediğin için çok teşekkür ederiz.",
				false);

		eb.addField("Hatırlatma", "İndirmeler ve lisans değişim işlemleri sitemizden yapılmaktadır. " + Main.TITLE_LINK,
				false);

		eb.setFooter(Main.FOOTER_NAME, Main.PHOTO_LINK);

		eb.setThumbnail(Main.PHOTO_LINK);

		return eb;

	}

}
