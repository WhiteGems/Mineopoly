package com.kill3rtaco.mineopoly.game.cards.communitychest;

import com.kill3rtaco.mineopoly.game.cards.CardType;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCard;

public class CommunityChestCard extends MineopolyCard {

	protected String description;
	
	public CommunityChestCard(String name, String description, String action){
		super(name, CardType.COMMUNITY_CHEST, description, action);
	}
	
}
