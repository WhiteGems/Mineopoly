package com.kill3rtaco.mineopoly.game.sections;

import java.util.Random;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;


/**
 * Represents a utility space on the board. Implements {@link taco.mineopoly.sections.Ownable Ownable}
 * @author Taco
 *
 */
public class Utility extends OwnableSection implements CardinalSection{

	private int side;
	
	public Utility(int id, String pathToName, char color, int side) {
		super(id, pathToName, color, 150, SectionType.UTILITY);
		this.side = side;
	}

	@Override
	public void getInfo(Player player){
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&6---[" + getColorfulName() + "&b(&3" + getId() + "&b)&6]---");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&" + color + "Owned&7: &b" + (isOwned() ? owner.getName() : "none"));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&" + color + (isOwned() ? "Rent&7: " + "&bLand on it to find out" : "Price&7: " + price));
	}
	
	public int getRent(){
		if(this.isOwned()){
			return getRent(owner.ownedUtilities() - 1);
		}else{
			return 0;
		}
	}
	
	public int getRent(int otherOwned){
		Random random = new Random();
		switch(otherOwned){
			case 1:
				return 10 * ((random.nextInt(6) + 1) + (random.nextInt(6) + 1));
			default:
				return 4 * ((random.nextInt(6) + 1) + (random.nextInt(6) + 1));
		}
	}
	
	public String getName(){
		return super.getName() + " Company";
	}
	
	public String getColorfulName(){
		return super.getColorfulName() + " Company";
	}

	@Override
	public int getSide() {
		return side;
	}
}
