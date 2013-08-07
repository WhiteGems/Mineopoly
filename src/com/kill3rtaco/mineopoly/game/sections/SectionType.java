package com.kill3rtaco.mineopoly.game.sections;

public enum SectionType {

	CHANCE,
	COMMUNITY_CHEST,
	PROPERTY,
	SQUARE,
	RAILROAD,
	TAX,
	UTILITY;
	
	
	public String toString(){
		return this.name();
	}
}
