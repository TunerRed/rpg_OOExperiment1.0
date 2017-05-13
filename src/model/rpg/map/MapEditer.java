package model.rpg.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import control.ImageSets;
import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.map.MapObjects.MapAuto;
import model.rpg.map.MapObjects.MapDoor;
import model.rpg.map.MapObjects.MapEvent;
import model.rpg.map.MapObjects.MapInfo;
import model.rpg.map.MapObjects.MapObject;

/**
 * 地图编辑器
 * 
 * 在实际游戏中并没有什么具体的作用
 * 只有编辑并生成大块地图的功能
 * 在游戏开发维护过程中才有用
 * */
public class MapEditer {

	private ArrayList<String> paths;
	private ArrayList<Map> maps;
	private ObjectOutputStream output;
	private Properties properties = new Properties();
	private Properties auto_properties = new Properties();

	public static void main(String[] args) {
		System.out.println("BUILD START");
		new MapEditer().init();
		System.out.println("BUILD SUCCESSFULLY");
	}
	
	public void init(){
		ImageSets.init();
		createMap();
		editMap();
		write();
	}
	
	public void write() {
		try {
			for(int i = 0; i < paths.size(); i++){
				File file = new File(paths.get(i));
				if(file.exists())
					file.delete();
				output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(maps.get(i));
				
				output.flush();
				output.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createMap(){
		loadProperties();
		maps = new ArrayList<Map>();
		paths = new ArrayList<String>();
		int createCount = 0;
		while(true){
			String createProperty = properties.getProperty("M"+createCount);
			if(createProperty==null)
				break;
			else{
				String createInfo[] = createProperty.split("_");
				//create map from file information
				Map map = new Map(Integer.parseInt(createInfo[2]),Integer.parseInt(createInfo[3]));
				map.setMapNo(Integer.parseInt(createInfo[0]));
				maps.add(map);		
				String path = "src/source/rpg/maps/"+createInfo[1]+".map";
				paths.add(path);
			}
			createCount++;
		}
	}
	
	public boolean loadProperties(){
		try {
			properties.load(MapEditer.class.getClassLoader().getResourceAsStream("source/rpg/properties/Maps.properties"));
			auto_properties.load(MapEditer.class.getClassLoader().getResourceAsStream("source/rpg/properties/Auto.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return true;
	}
	
	private void editMap(){
		int editCount = 0;
		while(true){
			String editProperty = properties.getProperty("E"+editCount);
			if(parse(editProperty))
				editCount++;
			else
				break;
		}
	}	
	
	public void setMaps(ArrayList<Map> maps){
		this.maps = maps;
	}

	public boolean parse(String editProperty){
		if(editProperty==null)
			return false;
		else{
			String[] editInfo = editProperty.split("_");
			int which = Integer.parseInt(editInfo[0]);
			int row = Integer.parseInt(editInfo[1]);
			int col = Integer.parseInt(editInfo[2]);
			Map map = maps.get(which);
			switch(Integer.parseInt(editInfo[3])){
			case 0:
				map.edit(row, col, new MapObject(MapKind.FLOOR));
				//floor that has different image
				if(editInfo.length > 4){
					map.remerge(row, col, Integer.parseInt(editInfo[4]));
				}
				break;
			case 1:
				map.edit(row, col, new MapObject(MapKind.BLOCK));
				//block that has different image
				if(editInfo.length > 4){
					map.remerge(row, col, Integer.parseInt(editInfo[4]));
				}
				break;
			case 2:
				//direction choose
				Direction d1 = Direction.U;
				switch(Integer.parseInt(editInfo[4])){
				case 0:
					d1 = Direction.U;
					break;
				case 1:
					d1 = Direction.D;
					break;
				case 2:
					d1 = Direction.L;
					break;
				case 3:
					d1 = Direction.R;
					break;
				default:
					break;
				}
				//info list
				String infos[] = editInfo[5].split("/");
				int infosNo[] = new int[infos.length];
				for(int i = 0; i < infos.length; i++){
					infosNo[i] = Integer.parseInt(infos[i]);
				}
				map.edit(row, col, new MapInfo(infosNo,d1));
				break;
			case 3:
				//direction choose
				Direction d2 = Direction.U;
				switch(Integer.parseInt(editInfo[4])){
				case 0:
					d2 = Direction.U;
					break;
				case 1:
					d2 = Direction.D;
					break;
				case 2:
					d2 = Direction.L;
					break;
				case 3:
					d2 = Direction.R;
					break;
				default:
					break;
				}
				//info list
				String infos2[] = editInfo[6].split("/");
				int infosNo2[] = new int[infos2.length];
				for(int i = 0; i < infos2.length; i++){
					infosNo2[i] = Integer.parseInt(infos2[i]);
				}
				
				String subEdit[] = null;
				if(editInfo.length == 8){
					subEdit = editInfo[7].split("/");
					for(int i = 0; i < subEdit.length; i++)
						subEdit[i] = properties.getProperty("A"+subEdit[i]);
				}
					
				Item item = null;
				if(Integer.parseInt(editInfo[5]) > 0){
					item = new Item(Integer.parseInt(editInfo[5]));
				}
				map.edit(row, col, new MapEvent(infosNo2,d2,item,subEdit,-1));
				
				break;
			case 4:
				//System.out.println("AUTO:"+editInfo[4]);
				//System.out.println(auto_properties.getProperty("A"+editInfo[4]));
				map.edit(row, col, 
						new MapAuto("model.rpg.map.MapObjects.auto." + auto_properties.getProperty("A"+editInfo[4])));
				break;
			case 5:
				if(editInfo.length == 5){
					String door[] = editInfo[4].split("/");
					map.edit(row, col, new MapDoor(
							Integer.parseInt(door[0]),Integer.parseInt(door[1]),Integer.parseInt(door[2])));
				}else{
					String door[] = editInfo[4].split("/");
					map.edit(row, col, new MapDoor(
							Integer.parseInt(door[0]),Integer.parseInt(door[1]),Integer.parseInt(door[2]),
							false,new Item(Integer.parseInt(editInfo[5]))
							));
				}
				break;
			case 6:
				map.edit(row, col, new MapObject(MapKind.SHELL));
				map.getSomewhere(row, col).setImage(Integer.parseInt(editInfo[4]));
				break;
			default:
				break;
			}
			return true;
		}
	}
	
}
