package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.ImageSets;
/**
 * ���ϽǷ��ӵ�����������ʽ���е��Ӵ��Ϸ�������
 * */
public class Die1 extends Die {
	
	/**
	 * ���췽�����ı丸��������ǰ��ʱ���ӳ�
	 *  @param map ����ģʽ��Ҫ�ػ��ĵ�ͼ
	 * */
	public Die1(BufferedImage image) {
		super(image);
		// TODO �Զ����ɵĹ��캯�����
		super.DISTANCE_TO_DIE = 15;
	}
	
	/**
	 * ���õ��ӷɹ��Ļ���
	 * */
	@Override
	protected void paint(Graphics g) {
		// TODO �Զ����ɵķ������
		step+=2;
		g.drawImage(ImageSets.getImg(39), 390, 100+step*12, null);
	}

}
