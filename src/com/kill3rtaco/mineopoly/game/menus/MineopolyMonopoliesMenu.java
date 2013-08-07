package com.kill3rtaco.mineopoly.game.menus;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyColor;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;

public class MineopolyMonopoliesMenu extends MineopolyMenu {

	public MineopolyMonopoliesMenu(MineopolyPlayer player){
		super(player);
	}
	
	@Override
	protected Inventory createInventory() {
		Inventory inv = Mineopoly.plugin.getServer().createInventory(this, 9, player.getName() + "'s monopolies");
		
		int count = 0;
		for(MineopolyColor c : MineopolyColor.values()){
			ItemStack color = makeItem(Material.WOOL, c.getWoolColor(), c.name());
			if(player.hasMonopoly(c)){
				inv.setItem(count, color);
			}
			count++;
		}
		
		return inv;
	}

	@Override
	public void action(MineopolyPlayer player, int cell) {
		//nothing to do
	}

}
