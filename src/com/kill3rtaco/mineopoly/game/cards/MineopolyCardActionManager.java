package com.kill3rtaco.mineopoly.game.cards;

import java.util.ArrayList;

import com.kill3rtaco.mineopoly.game.cards.actions.GiveAction;
import com.kill3rtaco.mineopoly.game.cards.actions.JailAction;
import com.kill3rtaco.mineopoly.game.cards.actions.MoveAction;
import com.kill3rtaco.mineopoly.game.cards.actions.MoveNearestAction;
import com.kill3rtaco.mineopoly.game.cards.actions.MoveToAction;
import com.kill3rtaco.mineopoly.game.cards.actions.PayAllAction;
import com.kill3rtaco.mineopoly.game.cards.actions.PayPlayerAction;
import com.kill3rtaco.mineopoly.game.cards.actions.PayPotAction;
import com.kill3rtaco.mineopoly.game.cards.actions.RepairsAction;
import com.kill3rtaco.mineopoly.game.cards.actions.TakeAction;

public class MineopolyCardActionManager {

	private static ArrayList<MineopolyCardAction> actions;
	
	private static void registerActions(){
		actions = new ArrayList<MineopolyCardAction>();
		actions.add(new GiveAction());
		actions.add(new JailAction());
		actions.add(new MoveAction());
		actions.add(new MoveNearestAction());
		actions.add(new MoveToAction());
		actions.add(new PayAllAction());
		actions.add(new PayPlayerAction());
		actions.add(new PayPotAction());
		actions.add(new RepairsAction());
		actions.add(new TakeAction());
	}
	
	public static ArrayList<MineopolyCardAction> getActions(){
		if(actions == null){
			registerActions();
		}
		return actions;
	}
	
	public static MineopolyCardAction getAction(String name){
		for(MineopolyCardAction a : getActions()){
			if(a.getName().equalsIgnoreCase(name)){
				return a;
			}
		}
		return null;
	}

}
