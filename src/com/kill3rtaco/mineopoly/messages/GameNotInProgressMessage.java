package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class GameNotInProgressMessage extends TacoMessage {

	public GameNotInProgressMessage(){
		this.message = "&c现在并没有进行强手棋游戏";
	}
	
}
