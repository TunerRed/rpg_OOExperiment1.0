package model.rpg.map.MapObjects;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import model.rpg.Direction;
import model.rpg.map.Map;
import model.rpg.map.MapKind;
/**
 * 已实现序列化因而可以写入存档的地图元素类
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
	 * 构造方法，
	 * 初级判断这个元素是不是人物可以踏上去的
	 * @param kind Map的种类
	 * @param imgType 选择的ImageSets中的图片
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
	 * 获得图片类型
	 * @return
	 * */
	public int getType(){
		return imgType;
	}
	/**
	 * 获得MapKind
	 * @return
	 * */
	public MapKind getKind(){
		return kind;
	}
	/**
	 * 获得触发事件需要的方向
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
