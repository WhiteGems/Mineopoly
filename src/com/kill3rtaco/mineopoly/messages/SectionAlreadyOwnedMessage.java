package com.kill3rtaco.mineopoly.messages;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;

import com.kill3rtaco.tacoapi.api.TacoMessage;

public class SectionAlreadyOwnedMessage extends TacoMessage {

	public SectionAlreadyOwnedMessage(MineopolySection section){
		OwnableSection space = (OwnableSection) section;
		MineopolyPlayer owner = space.getOwner();
		if(owner.getName().equalsIgnoreCase(Mineopoly.plugin.getGame().getPlayerWithCurrentTurn().getName()))
			this.message = "&c你已经拥有了这块地:" + section.getColorfulName();
		else
			this.message = "&c这块地:" + section.getColorfulName() + "&c已经属于&6" + space.getOwner().getName();
	}
	
}
