package com.kill3rtaco.mineopoly.game.chat;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.tacoapi.api.TacoMessage;

public class MineopolyChannelListener{
	
	protected Player player;
	
	public MineopolyChannelListener(Player player) {
		this.player = player;
	}

	public void sendMessage(String message){
		Player player = Mineopoly.plugin.getServer().getPlayer(getName());
		if(player != null) Mineopoly.plugin.chat.sendPlayerMessage(player, message);
	}
	
	public void sendMessage(TacoMessage message){
		Player player = Mineopoly.plugin.getServer().getPlayer(getName());
		if(player != null) Mineopoly.plugin.chat.sendPlayerMessage(player, message.getMessage());
	}
	
	public String getName(){
		return player.getName();
	}
	
	public Player getPlayer(){
		return player;
	}
	
}
