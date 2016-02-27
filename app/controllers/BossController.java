package controllers;

import java.util.Random;

import forms.BossForm;
import models.log.LogManager;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.boss;

public class BossController extends Controller {
	
	public Result index() {
		Form<BossForm> bossForm = new Form<BossForm>(BossForm.class).bindFromRequest();
		Random ran = new Random();
		int randm = ran.nextInt(46) + 1;
		
		LogManager.addLog(Messages.get("boss.comment." + randm));
		
		return ok(boss.render(LogManager.getLogs(), bossForm, "[BOSS]"));
	}
	
	public Result kill() {
		Form<BossForm> bossForm = new Form<BossForm>(BossForm.class);
		
		LogManager.addLog(Messages.get("boss.comment.clear"));
		LogManager.addLog("ゲームクリアです！！！！！！！！！");
		
		return ok(boss.render(LogManager.getLogs(), bossForm, "[CLEAR!!!!!!!!]"));
	}
 
}
