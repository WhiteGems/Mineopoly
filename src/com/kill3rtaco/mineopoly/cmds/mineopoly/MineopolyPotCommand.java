package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;

import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyPotCommand extends TacoCommand {

	public MineopolyPotCommand() {
		super("pot", new String[]{}, "", "See what is in the pot", "");
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				Mineopoly.plugin.getGame().getBoard().getPot().getInfo(player);
			}else{
				if(player.hasPermission(MineopolyConstants.P_VIEW_GAME_STATS)){
					Mineopoly.plugin.getGame().getBoard().getPot().getInfo(player);
				}else{
					Mineopoly.plugin.chat.sendInvalidPermissionsMessage(player);
				}
			}
		}else{
			
		}
	}

}
