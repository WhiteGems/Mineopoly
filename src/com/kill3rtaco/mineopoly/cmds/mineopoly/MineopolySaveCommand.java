package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolySaveCommand extends TacoCommand {

	public MineopolySaveCommand() {
		super("save", new String[]{}, "<name>", "Save the current game", MineopolyConstants.P_SAVE_GAME);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(args.length == 0){
			if(!game.isRunning()){
				Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
				return;
			}else if(!game.isLoadedFromSave()){
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThis game was not loaded from a save," +
						" please speicify a name to save this game");
				return;
			}
		}
		String name;
		if(args.length > 0) name = args[0];
		else name = game.getSave().getName();
		Mineopoly.plugin.chat.sendPlayerMessage(player, "&aSaving game as &e" + name + "&a...");
		game.save(name);		
		Mineopoly.plugin.chat.sendPlayerMessage(player, "&aDone!");
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(args.length == 0){
			if(!game.isRunning()){
				Mineopoly.plugin.chat.out(new GameNotInProgressMessage());
				return true;
			}else if(!game.isLoadedFromSave()){
				Mineopoly.plugin.chat.out("&cThis game was not loaded from a save," +
						" please speicify a name to save this game");
				return true;
			}
		}
		String name;
		if(args.length > 0) name = args[0];
		else name = game.getSave().getName();
		Mineopoly.plugin.chat.out("&aSaving game as &e" + name + "&a...");
		game.save(name);		
		Mineopoly.plugin.chat.out("&aDone!");
		return true;
	}

}
