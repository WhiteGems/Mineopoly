package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyEndCommand extends TacoCommand {

	public MineopolyEndCommand() {
		super("end", new String[]{"end-game"}, "", "End the current game", MineopolyConstants.P_END_GAME);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(game.isRunning()){
			game.getChannel().sendMessage("&e" + player.getName() + " ended the game.");
			game.end();
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
