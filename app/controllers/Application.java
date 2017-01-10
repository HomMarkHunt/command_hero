package controllers;

import forms.CommandForm;
import forms.TutorialForm;
import models.log.LogManager;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import services.OpeningService;
import utils.CommandUtil;
import views.html.openning;
import views.html.town;

public class Application extends Controller {

	private String pronpt = null;
	
	public Result index() {
		Form<TutorialForm> form = new Form<TutorialForm>(TutorialForm.class);
		OpeningService.setIndex();
		
		this.pronpt = Messages.get("pronpt.index");
		return ok(openning.render(LogManager.getLogs(), form, pronpt));
	}

	public Result tutorial() {
		Form<TutorialForm> form = new Form<TutorialForm>(TutorialForm.class).bindFromRequest();
		
		// フォームのエラーの場合トップに戻す
		if (form.hasErrors()) {
			return badRequest(openning.render(LogManager.getLogs(), form, pronpt));
		}
		
		String command = form.get().command;
		this.pronpt = Messages.get("pronpt.tutorial");
		CommandUtil.addUserLog(this.pronpt, command);

		boolean tutorialFlg = OpeningService.tutorial(command);
		
		if (tutorialFlg) {
			this.pronpt = Messages.get("pronpt.myroom");
			Form<CommandForm> com = new Form<CommandForm>(CommandForm.class);
			return ok(town.render(LogManager.getLogs(), com, pronpt));
		}
		return ok(openning.render(LogManager.getLogs(), form, pronpt));
	}

}
