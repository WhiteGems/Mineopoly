package com.kill3rtaco.mineopoly.game.cards;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolyPlayer;

import com.kill3rtaco.tacoapi.TacoAPI;

public abstract class MineopolyCard {

	private String description, name, action;
	protected MineopolyCardAction cardAction;
	protected CardResult result = null;
	private CardType type;
	private boolean valid = true;
	private Object[] params;
	
	public MineopolyCard(String name, CardType type, String description, String action) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.action = action;
		validate();
	}
	
	public String getDescription(){
		return description;
	}
	
	public void readDescription(MineopolyPlayer player){
		Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + player.getName() + " &3drew a " + type.getName() + " &3card, it says:");
		Mineopoly.plugin.getGame().getChannel().sendMessage(getFormattedDescription());
	}
	
	public String getFormattedDescription(){
		String message = "";
		for(String s : description.split("\\s+")){
			boolean found = false;
			for(MineopolyCardVariable v : MineopolyCardVariable.values()){
				if(s.matches(v.getRegex())){
					message += v.getReplaceMethod().replace(s) + " ";
					found = true;
				}
			}
			if(!found) message += s + " ";
		}
		return message;
	}
	
	public void action(MineopolyPlayer player){
		result = cardAction.doAction(player, params);
	}
	
	private void validate(){
		String name = "";
		String[] params = action.split("\\s+");
		if(params.length == 0){
			valid = false;
			return;
		}else{
			name = params[0];
			params = TacoAPI.getChatUtils().removeFirstArg(params);
		}
		boolean found = false;
		for(MineopolyCardAction a : MineopolyCardActionManager.getActions()){
			if(a.getName().equalsIgnoreCase(name) && a.getRequiredParamLength() == params.length){
				found = true;
				this.params = new Object[a.getRequiredParamLength()];
				String types = a.getParamTypes();
				for(int i=0; i<types.length(); i++){
					if(types.charAt(i) == 'b'){
						this.params[i] = Boolean.valueOf(params[i]);
					}else if(types.charAt(i) == 'i'){
						if(TacoAPI.getChatUtils().isNum(params[i])){
							this.params[i] = Integer.parseInt(params[i]);
						}else{
							valid = false;
							break;
						}
					}else if(types.charAt(i) == 's'){
						this.params[i] = params[i];
					}
				}
			}
		}
		if(found && valid){
			cardAction = MineopolyCardActionManager.getAction(name);
		}
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public CardResult getResult(){
		return result;
	}
	
	public CardType getType(){
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFormattedAction() {
		if(valid){
			String[] parts = action.split("\\s+");
			String formattedAction = parts[0] + "(";
			for(int i=1; i<parts.length; i++){
				formattedAction += parts[i];
				if(i < parts.length - 1){
					formattedAction += ", ";
				}
			}
			return formattedAction + ")";
		}else{
			return "InvalidAction";
		}
	}

}
