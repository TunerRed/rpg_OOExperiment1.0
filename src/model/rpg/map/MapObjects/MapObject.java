package model.rpg.map.MapObjects;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import model.rpg.Direction;
import model.rpg.map.Map;
import model.rpg.map.MapKind;
/**
 * ��ʵ�����л��������д��浵�ĵ�ͼԪ����
 * */
@SuppressWarnings("serial")
public class MapObject implements Serializable{
	public static final int DEFAULT_IMGTYPE = -1;
	public int toWhich,playerX=5, playerY=5;
	private boolean canGo;
	private int imgType=0;
	
	protected Direction direction;
	protected MapKind kind;
	
	/**
	 * ���췽����
	 * �����ж����Ԫ���ǲ����������̤��ȥ��
	 * @param kind Map������
	 * @param imgType ѡ���ImageSets�е�ͼƬ
	 * */
	public MapObject(MapKind kind){
		this.kind = kind;
		this.imgType = DEFAULT_IMGTYPE;
		
		if(kind==MapKind.BLOCK||kind==MapKind.SHELL)
			canGo = false;
		else
			canGo = true;
	}
	
	public void setImage(int imageNo){
		this.imgType = imageNo;
	}
	
	public boolean canGo(){
		return canGo;
	}
	protected void setCanGo(boolean cango){
		canGo = cango;
	}
	
	/**
	 * ���ͼƬ����
	 * @return
	 * */
	public int getType(){
		return imgType;
	}
	/**
	 * ���MapKind
	 * @return
	 * */
	public MapKind getKind(){
		return kind;
	}
	/**
	 * ��ô����¼���Ҫ�ķ���
	 * @return
	 * */
	public Direction getDirection(){
		return direction;
	}
	public void editMapList(ArrayList<Map> mapList){}
	
	public ArrayList<String> getInfo(){
		return null;
	}
	public void die(BufferedImage map){
	}
}
