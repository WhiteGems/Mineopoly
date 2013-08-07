package com.kill3rtaco.mineopoly;

public class MineopolyConstants {

	//Configuration Constants
	public static final String C_AUTO_TURN_END = "mineopoly.game.automatic-turn-ending";
	public static final String C_ADD_BANNED = "mineopoly.game.add-even-when-banned";
	public static final String C_MAX_PLAYERS = "mineopoly.game.max-players";
	public static final String C_MIN_PLAYERS = "mineopoly.game.min-players";
	public static final String C_SESSION_TIMEOUT = "mineopoly.session-timeout-minutes";
	public static final String C_SCHEMA_WORLD = "mineopoly.schematic.world";
	public static final String C_SCHEMA_NEEDS_PASTE = "mineopoly.schematic.needs_paste";
	public static final String C_SCHEMA_ORIGIN_X = "mineopoly.schematic.origin-x";
	public static final String C_SCHEMA_ORIGIN_Y = "mineopoly.schematic.origin-y";
	public static final String C_SCHEMA_ORIGIN_Z = "mineopoly.schematic.origin-z";
	public static final String C_SHOW_TIPS = "mineopoly.tips.show";
	public static final String C_TIP_INTERVAL = "mineopoly.tips.interval";
	public static final String C_WIN_METHOD = "mineopoly.game.win-method";
	public static final String C_WIN_REWARD = "mineopoly.game.reward";
	public static final String C_METRICS_SEND = "mineopoly.metrics.send_data";
//	public static final String C_VOTING_INTERVAL = "mineopoly.voting.interval";
	public static final String C_VOTING_TIME_LIMIT = "mineopoly.voting.time-limit";
	
	//House Rule Constants
	public static final String HR_BAIL_PRICE = "bail-price"; //done
//	public static final String HR_BANK_LOAN_AMOUNT = "bank-loan-amount";
//	public static final String HR_BANK_LOAN_DEFINABLE = "bank-loan-definable";
//	public static final String HR_BANK_LOANS = "bank-loans";
	public static final String HR_COLLECT_WHILE_JAILED = "collect-while-jailed"; //done
	public static final String HR_HOUSE_SYNCRONIZATION = "house-sycronization"; //done
	public static final String HR_LAND_ON_GO_BONUS = "land-on-go-bonus"; //done
	public static final String HR_MONEY_CAP = "money-cap"; //done
	public static final String HR_IMPROVMENT_REQUIRES_LOCATION = "improvement-requires-location"; //done
	public static final String HR_IMPROVMENT_REQUIRES_MONOPOLY = "improvement-requires-monopoly"; //done
	public static final String HR_IMPROVE_WHILE_JAILED = "improve-while-jailed"; //done
	public static final String HR_PURCHASE_AFTER_GO_PASSES = "purchase-after-go-passes"; //done
	public static final String HR_STARTING_MONEY = "starting-money"; //done
	public static final String HR_TIME_LIMIT = "time-limit-minutes"; //done
	public static final String HR_TRADE_ANYTIME = "trade-anytime"; //done
	public static final String HR_TRADE_WHILE_JAILED = "trade-while-jailed"; //done
	public static final String HR_TRAVELING_RAILROADS = "traveling-railroads"; //done
	public static final String HR_VOTING_ALLOWED = "voting-allowed";
	
	//Name Constants
	public static final String N_P_PREFIX = "properties.";
	public static final String N_U_PREFIX = "companies.";
	public static final String N_RR_PREFIX = "railroads.";
	
	public static final String N_ATLANTIC = N_P_PREFIX + "atlantic_ave";
	public static final String N_BALTIC = N_P_PREFIX + "baltic_ave";
	public static final String N_BO_RR = N_RR_PREFIX + "b_o";
	public static final String N_BOARDWALK = N_P_PREFIX + "boardwalk";
	public static final String N_CT = N_P_PREFIX + "connecticut_ave";
	public static final String N_ELECTRIC_COMPANY = N_U_PREFIX + "electric";
	public static final String N_KY = N_P_PREFIX + "kentucky_ave";
	public static final String N_IL = N_P_PREFIX + "illinois_ave";
	public static final String N_IN = N_P_PREFIX + "indiana_ave";
	public static final String N_MARVIN_GARDENS = N_P_PREFIX + "marvin_gardens";
	public static final String N_MEDITERRANEAN = N_P_PREFIX + "mediterranean_ave";
	public static final String N_NC = N_P_PREFIX + "north_carolina_ave";
	public static final String N_NY = N_P_PREFIX + "new_york_ave";
	public static final String N_ORIENTAL = N_P_PREFIX + "oriental_ave";
	public static final String N_PACIFIC = N_P_PREFIX + "pacific_ave";
	public static final String N_PARK = N_P_PREFIX + "park_place";
	public static final String N_PA = N_P_PREFIX + "pennsylvania_ave";
	public static final String N_PA_RR = N_RR_PREFIX + "pennsylvania";
	public static final String N_READING_RR = N_RR_PREFIX + "reading";
	public static final String N_SHORT_LINE_RR = N_RR_PREFIX + "short_line";
	public static final String N_ST_CHARLES = N_P_PREFIX + "st_charles_place";
	public static final String N_ST_JAMES = N_P_PREFIX + "st_james_place";
	public static final String N_STATES = N_P_PREFIX + "states_ave";
	public static final String N_TN = N_P_PREFIX + "tennessee_ave";
	public static final String N_VENTOR = N_P_PREFIX + "ventor_ave";
	public static final String N_VERMONT = N_P_PREFIX + "vermont_ave";
	public static final String N_VA = N_P_PREFIX + "virginia_ave";
	public static final String N_WATER_COMPANY = N_U_PREFIX + "water";
	
	//Permission Constants
	public static final String P_BAN_PLAYER_FROM_GAME = "Mineopolyadminban";
	public static final String P_UNBAN_PLAYER_FROM_GAME = "Mineopolyadminunban";
	public static final String P_CHANNEL_CHAT = "Mineopolygeneralchat";
	public static final String P_FORCE_ADD_PLAYER = "Mineopolyadminforce-add";
	public static final String P_END_GAME = "Mineopolyadminend";
	public static final String P_JOIN_GAME = "Mineopolygeneralgame";
	public static final String P_KICK_PLAYER_FROM_GAME = "Mineopolyadminkick";
	public static final String P_RELOAD = "Mineopolyadminreload";
	public static final String P_RESUME_GAME = "Mineopolyadminresume";
	public static final String P_SAVE_GAME = "Mineopolyadminsave";
	public static final String P_SET_PASTE_LOCATION = "Mineopolyadminset-paste-location";
	public static final String P_START_GAME = "Mineopolyadminstart";
	public static final String P_VIEW_PLAYER_QUEUE = "Mineopolyadminqueue";
	public static final String P_VIEW_GAME_STATS = "Mineopolygeneralstats";

}
