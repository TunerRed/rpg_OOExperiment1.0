package model.start;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameObject;
import model.over.GameOverObject;
import model.rpg.RpgObject;
import model.rpg.SaveData;
/**
 * 缓冲功能，在加载RpgObject类之前使用此类，避免游戏看上去变卡的现象。
 * */
public class LoadingObject extends GameObject {
	private int wait = 0;
	private static Image gameover;
	private SaveData save;

	static {
		try {
			gameover = ImageIO.read(GameOverObject.class.getClassLoader().getResourceAsStream("source/loading.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造方法。
	 *  @param save 要读取的存档。当传入null时即认为是新的开始，没有存档
	 * */
	public LoadingObject(SaveData save) {
		this.save = save;
	}

	@Override
	protected void keyResponse() {
	}
	
	/**
	 * 设置int变量，当变量达到条件时，
	 * 说明Load经过了足够时间，
	 * 将new出RpgObject
	 * @param g 画笔
	 * */
	@Override
	public void draw(Graphics g) {
		wait++;
		if (wait == 20) {
			die();
		}
		g.drawImage(gameover, 0, 0, null);
	}
	
	/**
	 * 判断目前是读档还是新的游戏，然后执行不同操作
	 * */
	@Override
	public void die() {
		if(save == null)
			new RpgObject();
		else
			new RpgObject(save);
	}

}
