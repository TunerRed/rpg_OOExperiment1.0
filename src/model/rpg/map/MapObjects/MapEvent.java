package model.rpg.map.MapObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.Player;
import model.rpg.map.MapKind;

@SuppressWarnings("serial")
public class MapEvent extends MapObject {
	protected static Properties properties = new Properties();
	protected static ArrayList<String> Null = new ArrayList<String>();
	static {
		try {
			properties.load(MapEvent.class.getClassLoader().getResourceAsStream("source/rpg/properties/eventInfo.properties"));
			Null.add("...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private ArrayList<String> info = new ArrayList<String>();
	
	private boolean hasShown = false;
	protected Item item = null;
	
	/**
	 * 多种构造方法
	 * 默认图片为地面
	 * 默认没有添加的物品，图片地面
	 * 带有物品的构造方法
	 * 带有物品并且图片可以编辑的构造方法
	 * @param infoNum 文字信息，在EventInfo.properties中查找
	 * @param direction 时间触发的方向
	 * */
	
	public MapEvent(int[] infoNum,Direction direction) {
		super(MapKind.EVENT,36);
		init(infoNum,direction);
	}
	public MapEvent(int[] infoNum,Direction direction,int imgType) {
		super(MapKind.EVENT,imgType);
		init(infoNum,direction);
	}
	public MapEvent(int[] infoNum,Direction direction,Item item){
		this(infoNum,direction);
		this.item = item;
	}
	public MapEvent(int[] infoNum,Direction direction,Item item,int imgType){
		this(infoNum,direction,imgType);
		this.item = item;
	}
	private void init(int[] infoNum,Direction direction){
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
	
	/**
	 * 返回这个事件在调查后应该显示的信息
	 * 如果调查后会获得物品则应该为人物添加物品，
	 * 并用boolean设置不会再添加第二次
	 * */
	public ArrayList<String> getInfo(){
		if(item != null && !hasShown){
			Player.getInstance().addItem(item);
			hasShown = true;
		}else if(item !=null && hasShown){
			return Null;
		}
		return info;
	}
}
