package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.sections.Railroad;
import com.kill3rtaco.mineopoly.game.sections.SectionType;
import com.kill3rtaco.mineopoly.messages.MustRollFirstMessage;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.InvalidTurnMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyTravelCommand extends TacoCommand {

	public MineopolyTravelCommand() {
		super("travel", new String[]{}, "", "Travel to the railroad across from you", "");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(!Mineopoly.houseRules.travelingRailroads()){
					mp.sendMessage("&cThe house rule '&6Traveling Railroads&c' is not enabled");
					return;
				}
				if(!mp.hasTurn()){
					mp.sendMessage(new InvalidTurnMessage());
					return;
				}
				if(!mp.hasRolled()){
					mp.sendMessage(new MustRollFirstMessage("traveling across the board"));
					return;
				}
				MineopolySection current = mp.getCurrentSection();
				MineopolySection across = Mineopoly.plugin.getGame().getBoard().getSection(current.getId() + 20);
				if(!mp.canTravel()){
					if(current.getType() != SectionType.RAILROAD){
						mp.sendMessage("&cYou are not on a railroad space");
						return;
					}else{
						Railroad rCurrent = (Railroad) current;
						Railroad rAcross = (Railroad) across;
						if(!rCurrent.isOwned()){
							mp.sendMessage("&cThis railroad is unowned");
							return;
						}else if(!rAcross.isOwned()){
							mp.sendMessage("&cThe railroad across from this one is unowned");
							return;
						}else if(!rCurrent.getOwner().getName().equalsIgnoreCase(rAcross.getName())){
							mp.sendMessage("&e" + rCurrent.getOwner().getName() + " &cdoes not own " + rAcross.getColorfulName());
							return;
						}
					}
				}else{
					Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3traveled from " + current.getColorfulName() + " &3to " + across.getColorfulName(), mp);
					mp.sendMessage("&3You traveled from " + current.getColorfulName() + " &3to " + across.getColorfulName());
					mp.setCurrentSection(across, false, false, false);
					mp.endTurnAutomatically();
				}
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, new NotPlayingGameMessage());
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
