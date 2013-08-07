package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.CannotPerformActionMessage;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyRollCommand extends TacoCommand {

	public MineopolyRollCommand() {
		super("roll", new String[]{}, "", "Roll the dice", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer p = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(p.hasTurn()){
					if(p.isJailed()){
						p.sendMessage("&cYou cannot use that command because you are jailed. Please use &6/" + Mineopoly.getJAlias() + " roll &cinstead");
					}else{
						if(p.canRoll()){
							p.roll();
						}else{
							p.sendMessage(new CannotPerformActionMessage());
						}
					}
				}else{
					p.sendMessage(new InvalidTurnMessage());
				}
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
