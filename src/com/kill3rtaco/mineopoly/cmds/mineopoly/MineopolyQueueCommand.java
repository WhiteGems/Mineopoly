package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.api.messages.TooManyArgumentsMessage;

public class MineopolyQueueCommand extends TacoCommand {

	public MineopolyQueueCommand() {
		super("queue", new String[]{"q"}, "", "View players in the game queue", MineopolyConstants.P_VIEW_PLAYER_QUEUE);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			if(Mineopoly.plugin.getQueue().getSize() == 0){
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThere is no one in the game queue");
			}else{
				Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&7Mineopoly Queue"));
				String players = "";
				for(Player p : Mineopoly.plugin.getQueue()){
					if(Mineopoly.plugin.getQueue().getIndexFromPlayer(p) == Mineopoly.plugin.getQueue().getSize() - 1){
						players = players + "&7" + p.getName();
					}else{
						players = players + "&7" + p.getName() + "&8, ";
					}
				}
				Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, players);
			}
		}else{
			player.sendMessage(new TooManyArgumentsMessage("/mineopoly queue") + "");
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}
	
}
