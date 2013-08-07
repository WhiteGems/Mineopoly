package com.kill3rtaco.mineopoly.game.sections;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyColor;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.obj.WorldEditObject;


/**
 * Represents a property space on the board. Implements {@link taco.mineopoly.sections.Ownable Ownable}
 * @author Taco
 *
 */
public class Property extends OwnableSection implements  CardinalSection{

	protected int side;
	private int housePrice = (getSide() + 1) * 50;
	private int hotelPrice = housePrice * 5;
	private int houses;
	private int r, h, h2, h3, h4, hotel;
	protected boolean hasHotel = false;
	protected MineopolyColor mColor;

	public Property(int id, String pathToName, MineopolyColor color, int side, int price, int[] rent) {
		super(id, pathToName, color.getChar(), price, SectionType.PROPERTY);
		this.mColor = color;
		this.side = side;
		setRent(rent[0], rent[1], rent[2], rent[3], rent[4], rent[5]);
	}
	
	public int getRent(){
		if(isOwned()){
			return getRent(houses, hasHotel);
		}else return 0;
	}
	
	public int getRent(int houses){
		return getRent(houses, false);
	}
	
	public int getRent(int houses, boolean hotel){
		if(hotel) return this.hotel;
		switch(houses){
			case 0:
				return r;
			case 1:
				return h;
			case 2:
				return h2;
			case 3:
				return h3;
			default:
				return h4;
		}
	}
	
	public void reset(){
		this.mortgaged = false;
		this.houses = 0;
		this.owned = false;
		this.hasHotel = false;
	}
	
	public MineopolyColor getColor(){
		return mColor;
	}
	
	protected void setRent(int r, int h, int h2, int h3, int h4, int hotel){
		this.r = r;
		this.h = h;
		this.h2 = h2;
		this.h3 = h3;
		this.h4 = h4;
		this.hotel = hotel;
	}
	
	public void getInfo(Player player){
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&6---[" + getColorfulName() + "&b(&3" + getId() + "&b)&6]---");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, mColor + "Owner&7:&b " + (isOwned() ? owner.getName() : "none"));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, mColor + (isOwned() ? "Rent&7: &2" + getRent() : "Price&7: &2" + getPrice()));
		if(getHouses() > 0 && !hasHotel()) Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, mColor + "Houses&7:&b " + getHouses());
		if(hasHotel) Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, mColor + "Hotel&7:&b " + hasHotel());
	}
	
	public boolean hasHotel(){
		return this.hasHotel;
	}
	
	public int getHouses(){
		return this.houses;
	}
	
	public void setMortgaged(boolean mortgaged){
		this.mortgaged = mortgaged;
		if(mortgaged){
			int refund = 0;
			for(Property p : owner.getPropertiesInMonopoly(mColor)){
				if(!p.getName().equalsIgnoreCase(getName())){
					if(p.getHouses() == 0 && !p.hasHotel){
						break;
					}else{
						refund += (p.getHousePrice() * p.getHouses()) / 2;
						refund += ((p.hasHotel() ? 1 : 0) * p.getHotelPrice()) / 2;
						p.removeAllHouses();
						p.removeHotel();
					}
				}
			}
			owner.addMoney(refund);
		}
	}
	
	public int getHousePrice(){
		return this.housePrice;
	}
	
	public int getHotelPrice(){
		return this.hotelPrice;
	}
	
	public boolean canAddHouse(){
		if(owner == null || houses >= 4) return false;
		if(Mineopoly.houseRules.improvementRequiresMonopoly()){
			if(!owner.hasMonopoly(mColor)) return false;
			ArrayList<Property> props = owner.getPropertiesInMonopoly(mColor);
			if(Mineopoly.houseRules.houseSycronization()){
				int[] allHouses = new int[props.size()];
				int index = -1;
				for(int i=0; i<props.size(); i++){
					allHouses[i] = props.get(i).getHouses();
					if(props.get(i).getId() == getId()) index = i;
				}
				int value = allHouses[0];
				boolean same = false;
				for(int i : allHouses){
					same = i == value;
				}
				if(same){
					return true;
				}else{
					allHouses[index] += 1;
					for(int i : allHouses){
						same = i == value;
					}
					return same;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	public boolean canAddHotel(){
		if(owner == null || houses < 4) return false;
		if(Mineopoly.houseRules.improvementRequiresMonopoly()){
			if(!owner.hasMonopoly(mColor)) return false;
			if(Mineopoly.houseRules.houseSycronization()){
				for(Property p : owner.getPropertiesInMonopoly(mColor)){
					if(!p.hasHotel() && p.getHouses() < 4){
						return false;
					}
				}
				return true;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	public void addHotel(){
		addHotel(true);
	}
	
	public void addHotel(boolean takeMoney){
		if(takeMoney) owner.takeMoney(hotelPrice);
		this.hasHotel = true;
		this.houses = 0;
		updateVisibleImprovements();
	}
	
	public void addHouse(){
		addHouse(true);
	}
	
	public void addHouse(boolean takeMoney){
		if(takeMoney) owner.takeMoney(housePrice);
		this.houses += 1;
		updateVisibleImprovements();
	}
	
	public void setHouses(int houses){
		this.houses = houses;
		updateVisibleImprovements();
	}
	
	public void removeHouse(){
		houses--;
		owner.addMoney(housePrice / 2);
		updateVisibleImprovements();
	}
	
	public void removeAllHouses(){
		owner.addMoney(houses / 2);
		houses = 0;
		updateVisibleImprovements();
	}
	
	public void removeHotel(){
		hasHotel = false;
		houses = 4;
		owner.addMoney(hotelPrice / 2);
		updateVisibleImprovements();
	}

	@Override
	public int getSide() {
		return side;
	}
	
	public void clearImprovements(){
		hasHotel = false;
		houses = 0;
		updateVisibleImprovements();
	}
	
	public void updateVisibleImprovements(){
		//just to be safe
		if(!TacoAPI.isWorldEditAPIOnline()) return;
		WorldEditObject we = TacoAPI.getWorldEditAPI();
		Location corner, corner2;
		World world = getLocation().getWorld();
		corner = getLocationRelative(-4, 0, 6);
		corner2 = getLocationRelative(4, 3, 10);
		we.setAreaWithBlock(world.getName(), corner, corner2, "0");
//		we.setAreaWithBlock(world.getName(), corner, corner, "black");
//		we.setAreaWithBlock(world.getName(), corner2, corner2, "black");
		if(hasHotel){
			Location hc = getLocationRelative(-1, 0, 8);
			Location hc2 = getLocationRelative(1, 3, 9);
			we.setAreaWithBlock(world.getName(), hc, hc2, "red");
		}else{
			if(houses <= 0) return;
			for(int i=0; i<houses; i++){
				if(i > 4) break;
				int delta = i * 2;
				Location house = getLocationRelative(-3 + delta, 0, 9);
				we.setAreaWithBlock(world.getName(), house, house, "green");
			}
		}
	}
	
}
