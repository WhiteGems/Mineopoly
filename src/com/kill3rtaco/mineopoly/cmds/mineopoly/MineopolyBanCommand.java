package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyBanCommand extends TacoCommand {

	public MineopolyBanCommand() {
		super("ban", new String[]{}, "<player>", "Ban a player from playing Mineopoly", MineopolyConstants.P_BAN_PLAYER_FROM_GAME);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&cMust specify a player to ban");
		}else{
			String name = args[0];
			Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
			if(p != null) name = p.getName();
			if(!Mineopoly.plugin.isBanned(name)){
				Mineopoly.plugin.banPlayer(name);
				if(p != null && p.isOnline() && Mineopoly.plugin.getGame().hasPlayer(p)){
					MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
					Mineopoly.plugin.getGame().kick(mp, "banned by " + player.getName());
				}
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&ePlayer banned");
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cPlayer &e" + name + " &cis already banned");
			}
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.out("Must specify a player to ban");
		}else{
			String name = args[0];
			Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
			if(p != null) name = p.getName();
			if(!Mineopoly.plugin.isBanned(name)){
				Mineopoly.plugin.banPlayer(name);
				if(p != null && p.isOnline() && Mineopoly.plugin.getGame().hasPlayer(p)){
					MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
					Mineopoly.plugin.getGame().kick(mp, "banned by CONSOLE");
				}
				Mineopoly.plugin.chat.out("&ePlayer banned");
			}else{
				Mineopoly.plugin.chat.out("&cPlayer &e" + name + " &cis already banned");
			}
		}
		return false;
	}

}
