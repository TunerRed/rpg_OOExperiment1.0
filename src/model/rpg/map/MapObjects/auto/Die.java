package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import model.GameObject;
import model.over.GameOverObject;
/**
 * ���ﵽ��ĳ��������ʱ�Ĵ���Ϊ������
 * */
public abstract class Die extends Auto {

	public Die(BufferedImage image) {
		super(image);
		// TODO �Զ����ɵĹ��캯�����
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
