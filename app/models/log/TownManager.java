package models.log;

import java.io.File;

import play.Logger;

public class TownManager {

		private TownManager() {}
		
		private static final String S = File.separator;
		private static String currentDir = "はじまりの町" + S + "家";

		public static String getCurrentDir() {
			Logger.debug("get directory [{}]", currentDir);
			return TownManager.currentDir;
		}
		
		public static void setCurrentDir(String currentDir) {
			TownManager.currentDir = currentDir;
		}
		
		public static String getpronpt() {
			File file = new File(TownManager.currentDir);
			return "[" + file.getName() + "]";
		}

}
