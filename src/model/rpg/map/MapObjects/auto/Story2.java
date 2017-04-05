package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;

import model.rpg.RpgObject;
import control.Lib;
/**游戏开始前的故事*/
public class Story2 extends Story {

	public Story2() {
		super(null);
		// TODO 自动生成的构造函数存根
		super.initial = "S";
		super.DISTANCE_TO_DIE = 4;
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
		new RpgObject();
	}

}
