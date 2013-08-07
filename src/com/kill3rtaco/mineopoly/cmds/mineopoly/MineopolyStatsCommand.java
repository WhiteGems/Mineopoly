package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;

import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.api.messages.PlayerNotOnlineMessage;
import com.kill3rtaco.tacoapi.api.messages.TooManyArgumentsMessage;

public class MineopolyStatsCommand extends TacoCommand {
	
	public MineopolyStatsCommand() {
		super("stats", new String[]{"info", "player-info", "pi", "s"}, "[player]", "View player stats", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			if(Mineopoly.plugin.getGame().isRunning()){
				if(Mineopoly.plugin.getGame().hasPlayer(player)){
					Mineopoly.plugin.getGame().getBoard().getPlayer(player).getInfo(player);
				}else{
					Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
			}
		}else if(args.length == 1){
			if(Mineopoly.plugin.getGame().isRunning()){
				Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
				if(p != null){
					if(Mineopoly.plugin.getGame().hasPlayer(player)){
						Mineopoly.plugin.getGame().getBoard().getPlayer(p).getInfo(player);
					}else{
						if(player.hasPermission(MineopolyConstants.P_VIEW_GAME_STATS)){
							Mineopoly.plugin.getGame().getBoard().getPlayer(p).getInfo(player);
						}else{
							Mineopoly.plugin.chat.sendInvalidPermissionsMessage(player);
						}
					}
				}else{
					Mineopoly.plugin.chat.sendPlayerMessage(player, new PlayerNotOnlineMessage(args[0]));
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new TooManyArgumentsMessage("/mineopoly stats [player]") + "");
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
