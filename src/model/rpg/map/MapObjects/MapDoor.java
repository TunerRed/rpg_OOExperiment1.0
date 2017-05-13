package model.rpg.map.MapObjects;

import java.util.ArrayList;

import model.rpg.Item;
import model.rpg.Player;
import model.rpg.map.MapKind;
/**
 * 门和地图之间的转换
 * */
@SuppressWarnings("serial")
public class MapDoor extends MapObject {
	private Item item = null;
	public MapDoor(int toWhich, int playerX, int playerY) {
		super(MapKind.DOOR);
		super.setCanGo(true);
		super.toWhich = toWhich;
		super.playerX = playerX;
		super.playerY = playerY;
	}
	
	/**。
	 * 构造方法
	 * @param toWhich 即下一个地图是RpgObject中ArrayList Map 中的第几个，
	 * @param playerX 人物横坐标
	 * @param playerY 人物纵坐标
	 * @param imgType 即图片类型，
	 * @param cango 设置目前是否能走动，
	 * @param item 设置通过这扇门可能需要的物品
	 * */
	public MapDoor(int toWhich, int playerX, int playerY,boolean cango,Item item){
		this(toWhich, playerX, playerY);
		this.item = item;
		super.setCanGo(cango);
	}
	
	/**
	 * 查看目前这扇门能不能走，即是否符合了某个条件
	 * */
	@Override
	public boolean canGo(){
		if(!super.canGo() && checkItem(Player.getInstance().getItems())){
			super.setCanGo(true);
		}
		return super.canGo();
	}
	/**
	 * 检查人物身上是否带有某个物品
	 * */
	private boolean checkItem(ArrayList<Item> items){
		for(int i = 0; i < items.size() ; i++){
			if(items.get(i).equals(item)){
				//Player.getInstance().removeItem(i);
				return true;
			}
		}
		return false;
	}
}
