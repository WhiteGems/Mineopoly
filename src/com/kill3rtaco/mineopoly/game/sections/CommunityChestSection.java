package com.kill3rtaco.mineopoly.game.sections;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.cards.communitychest.CommunityChestCard;


public class CommunityChestSection extends MineopolySection implements ActionProvoker, CardinalSection, CardSetSection {

	private int side;
	private CommunityChestCard lastCard;
	
	public CommunityChestSection(int id, int side) {
		super(id, "Community Chest", 'e', SectionType.COMMUNITY_CHEST);
		this.side = side;
	}

	public void getInfo(Player player){
		player.sendMessage("&6---[" + getColorfulName() + "&b(&3" + getId() + "&b)&6]---");
		player.sendMessage("&3Landing on this space will draw a card from the &eCommunity Chest &3card pile");
	}
	
	public void provokeAction(MineopolyPlayer player){
		CommunityChestCard card = (CommunityChestCard) Mineopoly.plugin.getGame().getBoard().getCommunityChestCards().drawCard(player);
		lastCard = card;
//		if(card.getResult() == CardResult.MONEY_RELATED){
//			player.setCanEndTurnAutomatically(true);
//		}
	}

	@Override
	public int getSide() {
		return side;
	}
	
	public CommunityChestCard getLastCard(){
		return lastCard;
	}

}
