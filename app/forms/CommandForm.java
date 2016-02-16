package forms;

import org.apache.commons.codec.binary.StringUtils;

import play.data.validation.Constraints.Required;
import types.TownCommands;

public class CommandForm {
	
	@Required
	public String command;
	
	public String validate() {
		// cdか../であるか確認
		if (command.startsWith("cd ") || command.startsWith("talk") || StringUtils.equals(command, "../")) {
			return null;
		}
		
		try {
			TownCommands.valueOf(command);
		} catch(Exception e) {
			return "[" + command + "]　はサポートされていないコマンドです。";
		}
		return null;
	}

}
