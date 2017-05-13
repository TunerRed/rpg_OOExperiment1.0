package model.rpg.map.MapObjects;

import java.util.ArrayList;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.Player;
import model.rpg.map.Map;
import model.rpg.map.MapEditer;
import model.rpg.map.MapKind;

/**
 * ������ĳ����Ϣ֮��
 * ���ĳ�����������ɾͣ�
 * ��ʱ��ͼ��ĳ���ط������ı�
 * �Ӷ���������һ����仯�ĵ�ͼ��̽��
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
	 * ���ݵ�ͼ��Ϣ��ͬ��ִ�в�ͬ�������ı��ͼĳ���ط���״̬
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
