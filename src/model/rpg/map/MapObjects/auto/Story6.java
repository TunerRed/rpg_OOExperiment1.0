package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.Lib;

public class Story6 extends Story {

	public Story6(BufferedImage map) {
		super(map);
		super.initial = "D";
		super.DISTANCE_TO_DIE = 2;
	}
	
	protected void paint(Graphics g){
		g.setColor(Color.white);
		g.setFont(Lib.regular);
		super.paint(g);
	}

}
