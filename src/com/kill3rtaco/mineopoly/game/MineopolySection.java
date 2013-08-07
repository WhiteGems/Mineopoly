package com.kill3rtaco.mineopoly.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.sections.CardinalSection;
import com.kill3rtaco.mineopoly.game.sections.SectionType;
import com.kill3rtaco.mineopoly.game.sections.SpecialSquare;
import com.kill3rtaco.mineopoly.game.sections.squares.*;


/**
 * Represents any section on the board
 * @author Taco
 *
 */
public abstract class MineopolySection implements Comparable<MineopolySection> {

	private int id;
	protected String name;
	protected char color;
	private Location location = getLocation();
	private SectionType type;
	
	public MineopolySection(int id, String name, char color, SectionType type){
		this.name = name;
		this.color = color;
		this.id = id;
		this.type = type;
	}
	
	public abstract void getInfo(Player player);
	
	public String getColorfulName(){
		return "&" + color + name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public char getDisplayColor(){
		return this.color;
	}
	
	public String toString(){
		return getName();
	}
	
	public int getId(){
		return id;
	}
	
	public Location getLocation(){
		Location origin = Mineopoly.config.boardOrigin();
		location = origin;
		setPitchYaw();
		if(this instanceof CardinalSection){
			if(id > 0 && id < 10){
				location.setX(location.getX() - (13.5 + (10 * id)));
				location.setZ(location.getZ() - 8.5);
			}else if(id > 10 && id < 20){
				location.setX(location.getX() - 118.5);
				location.setZ(location.getZ() - (13.5 + (10 * (id - 10))));
			}else if(id > 20 && id < 30){
				location.setX(location.getX() - (13.5 + (10 * (30 - id))));
				location.setZ(location.getZ() - 118.5);
			}else if(id > 30){
				location.setX(location.getX() - 8.5);
				location.setZ(location.getZ() - (13.5 + (10 * (40 - id))));
			}
			
		}else if(this instanceof SpecialSquare){
			if(this instanceof GoSquare){
				location.setX(location.getX() - 9.5);
				location.setZ(location.getZ() - 8.5);
			}else if(this instanceof FreeParkingSquare){
				location.setX(location.getX() - 119.5);
				location.setZ(location.getZ() - 118.5);
			}else if(this instanceof JailSquare){
				location.setX(location.getX() - 9.5);
				location.setZ(location.getZ() - 118.5);
			}
		}
		return location;
	}
	
	//get a location relative to this section
	public Location getLocationRelative(int x, int y, int z){
		Location location = getLocation();
		if(this instanceof CardinalSection){
			CardinalSection c = (CardinalSection) this;
			int side = c.getSide();
			if(side == 0){
				location.setX(location.getX() + x);
				location.setY(location.getY() + y);
				location.setZ(location.getZ() - z);
			}else if(side == 1){
				location.setX(location.getX() + z);
				location.setY(location.getY() + y);
				location.setZ(location.getZ() + x);
			}else if(side == 2){
				location.setX(location.getX() - x);
				location.setY(location.getY() + y);
				location.setZ(location.getZ() + z);
			}else{
				location.setX(location.getX() - z);
				location.setY(location.getY() + y);
				location.setZ(location.getZ() - x);
			}
		}else{
			location.setX(location.getX() + x);
			location.setY(location.getY() + y);
			location.setZ(location.getZ() + z);
		}
		return location;
	}
	
	private void setPitchYaw(){
		location.setPitch(0);
		if(this instanceof CardinalSection){
			CardinalSection section = (CardinalSection) this;
			location.setYaw((section.getSide() + 1) * 90);
		}else if(this instanceof SpecialSquare){
			SpecialSquare square = (SpecialSquare) this;
			if(square instanceof GoSquare){
				location.setYaw(90);
			}else if(square instanceof JailSquare){
				location.setYaw(180);
			}else if(square instanceof FreeParkingSquare){
				location.setYaw(270);
			}else if(square instanceof GoToJailSquare){
				location.setYaw(0);
			}
		}
	}
	
	public SectionType getType(){
		return type;
	}
	
	@Override
	public int compareTo(MineopolySection section){
		return id - section.getId();
	}
}
