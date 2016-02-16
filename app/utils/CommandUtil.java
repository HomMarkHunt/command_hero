package utils;

import models.log.LogManager;

public class CommandUtil {

	public static void addUserLog(String pronpt, String command) {
		StringBuilder sb = new StringBuilder();
		sb.append(pronpt);
		sb.append(" : ");
		sb.append(command);
		LogManager.addLogs(sb.toString());
	}
}
