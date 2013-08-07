package com.kill3rtaco.mineopoly.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.cmds.vote.VoteCommand;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class VoteCommandHandler extends TacoCommandHandler {

	public VoteCommandHandler() {
		super("vote", "Mineopoly voting commands", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new VoteCommand("no", new String[]{"continue"}, "Vote to keep the Mineopoly game running", false));
		registerCommand(new VoteCommand("yes", new String[]{"end"}, "Vote to end the Mineopoly game", true));
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String cmd = "vote";
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&c/" + cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Aliases&7: " + Mineopoly.plugin.getAliases(cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Commands&7: &b/" + cmd +" ? [page]");
	}

}
