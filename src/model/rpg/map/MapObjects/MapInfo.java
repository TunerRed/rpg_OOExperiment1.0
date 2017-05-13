package model.rpg.map.MapObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import model.rpg.Direction;
import model.rpg.map.MapKind;


public class MapInfo extends MapObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Properties properties = new Properties();
	static {
		try {
			properties.load(MapInfo.class.getClassLoader().getResourceAsStream("source/rpg/properties/Info.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected ArrayList<String> info = new ArrayList<String>();
	
	/**
	 * 多种构造方法
	 * 默认图片为地面
	 * 默认没有添加的物品，图片地面
	 * 带有物品的构造方法
	 * 带有物品并且图片可以编辑的构造方法
	 * @param infoNum 文字信息，在EventInfo.properties中查找
	 * @param direction 时间触发的方向
	 * */
	
	public MapInfo(int[] infoNum,Direction direction) {
		super(MapKind.INFO);
		init(infoNum,direction);
	}
	
	private void init(int[] infoNum,Direction direction){
		info.clear();
		for(int i = 0 ; i < infoNum.length ; i++){
			info.add(properties.getProperty("I"+infoNum[i],"..."));
		}
		super.direction = direction;
	}
	/**
	 * 改变这个元素的MapKind，给子类使用。
	 * @param kind Map的种类
	 * */
	protected void changeKind(MapKind kind){
		super.kind = kind;
	}
	
	public ArrayList<String> getInfo(){
		return info;
	}
	
	public void clearInfo(){
		info.clear();
		info.add("...");
	}
}
