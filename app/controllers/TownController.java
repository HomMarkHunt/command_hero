package controllers;

import forms.BossForm;
import forms.CommandForm;
import models.log.LogManager;
import models.log.TownManager;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.TownService;
import types.TownCommands;
import views.html.boss;
import views.html.town;

public class TownController extends Controller {
	
	public Result distribute() {
		Form<CommandForm> form = new Form<CommandForm>(CommandForm.class).bindFromRequest();
		
		if (form.hasErrors()) {
			return badRequest(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		String command = form.get().command;
		
		// clearコマンド時の挙動
		if (command.equals(TownCommands.clear.toString())) {
			LogManager.clearLogs();
			return ok(town.render(LogManager.getLogs(), form,  TownManager.getpronpt()));
		}
		
		// helpコマンド時の挙動
		if (command.equals(TownCommands.help.toString())) {
			TownService.createHelp();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		// pwdコマンド時の操作
		if (command.equals(TownCommands.pwd.toString())) {
			LogManager.addLogs("勇者の現在地 : " + TownService.showPwd());
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		// lsコマンド時の操作
		if (command.equals(TownCommands.ls.toString())) {
			TownService.showLs();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		// cd コマンド時の操作
		if (command.startsWith("cd ")) {
			boolean moveResult = TownService.moveCd(command);
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		// ../コマンド時の操作
		if (command.equals("../")) {
			TownService.moveUp();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		// talkコマンド時の操作
		if (command.startsWith("talk ")) {
			boolean boosFlg = TownService.talk(command);
			
			if (boosFlg) {
				Form<BossForm> bossForm = new Form<BossForm>(BossForm.class);
				return ok(boss.render(LogManager.getLogs(), bossForm, "[BOSS]"));
			}
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}

		return null;
	}

}
