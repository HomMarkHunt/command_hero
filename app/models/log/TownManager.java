package models.log;

import java.io.File;

import play.Logger;

public class TownManager {

		private TownManager() {}
		
		private static final String S = File.separator;
		private static String cd = "はじまりの町" + S + "家";

		public static String getCd() {
			Logger.debug("get directory [{}]", cd);
			return TownManager.cd;
		}
		
		public static void setCd(String cd) {
			TownManager.cd = cd;
		}
		
		public static String getpronpt() {
			File file = new File(TownManager.cd);
			return "[" + file.getName() + "]";
		}

}
