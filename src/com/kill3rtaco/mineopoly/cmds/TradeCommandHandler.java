package com.kill3rtaco.mineopoly.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.cmds.trade.TradeAcceptCommand;
import com.kill3rtaco.mineopoly.cmds.trade.TradeCancelCommand;
import com.kill3rtaco.mineopoly.cmds.trade.TradeDeclineCommand;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class TradeCommandHandler extends TacoCommandHandler {

	public TradeCommandHandler() {
		super("trade", "Mineopoly Trading commands", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new TradeAcceptCommand());
		registerCommand(new TradeCancelCommand());
		registerCommand(new TradeDeclineCommand());
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String cmd = "trade";
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&c/" + cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Aliases&7: " + Mineopoly.plugin.getAliases(cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Commands&7: &b/" + cmd +" ? [page]");
	}

}
