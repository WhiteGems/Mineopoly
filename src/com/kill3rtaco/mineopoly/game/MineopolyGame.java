package com.kill3rtaco.mineopoly.game;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.chat.MineopolyChatChannel;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.game.tasks.MineopolySessionTask;
import com.kill3rtaco.mineopoly.game.tasks.MineopolyTimeLimitTask;
import com.kill3rtaco.mineopoly.game.tasks.MineopolyTipTask;
import com.kill3rtaco.mineopoly.game.tasks.MineopolyVotingTask;
import com.kill3rtaco.mineopoly.saves.MineopolySaveGame;
import com.kill3rtaco.tacoapi.TacoAPI;

public class MineopolyGame {

	private MineopolyBoard board;
	private MineopolyChatChannel channel;
	private MineopolySaveGame save;
	private int index = 0, sessionTaskId, timeLimitTaskId, tipTaskId, votingTaskId;
	private int continueVotes = 0, endVotes = 0;
	private long start = 0, end = 0, voteStart;
	private boolean running, loadedFromSave = false, pollsOpen = false;
	public HashMap<String, Location> locations = new HashMap<String, Location>();
	private ArrayList<String> voted;
	
	public MineopolyGame(){
		if(canStart()){
			start = System.currentTimeMillis();
			Mineopoly.plugin.chat.out("[游戏] 载入新的 Mineopoly 游戏...");
			board = new MineopolyBoard();
			channel = new MineopolyChatChannel();
			addPlayers();
			nextPlayer();
			this.sessionTaskId = Mineopoly.plugin.getServer().getScheduler()
					.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolySessionTask(), 0L, 20 * 5L);
			if(Mineopoly.houseRules.timeLimit() > 0){
				this.timeLimitTaskId = Mineopoly.plugin.getServer().getScheduler()
						.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolyTimeLimitTask(), 0L, 20 * 5L);
			}
			if(Mineopoly.config.showTips()){
				int interval = Mineopoly.config.tipInterval();
				this.tipTaskId = Mineopoly.plugin.getServer().getScheduler()
						.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolyTipTask(), interval, 20 * interval);
			}
			this.running = true;
			Mineopoly.plugin.chat.out("[游戏] 载入完毕!");
		}else{
			this.running = false;
		}
	}
	
	public MineopolyGame(MineopolySaveGame save){
		if(canStart(save)){
			start = System.currentTimeMillis();
			Mineopoly.plugin.chat.out("[游戏] 从文件 " + save.getFilename() + " 中载入游戏...");
			this.save = save;
			board = new MineopolyBoard();
			channel = new MineopolyChatChannel();
			this.loadedFromSave = true;
			Mineopoly.plugin.chat.out("[游戏] 载入完毕!");
		}
	}

	public void save(String name){
		File file = new File(Mineopoly.plugin.getDataFolder() + "/saves/" + name + ".yml");
		MineopolySaveGame save = new MineopolySaveGame(file);
		save.setData(Mineopoly.plugin.getGame());
		this.loadedFromSave = true;
		this.save = save;
	}
	
	public MineopolySaveGame getSave(){
		return save;
	}
	
	public void setData(){
		if(!loadedFromSave) return;
		addPlayers();
		channel.sendMessage("&a设置轮流次序...");
		String currentTurn = save.getString("game.current-turn");
		String turnOrder = save.getString("game.turn-order");
		if(save.contains("game.time-running")){
			setTimeRunning(save.getTimeRunning());
		}
		String[] names = turnOrder.split(" ");
		channel.sendMessage("&a设置 MineopolyPot 内容...");
		MineopolyPot pot = board.getPot();
		pot.setMoney(save.getInt("board.pot.amount"));
		if(save.getBoolean("board.pot.card_chance")){
			pot.addChanceJailCard();
		}
		if(save.getBoolean("board.path.card_community-chest")){
			pot.addCommunityChestJailCard();
		}
		for(int i=0; i<names.length; i++){
			if(names[i].equalsIgnoreCase(currentTurn)){
				index = i;
			}
		}
		
		channel.sendMessage("&a正在分发财产给玩家们...");
		for(MineopolyPlayer mp : board.getPlayers()){
			String playerRoot = "players." + mp.getName();
			List<Integer> properties = save.getIntList(playerRoot + ".properties.owned");
			//assign properties
			for(int i : properties){
				String propertyRoot = playerRoot + "properties." + i;
				MineopolySection section = board.getSection(i);
				if(section instanceof OwnableSection){
					OwnableSection ownable = (OwnableSection) section;
					if(ownable instanceof Property){
						Property property = (Property) ownable;
						if(save.getBoolean(propertyRoot + ".hotel")){
							property.addHotel();
						}else{
							property.setHouses(save.getInt(propertyRoot + ".houses"));
						}
						mp.addSection(property);
					}else{
						mp.addSection(ownable);
					}
				}
			}
			boolean jailed = save.getBoolean(playerRoot + ".jailed");
			if(!jailed){
				MineopolySection dest = board.getSection(save.getInt(playerRoot + ".section"));
				mp.setCurrentSection(dest, false, false);
			}
			
			mp.setBalance(save.getInt(playerRoot + ".balance"));
			if(save.getBoolean(playerRoot + ".card_chance")){
				mp.giveChanceJailCard();
			}
			
			if(save.getBoolean(playerRoot + ".card_community-chest")){
				mp.giveCommunityChestJailCard();
			}
			
			if(jailed){
				mp.setJailed(true, true);
			}
			
			mp.setTotalRolls(save.getInt(playerRoot + ".rolls"));
			if(save.contains(playerRoot + ".go-passes")){
				mp.setGoPasses(save.getInt(playerRoot + ".go-passes"));
			}else{
				mp.setGoPasses(Mineopoly.houseRules.purchaseAfterGoPasses());
			}
		}
		channel.sendMessage("&a游戏准备完毕! 准备开始游戏...");
		nextPlayer();
		this.sessionTaskId = Mineopoly.plugin.getServer().getScheduler()
				.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolySessionTask(), 0L, 20 * 5L);
		if(Mineopoly.houseRules.timeLimit() > 0){
			this.timeLimitTaskId = Mineopoly.plugin.getServer().getScheduler()
					.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolyTimeLimitTask(), 0L, 20 * 5L);
		}
		if(Mineopoly.config.showTips()){
			int interval = Mineopoly.config.tipInterval() * 20;
			this.tipTaskId = Mineopoly.plugin.getServer().getScheduler()
					.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolyTipTask(), interval, interval);
		}
		this.running = true;
	
	}
	
	private void addPlayers(){
		Mineopoly.plugin.chat.out("[游戏] [玩家] 添加玩家中...");
		if(loadedFromSave){
			String turnOrder = save.getString("game.turn-order");
			String[] names = turnOrder.split(" ");
			for(String s : names){
				Player player = Mineopoly.plugin.getServer().getPlayer(s);
				MineopolyPlayer mp = new MineopolyPlayer(player);
				board.addPlayer(mp);
				channel.addPlayer(mp);
				Mineopoly.plugin.chat.out("[游戏] [玩家] 已添加玩家: " + mp.getName());
			}
		}else{
			int queueSize = Mineopoly.plugin.getQueue().getSize();
			int maxPlayers = Mineopoly.config.maxPlayers();
			for(int i=1; i<=queueSize; i++){
				if(i < maxPlayers && queueSize > 0){
					Random random = new Random();
					int index = random.nextInt(Mineopoly.plugin.getQueue().getSize());
					Player p = Mineopoly.plugin.getQueue().getPlayer(index);
					MineopolyPlayer player = new MineopolyPlayer(p);
					board.addPlayer(player);
					channel.addPlayer(player);
					Mineopoly.plugin.chat.out("[游戏] [玩家] 已添加玩家: " + player.getName());
					Mineopoly.plugin.getQueue().removePlayer(index);
					player.setCurrentSection(board.getSection(0), false);
				}else{
					break;
				}
			}
		}
		Mineopoly.plugin.chat.out("[游戏] [玩家] 已完成!");
	}
	
	public void nextPlayer(){
		if(index > board.getPlayers().size() - 1)
			index = 0;
		board.getPlayers().get(index).setTurn(true, false);
		MineopolyPlayer currPlayer = getPlayerWithCurrentTurn();
		channel.sendMessage("&3现在该 &b" + currPlayer.getName() + "&3 行动了", currPlayer);
		if(currPlayer != null){
			currPlayer.sendMessage("&3现在该你行动了");
			if(currPlayer.isJailed())
				currPlayer.sendMessage("&3使用 &b/" + Mineopoly.getJAlias() + " roll&3 来掷骰子!");
			else
				currPlayer.sendMessage("&3使用 &b/" + Mineopoly.getMAlias() + " roll&3 来掷骰子!");
		}
		index++;
	}
	
	public MineopolyPlayer getPlayerWithCurrentTurn(){
		for(MineopolyPlayer p : board.getPlayers()){
			if(p.hasTurn()){
				return p;
			}
		}
		return null;
	}
	
	public MineopolyBoard getBoard(){
		return this.board;
	}
	
	public MineopolyChatChannel getChannel(){
		return channel;
	}
	
	public boolean hasPlayer(Player player){
		if(!running) return false;
		for(MineopolyPlayer p : board.getPlayers()){
			if(p.getName().equalsIgnoreCase(player.getName()))
				return true;
		}
		return false;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public boolean isLoadedFromSave(){
		return loadedFromSave;
	}
	
	public int getSessionTaskId(){
		return sessionTaskId;
	}
	
	public void end(MineopolyPlayer winner){
		this.running = false;
		for(Property p : board.getProperties()){
			if(p.hasHotel() || p.getHouses() > 0){
				p.clearImprovements();
			}
		}
		Mineopoly.plugin.getServer().getScheduler().cancelTask(sessionTaskId);
		Mineopoly.plugin.getServer().getScheduler().cancelTask(timeLimitTaskId);
		if(tipTaskId > -1) Mineopoly.plugin.getServer().getScheduler().cancelTask(tipTaskId);
		if(winner == null) winner = determineWinner();
		board.removeAllPlayers();
		Mineopoly.plugin.chat.sendGlobalMessage("&eMineopoly 游戏已结束");
		Mineopoly.plugin.chat.sendGlobalMessage("&3" + winner.getName() + " &b是本场比赛的第一名!");
		if(TacoAPI.isEconAPIOnline()){
			double reward = Mineopoly.config.winReward();
			if(reward > 0){
				String singular = TacoAPI.getEconAPI().currencyName();
				String plural = TacoAPI.getEconAPI().currencyNamePlural();
				Mineopoly.plugin.chat.sendGlobalMessage("&a" + winner.getName() + " &e得到了 &2" + reward + " " + (reward == 1 ? singular : plural) + " &e作为第一名的奖励");
				TacoAPI.getEconAPI().deposit(winner.getName(), reward);
			}
		}
		if(Mineopoly.plugin.getGame().canStart()){
			Mineopoly.plugin.chat.sendGlobalMessage("&e下一场比赛很快就要开始了");
		}else{
			Mineopoly.plugin.chat.sendGlobalMessage("&e下一场比赛将会在有足够等候者的时候开始");
		}
		end = System.currentTimeMillis();
	}
	
	public void end(){
		end(null);
	}
	
	public long getStartTime(){
		return start;
	}
	
	public long getEndTime(){
		return end;
	}
	
	public void setTimeRunning(long time){
		start = System.currentTimeMillis() - time;
	}
	
	public String getTimeRunningString() {
		return getTimeString(getTimeRunning());
	}
	
	public long getTimeRunning(){
		if(end > 0){
			return end - start;
		}
		return System.currentTimeMillis() - start;
	}
	
	public String getTimeLeftString(){
		return getTimeString(getTimeLeft());
	}
	
	public long getTimeLeft(){
		double timeLimit = Mineopoly.houseRules.timeLimit();
		if(!running) return 0;
		if(timeLimit > 0){
			return ((long) timeLimit * 1000L * 60) - (System.currentTimeMillis() - start);
		}
		return 0;
	}
	
	private String getTimeString(long time){
		long second = 1000L;
		long minute = second * 60;
		long hour = minute * 60;
		long day = hour * 24;
		long year = day * 365;
		
		long years = time / year;
		long days = time % year / day;
		long hours = time % year % day / hour;
		long minutes = time % year % day % hour / minute;
		long seconds = time % year % day % hour % minute / second;
		
		String timeString = "";
		if(years > 0) timeString += years + "年 ";
		if(days > 0) timeString += days + "日 ";
		if(hours > 0) timeString += hours + "时 ";
		if(minutes > 0) timeString += minutes + "分 ";
		if(seconds > 0) timeString += seconds + "秒 ";
		return timeString;
	}
	
	public MineopolyPlayer determineWinner(){
		WinMethod wm = Mineopoly.config.winMethod();
		if(wm == WinMethod.MONEY){
			MineopolyPlayer max = null;
			for(MineopolyPlayer mp : board.getPlayers()){
				if(max == null){
					max = mp;
				}else if(mp.getBalance() > max.getBalance()){
					max = mp;
				}
			}
			return max;
		}else if(wm == WinMethod.PROPERTY_AMOUNT){
			MineopolyPlayer max = null;
			for(MineopolyPlayer mp : board.getPlayers()){
				if(max == null){
					max = mp;
				}else if(mp.ownedSections().size() > max.ownedSections().size()){
					max = mp;
				}
			}
			return max;
		}else{ //property value
			MineopolyPlayer max = null;
			for(MineopolyPlayer mp : board.getPlayers()){
				if(max == null){
					max = mp;
				}else if(mp.valueOfOwnedSections() > max.valueOfOwnedSections()){
					max = mp;
				}
			}
			return max;
		}
	}
	
	public boolean canStart(){
		return Mineopoly.plugin.getQueue().getSize() >= Mineopoly.config.minPlayers();
	}
	
	public boolean canStart(MineopolySaveGame save){
		String[] players = save.getString("game.turn-order").split("\\s+");
		for(String s : players){
			Player player = Mineopoly.plugin.getServer().getPlayer(s);
			if(player == null || !player.isOnline()){
				return false;
			}
		}
		return true;
	}
	
	public void kick(MineopolyPlayer player, String reason){
		for(MineopolySection section : player.ownedSections()){
			if(section instanceof OwnableSection){
				((OwnableSection) section).reset();
			}
		}
		
		if(player.hasMoney(1))
			getBoard().getPot().addMoney(player.getBalance());
		Mineopoly.plugin.chat.sendGlobalMessage("&3" + player.getName() + " &b已经被移出游戏 (&3" + reason + "&b)");
		MineopolyPlayer current = Mineopoly.plugin.getGame().getPlayerWithCurrentTurn();
		boolean next = player.getName().equalsIgnoreCase(current.getName());
		board.removePlayer(player);
		if(board.getPlayers().size() == 1){
			Mineopoly.plugin.chat.sendGlobalMessage("&3" + board.getPlayers().get(0).getName() + " &b获得了胜利!");
			end();
			return;
		}
		if(next){
			nextPlayer();
		}
	}
	
	public String getTurnOrder(){
		String turnOrder = "";
		for(int i=0; i<board.getPlayers().size(); i++){
			turnOrder += board.getPlayers().get(i).getName() + " ";
		}
		return turnOrder;
	}
	
	public boolean pollsAreOpen(){
		return pollsOpen;
	}
	
	public void openPolls(){
		voted = new ArrayList<String>();
		continueVotes = 0;
		endVotes = 0;
		pollsOpen = true;
		channel.sendPlayersMessage("&3有人想结束游戏! 投票现已开始!");
		voteStart = System.currentTimeMillis();
		votingTaskId = Mineopoly.plugin.getServer().getScheduler()
				.scheduleSyncRepeatingTask(Mineopoly.plugin, new MineopolyVotingTask(), 0L, 20L);
	}
	
	public void closePolls(){
		pollsOpen = false;
		channel.sendPlayersMessage("&3投票已停止!");
		channel.sendPlayersMessage("&3结果&7: &9继续游戏 &7- &b" + continueVotes + " &9停止游戏 &7- &b" + endVotes);
		if(testVotes()){
			end();
		}else{
			continueVotes = 0;
			endVotes = 0;
			channel.sendPlayersMessage("&3游戏将继续进行");
		}
		Mineopoly.plugin.getServer().getScheduler().cancelTask(votingTaskId);
	}
	
	public void addVote(boolean end){
		if(end) endVotes++;
		else continueVotes++;
		if(testVotes()){
			closePolls();
		}
	}
	
	public int getVote(boolean end){
		if(end) return endVotes;
		else return continueVotes;
	}
	
	public long getVoteStart(){
		return voteStart;
	}
	
	public boolean testVotes(){
		int players = board.getPlayers().size();
		if(endVotes >= (players / 2) + 1){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean hasVoted(String name){
		for(String s : voted){
			if(s.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
}
