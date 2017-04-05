package model.rpg;

import java.io.Serializable;
import java.util.ArrayList;

import model.rpg.map.Map;
/**
 * 存档类，实现游戏存档和读档功能。游戏进入Auto状态时也会生成一个另外的publicData.dat存档
 * 当Auto模块结束后即进行一次读档操作，仍然读取publicData.dat存档。
 * */
public class SaveData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Map> map = null;
	private ArrayList<Item> items = null;
	private Map currentMap = null;
	private long lastTime = 0;
	private int x,y;
	private Direction direction;
	public SaveData (){
	}
	/**
	 * 正式使用的构造方法，存储的内容包括：
	 * @param mapList 全部的地图ArrayList
	 * @param currentMap 现在的地图是哪个
	 * @param itemList 人物的物品栏
	 * @param x 人物现在在X坐标
	 * @param y 人物现在在Y坐标
	 * @param direction 人物的方向
	 * @param lastTime 游戏进行了多长时间（不包括Auto使用的时间）
	 * */
	public SaveData (ArrayList<Map> mapList , ArrayList<Item> itemList , Map currentMap ,int x,int y, Direction direction,long lastTime){
		map = mapList;
		items = itemList;
		this.currentMap = currentMap;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.lastTime = lastTime;
	}
	
	public ArrayList<Map> getMaps(){
		return map;
	}
	public ArrayList<Item> getItem(){
		return items;
	}
	public Map getCurrent(){
		return currentMap;
	}
	public Direction getDirection(){
		return direction;
	}
	public long getLastTime(){
		return lastTime;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
