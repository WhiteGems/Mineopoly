package com.kill3rtaco.mineopoly.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.bukkit.entity.Player;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.cards.CardResult;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCard;
import com.kill3rtaco.mineopoly.game.chat.MineopolyChannelListener;
import com.kill3rtaco.mineopoly.game.menus.MineopolyMenu;
import com.kill3rtaco.mineopoly.game.sections.ActionProvoker;
import com.kill3rtaco.mineopoly.game.sections.CardSetSection;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.game.sections.SectionType;
import com.kill3rtaco.mineopoly.game.sections.SpecialSquare;
import com.kill3rtaco.mineopoly.game.sections.squares.FreeParkingSquare;
import com.kill3rtaco.mineopoly.game.sections.squares.JailSquare;
import com.kill3rtaco.mineopoly.game.trading.TradeRequest;

import com.kill3rtaco.tacoapi.TacoAPI;

public class MineopolyPlayer extends MineopolyChannelListener{

	private boolean jailed = false;
	private boolean canRoll;
	private boolean hasRolled = false;
	private boolean hasTurn = false;
	private boolean needsGoMoney = false;
	private boolean payRent = true;
	private boolean endTurnAuto = false;
	private int money = Mineopoly.houseRules.startingMoney();
	private int roll1;
	private int roll2;
	private int totalRolls = 0;
	private int doubleRolls = 0;
	private int jailRolls = 0;
	private int goPasses = 0;
	private boolean chanceJailCard;
	private boolean ccJailCard;
	private MineopolySection sectionOn;
	private ArrayList<OwnableSection> ownedSections = new ArrayList<OwnableSection>();
	private ArrayList<TradeRequest> sentRequests = new ArrayList<TradeRequest>();
	private ArrayList<TradeRequest> receivedRequests = new ArrayList<TradeRequest>();
	private ArrayList<MineopolyColor> monopolies = new ArrayList<MineopolyColor>();

	public MineopolyPlayer(Player player) {
		super(player);
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public void setBalance(int amount){
		this.money = amount;
		sendBalanceMessage();
	}
	
	public void setTurn(boolean turn, boolean supressDoubles){
		this.hasTurn = turn;
		this.canRoll = turn;
		this.hasRolled = !turn;
		if(!turn){
			if(roll1 == roll2 && !supressDoubles && !jailed){
				this.hasTurn = true;
				this.canRoll = true;
				Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + "&3 已结束了他的回合, 但是他因为摇到了偶数所以将再掷一次骰子...");
				roll();
			}else{
				Mineopoly.plugin.getGame().nextPlayer();
			}
		}
	}
	
	/**
	 * Gets the amount of money that this player has. Note that this money is separate from any money
	 * earned in the server economy, as the game uses a separate economy to play the game.
	 * @return The amount of money this player has
	 */
	public int getBalance(){
		return this.money;
	}
	
	public boolean hasRolled(){
		return this.hasRolled;
	}
	
	public boolean hasTurn(){
		return this.hasTurn;
	}
	
