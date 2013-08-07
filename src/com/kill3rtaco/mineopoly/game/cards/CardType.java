package com.kill3rtaco.mineopoly.game.cards;

public enum CardType {

	CHANCE("&6Chance"),
	COMMUNITY_CHEST("&eCommunity Chest");
	
	private String name;
	
	private CardType(String name){
		this.name = name;
	}
	
	public String toString(){
		return name();
	}
	
	public String getName(){
		return this.name;
	}

}
