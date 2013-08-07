package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolySetPasteLocationCommand extends TacoCommand {

	public MineopolySetPasteLocationCommand() {
		super("set-paste-location", new String[]{"spl"}, "[x y z]", "Set the paste location of the board", MineopolyConstants.P_SET_PASTE_LOCATION);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			Mineopoly.config.setBoardOrigin(player.getLocation());
			Mineopoly.config.setBoolean("mineopoly.schematic.needs_paste", true);
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&aPaste location saved");
		}else if(args.length == 1){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cMissing &eY &cand &eZ &ccoordinates");
		}else if(args.length == 2){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cMissing &eZ &ccoordinate");
		}else{
			
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length > 2){
			
		}else{
			
		}
		return true;
	}
	
}
