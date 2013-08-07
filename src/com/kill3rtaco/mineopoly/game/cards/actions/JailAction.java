package com.kill3rtaco.mineopoly.game.cards.actions;

import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.cards.CardResult;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCardAction;

public class JailAction extends MineopolyCardAction {

	public JailAction() {
		super("jail");
	}

	@Override
	public CardResult doAction(MineopolyPlayer player, Object... params) {
		player.setJailed(true, true);
		return CardResult.MOVE_RELATED;
	}

}
