package model.rpg.map.MapObjects;

import java.util.ArrayList;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.Player;
import model.rpg.map.Map;
import model.rpg.map.MapEditer;
import model.rpg.map.MapKind;

/**
 * 当看过某个信息之后
 * 达成某个条件（即成就）
 * 此时地图的某个地方发生改变
 * 从而让人物在一个会变化的地图中探索
 * */

public class MapEvent extends MapInfo {
	private static final long serialVersionUID = 1L;
	private String []subEdit = null;
	private boolean triger = false;
	protected Item item = null;
	
	public MapEvent(int[] infoNum,Direction direction,Item item,String[] subEdit,int imgType) {
		super(infoNum, direction);
		super.changeKind(MapKind.EVENT);
		this.item = item;
		setImage(imgType);
		this.subEdit = subEdit;
	}
	/**
	 * 根据地图信息不同，执行不同操作，改变地图某个地方的状态
	 * */
	public void editMapList(ArrayList<Map> mapList){
		if(triger)
			return;
		triger = true;
		if(item !=null){
			Player.getInstance().addItem(item);
		}
		if(subEdit == null)
			return;
		MapEditer editer = new MapEditer();
		editer.loadProperties();
		editer.setMaps(mapList);
		for(int i = 0; i < subEdit.length; i++){
			editer.parse(subEdit[i]);
		}
	}
}
