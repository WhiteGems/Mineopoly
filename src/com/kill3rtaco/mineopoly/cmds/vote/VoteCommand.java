package com.kill3rtaco.mineopoly.cmds.vote;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.mineopoly.messages.NotPlayingGameMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class VoteCommand extends TacoCommand {

	protected boolean end;
	
	public VoteCommand(String name, String[] aliases, String description, boolean end) {
		super(name, aliases, "", description, "");
		this.end = end;
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		MineopolyGame game = Mineopoly.plugin.getGame();
		if(!game.isRunning()){
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, new GameNotInProgressMessage());
			return;
		}
		if(!game.hasPlayer(player)){
			Mineopoly.plugin.chat.sendPlayerMessageNoHeader(player, new NotPlayingGameMessage());
			return;
		}
		
		MineopolyPlayer mp = game.getBoard().getPlayer(player);
		if(!game.pollsAreOpen()){
			if(!end){
				mp.sendMessage("&cThe polls for ending the game are not open");
				return;
			}
			mp.sendMessage("&aYour vote has been added");
			game.openPolls();
		}else{
			if(game.hasVoted(mp.getName())){
				mp.sendMessage("&cYou have already voted");
				return;
			}
			mp.sendMessage("&aYour vote has been added");
		}
		game.addVote(end);
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
