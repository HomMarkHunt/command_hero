package models.log;

import java.io.File;

import play.Logger;

public class TownManager {

		private TownManager() {}
		
		private static String cd = "C:\\Enviroment\\workspaces\\command_hero\\command_hero\\はじまりの町\\家";

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
