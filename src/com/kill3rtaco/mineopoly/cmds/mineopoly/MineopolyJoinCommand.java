package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;

import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyJoinCommand extends TacoCommand {

	public MineopolyJoinCommand() {
		super("join", new String[]{"j"}, "", "Join the game queue", MineopolyConstants.P_JOIN_GAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getQueue().playerIsInQueue(player)){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou are already queued to join the next Mineopoly game");
		}else if(Mineopoly.plugin.getGame().isRunning() && Mineopoly.plugin.getGame().hasPlayer(player)){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou are already playing Mineopoly");
		}else{
			if(Mineopoly.plugin.isBanned(player.getName())){
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou are banned, you cannot play");
			}else{
				Mineopoly.plugin.getQueue().addPlayer(player);
				Mineopoly.plugin.chat.sendPlayerMessage(player, "You've been added to the game queue, please wait until the next game is over or until more players join");
				Mineopoly.plugin.chat.sendGlobalMessageNoHeader("&b" + player.getName() + " &3just joined the &9Mineopoly &3game queue! &b/mineopoly join");
			}
		}
		
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
