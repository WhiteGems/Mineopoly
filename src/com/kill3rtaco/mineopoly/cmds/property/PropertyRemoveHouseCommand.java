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

public class PropertyRemoveHouseCommand extends TacoCommand {

	public PropertyRemoveHouseCommand() {
		super("remove-house", new String[]{"rm-house"}, "[property]", "Remove a house from a property", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(mp.hasTurn()){
					if(args.length == 0){ //remove house to current property
						if(mp.getCurrentSection() instanceof Property){
							Property prop = (Property) mp.getCurrentSection();
							if(prop.getHouses() > 0){
								prop.removeHouse();
								Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3removed a house from " + prop.getColorfulName(), mp);
								mp.sendMessage("&3You removed a house from " + prop.getColorfulName());
							}else{
								mp.sendMessage("&cThat property has no houses");
							}
						}else{
							mp.sendMessage("&cThat is not a valid space to remove houses");
						}
					}else{ //get specified property and remove house from it
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
								if(prop.getHouses() > 0){
									prop.removeHouse();
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3removed a house from " + prop.getColorfulName(), mp);
									mp.sendMessage("&3You removed a house from " + prop.getColorfulName());
								}else{
									mp.sendMessage("&cThat property has no houses");
								}
							}else{
								mp.sendMessage("&cThat is not a valid space to remove houses");
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
