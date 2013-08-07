package com.kill3rtaco.mineopoly.game.sections;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.cards.chance.ChanceCard;

public class ChanceSection extends MineopolySection implements CardinalSection, ActionProvoker, CardSetSection {

	private int side;
	private ChanceCard lastCard;
	
	public ChanceSection(int id, int side) {
		super(id, "Chance", '6', SectionType.CHANCE);
		this.side = side;
	}

	@Override
	public void getInfo(Player player) {
		player.sendMessage("&6---[" + getColorfulName() + "&b(&3" + getId() + "&b)&6]---");
		player.sendMessage("&3Landing on this space will draw a card from the &6Chance &3card pile");
	}

	@Override
	public int getSide() {
		return side;
	}

	@Override
	public void provokeAction(MineopolyPlayer player) {
		ChanceCard card = (ChanceCard) Mineopoly.plugin.getGame().getBoard().getChanceCards().drawCard(player);
		lastCard = card;
//		if(card.getResult() == CardResult.MONEY_RELATED){
//			player.setCanEndTurnAutomatically(true);
//		}
	}
	
	public ChanceCard getLastCard(){
		return lastCard;
	}

}
