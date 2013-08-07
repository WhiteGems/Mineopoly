package com.kill3rtaco.mineopoly.game.cards.communitychest;

import com.kill3rtaco.mineopoly.game.MineopolyPlayer;

public class CommunityChestJailCard extends CommunityChestCard {

	public CommunityChestJailCard() {
		super("GetOutOfJailFree", "Get out of Jail Free card. You may use this card to get out of jail", "");
	}

	@Override
	public void action(MineopolyPlayer player) {
		player.giveCommunityChestJailCard();
	}

}
