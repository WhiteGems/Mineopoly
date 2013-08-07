package com.kill3rtaco.mineopoly.cmds.trade;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.game.trading.TradeRequest;
import com.kill3rtaco.mineopoly.game.trading.TradeType;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class TradeAcceptCommand extends TacoCommand {

	public TradeAcceptCommand() {
		super("accept", new String[]{"acc", "a"}, "<player>", "Accept a trading request from a player", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(!Mineopoly.plugin.getGame().isRunning()){
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
			return;
		}
		if(!Mineopoly.plugin.getGame().hasPlayer(player)){
			Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			return;
		}
		MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
		if(args.length == 0){
			mp.sendMessage("&cMust specify a player");
			return;
		}
		MineopolyPlayer trader = Mineopoly.plugin.getGame().getBoard().getPlayer(args[0]);
		if(trader == null){
			mp.sendMessage("&e" + args[0] + " &cis not playing Mineopoly");
			return;
		}
		
		TradeRequest request = mp.getReceivedRequest(trader.getName());
		if(request == null){
			mp.sendMessage("&cYou do not have a request from &e" + trader.getName());
			return;
		}
		if(request.getType() == TradeType.SELL){
			int money = request.getRequestedMoney();
			OwnableSection section = request.getOfferedSection();
			if(!mp.hasMoney(money)){
				mp.sendMessage("&cYou cannot accept this request because you do not have enough money (&2" + money + "&c)");
				return;
			}
			mp.payPlayer(trader, money);
			trader.removeSection(section);
			trader.sendMessage("&aYou no longer own " + section.getColorfulName());
			section.setOwner(mp);
			mp.sendMessage("&aYou now own " + section.getColorfulName());
			Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3accept &b" + trader.getName() + "&3's trade request&7: " +
					"&2" + money + " &7-> " + section.getColorfulName(), mp, trader);
		}else{
			OwnableSection requested = request.getRequestedSection();
			OwnableSection offered = request.getOfferedSection();
			mp.removeSection(requested);
			mp.sendMessage("&aYou no longer own " + requested.getColorfulName());
			trader.removeSection(offered);
			trader.sendMessage("&aYou no longer own " + offered.getColorfulName());
			offered.setOwner(mp);
			mp.sendMessage("&aYou now own " + offered.getColorfulName());
			requested.setOwner(trader);
			trader.sendMessage("&aYou now own " + requested.getColorfulName());
			Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3accept &b" + trader.getName() + "&3's trade request&7: " +
					 requested.getColorfulName() + " &7-> " + offered.getColorfulName(), mp, trader);
		}
		
		mp.clearRequestsWithPlayer(trader.getName());
		trader.clearRequestsWithPlayer(mp.getName());
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