	private void payRent(){
		if(sectionOn instanceof OwnableSection){
			OwnableSection section = (OwnableSection)sectionOn;
			if(section.isOwned()){
				if(section.getOwner().isJailed() && !Mineopoly.houseRules.collectWhileJailed()){
					Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + section.getOwner() + " &3不能因 &b" + getName() + 
							" &3走在 " + sectionOn.getColorfulName() + " &3上而得到租金因为他已进入 &1监狱", section.getOwner());
					section.getOwner().sendMessage("&3你将不会因为 &b" + getName() + " &3走在 " + sectionOn.getColorfulName() +
							" &3上而支付租金, 因为你已进入 &1监狱");
				}else{
					if(!section.getOwner().getName().equalsIgnoreCase(getName())){
						Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + " &3支付了 &b" + section.getOwner().getName() +
								" &2" + section.getRent() + " &3由于踏上了 " + sectionOn.getColorfulName(), this);
						sendMessage("&3你支付了 &b" + section.getOwner() + " &2" + section.getRent() + " &3由于踏上了 " + sectionOn.getColorfulName());
						takeMoney(section.getRent());
						section.getOwner().addMoney(section.getRent());
					}
				}
			}
		}
	}
	
	public boolean canBuy(OwnableSection section){
		return getBalance() >= section.getPrice();
	}
	
	public void getInfo(Player p){
		int cards = 0;
		if(hasChanceJailCard()) cards++;
		if(hasCommunityChestJailCard()) cards++;
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, TacoAPI.getChatUtils().createHeader("&3Mineopoly&7: &b" + getName()));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&3金钱&7: &2" + getBalance());
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&3骰数&7: &2" + getTotalRolls() + " &3于地盘: " + getCurrentSection().getColorfulName() + " &3(&b" + getCurrentSection().getId() + "&3)" +
				(getCurrentSection().getId() == 10 ? (isJailed() ? "&7[&c监狱&7]" : "&7[&a只是参观&7])") : ""));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&财产&7: &b" + ownedPropertiesSize());
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&3Monopolies&7: &b" + monopolySize());
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&3免费离开监狱卡片&7: &2" + (cards > 0 ? cards : "无"));
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "");
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&8我的财产: &7/mineopoly deeds " + getName());
		Mineopoly.plugin.chat.sendPlayerMessageNoHeader(p, "&8我的monopolies: &7/mineopoly monopolies " + getName());
	}
	
	public boolean hasMonopoly(MineopolyColor color){
		if(monopolies.contains(color))
			return true;
		else
			return false;
	}
	
	public void addMoney(int amount){
		addMoney(amount, true);
		int cap = Mineopoly.houseRules.moneyCap();
		if(cap > 0 && cap > Mineopoly.houseRules.startingMoney()){
			Mineopoly.plugin.getGame().end(this);
		}
	}
	
	public void addMoney(int amount, boolean sendMessage){
		this.money += amount;
		if(sendMessage) sendBalanceMessage();
	}
	
	public void takeMoney(int amount){
		takeMoney(amount, true);
	}
	
	public void takeMoney(int amount, boolean sendMessage){
		this.money -= amount;
		if(sendMessage) sendBalanceMessage();
	}
	
	public void sendBalanceMessage(){
		sendMessage("&3你的资金现在是 &2" + getBalance());
	}
	
	public void payPot(int amount){
		payPot(amount, true);
	}
	
	public void payPot(int amount, boolean sendMessage){
		this.money -= amount;
		Mineopoly.plugin.getGame().getBoard().getPot().addMoney(amount);
		if(sendMessage) sendBalanceMessage();
	}
	
	public boolean hasMoney(int amount){
		return getBalance() >= amount;
	}
	
	public void payPlayer(MineopolyPlayer player, int amount){
		payPlayer(player, amount, true, true);
	}
	
	public void payPlayer(MineopolyPlayer player, int amount, boolean sendMessageSender, boolean sendMessageReceiver){
		takeMoney(amount, sendMessageSender);
		player.addMoney(amount, sendMessageReceiver);
	}
	
	public void roll(){
		this.totalRolls++;
		this.canRoll = false;
		this.hasRolled = true;
		Random random = new Random();
		this.roll1 = random.nextInt(6) + 1;
		this.roll2 = random.nextInt(6) + 1;
		int sum = roll1 + roll2 + getCurrentSection().getId();
		if(sum > 39){
			sum -= 40;
		}

		Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + " &3掷出了 &b" + roll1 + "&3 以及 &b" + roll2, this);
		
		if(roll1 == roll2){
			doubleRolls++;
			if(doubleRolls == 3){
				doubleRolls = 0;
				setJailed(true, true);
				Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + "&3 由于三次掷出相同的数字而被关进监狱", this);
				sendMessage("&3你因为三次掷出相同的数字而被关进监狱");
			}else{
				moveForward(sum);
			}
		}else{
			doubleRolls = 0;
			moveForward(sum);
		}
	}
	
	public int getHotels(){
		int hotels = 0;
		for(MineopolySection s : ownedSections()){
			if(s instanceof Property){
				if(((Property) s).hasHotel()) hotels ++;
			}
		}
		return hotels;
	}
	
	public int getHouses(){
		int houses = 0;
		for(MineopolySection s : ownedSections()){
			if(s instanceof Property){
				houses += ((Property) s).getHouses();
			}
		}
		return houses;
	}
	
	private void moveForward(int forward){
		MineopolySection next = Mineopoly.plugin.getGame().getBoard().getSection(forward);
		if(!(getCurrentSection() instanceof FreeParkingSquare))
			Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + " &3停在了 " + next.getColorfulName(), this);
		sendMessage("&3你掷出了 &b" + roll1 + "&3 以及 &b" + roll2);
		if(!(getCurrentSection() instanceof FreeParkingSquare))
			sendMessage("&3你停在了 " + next.getColorfulName());
		setCurrentSection(next);
	}
	
	public void moveToNearestRailroad(){
		int id = getCurrentSection().getId();
		if(id > 35 || id < 5)
			setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(5));
		else if(id > 5 && id < 15)
			setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(15));
		else if(id > 15 && id < 25)
			setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(25));
		else if(id > 25 && id < 35)
			setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(35));
	}
	
	public void moveToNearestUtility(){
		int id = getCurrentSection().getId();
		if(id < 12 || id > 24)
			setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(12));
		else
			setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(24));
	}
	
	public void setJailRolls(int rolls){
		jailRolls = rolls;
	}
	
	public int getJailRolls(){
		return jailRolls;
	}
	
	public int getTotalRolls(){
		return totalRolls;
	}
	
	public void setTotalRolls(int rolls){
		this.totalRolls = rolls;
	}
	
	public boolean canRoll(){
		return this.canRoll;
	}
	
	public boolean canEndTurnAutomatically(){
		return this.endTurnAuto;
	}
	
	public void setCanEndTurnAutomatically(boolean endTurn){
		this.endTurnAuto = endTurn;
	}
	
	public void moveWithoutRent(MineopolySection section){
		payRent = false;
		setCurrentSection(section);
	}
	
	public void setCurrentSection(MineopolySection section){
		setCurrentSection(section, true);
	}
	
	public void setCurrentSection(MineopolySection section, boolean goMoney){
		setCurrentSection(section, goMoney, true);
	}
	
	public void setCurrentSection(MineopolySection section, boolean process, boolean goMoney){
		setCurrentSection(section, goMoney, process, true);
	}
	
	public void setCurrentSection(MineopolySection section, boolean goMoney, boolean process, boolean endAuto){
		if(process){
			int lastId = 0;
			if(this.sectionOn != null)
				lastId = this.sectionOn.getId();
			this.sectionOn = section;
			
			if(section.getId() < lastId && section.getId() != 0){
				if(section instanceof JailSquare){
					if(isJailed()){
						needsGoMoney = false;
					}
				}
				needsGoMoney = true;
				goPasses++;
			}else if(lastId == 0 && totalRolls > 1){
				needsGoMoney = true;
			}else if(goMoney && totalRolls > 1 && section.getId() == 0 && Mineopoly.houseRules.landOnGoBonus() > 0){
				int bonus = Mineopoly.houseRules.landOnGoBonus();
				Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + " &3停在了 &6出发 &3并已获得 &2" + bonus + " 作为奖励", this);
				sendMessage("&3你停在了 &6出发 &3并已得到 &2" + bonus + " 作为奖励");
				addMoney(bonus);
			}
			
			if(!goMoney) needsGoMoney = false;
			
			if(needsGoMoney){
				Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + getName() + " &3走过了 &6出发 &3并已获得 &2200", this);
				sendMessage("&3你走过了 &6出发 &3并已获得 &2200");
				addMoney(200);
				needsGoMoney = false;
			}

			MineopolyCard cardDrawn = null;
			if(section instanceof SpecialSquare){
				if(section instanceof JailSquare){
					JailSquare js = (JailSquare) section;
					if(isJailed()){
						TacoAPI.getPlayerAPI().teleport(getPlayer(), js.getJailCellLocation());
					}else{
						TacoAPI.getPlayerAPI().teleport(getPlayer(), js.getJustVisitingLocation());
						endTurnAuto = true;
					}
				}else{
					TacoAPI.getPlayerAPI().teleport(getPlayer(), section.getLocation());
					SpecialSquare square = (SpecialSquare) section;
					square.provokeAction(this);
				}
			}else if(section instanceof ActionProvoker){
				TacoAPI.getPlayerAPI().teleport(getPlayer(), section.getLocation());
				ActionProvoker ss = (ActionProvoker) section;
				ss.provokeAction(this);
				if(section instanceof CardSetSection){
					CardSetSection css = (CardSetSection) section;
					cardDrawn = css.getLastCard();
					if(cardDrawn.getResult() == CardResult.MONEY_RELATED) endTurnAuto = true;
				}
			}else if(section instanceof OwnableSection){
				TacoAPI.getPlayerAPI().teleport(getPlayer(), section.getLocation());
				if(this.payRent && !((OwnableSection) section).isMortgaged())
					payRent();
				this.payRent = true;
				OwnableSection ownable = (OwnableSection) section;
				if(!ownable.isOwned()){
					String type;
					if(ownable.getType() == SectionType.PROPERTY) type = "property";
					else if(ownable.getType() == SectionType.RAILROAD) type = "railroad";
					else if(ownable.getType() == SectionType.UTILITY) type = "utility";
					else type = "space";
					if(canBuy(ownable)){										
						sendMessage("&3你可以购买这个 &b" + type + " &3并花费 &2" + ownable.getPrice() + " &3来 &b/" + Mineopoly.getPAlias() + " 购买");
					}else{
						sendMessage("&3你没有足够的资金来购买这个 &b" + type + " (&2" + ownable.getPrice() + "&b)");
						endTurnAuto = true;
					}
				}else{
					if(canTravel()){
						sendMessage("&3你可以使用铁路 &b穿过棋盘 &3并使用 &b/" + Mineopoly.getMAlias() + " 来旅行");
					}else{						
						endTurnAuto = true;
					}
				}
			}
			if(cardDrawn != null && cardDrawn.getResult() != CardResult.MOVE_RELATED)
				sendMessage("&3输入 &b/" + Mineopoly.getPAlias() + " info &3来查看这个地方的信息");
			if(endTurnAuto && endAuto){
				endTurnAutomatically();
			}else if(endAuto && !isJailed()){
				if(cardDrawn != null && cardDrawn.getResult() != CardResult.MOVE_RELATED)
					if(totalRolls > 0) sendMessage("&3用 &b/" + Mineopoly.getMAlias() + " et&3来结束你的回合");
			}
		}else{
			this.sectionOn = section;
			TacoAPI.getPlayerAPI().teleport(getPlayer(), section.getLocation());
		}
	}
	
	public void endTurnAutomatically(){
		boolean ate = Mineopoly.config.allowAutomaticTurnEnding();
		if(ate){
			sendMessage("&a自动结束回合");
			getPlayer().chat("/mineopoly end-turn");
		}else{
			sendMessage("&3用 &b/" + Mineopoly.getMAlias() + " et&3来结束你的回合");
		}
		endTurnAuto = false;
	}
	
	public void move(int amount){
		boolean goMoney = true;
		if(amount < 0) goMoney = false;
		int lastId = getCurrentSection().getId();
		MineopolySection next = Mineopoly.plugin.getGame().getBoard().getSection(lastId + amount);
		setCurrentSection(next, goMoney);
	}
	
	public boolean canTravel(){
		if(sectionOn.getType() == SectionType.RAILROAD && Mineopoly.houseRules.travelingRailroads()){
			int across = sectionOn.getId() + 20; 
			OwnableSection rr = (OwnableSection) Mineopoly.plugin.getGame().getBoard().getSection(across);
			if(rr.isOwned() && rr.getOwner().getName().equalsIgnoreCase(this.getName())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public int getOwnedPropertiesWithColor(MineopolyColor color){
		int amount = 0;
		for(MineopolySection s : ownedSections()){
			if(s instanceof Property){
				Property p = (Property) s;
				if(p.getColor() == color)
					amount++;
			}
		}
		return amount;
	}
	
	public boolean ownsCurrentSection(){
		if(getCurrentSection() instanceof OwnableSection){
			OwnableSection section = (OwnableSection) getCurrentSection();
			return ownsSection(section);
		}else{
			return false;
		}
	}
	
	public int ownedProperties(){
		return ownedSections(SectionType.PROPERTY).size();
	}
	
	public int ownedRailRoads(){
		return ownedSections(SectionType.RAILROAD).size();
	}
	
	public int ownedUtilities(){
		return ownedSections(SectionType.UTILITY).size();
	}
	
	public void setJailed(boolean jailed, boolean ignoreDoubles){
		this.jailRolls = 0;
		this.jailed = jailed;
		setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(10), false, true, false);
		setTurn(false, ignoreDoubles);
	}
	
	public boolean isJailed(){
		return this.jailed;
	}
	
	public void giveChanceJailCard(){
		chanceJailCard = true;
	}
	
	public void takeChanceJailCard(){
		chanceJailCard = false;
	}
	
	public boolean hasChanceJailCard(){
		return chanceJailCard;
	}
	
	public void giveCommunityChestJailCard(){
		ccJailCard = true;
	}
	
	public void takeCommunityChestJailCard(){
		ccJailCard = false;
	}
	
	public boolean hasCommunityChestJailCard(){
		return ccJailCard;
	}
	
	public MineopolySection getCurrentSection(){
		return this.sectionOn;
	}
	
	public boolean ownsSection(OwnableSection section){
		return ownedSections.contains(section);
	}
	
	public void addSection(OwnableSection section){
		ownedSections.add(section);
		if(section instanceof Property){
			Property p = (Property) section;
			if(p.getColor() == MineopolyColor.BLUE || p.getColor() == MineopolyColor.PURPLE){
				if(getOwnedPropertiesWithColor(p.getColor()) == 2)
					monopolies.add(p.getColor());
			}else{
				if(getOwnedPropertiesWithColor(p.getColor()) == 3)
					monopolies.add(p.getColor());
			}
		}
	}
	
	public ArrayList<OwnableSection> ownedSections(){
		return ownedSections;
	}
	
	public ArrayList<OwnableSection> ownedSections(SectionType type){
		if(type != SectionType.PROPERTY && type != SectionType.RAILROAD && type != SectionType.UTILITY){
			throw new IllegalArgumentException("必须输入 PROPERTY, RAILROAD, 或 UTILITY");
		}
		ArrayList<OwnableSection> result = new ArrayList<OwnableSection>();
		if(ownedSections.size() == 0) return result;
		for(OwnableSection o : ownedSections){
			if(o.getType() == type){
				result.add(o);
			}
		}
		return result;
	}
	
	public int ownedPropertiesSize(){
		return ownedSections.size();
	}
	
	public int valueOfOwnedSections(){
		int amount = 0;
		for(OwnableSection o : ownedSections()){
			amount += o.getPrice();
			if(o instanceof Property){
				Property p = (Property) o;
				if(p.hasHotel()){
					amount += p.getHotelPrice();
				}else if(p.getHouses() > 0){
					amount += p.getHousePrice() * p.getHouses();
				}
			}
		}
		return amount;
	}
	
	public ArrayList<Property> getPropertiesInMonopoly(MineopolyColor color){
		return getPropertiesLessHousing(color, 5);
	}
	
	public ArrayList<Property> getPropertiesLessHousing(MineopolyColor color, int houses){
		ArrayList<Property> result = new ArrayList<Property>();
		for(OwnableSection s : ownedSections){
			if(s.getType() == SectionType.PROPERTY){
				Property p = (Property) s;
				if(p.getColor() == color && p.getHouses() < houses)
					result.add(p);
			}
		}
		return result;
	}
	
	public ArrayList<Property> getPropertiesNeedingHousing(Property parent){
		ArrayList<Property> properties = getPropertiesInMonopoly(parent.getColor());
		int max = 0;
		for(Property p : properties){
			if(p.getHouses() > max){
				max = p.getHouses();
			}
		}
		ArrayList<Property> result = new ArrayList<Property>();
		for(Property p : properties){
			if(p.getHouses() < max && p.getId() != parent.getId()){
				result.add(p);
			}
		}
		return result;
	}
	
	public ArrayList<Property> getPropertiesCanHaveHouses(){
		ArrayList<Property> properties = new ArrayList<Property>();
		for(OwnableSection s : ownedSections){
			if(s.getType() != SectionType.PROPERTY) continue;
			Property p = (Property) s;
			if(p.canAddHouse()) properties.add(p);
		}
		return properties;
	}
	
	public ArrayList<Property> getPropertiesCanHaveHotel(){
		ArrayList<Property> properties = new ArrayList<Property>();
		for(OwnableSection s : ownedSections){
			if(s.getType() != SectionType.PROPERTY) continue;
			Property p = (Property) s;
			if(p.canAddHotel()) properties.add(p);
		}
		return properties;
	}
	
	public int monopolySize(){
		return monopolies.size();
	}
	
	public ArrayList<MineopolyColor> getMonopolies(){
		Collections.sort(monopolies);
		return monopolies;
	}
	
	public OwnableSection getOwnableSection(int id){
		for(OwnableSection o : ownedSections){
			if(o.getId() == id){
				return o;
			}
		}
		return null;
	}
	
	public int getGoPasses(){
		return goPasses;
	}
	
	public void setGoPasses(int passes){
		this.goPasses = passes;
	}
	
	//this player was sent a request
	public boolean sendRequest(TradeRequest request){
		if(getReceivedRequest(request.getReceiver().getName()) == null){
			receivedRequests.add(request);
			return true;
		}
		return false;
	}
	
	//this player sent a request
	public boolean addRequest(TradeRequest request){
		if(getSentRequest(request.getReceiver().getName()) == null){
			sentRequests.add(request);
			return true;
		}
		return false;
	}
	
	public TradeRequest getReceivedRequest(String sender){
		for(TradeRequest r : receivedRequests){
			if(!r.isCancelled() && r.getSender().getName().equalsIgnoreCase(sender)){
				return r;
			}
		}
		return null;
	}
	
	public TradeRequest getSentRequest(String receiver){
		for(TradeRequest r : receivedRequests){
			if(!r.isCancelled() && r.getReceiver().getName().equalsIgnoreCase(receiver)){
				return r;
			}
		}
		return null;
	}
	
	public boolean hasRequest(){
		return receivedRequests.size() > 0;
	}
	
	public boolean hasSentRequest(){
		return sentRequests.size() > 0;
	}
	
	public void clearRequestsWithPlayer(String name){
		ArrayList<TradeRequest> sent = new ArrayList<TradeRequest>();
		ArrayList<TradeRequest> received = new ArrayList<TradeRequest>();
		for(TradeRequest r : sentRequests){
			if(!r.getReceiver().getName().equalsIgnoreCase(name)){
				sent.add(r);
			}
		}
		for(TradeRequest r : receivedRequests){
			if(!r.getSender().getName().equalsIgnoreCase(name)){
				received.add(r);
			}
		}
		sentRequests = sent;
		receivedRequests = received;
	}
	
	public void showMenu(MineopolyMenu menu){
		player.closeInventory();
		if(menu != null) player.openInventory(menu.getInventory());
	}
	
	public String toString(){ return getName(); }

	public boolean canAddHouse() {
		if(ownedSections.size() == 0) return false;
		for(OwnableSection s : ownedSections){
			if(s.getType() != SectionType.PROPERTY) continue;
			Property p = (Property) s;
			if(p.canAddHouse()) return true;
		}
		return false;
	}
	
	public boolean canAddHotel() {
		if(ownedSections.size() == 0) return false;
		for(OwnableSection s : ownedSections){
			if(s.getType() != SectionType.PROPERTY) continue;
			Property p = (Property) s;
			if(p.canAddHotel()) return true;
		}
		return false;
	}

	public void removeSection(OwnableSection section) {
		for(OwnableSection s : ownedSections){
			if(s.getId() == section.getId()){
				ownedSections.remove(s);
				break;
			}
		}
	}

	public ArrayList<TradeRequest> getSentRequests() {
		return sentRequests;
	}
	
	public ArrayList<TradeRequest> getRequests() {
		return receivedRequests;
	}
	
}
