package com.kill3rtaco.mineopoly.game.trading;

import com.kill3rtaco.mineopoly.game.MineopolyPlayer;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;

public class TradeRequest {

	private MineopolyPlayer sender, receiver;
	private OwnableSection offeredSection, requestedSection;
	private int offeredMoney, requestedMoney;
	private boolean cancelled = false;
	private TradeType type;
	
	public TradeRequest(MineopolyPlayer sender, MineopolyPlayer receiver, TradeType type) {
		this.sender = sender;
		this.receiver = receiver;
		this.offeredMoney = 0;
		this.requestedMoney = 0;
		this.type = type;
	}
	
	public MineopolyPlayer getSender(){
		return sender;
	}
	
	public MineopolyPlayer getReceiver(){
		return receiver;
	}
	
	public OwnableSection getOfferedSection(){
		return offeredSection;
	}
	
	public OwnableSection getRequestedSection(){
		return requestedSection;
	}
	
	public int getOfferedMoney(){
		return offeredMoney;
	}
	
	public int getRequestedMoney(){
		return requestedMoney;
	}
	
	public void setOfferedSection(OwnableSection section){
		offeredSection = section;
	}
	
	public void setRequestedSection(OwnableSection section){
		requestedSection = section;
	}
	
	public void setOfferMoney(int money){
		offeredMoney = money;
	}
	
	public void setRequestMoney(int money){
		requestedMoney = money;
	}
	
	public void cancel(){
		setCancelled(false);
	}
	
	public void setCancelled(boolean cancel){
		this.cancelled = cancel;
	}
	
	public boolean isCancelled(){
		return cancelled;
	}
	
	public TradeType getType(){
		return type;
	}

}
