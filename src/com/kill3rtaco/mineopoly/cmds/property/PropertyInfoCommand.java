package com.kill3rtaco.mineopoly.cmds.property;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class PropertyInfoCommand extends TacoCommand {

	public PropertyInfoCommand() {
		super("info", new String[]{}, "[space]", "View information on a space", "");
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(player.hasPermission(MineopolyConstants.P_VIEW_GAME_STATS) || Mineopoly.plugin.getGame().hasPlayer(player)){
				if(args.length == 0){
					if(Mineopoly.plugin.getGame().hasPlayer(player)){
						MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
						mp.getCurrentSection().getInfo(player);
					}else{
						Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
					}
				}else{
					MineopolySection section;
					if(TacoAPI.getChatUtils().isNum(args[0])){
						int id = Integer.parseInt(args[0]);
						section = Mineopoly.plugin.getGame().getBoard().getSection(id);
						if(section == null){
							Mineopoly.plugin.chat.sendPlayerMessage(player, "&cID cannot be lower than &60");
						}
					}else{
						section = Mineopoly.plugin.getGame().getBoard().getSection(args[0]);
						if(section == null){
							Mineopoly.plugin.chat.sendPlayerMessage(player, "&cSpace on board with name of &6" + args[0] + " &cnot found");
						}
					}
					section.getInfo(player);
				}
			}else if(!player.hasPermission(MineopolyConstants.P_VIEW_GAME_STATS)){
				Mineopoly.plugin.chat.sendInvalidPermissionsMessage(player);
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

}
