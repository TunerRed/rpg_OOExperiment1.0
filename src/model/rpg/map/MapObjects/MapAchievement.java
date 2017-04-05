package model.rpg.map.MapObjects;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.map.Map;
import model.rpg.map.MapKind;

/**
 * 当看过某个信息之后
 * 达成某个条件（即成就）
 * 此时地图的某个地方发生改变
 * 从而让人物在一个会变化的地图中探索
 * */

public class MapAchievement extends MapEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String changeSomewhere,no;
	private ArrayList<String> Temp = Null;
	private Map temp;
	private boolean showingDialog = false,opened = false;
	public MapAchievement(int[] infoNum, Direction direction,int changeMethod) {
		super(infoNum, direction);
		if(infoNum[0] == -1){
			no = "I"+infoNum[1];
		}
		super.changeKind(MapKind.ACHIEVEMENT);
		changeSomewhere = properties.getProperty("C"+changeMethod);
	}
	public MapAchievement(int[] infoNum, Direction direction,int changeMethod,int imgType) {
		super(infoNum, direction,imgType);
		if(infoNum[0] == -1){
			no = "I"+infoNum[1];
		}	
		super.changeKind(MapKind.ACHIEVEMENT);
		changeSomewhere = properties.getProperty("C"+changeMethod);
	}
	
	public MapAchievement(int[] infoNum,Direction direction,Item item,int changeMethod,int imgType) {
		this(infoNum,direction,changeMethod,imgType);
		super.item = item;
		super.changeKind(MapKind.ACHIEVEMENT);
	}
	/**
	 * 根据地图信息不同，执行不同操作，改变地图某个地方的状态
	 * */
	public void editMapList(ArrayList<Map> mapList){
		temp = mapList.get(Integer.parseInt(changeSomewhere.split("_")[0]));
		switch(Integer.parseInt(changeSomewhere.split("_")[0])){
		case 4:
			Temp = super.getInfo();
			//鬼打墙更改为进入boss的门
			if(Integer.parseInt(changeSomewhere.split("_")[1])==6)
				temp.edit(6, 4,new MapDoor(5,6,10,36));
			else if(Integer.parseInt(changeSomewhere.split("_")[1])==3){
				temp.edit(5, 12,new MapEvent(new int[]{0}, Direction.U));
				temp.edit(3, 12, new MapObject(MapKind.BARY,66));
			}
				
			break;
		case 0:
			if(!no.isEmpty()&& !showingDialog && !opened){
				//输入密码
				showingDialog = true;
				if(JOptionPane.showInputDialog(properties.getProperty(no)).equals("1000")){
					super.getInfo().remove(0);
					super.getInfo().remove(0);
					Temp = super.getInfo();
					opened = true;
				}
			}
			break;
		default:
				break;
		}	
	}
	
	/**
	 * 返回信息，如果是结局相关的条件，检验是否达成后选择是否给予好结局
	 * */
	@Override
	public ArrayList<String> getInfo(){
		showingDialog = false;
		if(opened){
			//好结局记录
			temp.edit(Integer.parseInt(changeSomewhere.split("_")[1]), Integer.parseInt(changeSomewhere.split("_")[2]),
					new MapObject(MapKind.FLOOR,0));
		}
		return Temp;
	}
}
