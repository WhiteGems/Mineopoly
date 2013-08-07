package com.kill3rtaco.mineopoly.cmds.mineopoly;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.api.messages.PlayerNotOnlineMessage;

public class MineopolyDeedsCommand extends TacoCommand {

	

	public MineopolyDeedsCommand() {
		super("deeds", new String[]{"props", "properties"}, "[player]", "View a players properties", "");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			if(Mineopoly.plugin.getGame().isRunning()){
				if(Mineopoly.plugin.getGame().hasPlayer(player)){
					MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
					if(mp.ownedSections().size() == 0){
						Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou do not own any title deeds");
					}else{
						String s = "";
						ArrayList<OwnableSection> props = mp.ownedSections();
						Collections.sort(props);
						for(int i=0; i<props.size(); i++){
							if(i == props.size() - 1)
								s = s + props.get(i).getColorfulName();
							else s = s + props.get(i).getColorfulName() + "&8, ";
						}
						Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&3" + mp.getName() + "&b's Title Deeds")); //TODO createHeader()
						Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, s);
					}
				}
			}
		}else{
			if(Mineopoly.plugin.getGame().isRunning()){
				Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
				if(p ==  null){
					player.sendMessage(new PlayerNotOnlineMessage(args[0]) + "");
				}else{
					if(Mineopoly.plugin.getGame().hasPlayer(p)){
						MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
						if(mp.ownedSections().size() == 0){
							Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThis player does not own any title deeds");
						}else{
							String s = "";
							ArrayList<OwnableSection> props = mp.ownedSections();
							Collections.sort(props);
							for(int i=0; i<props.size(); i++){
								if(i == props.size() - 1)
									s = s + props.get(i).getColorfulName();
								else s = s + props.get(i).getColorfulName() + "&8, ";
							}
							Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&3" + mp.getName() + "&b's Title Deeds"));
							Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, s);
						}
					}
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
			}
		}
	}

	

}
