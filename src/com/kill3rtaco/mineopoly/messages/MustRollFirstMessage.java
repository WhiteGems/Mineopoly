package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class MustRollFirstMessage extends TacoMessage {

	public MustRollFirstMessage(){
		this.message = "You must roll the dice first";
	}
	
	public MustRollFirstMessage(String message){
		this.message = "&cYou must roll the dice before " + message;
	}
	
}
