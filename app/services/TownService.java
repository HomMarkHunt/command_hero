package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

import models.log.LogManager;
import models.log.TownManager;
import play.Logger;
import play.i18n.Messages;

public class TownService {

	public static void execHelp() {
		IntStream.range(1, 7).forEach(i -> {
			LogManager.addLogs(Messages.get("command.help." + i));
		});
	}
	
	public static void execLs() {
		Logger.debug("show ls : is start");
	 	
	 	File currentDir = new File(TownManager.getCurrentDir());
	 	File[] children = currentDir.listFiles();
	 	Arrays.asList(children).stream().forEach(child -> {
	 		LogManager.addLogs(child.getName());
	 	});
	 }
	
	// execTalkと処理が似ているので共通化出来ないか？
	public static boolean execCd(String command) {
		Logger.debug("mocecd : is start");
		
		String target = command.substring(3);
		if (target.length() == 0) {
			LogManager.addLogs("移動先を入力してください");
			return false;
		}
		
		String currentDir = TownManager.getCurrentDir();
		if (currentDir.contains("家") || currentDir.contains("宿") || currentDir.contains("武器")) {
			LogManager.addLogs("そこには移動できません");
			return false;
		}
		
		String targetPath = TownManager.getCurrentDir() + File.separator + target;
		File changeDirTarget = new File(targetPath);
		if (changeDirTarget.isFile()) {
			LogManager.addLogs("そこには移動できません");
			return false;
		}
		
		TownManager.setCurrentDir(targetPath);
		return true;
	}
	
	public static boolean execUp() {
		Logger.debug("../ : is start");
		
		String currentDir = TownManager.getCurrentDir();
		Logger.debug("path is [{}]", currentDir);
		
		String parentFileName = new File(currentDir).getParent();
		Logger.debug("Parent path is [{}]", parentFileName);
		if (parentFileName == null) {
			Logger.warn("attempt to over root");
			LogManager.addLogs("この町から出ることはできない");
			return false; // ここで処理めたいだけなのでbreakでいいか、TownControllerも見て検討？
		}
		
		TownManager.setCurrentDir(parentFileName);
		return true;
	}
	
	public static void execPwd() {
		LogManager.addLogs("勇者の現在地 : " + TownManager.getCurrentDir());
	}
	
	public static boolean execTalk(String command) {
		Logger.debug("mocecd : is start");
	
		String targetDir = command.substring(5);
		if (targetDir.length() == 0) {
			LogManager.addLogs("会話の相手を入力してください。");
			return false;
		}
		
		String targetPath = TownManager.getCurrentDir() + File.separator + targetDir;
		File talkTarget = new File(targetPath);
		if (talkTarget.isDirectory()) {
			LogManager.addLogs("それとは会話できません。");
			return false;
		}
		
		readFile(talkTarget);

		if (targetDir.equals("凄く大事なことを言う村長.txt")) {
			Logger.debug("boos flg true");
			return true;
		}
		
		return false;
	}
	
	// メソッド名が不適切、思いつかないのでこのまま
	private static void readFile(File file) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {
			String line;
			while ((line = br.readLine()) != null) {
				LogManager.addLogs(line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			LogManager.addLogs("会話に失敗しました。");
		}
	}
	
}
