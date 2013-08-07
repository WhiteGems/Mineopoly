package com.kill3rtaco.mineopoly.game.sections.squares;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.sections.SpecialSquare;


public class FreeParkingSquare extends SpecialSquare {
	
	public FreeParkingSquare() {
		super(20, "Free Parking", '4');
	}

	@Override
	public void provokeAction(MineopolyPlayer player) {
		Mineopoly.plugin.getGame().getBoard().getPot().give(player);
		player.setCanEndTurnAutomatically(true);
	}

	@Override
	public void getInfo(Player player) {
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&6---[" + getColorfulName() + "&b(&3" + getId() + "&b)&6]---");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&bLand on this square and you shall recieve all money in the pot");
	}

}
