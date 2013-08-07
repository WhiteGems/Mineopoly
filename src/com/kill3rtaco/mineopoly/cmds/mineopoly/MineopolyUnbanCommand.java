package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyUnbanCommand extends TacoCommand {

	public MineopolyUnbanCommand() {
		super("unban", new String[]{}, "<player>", "Unban a player from playing Mineopoly", MineopolyConstants.P_UNBAN_PLAYER_FROM_GAME);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.out("Must specify a player to unban");
		}else{
			String name = args[0];
			Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
			if(p != null) name = p.getName();
			if(Mineopoly.plugin.isBanned(name)){
				Mineopoly.plugin.unbanPlayer(name);
				Mineopoly.plugin.chat.out("Player unbanned");
			}else{
				Mineopoly.plugin.chat.out("Player '" + name + "' is not banned");
			}
		}
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cMust specify a player to unban");
		}else{
			String name = args[0];
			Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
			if(p != null) name = p.getName();
			if(Mineopoly.plugin.isBanned(name)){
				Mineopoly.plugin.unbanPlayer(name);
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&ePlayer unbanned");
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cPlayer &e" + name + " &cis not banned");
			}
		}
	}

}
