package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.IntStream;

import models.log.LogManager;
import models.log.TownManager;
import play.Logger;
import play.i18n.Messages;

public class TownService {

	public static void createHelp() {
		IntStream.range(1, 7).forEach(i -> {
			LogManager.addLogs(Messages.get("command.help." + i));
		});
	}
	
	public static boolean showLs() {
		Logger.debug("show ls : is start");
	 	String cd = TownManager.getCd();
	 	
	 	File dir = new File(cd);
	 	
	 	Logger.debug("[{}] is directory?? [{}]", cd, dir.isDirectory());
	 	
	 	File[] arrayFiles = dir.listFiles();
	 	for (File file : arrayFiles) {
			LogManager.addLogs(file.getName());
		}
	 	return true;
	 }
	
	public static boolean moveCd(String command) {
		Logger.debug("mocecd : is start");
		String target = command.substring(3);
		
		if (target.length() == 0) {
			LogManager.addLogs("移動先を入力してください");
			return false;
		}
		
		Logger.debug("cd : Target directory is [{}]", target);
		
		String cd = TownManager.getCd();
		
		if (cd.contains("家") || cd.contains("宿") || cd.contains("武器")) {
			LogManager.addLogs("そこには移動できません");
			return false;
		}
		
		String path = TownManager.getCd() + File.separator + target;
		
		File file = new File(path);
		if (file.isFile()) {
			LogManager.addLogs("そこには移動できません");
			return false;
		}
		
		TownManager.setCd(path);
		return true;
	}
	
	public static boolean moveUp() {
		Logger.debug("../ : is start");
		
		String cd = TownManager.getCd();
		Logger.debug("path is [{}]", cd);
		
		File file = new File(cd);
		String parentFile = file.getParent();
		Logger.debug("Parent path is [{}]", parentFile);
		
		if (parentFile == null) {
			Logger.warn("attempt to over root");
			LogManager.addLogs("この町から出ることはできない");
			return false;
		}
		
		TownManager.setCd(parentFile);
		return true;
	}
	
	public static String showPwd() {
		String cd = TownManager.getCd();
		Logger.debug("Parent path is [{}]", cd);
		File file = new File(cd);
		String full = file.getAbsolutePath();
		return full.substring(51);
	}
	
	public static boolean talk(String command) {
		Logger.debug("mocecd : is start");
		String target = command.substring(5);
		
		if (target.length() == 0) {
			LogManager.addLogs("会話の相手を入力してください。");
			return false;
		}
		
		Logger.debug("talk : Target is [{}]", target);
		
		
		
		String path = TownManager.getCd() + File.separator + target;
		
		File file = new File(path);
		if (file.isDirectory()) {
			LogManager.addLogs("それとは会話できません。");
			return false;
		}
		
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	LogManager.addLogs(line);
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	LogManager.addLogs("会話に失敗しました。");
	    }
	        
	    // ボス戦
	    if (target.equals("凄く大事なことを言う村長.txt")) {
	    	Logger.debug("boos flg true");
	    	return true;
	    }
	    
		TownManager.setCd(path);
		return false;
	}
	
}
