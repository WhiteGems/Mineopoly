package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class CannotPerformActionMessage extends TacoMessage {
	
	public CannotPerformActionMessage(){
		this.message = "&c你现在不能那样做";
	}
	
	public CannotPerformActionMessage(String action){
		this.message = "&c你不能" + action;
	}

}
