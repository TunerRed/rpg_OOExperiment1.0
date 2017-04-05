package model.rpg.map.MapObjects.auto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import control.Lib;
import model.GameObject;
import model.rpg.Player;
import model.rpg.map.Map;


/**
 * ��ͼ�еľ���ģ�飬
 * ������������Ҳ�����ǹ��¡�
 * ��ʱ����Ķ����յ����ơ�
 * new������ǰ����ʱ�浵��publicData�У�������ָ�RpgObject����ȡpublicData�浵
 * */
public abstract class Auto extends GameObject{
	protected BufferedImage image;
	/**
	 * DISTANCE_TO_DIE ����ǰ���Ⱦ���˳����������ж��پ���
	 * ��������Ǹ��ݼ�����Ӧ����
	 * Ҳ�п�����ϵͳ�Զ����㲢����
	 * */
	protected int DISTANCE_TO_DIE = 10;
	protected int step = 0;
	public Auto(BufferedImage image){
		this.image = image;
	}
	@Override
	protected void keyResponse() {
		
	}
	
	/**
	 * �����paint������
	 * ������ʵ�֡�
	 * */
	protected abstract void paint(Graphics g);
	
	/**
	 * ����Ĭ�ϵ�draw�������ѵ�ͼ���������Ӱ������
	 * */
	@Override
	public void draw(Graphics g) {
		g.clipRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		g.drawImage(image,
				Player.getInstance().getPaintX()-Map.playerX*Lib.boundsPerImg + Lib.adjustX,
				Player.getInstance().getPaintY()-Map.playerY*Lib.boundsPerImg + Lib.adjustY,
				null);
		paint(g);
		//��Player��Directionδ��ʼ���Ļ����˴�������������
		Player.getInstance().draw(g, Player.getInstance().getDirection());
	}
	
	@Override
	public abstract void die();
}
