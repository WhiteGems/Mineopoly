package com.kill3rtaco.mineopoly.cmds.property;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.game.sections.SectionType;
import com.kill3rtaco.mineopoly.game.trading.TradeRequest;
import com.kill3rtaco.mineopoly.game.trading.TradeType;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class PropertySellCommand extends TacoCommand {

	public PropertySellCommand() {
		super("sell", new String[]{"s"}, "[property] <player> <price>", "Sell a property to another player", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(!Mineopoly.plugin.getGame().hasPlayer(player)){
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
				return;
			}
			MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
			if(!Mineopoly.houseRules.tradeAnytime() && !mp.hasTurn()){
				mp.sendMessage("&cYou can only iniate a trade on your turn");
				return;
			}
			if(mp.isJailed() && !Mineopoly.houseRules.tradeWhileJailed()){
				mp.sendMessage("&cYou cannot trade while in jail");
				return;
			}
			OwnableSection owned;
			MineopolyPlayer trader;
			if(args.length < 2){
				
				return;
			}else if(args.length > 2){ //determine what property the player wants to trade
				MineopolySection section;
				if(TacoAPI.getChatUtils().isNum(args[0])){
					int id = Integer.parseInt(args[0]);
					section = Mineopoly.plugin.getGame().getBoard().getSection(id);
				}else{
					section = Mineopoly.plugin.getGame().getBoard().getSection(args[0]);
				}
				
				if(section == null){
					mp.sendMessage("&e" + args[0].replace("_", " ") + " &cis not a space on the board");
					return;
				}
				if(section.getType() != SectionType.PROPERTY && section.getType() != SectionType.RAILROAD && section.getType() != SectionType.UTILITY){
					mp.sendMessage(section.getColorfulName() + " &cis not a property, railroad, or utility");
					return;
				}
				owned = (OwnableSection) section;
				if(owned.getOwner() != null && !owned.getOwner().getName().equalsIgnoreCase(mp.getName())){
					mp.sendMessage("&cYou do not own " + section.getColorfulName());
					return;
				}
				args = TacoAPI.getChatUtils().removeFirstArg(args);
			}else{
				MineopolySection section = mp.getCurrentSection();
				if(section.getType() != SectionType.PROPERTY && section.getType() != SectionType.RAILROAD && section.getType() != SectionType.UTILITY){
					mp.sendMessage(section.getColorfulName() + " &cis not a property, railroad, or utility");
					return;
				}
				owned = (OwnableSection) section;
				if(owned.getOwner() != null && !owned.getOwner().getName().equalsIgnoreCase(mp.getName())){
					mp.sendMessage("&cYou do not own " + section.getColorfulName());
					return;
				}
			}
			trader = Mineopoly.plugin.getGame().getBoard().getPlayer(args[0]);
			if(trader == null){
				mp.sendMessage("&e" + args[0] + " &cis not playing Mineopoly");
				return;
			}
			if(trader.isJailed() && !Mineopoly.houseRules.tradeWhileJailed()){
				mp.sendMessage("&e" + trader.getName() + " &ccannot trade because they are in jail");
				return;
			}
			if(TacoAPI.getChatUtils().isNum(args[1])){
				int price = Integer.parseInt(args[1]);
				TradeRequest request = new TradeRequest(mp, trader, TradeType.SELL);
				request.setOfferedSection(owned);
				request.setRequestMoney(price);
				mp.addRequest(request); //sent to 'trader'
				trader.sendRequest(request); //received from 'mp'
				mp.sendMessage("&aYou requested to trade with &e" + trader.getName() + "&7: " + owned.getColorfulName() + " &7-> &2" + price);
				trader.sendMessage("&e" + mp.getName() + " &ahas requested to trade&7: &2" + price + " &7-> " + owned.getColorfulName());
				Mineopoly.plugin.getGame().getChannel()
					.sendMessage("&b" + mp.getName() + " &3has requested to trade with &b" + trader.getName() + "&7: " +
							owned.getColorfulName() + " &7-> &2" + price, mp, trader);
			}else{
				mp.sendMessage("&e" + args[1] + " &cis not an integer");
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
