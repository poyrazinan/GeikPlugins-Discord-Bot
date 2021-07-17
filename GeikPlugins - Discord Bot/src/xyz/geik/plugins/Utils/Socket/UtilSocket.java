package xyz.geik.plugins.Utils.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import com.google.common.io.BaseEncoding;

import xyz.geik.plugins.Utils.MainCommands;

public class UtilSocket {

	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(input.getBytes());

			String result = new BigInteger(1, md.digest()).toString(16);
			if (result.length() % 2 != 0) {
				result = "0" + result;
			}

			return result;
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * @author MediaRise
	 */
	public static String readString(DataInputStream in, boolean base64) throws IOException {
		int stringSize = in.readInt();
		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < stringSize; i++) {
			buffer.append(in.readChar());
		}

		return base64 ? DecodeBASE64(buffer.toString()) : buffer.toString();
	}

	public static String DecodeBASE64(String text) throws UnsupportedEncodingException {
		byte[] bytes = BaseEncoding.base64().decode(text);
		return new String(bytes, "UTF-8");
	}

	public static void writeString(DataOutputStream out, String string) throws IOException {
		out.writeInt(string.length());
		out.writeChars(string);
	}

	public static void sendCommand(String command, DataOutputStream out) throws IOException {
		boolean success = true;
		try {

			List<String> tokens = Arrays.asList(command.split(" "));

			new MainCommands(tokens);

		} catch (Exception ex) {
			createLog(String.valueOf(SocketConfig.prefix) + "ERROR: " + ex.getMessage());
			success = false;
		}

		out.writeInt(success ? 1 : 0);
		out.flush();
	}

	public static void createLog(String data) {
		if (SocketConfig.consoleInfo.equals("true")) {
			System.out.println(String.valueOf(SocketConfig.prefix) + data);
		}
	}

}
