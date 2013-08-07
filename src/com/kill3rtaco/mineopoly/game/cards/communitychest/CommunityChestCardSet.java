package com.kill3rtaco.mineopoly.game.cards.communitychest;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCard;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCardSet;


public class CommunityChestCardSet extends MineopolyCardSet{

	@Override
	protected void initCards() {
		Mineopoly.plugin.chat.out("[Game] [Board] [CommunityChestCards] Loading Community Chest cards...");
		cards = new ArrayList<MineopolyCard>();
		File container = new File(Mineopoly.plugin.getDataFolder() + "/cards/communitychest/");
		int count = 0;
		if(container.isDirectory()){
			for(File f : container.listFiles()){
				if(f.isDirectory()) continue;
				YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
				String name = f.getName().substring(0, f.getName().lastIndexOf('.'));
				if(c != null){
					if(c.contains("card.description") && c.contains("card.action")){
						if(!c.getString("card.description").equalsIgnoreCase("") && !c.getString("card.action").equalsIgnoreCase("")){
							CommunityChestCard card = new CommunityChestCard(name, c.getString("card.description"), c.getString("card.action"));
							if(card.isValid()){
								addCard(card);
								count++;
							}else{
								Mineopoly.plugin.chat.out("[CommunityChestCards] Card is invalid: " + f.getName() + ", skipping...");
							}
						}else{
							Mineopoly.plugin.chat.out("[CommunityChestCards] Card is invalid: " + f.getName() + ", skipping...");
						}
					}else{
						Mineopoly.plugin.chat.out("[CommunityChestCards] Card is invalid: " + f.getName() + ", skipping...");
					}
				}
			}
		}
		Mineopoly.plugin.chat.out("[Game] [Board] [CommunityChestCards] " + count + " cards loaded!");
	}

	@Override
	protected void addJailCard() {
		addCard(new CommunityChestJailCard());
	}

}
