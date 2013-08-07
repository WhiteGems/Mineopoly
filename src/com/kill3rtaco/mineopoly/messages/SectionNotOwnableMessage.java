package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.mineopoly.game.MineopolySection;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class SectionNotOwnableMessage extends TacoMessage {

	public SectionNotOwnableMessage(MineopolySection section, String action){
		this.message = "&cYou cannot " + action + " the space &6" + section.getColorfulName();
	}
	
	
}
