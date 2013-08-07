package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class CannotPerformActionMessage extends TacoMessage {
	
	public CannotPerformActionMessage(){
		this.message = "&cYou cannot do that right now";
	}
	
	public CannotPerformActionMessage(String action){
		this.message = "&cYou cannot " + action;
	}

}
