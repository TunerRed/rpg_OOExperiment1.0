package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.Lib;
import model.rpg.RpgObject;
import model.start.LoadObject;

public class Start extends Story {

	public Start(BufferedImage map) {
		super(map);
		super.DISTANCE_TO_DIE = 8;
		super.initial = "A1";
	}
	
	protected void paint(Graphics g){
		g.clearRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		super.paint(g);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(Lib.regular);
		paint(g);
	}
	
	@Override
	public void die(){
		new RpgObject(LoadObject.read(3),0);
	}

}
