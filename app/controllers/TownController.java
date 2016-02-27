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

/**
 * コマンド一覧
 *
 * clear : 操作ログを全て消す
 * help  : コマンドの説明を表示
 * pwd   : アプリケーションルートからカレントディレクトリの相対パスを表示
 * ls    : カレントディレクトリのファイル・フォルダを表示
 * cd    : 半角スペースで区切られた先のディレクトリに移動する
 * ../   : 一つ上のディレクトリに移動する
 * talk  : 半角スペースで区切られた先のフ.txtァイルを表示する
 * 
 * @author homma
 * 
 */
public class TownController extends Controller {
	
	/**
	 * 入力されたコマンドごとに操作を実行する
	 * 
	 * @return render(List<String> logs, Form<CommandForm> form, String pronpt)
	 * List<String> logs :
	 * Form<CommandForm> :
	 * String pronpt	 :
	 */
	public Result execCommand() {
		Form<CommandForm> form = new Form<CommandForm>(CommandForm.class).bindFromRequest();
		
		if (form.hasErrors()) {
			return badRequest(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
 
		String inputCommand = form.get().command;
		
		if (inputCommand.equals(TownCommands.clear.toString())) {
			LogManager.clear();
			return ok(town.render(LogManager.getLogs(), form,  TownManager.getpronpt()));
		}
		
		if (inputCommand.equals(TownCommands.help.toString())) {
			TownService.help();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.equals(TownCommands.pwd.toString())) {
			TownService.pwd();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.equals(TownCommands.ls.toString())) {
			TownService.ls();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.startsWith("cd ")) {
			TownService.cd(inputCommand);
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		// FIXME cdメソッドの動作に振り分ける
		if (inputCommand.equals("../")) {
			TownService.execUp();
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}
		
		if (inputCommand.startsWith("talk ")) {
			boolean boosFlg = TownService.talk(inputCommand);
			
			if (boosFlg) {
				Form<BossForm> bossForm = new Form<BossForm>(BossForm.class);
				return ok(boss.render(LogManager.getLogs(), bossForm, "[BOSS]"));
			}
			return ok(town.render(LogManager.getLogs(), form, TownManager.getpronpt()));
		}

		return null; // return ok() に修正, 別ブランチで
	}

}
