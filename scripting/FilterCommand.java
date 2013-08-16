package scripting;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class FilterCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "filter";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/filter <script> or /filter for help";
	}

	//TODO 
	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) {
		if (args.length == 0) {
			//Send a list of filters
		}
		else if (args.length == 1) {
			//run filter
		}
		else {
			//send error message
		}

	}

}
