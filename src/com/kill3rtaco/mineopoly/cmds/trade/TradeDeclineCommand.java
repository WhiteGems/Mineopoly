package com.kill3rtaco.mineopoly.cmds.trade;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.trading.TradeRequest;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class TradeDeclineCommand extends TacoCommand {

	public TradeDeclineCommand() {
		super("decline", new String[]{"d"}, "<player>", "Decline a request from another player", "");
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
		
		mp.clearRequestsWithPlayer(trader.getName());
		trader.clearRequestsWithPlayer(mp.getName());
		
		mp.sendMessage("&aYou declined &e" + trader.getName() + "&a's trade request");
		trader.sendMessage("&e" + mp.getName() + " &adeclined your trade request");
		
		Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3declined &b" + trader.getName() + "&3's trade request", mp, trader);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
