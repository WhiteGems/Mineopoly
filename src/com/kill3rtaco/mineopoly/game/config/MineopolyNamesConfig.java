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
		addDefaultValue("properties.mediterranean_ave", "Nether Ave"); 			//brown
		addDefaultValue("properties.baltic_ave", "Slimey Street");
		addDefaultValue("properties.oriental_ave", "Temple Road"); 				//light_blue
		addDefaultValue("properties.vermont_ave", "Stronghold Lane");
		addDefaultValue("properties.connecticut_ave", "Dungeon Ave");
		addDefaultValue("properties.st_charles_place", "St. Blaze Place"); 		//magenta
		addDefaultValue("properties.states_ave", "Zombe Gardens");
		addDefaultValue("properties.virginia_ave", "Spider Lane");
		addDefaultValue("properties.st_james_place", "Enderman Place"); 		//orange
		addDefaultValue("properties.tennessee_ave", "Notch Place");
		addDefaultValue("properties.new_york_ave", "Skeleton Ave");
		addDefaultValue("properties.kentucky_ave", "Bed Place"); 				//red
		addDefaultValue("properties.indiana_ave", "Bow and Arrow Place");
		addDefaultValue("properties.illinois_ave", "Wolf Lane");
		addDefaultValue("properties.atlantic_ave", "Good Street"); 				//yellow
		addDefaultValue("properties.ventor_ave", "Bad Street");
		addDefaultValue("properties.marvin_gardens", "Ugly Street");
		addDefaultValue("properties.pacific_ave", "Cookie Drive"); 				//green
		addDefaultValue("properties.north_carolina_ave", "Watermelon Ave"); 
		addDefaultValue("properties.pennsylvania_ave", "Pumpkin Road");
		addDefaultValue("properties.park_place", "Harbor Ave"); 				//dark_blue
		addDefaultValue("properties.boardwalk", "Marshmellow Drive");
		addDefaultValue("railroads.reading", "Enchanted");						//railroads
		addDefaultValue("railroads.pennsylvania", "Creeper");
		addDefaultValue("railroads.b_o", "TNT");
		addDefaultValue("railroads.short_line", "The End");
		addDefaultValue("companies.electric", "Redstone");						//utilities
		addDefaultValue("companies.water", "Water");
	}

}
