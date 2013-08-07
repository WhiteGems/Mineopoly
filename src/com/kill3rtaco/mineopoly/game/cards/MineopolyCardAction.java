package com.kill3rtaco.mineopoly.game.cards;

import com.kill3rtaco.mineopoly.game.MineopolyPlayer;

public abstract class MineopolyCardAction {

	protected String name, paramTypes;
	
	public MineopolyCardAction(String name){
		this(name, "");
	}
	
	public MineopolyCardAction(String name, String paramTypes) {
		this.name = name;
		this.paramTypes = paramTypes;
	}
	
	public abstract CardResult doAction(MineopolyPlayer player, Object... params);
	
	public String getName(){
		return name;
	}
	
	public int getRequiredParamLength(){
		return paramTypes.length();
	}
	
	public String getParamTypes(){
		return paramTypes;
	}

}
