package com.kill3rtaco.mineopoly.cmds.mineopoly;

import java.io.File;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.saves.MineopolySaveGame;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyResumeCommand extends TacoCommand {

	public MineopolyResumeCommand() {
		super("resume", new String[]{"res"}, "<save name>", "", MineopolyConstants.P_RESUME_GAME);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cSpecify the saved game's name");
		}else{
			File file = new File(Mineopoly.plugin.getDataFolder() + "/saves/" + args[0] + ".yml");
			if(file.exists()){
				MineopolySaveGame save = new MineopolySaveGame(file);
				if(Mineopoly.plugin.getGame().canStart(save)){
					Mineopoly.plugin.chat.sendPlayerMessage(player, "&aStarting game &e" + args[0] + "&a...");
					Mineopoly.plugin.resumeGame(save);
				}else{
					Mineopoly.plugin.chat.sendPlayerMessage(player, "&cCannot resume that game, not enough players are online");
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThe save &e" + args[0] + " &cdoes not exist");
			}
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.out("Specify the saved game's name");
		}else{
			File file = new File(Mineopoly.plugin.getDataFolder() + "/saves/" + args[0] + ".yml");
			if(file.exists()){
				MineopolySaveGame save = new MineopolySaveGame(file);
				if(Mineopoly.plugin.getGame().canStart(save)){
					Mineopoly.plugin.chat.out("Starting game '" + args[0] + "'...");
					Mineopoly.plugin.resumeGame(save);
				}else{
					Mineopoly.plugin.chat.out("Cannot resume that game, not enough players are online");
				}
			}else{
				Mineopoly.plugin.chat.out("The save '" + args[0] + "' does not exist");
			}
		}
		return true;
	}

}
