package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyGameInfoCommand extends TacoCommand {

	public MineopolyGameInfoCommand() {
		super("game-info", new String[]{"gi"}, "", "See information on the current Mineopoly game", MineopolyConstants.P_VIEW_GAME_STATS);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		//players, current turn [space], numProperties/railroads/utilities owned
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(game.isRunning()){
			String players = "&b" + TacoAPI.getChatUtils().join(game.getBoard().getPlayerList(), "&7, &b");
			MineopolyPlayer mp = game.getPlayerWithCurrentTurn();
			MineopolySection section = mp.getCurrentSection();
			int properties = game.getBoard().getTotalOwnedProperties();
			int railroads = game.getBoard().getTotalOwnedRailroads();
			int utilities = game.getBoard().getTotalOwnedUtilities();
			String header = TacoAPI.getChatUtils().createHeader("&3MineopolyGameStats");
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, header);
			Mineopoly.plugin.chat.sendPlayerMessage(player, "&3Time running&7: &d" + game.getTimeRunningString()
					+ (Mineopoly.houseRules.timeLimit() < 0 ? " &b(" + game.getTimeLeftString() + " &3 left&b)" : ""));
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Players&7: " + players);
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Current Turn&7: &b" + mp.getName() + " &7[" + section.getColorfulName() + "&7]");
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Properties Owned&7: &d" + properties);
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Railroads Owned&7: &d" + railroads);
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, "&3Utilities Owned&7: &d" + utilities);
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
