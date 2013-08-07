package com.kill3rtaco.mineopoly.cmds.property;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.game.sections.SectionType;
import com.kill3rtaco.mineopoly.messages.CannotPerformActionMessage;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.mineopoly.messages.SectionNotFoundMessage;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class PropertyAddHotelCommand extends TacoCommand {

	public PropertyAddHotelCommand() {
		super("add-hotel", new String[]{}, "[property]", "Add a Hotel to a property", "");
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(!mp.hasTurn()){
					mp.sendMessage(new InvalidTurnMessage());
					return;
				}
				if(mp.hasRolled()){
					mp.sendMessage("&cYou can only add hotels before you roll");
					return;
				}
				if(mp.isJailed() && !Mineopoly.houseRules.improveWhileJailed()){
					mp.sendMessage("&cYou cannot do that while in jail");
					return;
				}
				Property p = null;
				if(args.length == 0){
					MineopolySection section = mp.getCurrentSection();
					if(section.getType() != SectionType.PROPERTY){
						mp.sendMessage(new CannotPerformActionMessage() + "add a house to that");
					}else{
						p = (Property) section;
					}
				}else{
					if(TacoAPI.getChatUtils().isNum(args[0])){
						int id = Integer.parseInt(args[0]);
						MineopolySection section = Mineopoly.plugin.getGame().getBoard().getSection(id);
						if(section.getType() != SectionType.PROPERTY){
							mp.sendMessage(new CannotPerformActionMessage() + "add a house to that");
							return;
						}else{
							p = (Property) section;
						}
					}else{
						MineopolySection section = Mineopoly.plugin.getGame().getBoard().getSection(args[0]);
						if(section == null){
							mp.sendMessage(new SectionNotFoundMessage());
							return;
						}
						if(section.getType() != SectionType.PROPERTY){
							mp.sendMessage(new CannotPerformActionMessage() + "add a house to that");
							return;
						}else{
							p = (Property) section;
						}
					}
				}
				
				if(Mineopoly.houseRules.improvementRequiresLocation() && p.getId() != mp.getCurrentSection().getId()){
					mp.sendMessage("&cYou can only improve the property you are on");
					return;
				}
				
				if(p.getOwner().getName().equalsIgnoreCase(mp.getName())){
					if(mp.hasMoney(p.getHotelPrice())){
						if(!p.canAddHotel()){
							if(Mineopoly.houseRules.improvementRequiresMonopoly() && mp.hasMonopoly(p.getColor())){
								mp.sendMessage("&cYou do not have a monopoly for the color " + p.getColor().getName());
								return;
							}
							ArrayList<Property> props = mp.getPropertiesLessHousing(p.getColor(), 4);
							if(props.size() > 0){
								String message = "";
								for(int i=0; i<props.size(); i++){
									if(i == props.size() - 1){
										message += (message.isEmpty() ? "" : "and ") + props.get(i).getColorfulName();
									}else{
										message += props.get(i).getColorfulName() + " &7, &3";
									}
								}
								message += " &3 " + (props.size() == 1 ? "does" : "do") + " not have four houses";
								mp.sendMessage(message);
							}
						}else{
							Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3 added a hotel to " + p.getColorfulName(), mp);
							mp.sendMessage("&3You added a &chotel &3to " + p.getColorfulName());
							p.addHotel();
						}
					}else{
						int amount = p.getHotelPrice() - mp.getBalance();
						mp.sendMessage("&cYou need an additional &2" + amount + " &cbefore you can do that");
					}
				}else{
					mp.sendMessage("&cYou do not own " + p.getColorfulName());
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

}
