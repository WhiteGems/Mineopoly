package com.kill3rtaco.mineopoly.game.tasks;

import java.util.Random;

import com.kill3rtaco.mineopoly.Mineopoly;

public class MineopolyTipTask implements Runnable {

	public enum MineopolyTip {
		
		JAIL_CARD("&aStuck in &1Jail&a? Use a &eGet out of Jail Free &acard with &e/" + Mineopoly.getJAlias() + " card&a!"),
		JAIL_BAIL("&aStuck in &1Jail&a? Pay &ebail &awith &e/" + Mineopoly.getJAlias() + " bail&a!"),
		SHORTEST_CMD("&aDid you know that &6Mineopoly &aalways tries to find the shortest command alias for your convience?"),
		USE_THE_MENUS("&aSee the &ecrafting benches&a? See what happens when you &eright click them&a!"),
		VOTE_TO_END_GAME("&aYou can vote to end the game with &e/" + Mineopoly.getVAlias()+ " end");
		
		private String message;
		private boolean enabled;
		private static MineopolyTip lastTip;
		
		private MineopolyTip(String message){
			this(message, true);
		}
		
		private MineopolyTip(String message, boolean enabled){
			this.message = message;
			this.enabled = enabled;
		}
		
		public String getMessage(){
			return message;
		}
		
		public boolean isEnabled(){
			return enabled;
		}
		
		public static MineopolyTip randomTip(){
			Random random = new Random();
			MineopolyTip tip = values()[random.nextInt(values().length)];
			if(tip == lastTip){
				return randomTip();
			}
			lastTip = tip;
			return tip;
		}
		
	}

	@Override
	public void run() {
		if(!Mineopoly.plugin.getGame().isRunning()) return;
		MineopolyTip tip = MineopolyTip.randomTip();
		Mineopoly.plugin.getGame().getChannel().sendPlayersMessageNoHeader("&7[&6*&7] " + tip.getMessage());
	}

}
