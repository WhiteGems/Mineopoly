package com.kill3rtaco.mineopoly.cmds.jail;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.NotInJailMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;

import com.kill3rtaco.tacoapi.api.TacoCommand;

public class JailCardCommand extends TacoCommand{

	public JailCardCommand() {
		super("card", new String[]{"goojf", "gojf", "out"}, "", "Use a Get Out of Jail Free card", "");
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(mp.hasTurn()){
					if(mp.isJailed()){
						if(mp.hasChanceJailCard() || mp.hasCommunityChestJailCard()){
							if(mp.hasChanceJailCard()){
								mp.takeChanceJailCard();
								Mineopoly.plugin.getGame().getBoard().getPot().addChanceJailCard();
							}else if(mp.hasCommunityChestJailCard()){
								mp.takeCommunityChestJailCard();
								Mineopoly.plugin.getGame().getBoard().getPot().addCommunityChestJailCard();
							}
							Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3has used a &bGet out of Jail Free &3card", mp);
							mp.sendMessage("&3You are out of jail. You can now use &b/" + Mineopoly.getMAlias() + " roll on your next turn");
							mp.setJailed(false, true);
						}else{
							mp.sendMessage("&cYou do not have a &6Get out of Jail Free &ccard to use");
						}
					}else{
						mp.sendMessage(new NotInJailMessage());
					}
				}else{
					mp.sendMessage(new InvalidTurnMessage());
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

}
