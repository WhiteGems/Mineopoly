package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class InsufficientFundsMessage extends TacoMessage{

	public InsufficientFundsMessage(){
		this.message = "&cYou do not have enough money";
	}
	
}
