package com.kill3rtaco.mineopoly.cmds;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.cmds.property.PropertyAddHotelCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertyAddHouseCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertyBuyCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertyInfoCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertyRemoveHotelCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertyRemoveHouseCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertySellCommand;
import com.kill3rtaco.mineopoly.cmds.property.PropertyTradeCommand;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommandHandler;

public class PropertyCommandHandler extends TacoCommandHandler {

	public PropertyCommandHandler() {
		super("property", "Mineopoly Property commands", "");
	}

	@Override
	protected void registerCommands() {
		registerCommand(new PropertyAddHouseCommand());
		registerCommand(new PropertyAddHotelCommand());
		registerCommand(new PropertyBuyCommand());
		registerCommand(new PropertyInfoCommand());
		registerCommand(new PropertyRemoveHouseCommand());
		registerCommand(new PropertyRemoveHotelCommand());
		registerCommand(new PropertySellCommand());
		registerCommand(new PropertyTradeCommand());
	}

	@Override
	protected boolean onConsoleCommand() {
		return false;
	}

	@Override
	protected void onPlayerCommand(Player player) {
		String cmd = "property";
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, TacoAPI.getChatUtils().createHeader("&c/" + cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Aliases&7: " + Mineopoly.plugin.getAliases(cmd));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Commands&7: &b/" + cmd +" ? [page]");
	}

}
