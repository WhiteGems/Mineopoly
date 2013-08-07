package com.kill3rtaco.mineopoly.game;

public enum MineopolyColor {

	PURPLE(new int[]{1, 3}, '5', 10),
	LIGHT_BLUE(new int[]{6, 8, 9}, '3', 9),
	MAGENTA(new int[]{11, 13, 14}, 'd', 2),
	ORANGE(new int[]{16, 18, 19}, '6', 1),
	RED(new int[]{21, 23, 24}, '4', 14),
	YELLOW(new int[]{26, 28, 29}, 'e', 4),
	GREEN(new int[]{31, 32, 34}, '2', 13),
	BLUE(new int[]{37, 39}, '1', 11);
	
	private int[] ids;
	private char color;
	private int wool;
	
	private MineopolyColor(int[] ids, char color, int wool){
		this.ids = ids;
		this.color = color;
		this.wool = wool;
	}
	
	public int[] getIds(){
		return this.ids;
	}
	
	public String getName(){
		return "&" + this.color + name();
	}
	
	public String toString(){
		return "&" + color;
	}
	
	public static MineopolyColor getColor(String name){
		for(MineopolyColor mc : MineopolyColor.values()){
			if(name.equalsIgnoreCase(mc.name())){
				return mc;
			}
		}
		return null;
	}

	public char getChar() {
		return color;
	}
	
	public int getWoolColor(){
		return wool;
	}
	
}
