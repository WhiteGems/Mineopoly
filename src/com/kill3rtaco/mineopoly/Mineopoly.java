package com.kill3rtaco.mineopoly;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kill3rtaco.mineopoly.cmds.JailCommandHandler;
import com.kill3rtaco.mineopoly.cmds.MineopolyCommandHandler;
import com.kill3rtaco.mineopoly.cmds.PropertyCommandHandler;
import com.kill3rtaco.mineopoly.cmds.TradeCommandHandler;
import com.kill3rtaco.mineopoly.cmds.VoteCommandHandler;
import com.kill3rtaco.mineopoly.game.MineopolyGame;
import com.kill3rtaco.mineopoly.game.MineopolyQueue;
import com.kill3rtaco.mineopoly.game.config.MineopolyConfig;
import com.kill3rtaco.mineopoly.game.config.MineopolyHouseRulesConfig;
import com.kill3rtaco.mineopoly.game.config.MineopolyNamesConfig;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.listener.MineopolyListener;
import com.kill3rtaco.mineopoly.saves.MineopolySaveGame;
import com.kill3rtaco.mineopoly.test.MineopolyPluginTester;

import com.kill3rtaco.tacoapi.TacoAPI;
import com.kill3rtaco.tacoapi.api.TacoPlugin;

public class Mineopoly extends TacoPlugin{

	public static MineopolyConfig config;
	public static MineopolyNamesConfig names;
	public static MineopolyHouseRulesConfig houseRules;
	private MineopolyGame game;
	public static Mineopoly plugin;
	private File banned;
	private ArrayList<String> bannedPlayers;
	private MineopolyQueue queue;
	private static String J_ALIAS, M_ALIAS, P_ALIAS, T_ALIAS, V_ALIAS;
	
	public void onStop(){
		if(game.isRunning()){
			for(Property p : game.getBoard().getProperties()){
				if(p.isOwned() && (p.hasHotel() || p.getHouses() > 0)){
					p.clearImprovements();
				}
			}
		}
		chat.out("已禁用");
	}
	
	public void onStart(){
		checkVersion();
		plugin = this;
		config = new MineopolyConfig();
		names = new MineopolyNamesConfig();
		houseRules = new MineopolyHouseRulesConfig();
		queue = new MineopolyQueue();
		registerCommand(new MineopolyCommandHandler());
		registerCommand(new PropertyCommandHandler());
		registerCommand(new JailCommandHandler());
		registerCommand(new VoteCommandHandler());
		registerCommand(new TradeCommandHandler());
		registerEvents(new MineopolyListener());
		banned = new File(getDataFolder() + "/banned-players.txt");
		bannedPlayers = getBannedPlayers();
		restartGame();
		if(config.useMetrics())
			startMetrics();
	}
	
	public static String getMAlias(){
		if(M_ALIAS == null){
			M_ALIAS = TacoAPI.getServerAPI().getShortestAlias(plugin, "mineopoly");
		}
		return M_ALIAS;
	}
	
	public static String getPAlias(){
		if(P_ALIAS == null){
			P_ALIAS = TacoAPI.getServerAPI().getShortestAlias(plugin, "property");
		}
		return P_ALIAS;
	}
	
	public static String getJAlias(){
		if(J_ALIAS == null){
			J_ALIAS = TacoAPI.getServerAPI().getShortestAlias(plugin, "jail");
		}
		return J_ALIAS;
	}
	
	public static String getVAlias(){
		if(V_ALIAS == null){
			V_ALIAS = TacoAPI.getServerAPI().getShortestAlias(plugin, "vote");
		}
		return V_ALIAS;
	}
	
	public static String getTAlias(){
		if(T_ALIAS == null){
			T_ALIAS = TacoAPI.getServerAPI().getShortestAlias(plugin, "trade");
		}
		return T_ALIAS;
	}
	
	public String getAliases(String command){
		List<String> aliases = getCommand(command).getAliases();
		return "&b" + TacoAPI.getChatUtils().join(aliases.toArray(new String[]{}), "&7, &b");
	}
	
	public MineopolyQueue getQueue(){
		return queue;
	}
	
	public MineopolyGame getGame(){
		return game;
	}
	
	public void restartGame(){
		restartGame(false);
	}
	
