package com.kill3rtaco.mineopoly.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyDeedsCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyEndCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyEndTurnCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyForceAddPlayerCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyGameInfoCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyJoinChannelCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyJoinCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyKickPlayerCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyLeaveChannelCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyMenuCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyMonopoliesCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyQueueCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyQuitCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyReloadCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyResumeCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyRollCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolySaveCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolySetPasteLocationCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyStartCommand;
import com.kill3rtaco.mineopoly.cmds.mineopoly.MineopolyStatsCommand;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class MineopolyCommandHandler extends TacoCommandHandler{

	public MineopolyCommandHandler() {
		super("mineopoly", "Mineopoly commands", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new MineopolyDeedsCommand());
		registerCommand(new MineopolyEndCommand());
		registerCommand(new MineopolyEndTurnCommand());
		registerCommand(new MineopolyForceAddPlayerCommand());
		registerCommand(new MineopolyGameInfoCommand());
		registerCommand(new MineopolyJoinCommand());
		registerCommand(new MineopolyJoinChannelCommand());
		registerCommand(new MineopolyKickPlayerCommand());
		registerCommand(new MineopolyLeaveChannelCommand());
		registerCommand(new MineopolyMenuCommand());
		registerCommand(new MineopolyMonopoliesCommand());
		registerCommand(new MineopolyQueueCommand());
		registerCommand(new MineopolyQuitCommand());
		registerCommand(new MineopolyResumeCommand());
		registerCommand(new MineopolyReloadCommand());
		registerCommand(new MineopolyRollCommand());
		registerCommand(new MineopolySaveCommand());
		registerCommand(new MineopolySetPasteLocationCommand());
		registerCommand(new MineopolyStatsCommand());
		registerCommand(new MineopolyStartCommand());
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String cmd = "mineopoly";
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&c/" + cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Aliases&7: " + Mineopoly.plugin.getAliases(cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Commands&7: &b/" + cmd +" ? [page]");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&5Plugin Information"));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&6Author&7: &cKILL3RTACO");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&6Version&7: &5" + Mineopoly.plugin.getDescription().getVersion());
	}

}
