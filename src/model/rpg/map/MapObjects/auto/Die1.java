package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.ImageSets;
/**
 * 右上角房子的人物死亡方式，有刀子从上方飞来。
 * */
public class Die1 extends Die {
	
	/**
	 * 构造方法，改变父类中死亡前的时间延迟
	 *  @param map 剧情模式下要重画的地图
	 * */
	public Die1(BufferedImage image) {
		super(image);
		// TODO 自动生成的构造函数存根
		super.DISTANCE_TO_DIE = 15;
	}
	
	/**
	 * 设置刀子飞过的画法
	 * */
	@Override
	protected void paint(Graphics g) {
		// TODO 自动生成的方法存根
		step+=2;
		g.drawImage(ImageSets.getImg(39), 390, 100+step*12, null);
	}

}
