package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.Lib;
import model.Game;
import model.rpg.RpgObject;
import model.rpg.SaveData;
import model.rpg.map.Map;
import model.start.LoadObject;
/**×·ÖðÕ½Ç°×à*/
public class Story1 extends Story {
	public Story1(BufferedImage map) {
		super(map);
		super.DISTANCE_TO_DIE = 5;
		super.initial = "A0";
		super.musicStop();
		super.musicStart("source/rpg/chase.wav");
		
		LoadObject.write(new SaveData(LoadObject.read(3).getMaps(), 
				LoadObject.read(3).getItem() , LoadObject.read(3).getCurrent(),Map.playerX,Map.playerY , LoadObject.read(3).getDirection(),LoadObject.read(3).getLastTime()));
		
	}
	
	protected void paint(Graphics g){
		Map.startChase = true;
		g.setColor(Color.red);
		g.setFont(Lib.terror);
		super.paint(g);
	}
	
	@Override
	public void die(){
		System.out.println("story start");
		new RpgObject(LoadObject.read(3),0);
		Map.startChase = true;
	}
	
}