	public void restartGame(boolean runTests){
		long timeStart = System.currentTimeMillis();
		game = new MineopolyGame();
		long timeEnd = System.currentTimeMillis();
		double time = (timeEnd - timeStart) / 1000;
		if(game.isRunning()) chat.out("[游戏] 已在 " + time + " 秒内载入完毕");
		if(runTests && game.isRunning()) MineopolyPluginTester.run();
	}
	
	public void resumeGame(MineopolySaveGame save){
		long timeStart = System.currentTimeMillis();
		game = new MineopolyGame(save);
		long timeEnd = System.currentTimeMillis();
		double time = (timeEnd - timeStart) / 1000;
		if(game.isRunning()) chat.out("[游戏] 已在 " + time + " 秒内载入完毕");
		game.setData();
	}
	
	public ArrayList<String> getBannedPlayers(){
		if(bannedPlayers != null){
			return bannedPlayers;
		}else{
			ArrayList<String> bp = new ArrayList<String>();
			try {
				if(!banned.exists()){
					chat.out("[封禁] 未找到封禁文件, 正在创建中...");
					banned.createNewFile();
					chat.out("[封禁] 文件已创建");
					return bp;
				}else{
					chat.out("[封禁] 封禁文件已找到, 重新载入中...");
					Scanner x = new Scanner(banned);
					while(x.hasNextLine()){
						String line = x.nextLine();
						if(!line.isEmpty()){
							String[] parts = line.split("\\s");
							if(parts.length > 0 && !parts[0].isEmpty()){
								bp.add(parts[0]);
								continue;
							}
						}
					}
					x.close();
					chat.out("[封禁] 找到了 " + bp.size() + " 个封禁玩家");
					chat.out("[封禁] 已完成!");
				}
			} catch (IOException e) {
				return bp;
			}
			return bp;
		}
	}
	
	private void checkVersion(){
		try {
			URL url = new URL("http://ww.kill3rtaco.com/assets/mineopoly/version.txt");
			Scanner x = new Scanner(url.openStream());
			String version = null, file = null, type = null, date = null;
			while(x.hasNextLine()){
				String line = x.nextLine();
				String[] parts = line.split("\\t+");
				version = parts[0];
				file = parts[1];
				type = parts[2];
				date = parts[3];
			}
			x.close();
			if(version == null || file == null || type == null || date == null){
				chat.outWarn("不能验证版本");
				return;
			}
			String[] vParts = getDescription().getVersion().split("\\.");
			String[] fvParts = version.split("\\.");
			for(int i=0; i<fvParts.length; i++){
				int j = Integer.parseInt(fvParts[i]); //file
				int k = Integer.parseInt(vParts[i]); //plugin
				if(j == k) continue;
				if(j > k){
					chat.outWarn("Mineopoly ( 版本号 " + getDescription().getVersion() + ") 已经过期." +
							" Mineopoly 版本号 " + version + " 已在 " + date + " 时发布.");
					chat.outWarn("你可以在此处下载最新版本: http://dev.bukkit.org/server-mods/files/" + file);
					return;
				}else if(j < k){
					chat.outSevere("&c你怎么会在使用一个未来的版本? 你是一名开发者?");
					return;
				}
			}
			chat.out("&a这是目前最新版本的 Mineopoly");
		} catch (MalformedURLException e) {
			chat.outWarn("不能验证版本");
		} catch (IOException e) {
			chat.outWarn("不能验证版本");
		} catch (IndexOutOfBoundsException e){
			chat.outWarn("不能验证版本");
		}
	}
	
	public void banPlayer(String name){
		if(!bannedPlayers.contains(name)){
			bannedPlayers.add(name);
			writeBansToFile();
		}
	}
	
	public void unbanPlayer(String name){
		if(bannedPlayers.contains(name)){
			bannedPlayers.remove(name);
			writeBansToFile();
		}
	}
	
	public boolean isBanned(String name){
		for(String s : bannedPlayers){
			if(s.equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	
	private void writeBansToFile(){
		if(banned.exists()) banned.delete();
		try {
			banned.createNewFile();
			FileWriter writer = new FileWriter(banned);
			for(String s : bannedPlayers){
				writer.append(s + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
