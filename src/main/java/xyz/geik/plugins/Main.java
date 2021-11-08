package xyz.geik.plugins;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import xyz.geik.plugins.utils.ConsoleCommands;
import xyz.geik.plugins.utils.socket.SocketServer;
import xyz.geik.plugins.utils.socket.UtilSocket;

public class Main {

    // SQL Settings
    public static final String HOST_ADRESS = "HOST ADRESS";
    public static final String HOST_PORT = "3306";
    public static final String DATABASE_NAME = "DATABASE_NAME";
    public static final String USER_NAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    // BOT Settings
    public static final String BOT_TOKEN = "BOT TOKEN HERE";
    public static final String BOT_STATUS = "BOT STATUS HERE";
    public static final String TITLE_LINK = "TITLE WEBSITE LINK";
    public static final String PHOTO_LINK = "PHOTO LINK";
    public static final String CUSTOMER_ROLE_ID = "CUSTOMER ROLE ID";
    public static final String FOOTER_NAME = "EMBED FOOTER NAME";

    // Found License Embed
    public static final String JOIN_TITLE = "Merhaba!";
    public static final Color JOIN_COLOR = Color.RED;
    public static final String JOIN_FIRST_FIELD = "Sana ait lisans bulundu:";
    public static final String JOIN_FIRST_FIELD_DESC = "%licenses%";
    public static final String JOIN_SECOND_FIELD = "Hatırlatma:";
    public static final String JOIN_SECOND_FIELD_DESC = "İndirmeler sitemizden yapılmaktadır. %link";

    // Bought License Embed
    public static final String BUY_TITLE = "Merhaba!";
    public static final Color BUY_COLOR = Color.GREEN;
    public static final String BUY_FIRST_FIELD = "Satın alımın için teşekkürler";
    public static final String BUY_FIRST_FIELD_DESC = "%license% alıp bizi desteklediğin için çok teşekkür ederiz.";
    public static final String BUY_SECOND_FIELD = "Hatırlatma:";
    public static final String BUY_SECOND_FIELD_DESC = "İndirmeler sitemizden yapılmaktadır. %link%";

    public static int socketPort = 10280;
    public static String socketPassword = "123asd123asd123asd";
    public static String socketPrefix = "[Lisans Kaydı] ";
    public static String socketWrongPassword = "Incorrect password, please enter it carefully.";
    public static String socketWrongData = "Please check PHP variables.";
    public static String socketSuccesfullyLogin = "Login is successful.";

    public static void main(String[] args) {
        try {
            Listeners.onStart();
            UtilSocket.createLog("Socket listener starting...");
            SocketServer socket = new SocketServer();
            socket.start();

            mainInput();
        }
        catch (Exception e) {e.printStackTrace(); }
    }

    public static void mainInput() throws InterruptedException, IOException  {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            List<String> tokens = new ArrayList<>();
            Scanner lineScanner = new Scanner(scanner.nextLine());

            while (lineScanner.hasNext()) {
                tokens.add(lineScanner.next());
            }
            lineScanner.close();
            new ConsoleCommands(tokens);
        }
        scanner.close();
    }
}