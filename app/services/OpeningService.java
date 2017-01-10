package services;

import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import models.log.LogManager;
import play.i18n.Messages;

public class OpeningService {

	private static int tutorialCuount = 0;

	public static void setIndex() {
		LogManager.clearLogs();
		LogManager.addLogs(Messages.get("opening.1"));
		LogManager.addLogs(Messages.get("opening.2"));
		LogManager.addLogs(Messages.get("opening.3"));
		IntStream.range(0, 5).forEach(i -> LogManager.addLogs(StringUtils.SPACE));
		LogManager.addLogs(sayMother(""));
	}
	
	public static Boolean tutorial(String command) {
		if (tutorialCuount > 5) {
			tutorialCuount = 0;
			IntStream.range(1, 4).forEach(i -> {
				LogManager.addLogs(Messages.get("town." + i));
			});
			return true;
		}
		
		LogManager.addLogs(sayMother(command));
		return false;
	}

	private static String sayMother(String command) {
		StringBuilder sb = new StringBuilder();
		sb.append(Messages.get("mother.face"));
		sb.append(Messages.get("mother." + String.valueOf(tutorialCuount)));

		tutorialCuount++;
		return sb.toString();
	}
}
