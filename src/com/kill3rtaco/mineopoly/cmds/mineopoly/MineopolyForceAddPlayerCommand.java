package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;

import com.kill3rtaco.tacoapi.api.TacoCommand;
import com.kill3rtaco.tacoapi.api.messages.PlayerNotOnlineMessage;
import com.kill3rtaco.tacoapi.api.messages.TooFewArgumentsMessage;

public class MineopolyForceAddPlayerCommand extends TacoCommand {

	public MineopolyForceAddPlayerCommand() {
		super("force-add", new String[]{"fa", "add"}, "[player]", "Add a player to the Mineopoly game", MineopolyConstants.P_FORCE_ADD_PLAYER);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length < 1){
			Mineopoly.plugin.chat.out(new TooFewArgumentsMessage("/mineopoly force-add <player>").getMessage());
		}else{
			if(Mineopoly.plugin.getGame().isRunning()){
				Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
				if(p == null){
					Mineopoly.plugin.chat.out(new PlayerNotOnlineMessage(args[0]).getMessage());
				}else{
					if(!Mineopoly.plugin.getGame().hasPlayer(p)){
						boolean add = true;
						if(Mineopoly.plugin.isBanned(p.getName())){
							add = Mineopoly.config.addEvenWhenBanned();
						}
						
						if(add){
							MineopolyPlayer mp = new MineopolyPlayer(p);
							Mineopoly.plugin.getGame().getBoard().addPlayer(mp);
							Mineopoly.plugin.getGame().getChannel().addPlayer(mp);
							mp.setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(0), false);
							Mineopoly.plugin.chat.out(mp.getName() + " was added to the game");
						}else{
							Mineopoly.plugin.chat.out("That player is banned from playing Mineopoly");
						}
					}else{
						Mineopoly.plugin.chat.out("That player is already playing");
					}
				}
			}else{
				Mineopoly.plugin.chat.out(new GameNotInProgressMessage());
			}
		}
		return true;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(args.length < 1){
			Mineopoly.plugin.chat.sendPlayerMessage(player, new TooFewArgumentsMessage("mineopoly force-add <player>"));
		}else{
			if(Mineopoly.plugin.getGame().isRunning()){
				Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
				if(p == null){
					Mineopoly.plugin.chat.sendPlayerMessage(player, new PlayerNotOnlineMessage(args[0]));
				}else{
					if(!Mineopoly.plugin.getGame().hasPlayer(p)){
						boolean add = true;
						if(Mineopoly.plugin.isBanned(p.getName())){
							add = Mineopoly.config.addEvenWhenBanned();
						}
						
						if(add){
							MineopolyPlayer mp = new MineopolyPlayer(p);
							Mineopoly.plugin.getGame().getBoard().addPlayer(mp);
							Mineopoly.plugin.getGame().getChannel().addPlayer(mp);
							mp.setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(0), false);
							Mineopoly.plugin.chat.sendPlayerMessage(player, "&e" + mp.getName() + " was added to the game");
						}else{
							Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThat player is banned from playing &6Mineopoly");
						}
					}else{
						Mineopoly.plugin.chat.sendPlayerMessage(player, "&cThat player is already playing");
					}
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
			}
		}
	}
	
}
