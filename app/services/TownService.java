package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.IntStream;

import models.log.LogManager;
import models.log.TownManager;
import play.Logger;
import play.i18n.Messages;

public class TownService {

	public static void help() {
		IntStream.range(1, 7).forEach(i -> {
			LogManager.addLog(Messages.get("command.help." + i));
		});
	}
	
	public static void ls() {
		Logger.debug("show ls : is start");
	 	
	 	File currentDir = new File(TownManager.getCurrentDir());
	 	File[] files = currentDir.listFiles();
	 	Arrays.asList(files).stream().forEach(f -> {
	 		LogManager.addLog(f.getName());
	 	});
	 }
	
	// TODO voidに変更
	public static boolean cd(String command) {
		Logger.debug("mocecd : is start");
		
		// TODO Commandクラスを作成してそこに判定ロジックを移動、マジックナンバーも置き換える
		String target = command.substring(3);
		if (target.length() == 0) {
			LogManager.addLog("移動先を入力してください");
			return false;
		}
		
		File changeDirTarget = new File(TownManager.getCurrentDir() , target);
		if (!changeDirTarget.exists() || changeDirTarget.isFile()) {
			LogManager.addLog("そこには移動できません");
			return false;
		}
		
		TownManager.setCurrentDir(changeDirTarget.getPath());
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
			LogManager.addLog("この町から出ることはできない");
			return false;
		}
		
		TownManager.setCurrentDir(parentFileName);
		return true;
	}
	
	public static void pwd() {
		LogManager.addLog("勇者の現在地 : " + TownManager.getCurrentDir());
	}
	
	public static boolean talk(String command) {
		Logger.debug("mocecd : is start");
	
		// TODO Commandクラスを作成してそこに判定ロジックを移動、マジックナンバーも置き換える
		String targetDir = command.substring(5);
		if (targetDir.length() == 0) {
			LogManager.addLog("会話の相手を入力してください。");
			return false;
		}
		
		String targetPath = TownManager.getCurrentDir() + File.separator + targetDir;
		File talkTarget = new File(targetPath);
		if (talkTarget.isDirectory()) {
			LogManager.addLog("それとは会話できません。");
			return false;
		}
		
		readFile(talkTarget);

		if (targetDir.equals("凄く大事なことを言う村長.txt")) {
			Logger.debug("boos flg true");
			return true;
		}
		
		return false;
	}
	
	// XXX メソッド名が不適切、思いつかないのでこのまま
	private static void readFile(File file) {
		try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
			br.lines()
				.forEach(l -> LogManager.addLog(l));
		} catch(IOException e) {
			Logger.error("Failed to read [{}], cause by [{}]", file.getAbsolutePath(), e.getStackTrace());
			LogManager.addLog("会話に失敗しました");
		}
	}
	
}
