package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class NotPlayingGameMessage extends TacoMessage {

	public NotPlayingGameMessage(){
		this.message = "You are not playing Mineopoly";
	}
	
	public NotPlayingGameMessage(String name){
		this.message = "&cPlayer &6" + name + " &cis not playing Mineopoly";
	}
	
}
