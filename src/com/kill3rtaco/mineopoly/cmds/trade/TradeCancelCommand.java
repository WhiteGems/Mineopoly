package com.kill3rtaco.mineopoly.cmds.trade;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.trading.TradeRequest;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class TradeCancelCommand extends TacoCommand {

	public TradeCancelCommand() {
		super("cancel", new String[]{"c"}, "<*/player>", "Cancel a trade with another player (or all)", "");
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
		if(args[0].equalsIgnoreCase("*")){
			for(TradeRequest r : mp.getSentRequests()){
				MineopolyPlayer other = r.getReceiver();
				mp.clearRequestsWithPlayer(other.getName());
				other.clearRequestsWithPlayer(mp.getName());
			}
			Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3canceled all of their trading requests", mp);
			return;
		}
		MineopolyPlayer trader = Mineopoly.plugin.getGame().getBoard().getPlayer(args[0]);
		if(trader == null){
			mp.sendMessage("&e" + args[0] + " &cis not playing Mineopoly");
			return;
		}
		
		TradeRequest request = mp.getSentRequest(trader.getName());
		if(request == null){
			mp.sendMessage("&cYou have not sent a requnameest to &e" + trader.getName());
			return;
		}
		
		mp.clearRequestsWithPlayer(trader.getName());
		trader.clearRequestsWithPlayer(mp.getName());
		
		
		
		Mineopoly.plugin.getGame().getChannel().sendMessage("", mp, trader);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
