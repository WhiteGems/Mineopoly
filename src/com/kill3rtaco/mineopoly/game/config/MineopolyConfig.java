package com.kill3rtaco.mineopoly.game.config;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.World;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.MineopolyConstants;
import com.kill3rtaco.mineopoly.game.WinMethod;
import com.kill3rtaco.tacoapi.api.TacoConfig;

public class MineopolyConfig extends TacoConfig{

	public MineopolyConfig() {
		super(new File(Mineopoly.plugin.getDataFolder() + "/config/options.yml"));
	}

	@Override
	public void setDefaults() {
		int minute = 60;
		addDefaultValue(MineopolyConstants.C_AUTO_TURN_END, false);
		addDefaultValue(MineopolyConstants.C_ADD_BANNED, false);
		addDefaultValue(MineopolyConstants.C_MAX_PLAYERS, 8);
		addDefaultValue(MineopolyConstants.C_METRICS_SEND, true);
		addDefaultValue(MineopolyConstants.C_MIN_PLAYERS, 2);
		addDefaultValue(MineopolyConstants.C_SESSION_TIMEOUT, 2);
		addDefaultValue(MineopolyConstants.C_SCHEMA_WORLD, "world");
		addDefaultValue(MineopolyConstants.C_SCHEMA_NEEDS_PASTE, true);
		addDefaultValue(MineopolyConstants.C_SCHEMA_ORIGIN_X, 0);
		addDefaultValue(MineopolyConstants.C_SCHEMA_ORIGIN_Y, 0);
		addDefaultValue(MineopolyConstants.C_SCHEMA_ORIGIN_Z, 0);
		addDefaultValue(MineopolyConstants.C_SHOW_TIPS, true);
		addDefaultValue(MineopolyConstants.C_TIP_INTERVAL, minute * 2);
//		addDefaultValue(MineopolyConstants.C_VOTING_INTERVAL, 0);
		addDefaultValue(MineopolyConstants.C_VOTING_TIME_LIMIT, minute);
		addDefaultValue(MineopolyConstants.C_WIN_METHOD, "MONEY");
		addDefaultValue(MineopolyConstants.C_WIN_REWARD, 0);
		save();
	}
	
	public boolean addEvenWhenBanned(){
		return getBoolean(MineopolyConstants.C_ADD_BANNED);
	}
	
	public boolean allowAutomaticTurnEnding(){
		return getBoolean(MineopolyConstants.C_AUTO_TURN_END);
	}
	
	public Location boardOrigin(){
		World mWorld = Mineopoly.plugin.getServer().getWorld(getString(MineopolyConstants.C_SCHEMA_WORLD));
		return new Location(mWorld, schematicX(), schematicY(), schematicZ());
	}

	public int maxPlayers(){
		return getInt(MineopolyConstants.C_MAX_PLAYERS);
	}

	public int minPlayers(){
		return getInt(MineopolyConstants.C_MIN_PLAYERS);
	}
	
	public int schematicX(){
		return getInt(MineopolyConstants.C_SCHEMA_ORIGIN_X);
	}
	
	public int schematicY(){
		return getInt(MineopolyConstants.C_SCHEMA_ORIGIN_Y);
	}
	
	public int schematicZ(){
		return getInt(MineopolyConstants.C_SCHEMA_ORIGIN_Z);
	}
	
	public double sessionTimeout(){
		return getDouble(MineopolyConstants.C_SESSION_TIMEOUT);
	}
	
	public void setBoardOrigin(Location location){
		setString(MineopolyConstants.C_SCHEMA_WORLD, location.getWorld().getName());
		setInt(MineopolyConstants.C_SCHEMA_ORIGIN_X, location.getBlockX());
		setInt(MineopolyConstants.C_SCHEMA_ORIGIN_Y, location.getBlockY());
		setInt(MineopolyConstants.C_SCHEMA_ORIGIN_Z, location.getBlockZ());
		save();
		reload();
	}
	
	public boolean showTips(){
		return getBoolean(MineopolyConstants.C_SHOW_TIPS);
	}
	
	public int tipInterval(){
		return getInt(MineopolyConstants.C_TIP_INTERVAL);
	}
	
	public boolean useMetrics(){
		return getBoolean(MineopolyConstants.C_METRICS_SEND);
	}
	
//	public int votingInterval(){
//		return getInt(MineopolyConstants.C_VOTING_INTERVAL);
//	}
	
	public int votingTimeLimit(){
		return getInt(MineopolyConstants.C_VOTING_TIME_LIMIT);
	}
	
	public WinMethod winMethod(){
		return WinMethod.getWinMethod(getString(MineopolyConstants.C_WIN_METHOD));
	}
	
	public double winReward(){
		return getDouble(MineopolyConstants.C_WIN_REWARD);
	}
	
}
