package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class MustRollFirstMessage extends TacoMessage {

	public MustRollFirstMessage(){
		this.message = "你必须先掷骰子";
	}
	
	public MustRollFirstMessage(String message){
		this.message = "&c在" + message + "前你必须先掷骰子";
	}
	
}
