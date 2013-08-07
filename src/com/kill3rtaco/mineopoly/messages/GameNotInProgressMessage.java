package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class GameNotInProgressMessage extends TacoMessage {

	public GameNotInProgressMessage(){
		this.message = "&cThere isn't a Mineopoly game in progress right now";
	}
	
}
