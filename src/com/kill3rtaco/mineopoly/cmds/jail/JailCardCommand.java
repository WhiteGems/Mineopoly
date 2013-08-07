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
		super("card", new String[]{"goojf", "gojf", "out"}, "", "使用一张离开监狱卡", "");
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
							Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3已使用了一张 &b离开监狱 &3卡片", mp);
							mp.sendMessage("&3你已离开监狱. 你现在可以使用 &b/" + Mineopoly.getMAlias() + " 来掷骰子进行下一轮");
							mp.setJailed(false, true);
						}else{
							mp.sendMessage("&c你并没有 &6离开监狱 &c卡片可使用");
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
