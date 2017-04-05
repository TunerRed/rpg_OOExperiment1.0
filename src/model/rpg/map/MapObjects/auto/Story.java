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
 * ����ģʽ�����°����Ż���ʾ��һ����Ϣ��֪��ȫ����Ϣ��ʾ��ϡ�
 * */
public abstract class Story extends Auto {
	protected Properties properties = new Properties();
	/**
	 * String����initial������properties�ж�ȡ���ݵĸı�
	 * �磺
	 * �ı�initialΪ��S�����������Ϣ���ȡS0��S1��S2�ȵ�
	 * */
	protected String initial = null;
	/**
	 * ���췽�������������ļ���
	 * ��ʾ���¾����
	 * @param map ����ģʽ��Ҫ�ػ��ĵ�ͼ
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
		
		//ǿ��ת��Graphics2D���ò�͸����
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
