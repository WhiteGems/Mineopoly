package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class NotPlayingGameMessage extends TacoMessage {

	public NotPlayingGameMessage(){
		this.message = "你没在玩Mineopoly";
	}
	
	public NotPlayingGameMessage(String name){
		this.message = "&c玩家&6" + name + " &c没在玩Mineopoly";
	}
	
}
