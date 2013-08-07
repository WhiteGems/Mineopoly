package com.kill3rtaco.mineopoly.game;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.entity.Player;

public class MineopolyQueue implements Iterable<Player>{

	private ArrayList<Player> queue;
	
	public MineopolyQueue(){
		queue = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player){
			queue.add(player);
	}
	
	public boolean playerIsInQueue(Player player){
		for(Player p : queue){
			if(p.getName().equalsIgnoreCase(player.getName()))
				return true;
		}
		return false;
	}
	
	public void removePlayer(Player player){
		for(int i=0; i<queue.size(); i++){
			if(queue.get(i).getName().equalsIgnoreCase(player.getName()))
				queue.remove(i);
		}
	}
	
	public void removePlayer(int index){
		queue.remove(index);
	}
	
	public int getIndexFromPlayer(Player player){
		return queue.indexOf(player);
	}
	
	public Player getPlayer(int index){
		return queue.get(index);
	}
	
	public int getSize(){
		return queue.size();
	}
	
	@Override
	public Iterator<Player> iterator() {
		return queue.iterator();
	}

}
