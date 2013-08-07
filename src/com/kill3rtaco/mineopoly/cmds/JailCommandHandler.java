package com.kill3rtaco.mineopoly.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.cmds.jail.JailBailCommand;
import com.kill3rtaco.mineopoly.cmds.jail.JailCardCommand;
import com.kill3rtaco.mineopoly.cmds.jail.JailRollCommand;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class JailCommandHandler extends TacoCommandHandler {

	public JailCommandHandler() {
		super("jail", "Mineopoly 监狱指令", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new JailRollCommand());
		registerCommand(new JailCardCommand());
		registerCommand(new JailBailCommand());
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String cmd = "jail";
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&c/" + cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3缩写&7: " + Mineopoly.plugin.getAliases(cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3指令&7: &b/" + cmd +" ? [页数]");
	}

}
