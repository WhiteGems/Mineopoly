package com.kill3rtaco.mineopoly.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kill3rtaco.mineopoly.Mineopoly;
import com.kill3rtaco.mineopoly.game.MineopolySection;
import com.kill3rtaco.mineopoly.game.cards.MineopolyCard;
import com.kill3rtaco.mineopoly.game.sections.CardinalSection;
import com.kill3rtaco.mineopoly.game.sections.OwnableSection;
import com.kill3rtaco.mineopoly.game.sections.Property;
import com.kill3rtaco.mineopoly.game.sections.Railroad;
import com.kill3rtaco.mineopoly.game.sections.SectionType;
import com.kill3rtaco.mineopoly.game.sections.Utility;

public class MineopolyPluginTester {

	private static final String version = "1.2.5";
	private static String header, footer;
	private static File debugFile, sectionFile, cardFile;
	private static int indentTimes = 0;
	private static FileWriter debugWriter, sectionWriter, cardWriter;
	
	public static void run(){
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM_hh:mm:ss");
		String date = format.format(d);
		debugFile = new File(Mineopoly.plugin.getDataFolder() + "/.tests/" + date + "/debug.txt");
		sectionFile = new File(Mineopoly.plugin.getDataFolder() + "/.tests/" + date + "/sections.txt");
		cardFile = new File(Mineopoly.plugin.getDataFolder() + "/.tests/" + date + "/cards.txt");
		try {
			header = "---!!MineopolyTest v" + version + " " + date + " !!---\n\n";
			footer = "---!!MineopolyTest END!!---";
			testDebug();
			testSections();
			testCards();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void testDebug() throws IOException {
		if(!debugFile.getParentFile().exists()){
			debugFile.getParentFile().mkdirs();
		}
		debugWriter = new FileWriter(debugFile);
		debugWriter.append(header);
		debugWriter.append("os.name\t\t\t\t=\t\t" + System.getProperty("os.name") + "\n");
		debugWriter.append("os.version\t\t\t=\t\t" + System.getProperty("os.version") + "\n");
		debugWriter.append("os.arch\t\t\t\t=\t\t" + System.getProperty("os.arch") + "\n");
		debugWriter.append("java.version\t\t\t=\t\t" + System.getProperty("java.version") + "\n");
		debugWriter.append("java.vendor\t\t\t=\t\t" + System.getProperty("java.vendor") + "\n");
		debugWriter.append("sun.arch.data.model\t\t=\t\t" + System.getProperty("sun.arch.data.model") + "\n\n");
		debugWriter.append(footer);
		debugWriter.close();
	}
	
	private static void testSections() throws IOException {
		if(!sectionFile.getParentFile().exists()){
			sectionFile.getParentFile().mkdirs();
		}
		sectionWriter = new FileWriter(sectionFile);
		sectionWriter.append(header);
		for(MineopolySection s : Mineopoly.plugin.getGame().getBoard().getAllSections()){
			indentTimes = 0;
			sectionWriter.append("[" + s.getId() + "]");
			sectionWriter.append(indent(true) + "Name: " + s.getName() + "\n");
			sectionWriter.append(indent() + "Color: " + s.getDisplayColor() + "\tColoredName: " + s.getColorfulName() + "\n");
			sectionWriter.append(indent() + "Type: " + s.getType() + "\n");
			if(s instanceof CardinalSection){
				sectionWriter.append(indent() + "Side: " + ((CardinalSection) s).getSide() + "\n");
			}
			if(s instanceof OwnableSection){
				OwnableSection o = (OwnableSection) s;
				sectionWriter.append(indent(true) + "[Pricing]\n");
				sectionWriter.append(indent(true) + "BuyPrice: " + o.getPrice() + "\n");
				if(s.getType() == SectionType.PROPERTY){
					Property p = (Property) o;
					sectionWriter.append(indent() + "HousePrice: " + p.getHousePrice() + "\n");
					sectionWriter.append(indent() + "HotelPrice: " + p.getHotelPrice() + "\n");
				}
				sectionWriter.append(indent() + "[Rent]\n");
				if(s.getType() == SectionType.PROPERTY){
					Property p = (Property) o;
					sectionWriter.append(indent(true) + "House(0) - " + p.getRent(0) + "\n");
					sectionWriter.append(indent() + "House(1) - " + p.getRent(1) + "\n");
					sectionWriter.append(indent() + "House(2) - " + p.getRent(2) + "\n");
					sectionWriter.append(indent() + "House(3) - " + p.getRent(3) + "\n");
					sectionWriter.append(indent() + "House(4) - " + p.getRent(4) + "\n");
					sectionWriter.append(indent() + "Hotel: " +  + p.getRent(0, true) + "\n\n");
				}else if(s.getType() == SectionType.RAILROAD){
					Railroad r = (Railroad) o;
					sectionWriter.append(indent(true) + "Other x0 - " + r.getRent(0) + "\n");
					sectionWriter.append(indent() + "Other x1 - " + r.getRent(1) + "\n");
					sectionWriter.append(indent() + "Other x2 - " + r.getRent(2) + "\n");
					sectionWriter.append(indent() + "Other x3 - " + r.getRent(3) + "\n\n");
				}else if(s.getType() == SectionType.UTILITY){
					Utility u = (Utility) o;
					sectionWriter.append(indent(true) + "Other x0 - " + u.getRent(0) + "\n");
					sectionWriter.append(indent(true) + "Other x1 - " + u.getRent(1) + "\n\n");
				}
			}else{
				sectionWriter.append("\n");
			}
		}
		sectionWriter.append(footer);
		sectionWriter.close();
	}
	
	private static void testCards() throws IOException {
		if(!cardFile.getParentFile().exists()){
			cardFile.getParentFile().mkdirs();
		}
		int count = 1;
		cardWriter = new FileWriter(cardFile);
		cardWriter.append(header);
		cardWriter.append("[ChanceCards]\n");
		for(MineopolyCard c : Mineopoly.plugin.getGame().getBoard().getChanceCards()){
			indentTimes = 0;
			cardWriter.append("[" + (count++) + "]" + indent(true) + "Name: " + c.getName() + "\n");
			cardWriter.append(indent() + "Description: " + c.getDescription() + "\n");
			cardWriter.append(indent() + "[TestReplace]\n");
			cardWriter.append(indent(true) + c.getFormattedDescription() + "\n\n");
		}
		
		count = 1;
		cardWriter.append("\n[CommunityChestCards]\n");
		for(MineopolyCard c : Mineopoly.plugin.getGame().getBoard().getCommunityChestCards()){
			indentTimes = 0;
			cardWriter.append("[" + (count++) + "]" + indent(true) + "Name: " + c.getName() + "\n");
			cardWriter.append(indent() + "Description: " + c.getDescription() + "\n");
			cardWriter.append(indent() + "Action: " + c.getFormattedAction() + "\n");
			cardWriter.append(indent() + "[TestReplace]\n");
			cardWriter.append(indent(true) + c.getFormattedDescription() + "\n\n");
		}
		cardWriter.append(footer);
		cardWriter.close();
	}
	
	private static String indent(){
		return indent(false);
	}
	
	private static String indent(boolean addIndent){
		if(addIndent){
			indentTimes++;
		}
		String ind = "";
		for(int i=0; i<indentTimes; i++){
			ind += "\t";
		}
		return ind;
	}

}
