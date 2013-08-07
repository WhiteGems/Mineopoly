package com.kill3rtaco.mineopoly.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.menus.MineopolyMenu;
import com.kill3rtaco.mineopoly.game.sections.CardinalSection;
import com.kill3rtaco.mineopoly.game.sections.SpecialSquare;
import com.kill3rtaco.mineopoly.game.sections.squares.JailSquare;
import com.kill3rtaco.mineopoly.game.tasks.managers.PlayerSessionManager;
import com.kill3rtaco.mineopoly.messages.OutsideSectionMessage;

public class MineopolyListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(PlayerSessionManager.isInList(player.getName())){
			PlayerSessionManager.remove(player.getName());
			if(Mineopoly.plugin.getGame().isRunning()){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player.getName());
				if(mp != null) mp.setPlayer(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				PlayerSessionManager.addToList(mp.getName());
			}else{
				if(Mineopoly.plugin.getQueue().playerIsInQueue(player)){
					Mineopoly.plugin.getQueue().removePlayer(player);
				}
			}
		}else{
			if(Mineopoly.plugin.getQueue().playerIsInQueue(player)){
				Mineopoly.plugin.getQueue().removePlayer(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(event.getTo().getBlockX() == event.getFrom().getBlockX()
				&& event.getTo().getBlockZ() == event.getFrom().getBlockZ()) return;
		Player player = event.getPlayer();
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				MineopolySection section = mp.getCurrentSection();
				Location sLocation = section.getLocation();
				Location nLocation = event.getTo();
				if(section instanceof SpecialSquare){
					if(section instanceof JailSquare){
						JailSquare js = (JailSquare) section;
						if(mp.isJailed()){
							if((nLocation.getX() >= js.getJailCellLocation().getX() + 6 || nLocation.getX() <= js.getJailCellLocation().getX() - 5) ||
									(nLocation.getY() >= js.getJailCellLocation().getY() + 4 || nLocation.getY() <= js.getJailCellLocation().getY() - 1) ||
									(nLocation.getZ() >= js.getJailCellLocation().getZ() + 5.5 || nLocation.getZ() <= js.getJailCellLocation().getZ() - 6)){
//								player.teleport(js.getJailCellLocation());
								player.teleport(event.getFrom());
								mp.sendMessage("&cYou are &1jailed&c, do not try to escape. You can use a &6Get out of Jail Free &ccard if you need to." +
										" (&6/jail card&c)");
							}
						}else{
							if((nLocation.getX() >= js.getJustVisitingLocation().getX() + 10.5 || nLocation.getX() <= js.getJustVisitingLocation().getX() - 7.5) ||
									(nLocation.getZ() >= js.getJustVisitingLocation().getZ() + 2.5 || nLocation.getZ() <= js.getJustVisitingLocation().getZ() - 15.5)){
//								player.teleport(js.getJustVisitingLocation());
								player.teleport(event.getFrom());
								mp.sendMessage(new OutsideSectionMessage());
							}
						}
					}else{
						if((nLocation.getX() >= sLocation.getX() + 9.5 || nLocation.getX() <= sLocation.getX() - 8.5) ||
								(nLocation.getZ() >= sLocation.getZ() + 8.5 || nLocation.getZ() <= sLocation.getZ() - 9.5)){
//							player.teleport(sLocation);
							player.teleport(event.getFrom());
							mp.sendMessage(new OutsideSectionMessage());
						}
					}
				}else{
					CardinalSection cs = (CardinalSection) section;
					if(cs.getSide() == 0){
						if((nLocation.getX() >= sLocation.getX() + 4.5 || nLocation.getX() <= sLocation.getX() - 4.5) ||
								(nLocation.getZ() >= sLocation.getZ() + 8.5 || nLocation.getZ() <= sLocation.getZ() - 9.5)){
//							player.teleport(sLocation);
							player.teleport(event.getFrom());
							mp.sendMessage(new OutsideSectionMessage());
						}
					}else if(cs.getSide() == 1){
						if((nLocation.getX() >= sLocation.getX() + 9.5 || nLocation.getX() <= sLocation.getX() - 8.5) ||
								(nLocation.getZ() >= sLocation.getZ() + 4.5 || nLocation.getZ() <= sLocation.getZ() - 4.5)){
//							player.teleport(sLocation);
							player.teleport(event.getFrom());
							mp.sendMessage(new OutsideSectionMessage());
						}
					}else if(cs.getSide() == 2){
						if((nLocation.getX() >= sLocation.getX() + 4.5 || nLocation.getX() <= sLocation.getX() - 4.5) ||
								(nLocation.getZ() >= sLocation.getZ() + 9.5 || nLocation.getZ() <= sLocation.getZ() - 8.5)){
//							player.teleport(sLocation);
							player.teleport(event.getFrom());
							mp.sendMessage(new OutsideSectionMessage());
						}
					}else if(cs.getSide() == 3){
						if((nLocation.getX() >= sLocation.getX() + 8.5 || nLocation.getX() <= sLocation.getX() - 9.5) ||
								(nLocation.getZ() >= sLocation.getZ() + 4.5 || nLocation.getZ() <= sLocation.getZ() - 4.5)){
//							player.teleport(sLocation);
							player.teleport(event.getFrom());
							mp.sendMessage(new OutsideSectionMessage());
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if(Mineopoly.plugin.getGame().isRunning()){
				if(Mineopoly.plugin.getGame().hasPlayer(player)){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent event){
		if(Mineopoly.plugin.getGame().hasPlayer((Player) event.getEntity())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(game.isRunning() && game.hasPlayer(event.getPlayer())){
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				Block block = event.getPlayer().getTargetBlock(null, 25);
				if(block.getType() == Material.WORKBENCH){
					Location origin = game.getBoard().getOrigin();
					int boardLength = 129;
					if(block.getX() <= origin.getBlockX() + boardLength - 1
							&& block.getZ() <= origin.getBlockZ() + boardLength - 1
							&& block.getY() <= origin.getY() + 25){
						event.setCancelled(true);
						event.getPlayer().chat("/mineopoly menu");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		InventoryHolder holder = event.getInventory().getHolder();
		if(holder instanceof MineopolyMenu){
			MineopolyMenu menu = (MineopolyMenu) holder;
			if(menu.clickIsValid(event.getSlot())){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer((Player) event.getWhoClicked());
				if(mp != null){
					event.setCancelled(true);
					menu.action(mp, event.getSlot());
				}
			}
		}
	}
	
}
