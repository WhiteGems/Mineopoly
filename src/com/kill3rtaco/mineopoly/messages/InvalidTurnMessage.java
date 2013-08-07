package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class InvalidTurnMessage extends TacoMessage {

	public InvalidTurnMessage(){
		this.message = "&c并非你的回合";
	}
	
}
