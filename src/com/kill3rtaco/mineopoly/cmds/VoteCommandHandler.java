package com.kill3rtaco.mineopoly.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.cmds.vote.VoteCommand;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class VoteCommandHandler extends TacoCommandHandler {

	public VoteCommandHandler() {
		super("vote", "Mineopoly 投票指令", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new VoteCommand("no", new String[]{"continue"}, "投票来继续玩 Mineopoly 游戏", false));
		registerCommand(new VoteCommand("yes", new String[]{"end"}, "投票终止 Mineopoly 游戏", true));
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String cmd = "vote";
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&c/" + cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3缩写&7: " + Mineopoly.plugin.getAliases(cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3指令&7: &b/" + cmd +" ? [页数]");
	}

}
