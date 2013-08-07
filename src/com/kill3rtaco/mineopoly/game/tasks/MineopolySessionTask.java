package com.kill3rtaco.mineopoly.game.tasks;

import java.util.Set;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.tasks.managers.PlayerSessionManager;

public class MineopolySessionTask implements Runnable {

	@Override
	public void run() {
		if(Mineopoly.plugin.getGame().isRunning()){
			Set<String> players = PlayerSessionManager.getPlayers();
			for(String p : players){
				if(!PlayerSessionManager.canReturn(p)){
					MineopolyPlayer player = Mineopoly.plugin.getGame().getBoard().getPlayer(p);
					if(player != null){
						Mineopoly.plugin.getGame().kick(player, "has not returned");
					}
				}
			}
		}
	}

	

}
