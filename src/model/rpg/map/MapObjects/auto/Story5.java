package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.Lib;
import model.GameObject;
import model.over.BadEndObject;
/**普通结局前奏剧情部分*/
public class Story5 extends Story {
	
	public Story5(BufferedImage map) {
		super(map);
		super.initial = "F";
		super.DISTANCE_TO_DIE = 10;
		GameObject.musicStop();
		super.musicStart("source/end/badend.wav");
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
		new BadEndObject();
	}
}
