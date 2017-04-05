package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.GameObject;
import model.over.GameOverObject;
/**
 * 人物到达某个死亡点时的处理，为抽象类
 * */
public abstract class Die extends Auto {

	public Die(BufferedImage image) {
		super(image);
		// TODO 自动生成的构造函数存根
		GameObject.musicStop();
	}
	
	public void draw(Graphics g){
		super.draw(g);
		if(DISTANCE_TO_DIE+3 <= step){
			die();
		}
	}
	
	public void die(){
		new GameOverObject();
	}
	
}
