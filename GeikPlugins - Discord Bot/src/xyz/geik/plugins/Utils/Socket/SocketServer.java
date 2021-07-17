package xyz.geik.plugins.Utils.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

public class SocketServer extends Thread {

	public static int port = SocketConfig.port;

	public static ServerSocket listenSock = null;

	public static DataInputStream in = null;

	public static DataOutputStream out = null;

	public static Socket sock = null;

	private boolean connect_status = false;

	public void run() {
		try {
			listenSock = new ServerSocket(port);
			while (true) {
				sock = listenSock.accept();
				in = new DataInputStream(sock.getInputStream());
				out = new DataOutputStream(sock.getOutputStream());
				this.connect_status = true;
				try {
					if (in.readByte() == 1) {
						int random_code = (new SecureRandom()).nextInt();
						out.writeInt(random_code);
						boolean success = UtilSocket.readString(in, false)
								.equals(UtilSocket.hash(String.valueOf(random_code) + SocketConfig.password));
						if (success) {
							out.writeInt(1);
							UtilSocket.createLog(SocketConfig.succesfullyLogin);
						} else {
							out.writeInt(0);
							UtilSocket.createLog(SocketConfig.wrongPassword);
							this.connect_status = false;
						}
					} else {
						UtilSocket.createLog(SocketConfig.wrongData);
						this.connect_status = false;
					}
					while (this.connect_status) {
						byte packetNumber = in.readByte();
						if (packetNumber == 2) {
							String command = UtilSocket.readString(in, true);
							UtilSocket.sendCommand(command, out);
							continue;
						}
						if (packetNumber == 3) {
							this.connect_status = false;
							continue;
						}
						UtilSocket.createLog("Packet not found!");
					}
					out.flush();
					out.close();
					in.close();
				} catch (IOException ex) {
					UtilSocket.createLog(ex.getMessage());
					this.connect_status = false;
				}
				sock.close();
			}
		} catch (IOException ex) {
			UtilSocket.createLog(ex.getMessage());
			return;
		}
	}

}
