package com.kill3rtaco.mineopoly.game.sections;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;


/**
 * Represents a railroad space on the board.  Implements {@link taco.mineopoly.sections.Ownable Ownable}
 * @author Taco
 *
 */
public class Railroad extends OwnableSection implements CardinalSection{

	protected int side;

	
	public Railroad(String pathToName, int side) {
		super((side * 10) + 5, pathToName, '8', 200, SectionType.RAILROAD);
		this.side = side;
	}
	
	public String getName(){
		return super.getName() + " Railroad";
	}
	
	public String getColorfulName(){
		return super.getColorfulName() + " Railroad";
	}

	public void getInfo(Player player){
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&6---[" + getColorfulName() + "&b(&3" + getId() + "&b)]---");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&" + color + "Owner&7: &b" + (isOwned() ? owner.getName() : "none"));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&" + color + (isOwned() ? "Rent&7: " + getRent() : "Price&7: &2" + price));
	}
	
	public int getRent(){
		if(isOwned()){
			return getRent(owner.ownedRailRoads() - 1);
			
		}else return 0;
	}
	
	public int getRent(int otherOwned){
		switch(otherOwned){
		case 1:
			return 50;
		case 2:
			return 75;
		case 3:
			return 100;
		default:
			return 25;
		}
	}

	@Override
	public int getSide() {
		return side;
	}


}
