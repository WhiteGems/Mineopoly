package com.kill3rtaco.mineopoly.game.sections;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;

public abstract class OwnableSection extends MineopolySection {

	protected MineopolyPlayer owner;
	protected boolean owned, mortgaged;
	protected int price;
	
	public OwnableSection(int id, String pathToName, char color, int price, SectionType type) {
		super(id, Mineopoly.names.getString(pathToName), color, type);
		this.price = price;
	}

	public boolean isOwned(){
		return owned;
	}
	
	public boolean isMortgaged(){
		return mortgaged;
	}
	
	public void setMortgaged(boolean mortgage){
		this.mortgaged = mortgage;
	}
	
	public MineopolyPlayer getOwner(){
		return owner;
	}
	
	public void setOwner(MineopolyPlayer player){
		this.owner = player;
		this.owned = true;
		player.addSection(this);
	}
	
	public abstract int getRent();
	
	public int getPrice(){
		return price;
	}
	
	public void reset(){
		owned = false;
		mortgaged = false;
	}
	
}
