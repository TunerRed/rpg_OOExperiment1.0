package model.over;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import model.start.StartObject;
import control.Control;
/**
 * 人物死亡出现的GameObject
 * */
public class GameOverObject extends GameObject {
	private static Image gameover;
	static{
		try {
			gameover=ImageIO.read(GameOverObject.class.getClassLoader().getResourceAsStream("source/gameover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止游戏背景音乐，
	 * 并且播放死亡音效。
	 * */
	public GameOverObject(){
		super.musicStop();
		super.tempSoundPlay("source/rpg/die.wav");
		super.clearStatic();
	}
	
	/**
	 * Z按下时执行die方法结束GameOverObject
	 * */
	@Override
	protected void keyResponse() {
		// TODO Auto-generated method stub
		if(Control.Z){
			die();
		}
			
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		keyResponse();
		g.drawImage(gameover, 0, 0, null);
	}
	
	/**
	 * 游戏结束界面出现后一秒按下Z可以响应
	 * */
	@Override
	public void die() {
		if(super.checkStartGap(1000)){
			Game.getInstance().setCurrent(new StartObject());
		}
	}

}
