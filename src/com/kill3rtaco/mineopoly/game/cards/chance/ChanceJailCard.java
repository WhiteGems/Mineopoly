package com.kill3rtaco.mineopoly.game.cards.chance;

import com.kill3rtaco.mineopoly.game.MineopolyPlayer;

public class ChanceJailCard extends ChanceCard {

	public ChanceJailCard() {
		super("GetOutOfJailFree", "Get out of Jail Free card. You may use this card to get out of jail", "");
	}
	
	public void action(MineopolyPlayer player){
		player.giveChanceJailCard();
	}

}
