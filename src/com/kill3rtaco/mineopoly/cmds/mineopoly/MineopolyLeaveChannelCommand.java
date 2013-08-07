package com.kill3rtaco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.messages.GameNotInProgressMessage;
import com.kill3rtaco.tacoapi.api.TacoCommand;

public class MineopolyLeaveChannelCommand extends TacoCommand {

	public MineopolyLeaveChannelCommand() {
		super("leave-channel", new String[]{"lc"}, "", "Leave the Mineopoly Channel", MineopolyConstants.P_CHANNEL_CHAT);
	}

	@Override
	public void onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou cannot leave the channel if you are playing");
			}else if(Mineopoly.plugin.getGame().getChannel().isListeningToChannel(player.getName())){
				Mineopoly.plugin.getGame().getChannel().removePlayer(player.getName());
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&aYou are no longer listening to the channel");
			}else{
				Mineopoly.plugin.chat.sendPlayerMessage(player, "&cYou are you are not in the Mineopoly channel");
			}
		}else{
			Mineopoly.plugin.chat.sendPlayerMessage(player, new GameNotInProgressMessage());
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

}
