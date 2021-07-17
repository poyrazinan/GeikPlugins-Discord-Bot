package xyz.geik.plugins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.dv8tion.jda.api.exceptions.ContextException;
import xyz.geik.plugins.Utils.MainCommands;
import xyz.geik.plugins.Utils.Socket.SocketServer;
import xyz.geik.plugins.Utils.Socket.UtilSocket;

public class Main {

	/**
	 * 
	 * <b> MySQL Configuration Settings <b>
	 */
	public static String HOST_ADRESS = "ADRESS HERE";
	public static String HOST_PORT = "PORT HERE";
	public static String DATABASE_NAME = "DATABASE NAME HERE";
	public static String USER_NAME = "USER NAME HERE";
	public static String PASSWORD = "PASSWORD HERE";

	/**
	 * 
	 * <b> Discord BOT Configuration Settings
	 */
	public static String BOT_TOKEN = "BOT TOKEN HERE";
	public static String BOT_STATUS = "CUSTOM STATUS TEXT HERE";
	public static String TITLE_LINK = "TITLE LINK HERE";
	public static String PHOTO_LINK = "PHOTO LINK HERE";
	public static String CUSTOMER_ROLE_ID = "CUSTOMER DISCORD ID HERE";
	public static String EXCLUSIVE_BUYER_ROLE_ID = "EXCLUSIVE BUYER ROLE ID HERE";
	public static String FOOTER_NAME = "FOOTER COPYRIGHT NAME HERE";
	
	/**
	 * 
	 * <b> Console command discord extension <b>
	 */
	public static String GUILD_ID = "GUILD ID HERE";
	

	/**
	 * @author Geik
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Listeners.onStart();

		UtilSocket.createLog("Socket listener starting...");
		SocketServer socket = new SocketServer();
		socket.start();

		try {
			mainInput();
		} catch (ContextException e) {
			UtilSocket.createLog("The user who you want to send message don't take direct messages!");
		}

	}

	/**
	 * @author Geik
	 * @since 1.0.0
	 * @throws ContextException
	 */
	public static void mainInput() throws ContextException {

		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) {
			List<String> tokens = new ArrayList<>();

			Scanner lineScanner = new Scanner(scanner.nextLine());

			while (lineScanner.hasNext()) {

				tokens.add(lineScanner.next());

			}

			lineScanner.close();

			try {
				new MainCommands(tokens);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		scanner.close();

	}

}
