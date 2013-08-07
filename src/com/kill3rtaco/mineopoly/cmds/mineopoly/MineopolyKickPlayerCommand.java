package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;

import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.api.messages.TooFewArgumentsMessage;

public class MineopolyKickPlayerCommand extends TacoCommand {

	public MineopolyKickPlayerCommand() {
		super("kick", new String[]{}, "<player>", "Kick a player from the game", MineopolyConstants.P_KICK_PLAYER_FROM_GAME);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(args.length == 0){
				Mineopoly.plugin.chat.out(new TooFewArgumentsMessage("/mineopoly kick <player>") + "");
			}else{
				for(MineopolyPlayer mp : Mineopoly.plugin.getGame().getBoard().getPlayers()){
					if(mp.getName().equalsIgnoreCase(args[0]) || mp.getName().startsWith(args[0])){
						Mineopoly.plugin.getGame().kick(mp, "kicked by CONSOLE");
						break;
					}
				}
				Mineopoly.plugin.chat.out("Player '" + args[0] + "' not found");
				Mineopoly.plugin.chat.out("Player kicked");
			}
		}else{
			Mineopoly.plugin.chat.out(new GameNotInProgressMessage() + "");
		}
		return true;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(args.length == 0){
				player.sendMessage(new TooFewArgumentsMessage("/mineopoly kick <player>") + "");
			}else{
				for(MineopolyPlayer mp : Mineopoly.plugin.getGame().getBoard().getPlayers()){
					if(mp.getName().equalsIgnoreCase(args[0]) || mp.getName().startsWith(args[0])){
						Mineopoly.plugin.getGame().kick(mp, "kicked by " + player.getName());
						break;
					}
				}
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cPlayer &e" + args[0] + " &cnot found");
				Mineopoly.plugin.chat.sendPlayerMessage(player, "Player kicked");
			}
		}else{
			player.sendMessage(new GameNotInProgressMessage() + "");
		}
	}

}
