package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyBanCommand extends TacoCommand {

	public MineopolyBanCommand() {
		super("ban", new String[]{}, "<玩家ID>", "将一个玩家封禁, 使他不可以玩 Mineopoly", MineopolyConstants.P_BAN_PLAYER_FROM_GAME);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&c必须指定一名玩家进行封禁");
		}else{
			String name = args[0];
			Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
			if(p != null) name = p.getName();
			if(!Mineopoly.plugin.isBanned(name)){
				Mineopoly.plugin.banPlayer(name);
				if(p != null && p.isOnline() && Mineopoly.plugin.getGame().hasPlayer(p)){
					MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
					Mineopoly.plugin.getGame().kick(mp, "封禁者: " + player.getName());
				}
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&e玩家已被封禁");
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&c玩家 &e" + name + " &c已被封禁");
			}
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.chat.out("必须指定一个玩家进行封禁");
		}else{
			String name = args[0];
			Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
			if(p != null) name = p.getName();
			if(!Mineopoly.plugin.isBanned(name)){
				Mineopoly.plugin.banPlayer(name);
				if(p != null && p.isOnline() && Mineopoly.plugin.getGame().hasPlayer(p)){
					MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
					Mineopoly.plugin.getGame().kick(mp, "由控制台封禁");
				}
				Mineopoly.plugin.chat.out("&e玩家已被封禁");
			}else{
				Mineopoly.plugin.chat.out("&c玩家 &e" + name + " &c已经被封禁");
			}
		}
		return false;
	}

}
