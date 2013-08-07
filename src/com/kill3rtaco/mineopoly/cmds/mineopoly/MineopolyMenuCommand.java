package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.menus.MineopolyGameMenu;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyMenuCommand extends TacoCommand {

	public MineopolyMenuCommand() {
		super("menu", new String[]{"m"}, "", "Open the in-game menu", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(game.isRunning()){
			if(game.hasPlayer(player)){
				MineopolyPlayer mp = game.getBoard().getPlayer(player);
				mp.showMenu(new MineopolyGameMenu(mp));
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
