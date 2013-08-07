package com.kill3rtaco.mineopoly.saves;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.InvalidConfigurationException;

import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolyPot;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.tacoapi.api.TacoConfig;

public class MineopolySaveGame extends TacoConfig {

	private String name;
	
	public MineopolySaveGame(File file) {
		super(file);
		String name = file.getName();
		this.name = name.substring(0, name.lastIndexOf('.'));
	}

	@Override
	protected void setDefaults() {
		//no defaults
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getFilename(){
		return this.name = ".yml";
	}
	
	public void setData(MineopolyGame game){
		try {
			config.loadFromString("");
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			//this should never happen
		}
		
		String turnOrder = game.getTurnOrder().trim();
		String currentTurn = game.getPlayerWithCurrentTurn().getName();
		long timeRunning = game.getTimeRunning();
		
		MineopolyPot pot = game.getBoard().getPot();
		int potAmount = pot.getMoney();
		boolean potHasChanceCard = pot.hasChanceJailCard();
		boolean potHasCCCard = pot.hasCommunityChestJailCard();
		
		setString("game.turn-order", turnOrder);
		setString("game.current-turn", currentTurn);
		set("game.time-running", timeRunning);
		
		setInt("pot.amount", potAmount);
		setBoolean("pot.card_chance", potHasChanceCard);
		setBoolean("pot.card_community-chest", potHasCCCard);
		
		for(MineopolyPlayer mp : game.getBoard().getPlayers()){
			String name = mp.getName();
			String root = "players." + name + ".";
			
			int balance = mp.getBalance();
			ArrayList<Integer> properties = new ArrayList<Integer>();
			HashMap<Integer, Integer> houses = new HashMap<Integer, Integer>();
			HashMap<Integer, Boolean> hotel = new HashMap<Integer, Boolean>();
			for(MineopolySection s : mp.ownedSections()){
				int id = s.getId();
				properties.add(id);
				if(s instanceof Property){
					Property p = (Property) s;
					houses.put(id, p.getHouses());
					hotel.put(id, p.hasHotel());
				}
			}
			boolean hasChanceCard = mp.hasChanceJailCard();
			boolean hasCCCard = mp.hasCommunityChestJailCard();
			int sectionId = mp.getCurrentSection().getId();
			boolean jailed = mp.isJailed();
			int rolls = mp.getTotalRolls();
			int passes = mp.getGoPasses();
			
			setInt(root + "balance", balance);
			set(root + "properties.owned", properties);
			for(int i : properties){
				Integer h = houses.get(i);
				Boolean h2 = hotel.get(i);
				
				if(h == null){
					setInt(root + "properties." + i + ".houses", 0);
				}else{
					setInt(root + "properties." + i + ".houses", h);
				}
				
				if(h2 == null){
					setBoolean(root + "properties." + i + ".hotel", false);
				}else{
					setBoolean(root + "properties." + i + ".hotel", h2);
				}
			}
			setBoolean(root + "card_chance", hasChanceCard);
			setBoolean(root + "card_community-chest", hasCCCard);
			setInt(root + "section", sectionId);
			setBoolean(root + "jailed", jailed);
			setInt(root + "rolls", rolls);
			setInt(root + "go-passes", passes);
		}
	}
	
	public long getTimeRunning(){
		return config.getLong("game.time-running");
	}

}
