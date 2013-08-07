package com.kill3rtaco.mineopoly.cmds.property;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.game.sections.Railroad;
import com.kill3rtaco.mineopoly.game.sections.Utility;
import com.kill3rtaco.mineopoly.messages.InsufficientFundsMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.MustRollFirstMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.mineopoly.messages.SectionAlreadyOwnedMessage;
import com.kill3rtaco.mineopoly.messages.SectionNotOwnableMessage;

import com.kill3rtaco.tacoapi.api.TacoCommand;

public class PropertyBuyCommand extends TacoCommand {

	public PropertyBuyCommand() {
		super("buy", new String[]{}, "", "Buy the space your are on", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().hasPlayer(player)){
			MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
			MineopolySection section = mp.getCurrentSection();
			if(mp.hasTurn()){
				if(mp.hasRolled()){
					if(Mineopoly.houseRules.purchaseAfterGoPasses() > 0){
						int neededPasses = Mineopoly.houseRules.purchaseAfterGoPasses();
						if(mp.getGoPasses() < neededPasses){
							mp.sendMessage("&cYou need to pass &6Go &e" + (neededPasses - mp.getGoPasses()) + " &c more times to buy property");
							return;
						}
					}
					if(section instanceof OwnableSection){
						OwnableSection oSection = (OwnableSection) section;
						if(!oSection.isOwned()){
							int neededPasses = Mineopoly.houseRules.purchaseAfterGoPasses();
							if(neededPasses > 0 && mp.getGoPasses() < neededPasses){
								mp.sendMessage("&cYou need to pass &6Go &e" + (neededPasses - mp.getGoPasses()) + " &cmore times before you can buy property");
								return;
							}
							if(mp.canBuy(oSection)){
								oSection.setOwner(mp);
								Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3bought " + section.getColorfulName() +"&3 for &2" + oSection.getPrice(), mp);
								mp.sendMessage("&3You bought " + section.getColorfulName() + "&3 for &2" + oSection.getPrice());
								mp.takeMoney(oSection.getPrice());
								if(oSection instanceof Property){
									Property prop = (Property) oSection;
									if(mp.hasMonopoly(prop.getColor())){
										Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3now has a monopoly for the color " + prop.getColor().getName(), mp);
										mp.sendMessage("&3You now have a monopoly for the color " + prop.getColor().getName());
										mp.sendMessage("&3You can now add houses by typing &b/property add-house [property]");
									}
								}else if(oSection instanceof Railroad && mp.ownedRailRoads() == 4){
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3now owns all Railroad spaces", mp);
									mp.sendMessage("&3You now now own all Railroad spaces");
								}else if(oSection instanceof Utility && mp.ownedUtilities() == 2){
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3now owns both Utility spaces", mp);
									mp.sendMessage("&3You now own both Utility spaces");
								}
								boolean ate = Mineopoly.config.allowAutomaticTurnEnding();
								if(ate){
									mp.sendMessage("&aTurn ended automatically");
									mp.getPlayer().chat("/mineopoly end-turn");
								}else{
									mp.sendMessage("&3End your turn with &b/mgame et");
								}
							}else{
								mp.sendMessage(new InsufficientFundsMessage());
							}
						}else{
							mp.sendMessage(new SectionAlreadyOwnedMessage(section));
						}
					}else{
						mp.sendMessage(new SectionNotOwnableMessage(section, "buy"));
					}
				}else{
					mp.sendMessage(new MustRollFirstMessage("buying property"));
				}
			}else{
				mp.sendMessage(new InvalidTurnMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return true;
	}

}
