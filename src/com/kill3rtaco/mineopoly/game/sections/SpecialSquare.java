package com.kill3rtaco.mineopoly.game.sections;

import com.kill3rtaco.mineopoly.game.MineopolySection;

public abstract class SpecialSquare extends MineopolySection implements ActionProvoker {
	
	public SpecialSquare(int id, String name, char color) {
		super(id, name, color, SectionType.SQUARE);
	}
	
}
