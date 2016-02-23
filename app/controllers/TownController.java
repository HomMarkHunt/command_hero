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
	
	public Result execCommand() {
		Form<CommandForm> form = new Form<CommandForm>(CommandForm.class).bindFromRequest();
		
		if (form.hasErrors()) {
			return badRequest(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		String inputCommand = form.get().command;
		
		if (inputCommand.equals(TownCommands.clear.toString())) {
			LogManager.clearLogs();
			return ok(town.render(LogManager.getLogs(), form,  TownManager.getpronpt()));
		}
		
		if (inputCommand.equals(TownCommands.help.toString())) {
			TownService.execHelp();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.equals(TownCommands.pwd.toString())) {
			TownService.execPwd();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.equals(TownCommands.ls.toString())) {
			TownService.execLs();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.startsWith("cd ")) {
			TownService.execCd(inputCommand);
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.equals("../")) {
			TownService.execUp();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.startsWith("talk ")) {
			boolean boosFlg = TownService.execTalk(inputCommand);
			
			if (boosFlg) {
				Form<BossForm> bossForm = new Form<BossForm>(BossForm.class);
				return ok(boss.render(LogManager.getLogs(), bossForm, "[BOSS]"));
			}
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}

		return null;
	}

}
