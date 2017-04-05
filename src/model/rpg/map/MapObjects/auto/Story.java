package model.rpg.map.MapObjects.auto;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import control.Control;
import control.Lib;
import model.rpg.RpgObject;
import model.start.LoadObject;
/**
 * 故事模式，按下按键才会显示下一条信息，知道全部信息显示完毕。
 * */
public abstract class Story extends Auto {
	protected Properties properties = new Properties();
	/**
	 * String类型initial负责在properties中读取内容的改变
	 * 如：
	 * 改变initial为“S”，则故事信息会读取S0、S1、S2等等
	 * */
	protected String initial = null;
	/**
	 * 构造方法，载入配置文件，
	 * 显示故事剧情等
	 * @param map 剧情模式下要重画的地图
	 * */
	public Story(BufferedImage image) {
		super(image);
		try {
			properties.load(Story.class.getClassLoader().getResourceAsStream("source/rpg/properties/story.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paint(Graphics g) {
		if(step > super.DISTANCE_TO_DIE){
			die();
			return;
		}
		
		Color old = g.getColor();
		
		//强制转换Graphics2D设置不透明度
		g.setColor(Color.WHITE);
		((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(0.5f));
		g.fillRect(Lib.gameWIDTH / 7, Lib.gameHEIGHT - Lib.gameWIDTH / 7, Lib.gameWIDTH - 2 * Lib.gameWIDTH / 7,
				Lib.gameHEIGHT / 7);
		((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(1.0f));
		
		g.setColor(old);
		g.drawString(properties.getProperty(initial+step), 240, 540);
		if(Control.Z && super.checkTimeGap(200)){
			step++;
		}
		
	}
	
	public void die(){
		new RpgObject(LoadObject.read(3),0);
	}

}
