package models.log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import play.Logger;

public class LogManager {

	private LogManager() {}
	
	private static List<String> logs = new ArrayList<>();

	public static List<String> getLogs() {
		return logs;
	}
	
	public static void addLog(String log) {
		if (logs.size() > 20) {
			IntStream.rangeClosed(0, logs.size() - 20).forEach(i -> {
				logs.remove(i);
			});
		}
		
		LogManager.logs.add(log);
		Logger.debug("Add Log ->" + log);
	}
	
	public static void clear() {
		logs.clear();
		Logger.debug("Cleared all logs");
	}
	
}
