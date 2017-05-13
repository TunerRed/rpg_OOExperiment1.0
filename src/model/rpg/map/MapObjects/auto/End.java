package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.Lib;
import model.GameObject;
import model.over.GameEndObject;

public class End extends Story {
	public End(BufferedImage map) {
		super(map);
		super.initial = "A2";
		super.DISTANCE_TO_DIE = 2;
		GameObject.musicStop();
		super.musicStart("source/end/ÄºÉ«²ÔÈ».au");
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
		new GameEndObject();
	}
}
