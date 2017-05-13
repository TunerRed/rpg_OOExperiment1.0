package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.ImageSets;
import control.Lib;
import model.rpg.Player;
import model.rpg.map.MapObjects.auto.Die;
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
		super.DISTANCE_TO_DIE = 5;
	}
	
	/**
	 * ���õ��ӷɹ��Ļ���
	 * */
	@Override
	protected void paint(Graphics g) {
		// TODO �Զ����ɵķ������
		step++;
		g.drawImage(ImageSets.getBlockImage(0), Player.getInstance().getPaintX() + Lib.boundsPerImg * (7 - step)
				, Player.getInstance().getPaintY()+Lib.adjustY, null);
	}

}
