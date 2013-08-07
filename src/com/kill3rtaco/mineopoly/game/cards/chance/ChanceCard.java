package com.kill3rtaco.mineopoly.game.cards.chance;

import com.kill3rtaco.mineopoly.game.cards.CardType;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCard;


public class ChanceCard extends MineopolyCard {
	
	public ChanceCard(String name, String description, String action) {
		super(name, CardType.CHANCE, description, action);
	}
}
