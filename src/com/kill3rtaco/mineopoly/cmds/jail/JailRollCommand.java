package com.kill3rtaco.mineopoly.cmds.jail;

import java.util.Random;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.CannotPerformActionMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;

import com.kill3rtaco.tacoapi.api.TacoCommand;

public class JailRollCommand extends TacoCommand{

	public JailRollCommand() {
		super("roll", new String[]{}, "", "尝试掷出偶数", "");
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer p = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(p.hasTurn()){
					if(!p.isJailed()){
						p.sendMessage("&c你不能使用那个指令, 因为你并不在监狱内. 请使用 &6/mineopoly roll &6指令");
					}else{
						if(p.canRoll()){
							Random random = new Random();
							int roll1 = random.nextInt(6) + 1;
							int roll2 = random.nextInt(6) + 1;
							if(roll1 == roll2){
								Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + p.getName() + " &3已掷出偶数并已离开监狱", p);
								p.sendMessage("&3你掷出了偶数并已离开监狱");
								p.sendMessage("&3你已离开监狱. 你现在可以使用 &b/" + Mineopoly.getMAlias()+ " 来掷骰子进行下一轮");
								p.setJailed(false, true);
							}else{
								if(p.getJailRolls() == 2 && p.getBalance() >= 50){
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + p.getName() + " &3连续三次都没有掷出偶数, 并已离开监狱", p);
									p.sendMessage("&3你因为连续三次未能掷出偶数而离开监狱");
									p.sendMessage("&3你已离开监狱. 你现在可以使用 &b/" + Mineopoly.getMAlias()+ " 来掷骰子进行下一轮");
									p.setJailed(false, true);
								}else if(!(p.getBalance() >= 50)){
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + p.getName() + " &3连续三次都没有掷出偶数, 但是不能缴纳保释金 (&250&3) 所以不能离开监狱", p);
									p.sendMessage("&3你因为不能缴纳保释金 (&250&3) 而不能离开监狱");
									p.sendMessage("&3你必须要掷出偶数才能离开, 或者使用 &b离开监狱 &3卡片, 或者赚取 &250");
									p.setTurn(false, false);
								}else{
									Mineopoly.plugin.getGame().getChannel().sendMessage("&3已掷出 &b" + p.getName() + " &3并不能离开监狱", p);
									p.sendMessage("&3你不能离开监狱");
									p.setJailRolls(p.getJailRolls() + 1);
									p.setTurn(false, false);
								}
							}
						}else{
							p.sendMessage(new CannotPerformActionMessage());
						}
					}
				}else{
					p.sendMessage(new InvalidTurnMessage());
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
		}
	}

}
