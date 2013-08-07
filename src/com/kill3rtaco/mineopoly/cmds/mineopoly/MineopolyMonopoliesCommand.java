package com.kill3rtaco.mineopoly.cmds.mineopoly;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyColor;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.api.messages.PlayerNotOnlineMessage;

public class MineopolyMonopoliesCommand extends TacoCommand {

	public MineopolyMonopoliesCommand() {
		super("monopolies", new String[]{"monos", "ms"}, "[player]", "View a player's monopolies", "");
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			if(Mineopoly.plugin.getGame().isRunning()){
				if(Mineopoly.plugin.getGame().hasPlayer(player)){
					MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
					if(mp.ownedSections().size() == 0){
						Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou do not have any monopolies");
					}else{
						String s = "";
						ArrayList<MineopolyColor> monopolies = mp.getMonopolies();
						for(int i=0; i<monopolies.size(); i++){
							if(i == monopolies.size() - 1)
								s = s + monopolies.get(i).getName();
							else s = s + monopolies.get(i).getName() + "&8, ";
						}
						Mineopoly.plugin.chat.sendPlayerMessage(player, TacoAPI.getChatUtils().createHeader("&3" + mp.getName() + "&b's Monopolies"));
						Mineopoly.plugin.chat.sendPlayerMessage(player, s);
					}
				}
			}
		}else{
			if(Mineopoly.plugin.getGame().isRunning()){
				if(Mineopoly.plugin.getGame().hasPlayer(player) || player.hasPermission(MineopolyConstants.P_VIEW_GAME_STATS)){
					Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
					if(p ==  null){
						player.sendMessage(new PlayerNotOnlineMessage(args[0]) + "");
					}else{
						if(Mineopoly.plugin.getGame().hasPlayer(p)){
							MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
							if(mp.ownedSections().size() == 0){
								Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThis player does not have any monopolies");
							}else{
								String s = "";
								ArrayList<MineopolyColor> monopolies = mp.getMonopolies();
								for(int i=0; i<monopolies.size(); i++){
									if(i == monopolies.size() - 1)
										s = s + monopolies.get(i).getName();
									else s = s + monopolies.get(i).getName() + "&8, ";
								}
								Mineopoly.plugin.chat.sendPlayerMessage(player, TacoAPI.getChatUtils().createHeader("&3" + mp.getName() + "&b's Monopolies"));
								Mineopoly.plugin.chat.sendPlayerMessage(player, s);
							}
						}
					}
				}else{
					Mineopoly.plugin.chat.sendInvalidPermissionsMessage(player);
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
			}
		}
	}

}
