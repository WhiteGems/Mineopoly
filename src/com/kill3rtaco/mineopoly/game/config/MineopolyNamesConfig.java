package com.kill3rtaco.mineopoly.game.config;

import java.io.File;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.tacoapi.api.TacoConfig;

public class MineopolyNamesConfig extends TacoConfig {

	public MineopolyNamesConfig() {
		super(new File(Mineopoly.plugin.getDataFolder() + "/config/names.yml"));
	}

	@Override
	protected void setDefaults() {
		addDefaultValue("properties.mediterranean_ave", "下界大道"); 			//brown
		addDefaultValue("properties.baltic_ave", "史莱姆街");
		addDefaultValue("properties.oriental_ave", "神庙路"); 				//light_blue
		addDefaultValue("properties.vermont_ave", "碉堡巷");
		addDefaultValue("properties.connecticut_ave", "地牢街");
		addDefaultValue("properties.st_charles_place", "圣.烈焰人之地"); 		//magenta
		addDefaultValue("properties.states_ave", "僵尸花园");
		addDefaultValue("properties.virginia_ave", "蜘蛛巷");
		addDefaultValue("properties.st_james_place", "末影人之地"); 		//orange
		addDefaultValue("properties.tennessee_ave", "Notch之地");
		addDefaultValue("properties.new_york_ave", "骷髅大道");
		addDefaultValue("properties.kentucky_ave", "滚床大街"); 				//red
		addDefaultValue("properties.indiana_ave", "弓与箭之地");
		addDefaultValue("properties.illinois_ave", "狼之巷");
		addDefaultValue("properties.atlantic_ave", "好人巷"); 				//yellow
		addDefaultValue("properties.ventor_ave", "坏胚街");
		addDefaultValue("properties.marvin_gardens", "丑人巷");
		addDefaultValue("properties.pacific_ave", "曲奇快车道"); 				//green
		addDefaultValue("properties.north_carolina_ave", "西瓜大街"); 
		addDefaultValue("properties.pennsylvania_ave", "南瓜路");
		addDefaultValue("properties.park_place", "海港街"); 				//dark_blue
		addDefaultValue("properties.boardwalk", "棉花糖快车道");
		addDefaultValue("railroads.reading", "附魔大道");						//railroads
		addDefaultValue("railroads.pennsylvania", "爬行者巷");
		addDefaultValue("railroads.b_o", "TNT大道");
		addDefaultValue("railroads.short_line", "末地");
		addDefaultValue("companies.electric", "红石街");						//utilities
		addDefaultValue("companies.water", "流水巷");
	}

}
