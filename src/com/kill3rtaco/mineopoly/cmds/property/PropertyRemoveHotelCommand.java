package com.kill3rtaco.mineopoly.cmds.property;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.mineopoly.messages.SectionNotFoundMessage;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class PropertyRemoveHotelCommand extends TacoCommand {

	public PropertyRemoveHotelCommand() {
		super("remove-hotel", new String[]{"rm-hotel"}, "[property]", "Remove the hotel from a property", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(mp.hasTurn()){
					if(args.length == 0){ //add house to current property
						if(mp.getCurrentSection() instanceof Property){
							Property prop = (Property) mp.getCurrentSection();
							if(prop.hasHotel()){
								prop.removeHotel();
								Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3removed the hotel from " + prop.getColorfulName(), mp);
								mp.sendMessage("&3You removed the hotel from " + prop.getColorfulName());
							}else{
								mp.sendMessage("&cThat property doesn't have a hotel");
							}
						}else{
							mp.sendMessage("&cThat is not a valid space to remove a hotel");
						}
					}else{ 
						MineopolySection section;
						if(TacoAPI.getChatUtils().isNum(args[0]))
							section = Mineopoly.plugin.getGame().getBoard().getSection(Integer.parseInt(args[0]));
						else
							section = Mineopoly.plugin.getGame().getBoard().getSection(args[0]);
						if(section == null){
							mp.sendMessage(new SectionNotFoundMessage());
						}else{
							if(section instanceof Property){
								Property prop = (Property) section;
								if(prop.hasHotel()){
									prop.removeHotel();
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3removed the hotel from " + prop.getColorfulName(), mp);
									mp.sendMessage("&3You removed the hotel from " + prop.getColorfulName());
								}else{
									mp.sendMessage("&cThat property doesn't have a hotel");
								}
							}else{
								mp.sendMessage("&cThat is not a valid space to remove a hotel");
							}
						}
					}
				}else{
					Mineopoly.plugin.chat.sendPlayerMessage(player, new InvalidTurnMessage());
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
