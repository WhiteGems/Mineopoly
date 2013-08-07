package com.kill3rtaco.mineopoly.game.chat;

import java.util.ArrayList;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;

public class MineopolyChatChannel {

	private ArrayList<MineopolyChannelListener> listeners = new ArrayList<MineopolyChannelListener>();
	
	public void sendMessage(String message, MineopolyPlayer... exclude){
		ArrayList<MineopolyChannelListener> send = new ArrayList<MineopolyChannelListener>();
		for(MineopolyChannelListener l : listeners)
			send.add(l);
		for(MineopolyPlayer mp : exclude)
			send.remove(mp);
		for(MineopolyChannelListener l : send)
			l.sendMessage(message);
	}
	
	public void addPlayer(MineopolyChannelListener player){
		listeners.add(player);
	}
	
	public void removePlayer(String name){
		for(MineopolyChannelListener p : listeners){
			if(p.getName().equalsIgnoreCase(name)){
				listeners.remove(p);
			}
		}
	}
	
	public boolean isListeningToChannel(String name){
		for(MineopolyChannelListener p : listeners){
			if(p.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<MineopolyChannelListener> getListeners(){
		return listeners;
	}
	
	public void sendPlayersMessage(String message, MineopolyPlayer... exclude) {
		sendPlayersMessage(message, true, exclude);
	}

	public void sendPlayersMessage(String message, boolean header, MineopolyPlayer... exclude){
		ArrayList<MineopolyChannelListener> send = new ArrayList<MineopolyChannelListener>();
		for(MineopolyChannelListener l : listeners)
			send.add(l);
		for(MineopolyPlayer mp : exclude)
			send.remove(mp);
		for(MineopolyChannelListener l : listeners){
			if(header) l.sendMessage(message);
			else Mineopoly.plugin.chat.sendPlayerMessageNoHeader(l.getPlayer(), message);
		}
	}
	
	public void sendPlayersMessageNoHeader(String message, MineopolyPlayer... exclude){
		sendPlayersMessage(message, false, exclude);
	}
	
}
