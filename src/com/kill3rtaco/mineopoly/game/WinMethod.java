package com.kill3rtaco.mineopoly.game;

public enum WinMethod {

	MONEY,
	PROPERTY_AMOUNT,
	PROPERTY_VALUE;
	
	public static WinMethod getWinMethod(String name){
		for(WinMethod m : WinMethod.values()){
			if(m.name().equalsIgnoreCase(name)){
				return m;
			}
		}
		return WinMethod.MONEY; //default
	}
	
}
