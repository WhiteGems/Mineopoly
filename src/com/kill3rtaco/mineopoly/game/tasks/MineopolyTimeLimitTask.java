package com.kill3rtaco.mineopoly.game.tasks;

import com.kill3rtaco.mineopoly.Mineopoly;

public class MineopolyTimeLimitTask implements Runnable {

	@Override
	public void run() {
		long time = System.currentTimeMillis() - Mineopoly.plugin.getGame().getStartTime();
		double timeLimit = Mineopoly.houseRules.timeLimit() * 1000 * 60L;
		if(time >= timeLimit){
			Mineopoly.plugin.getGame().getChannel().sendMessage("&eTime's up!");
			Mineopoly.plugin.getGame().end();
		}
	}

}
