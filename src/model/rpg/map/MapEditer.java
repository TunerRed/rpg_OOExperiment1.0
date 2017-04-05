package model.rpg.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.rpg.Direction;
import model.rpg.Item;
import model.rpg.map.MapObjects.MapAchievement;
import model.rpg.map.MapObjects.MapAuto;
import model.rpg.map.MapObjects.MapDoor;
import model.rpg.map.MapObjects.MapEvent;
import model.rpg.map.MapObjects.MapObject;

/**
 * 地图编辑器
 * 
 * 在实际游戏中并没有什么具体的作用
 * 只有编辑并生成大块地图的功能
 * 在游戏开发维护过程中才有用
 * */
public class MapEditer {

	private String path;
	private Map map;
	private ObjectOutputStream output;

	public static void main(String[] args) {
		System.out.println("BUILD START");
		new MapEditer();
		System.out.println("BUILD SUCCESSFULLY");
	}

	public MapEditer(){
		editMap();
	}
	
	public void write() {
		try {
			output = new ObjectOutputStream(new FileOutputStream(new File(path)));
			output.writeObject(map);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editMap() {
		this.path = "bin/source/rpg/maps/keting.map";
		keting();
		write();
		this.path = "bin/source/rpg/maps/tushushi.map";
		tushushi();
		write();
		this.path = "bin/source/rpg/maps/woshi1.map";
		woshi1();
		write();
		this.path = "bin/source/rpg/maps/woshi2.map";
		woshi2();
		write();
		this.path = "bin/source/rpg/maps/guidaqiang.map";
		guidaqiang();
		write();
		this.path = "bin/source/rpg/maps/final.map";
		finalMap();
		write();
	}
	
	public void finalMap(){
		map = new Map(11,14);
		for(int i = 4;i<=8;i++)
			for(int j = 5; j <= 9 ; j++)
				map.edit(i, j, new MapObject(MapKind.FLOOR,36));
		//画上面的墙
		for (int j = 5; j <= 9; j++)
			map.edit(3, j, new MapObject(MapKind.BARY, 2));
		//画下面的墙
		for (int j = 5; j <= 9; j++)
			map.edit(9, j, new MapObject(MapKind.BARY, 1));
		
		//画左面的墙
		for (int j = 4; j <= 8; j++)
			map.edit(j, 4, new MapObject(MapKind.BARY, 4));
		//画右面的墙
		for (int j = 4; j <= 8; j++)
			map.edit(j, 10, new MapObject(MapKind.BARY, 3));
		map.edit(4, 10, new MapObject(MapKind.BARY, 7));
		map.edit(5, 10, new MapObject(MapKind.BARY, 0));
		map.edit(6, 10, new MapObject(MapKind.FLOOR, 36));
		map.edit(6, 11, new MapDoor(4,6,5,0));
		map.edit(7, 10, new MapObject(MapKind.BARY, 5));
		map.edit(6, 9, new MapAuto(36,1));
	}
	
	public void guidaqiang(){
		map = new Map(13,30);
		for(int i = 5 ; i <= 7 ; i++)
			for(int j = 6 ; j <= 23 ; j++)
				map.edit(i, j, new MapObject(MapKind.FLOOR,36));
		for(int j = 6 ; j <= 23 ; j++)
			map.edit(3, j, new MapObject(MapKind.BARY,2));
		map.edit(3, 12, new MapObject(MapKind.BARY,66));
		map.edit(5, 12, new MapEvent(new int[]{93,94,95},Direction.U,new Item(2),36));
		for(int j = 6 ; j <= 23 ; j++)
			map.edit(8, j, new MapObject(MapKind.BARY,1));
		
		map.edit(4, 5, new MapObject(MapKind.BARY,8));
		map.edit(7, 5, new MapObject(MapKind.BARY,6));
		map.edit(6, 5, new MapObject(MapKind.FLOOR,36));
		
		map.edit(4, 24, new MapObject(MapKind.BARY,7));
		map.edit(6, 24, new MapObject(MapKind.FLOOR,36));
		map.edit(7, 24, new MapObject(MapKind.BARY,5));
		map.edit(6, 23, new MapAuto(36,6));
		
		map.edit(6, 4, new MapDoor(4,6,24,0));
		map.edit(6, 25, new MapDoor(3,10,5,0));
	}
	
	public void tushushi(){
		map = new Map(15,16);
		for(int i = 5;i<=10;i++)
			for(int j = 5; j <= 11 ; j++)
				map.edit(i, j, new MapObject(MapKind.FLOOR,36));
		//画上面的墙
		for (int j = 5; j <= 11; j++)
			map.edit(3, j, new MapObject(MapKind.BARY, 2));
		//画下面的墙
		for (int j = 5; j <= 11; j++)
			map.edit(11, j, new MapObject(MapKind.BARY, 1));
		//画左面的墙
		for (int j = 4; j <= 10; j++)
			map.edit(j, 4, new MapObject(MapKind.BARY, 4));
		//画右面的墙
		for (int j = 4; j <= 10; j++)
			map.edit(j, 12, new MapObject(MapKind.BARY, 3));
		map.edit(11, 8, new MapObject(MapKind.FLOOR,36));
		map.edit(11, 7, new MapObject(MapKind.BARY,6));
		map.edit(11, 9, new MapObject(MapKind.BARY,5));
		
		map.edit(7, 11, new MapObject(MapKind.BARY,11));
		map.edit(10, 6, new MapObject(MapKind.BARY,12));
		
		//画书架
		map.edit(4, 6, new MapObject(MapKind.BARY,14));
		map.edit(4, 7, new MapObject(MapKind.BARY,0));
		map.edit(5, 6, new MapObject(MapKind.BARY,0));
		map.edit(5, 7, new MapObject(MapKind.BARY,0));
		
		map.edit(4, 9, new MapObject(MapKind.BARY,14));
		map.edit(5, 9, new MapObject(MapKind.BARY,0));
		map.edit(4, 10, new MapObject(MapKind.BARY,0));
		map.edit(5, 10, new MapObject(MapKind.BARY,0));
		//画特殊的书架
		//左半块
		map.edit(6, 6, new MapEvent(new int[]{15},Direction.U,44));
		map.edit(6, 9, new MapObject(MapKind.FLOOR,44));
		map.edit(8, 6, new MapObject(MapKind.FLOOR,44));
		map.edit(8, 9, new MapObject(MapKind.FLOOR,44));
		
		map.edit(5, 6, new MapObject(MapKind.SHELL,0));
		map.edit(5, 9, new MapObject(MapKind.SHELL,0));
		map.edit(7, 6, new MapObject(MapKind.SHELL,0));
		map.edit(7, 9, new MapObject(MapKind.SHELL,0));
		map.edit(9, 6, new MapObject(MapKind.SHELL,0));
		map.edit(9, 9, new MapObject(MapKind.SHELL,0));
		//右半块
		map.edit(6, 7, new MapObject(MapKind.FLOOR,45));
		map.edit(6, 10, new MapObject(MapKind.FLOOR,45));
		map.edit(8, 7, new MapObject(MapKind.FLOOR,45));
		map.edit(8, 10, new MapObject(MapKind.FLOOR,45));
		
		map.edit(5, 7, new MapObject(MapKind.SHELL,0));
		map.edit(5, 10, new MapObject(MapKind.SHELL,0));
		map.edit(7, 7, new MapObject(MapKind.SHELL,0));
		map.edit(7, 10, new MapObject(MapKind.SHELL,0));
		map.edit(9, 7, new MapObject(MapKind.SHELL,0));
		map.edit(9, 10, new MapObject(MapKind.SHELL,0));
		
		map.edit(12, 8, new MapDoor(0,7,7,0));
		map.edit(6, 6, new MapAchievement(new int[]{-1,15,80,81,82,83,84,85,86,87,88,89,90,91,92,0,96},Direction.U,0,44));
		map.edit(8, 10, new MapEvent(new int[]{21,22},Direction.U,45));
	}
	
	public void woshi2(){
		map = new Map(15,17);
		for(int i = 5;i<=10;i++)
			for(int j = 5; j <= 11 ; j++)
				map.edit(i, j, new MapObject(MapKind.FLOOR,36));
		//画上面的墙
		for (int j = 5; j <= 11; j++)
			map.edit(3, j, new MapObject(MapKind.BARY, 2));
		//画下面的墙
		for (int j = 4; j <= 11; j++)
			map.edit(11, j, new MapObject(MapKind.BARY, 1));
		
		//画左面的墙
		for (int j = 4; j <= 10; j++)
			map.edit(j, 4, new MapObject(MapKind.BARY, 4));
		//画右面的墙
		for (int j = 4; j <= 10; j++)
			map.edit(j, 12, new MapObject(MapKind.BARY, 3));
		map.edit(5, 5, new MapObject(MapKind.BARY,30));
		map.edit(6, 5, new MapObject(MapKind.BARY,0));
		
		map.edit(5, 6, new MapObject(MapKind.BARY,19));
		
		map.edit(7, 7, new MapObject(MapKind.BARY,20));
		map.edit(7, 8, new MapObject(MapKind.BARY,0));
		
		map.edit(4, 10, new MapObject(MapKind.BARY,21));
		map.edit(5, 10, new MapObject(MapKind.BARY,0));
		map.edit(4, 11, new MapObject(MapKind.BARY,0));
		map.edit(5, 11, new MapObject(MapKind.BARY,0));
		
		map.edit(7, 12, new MapObject(MapKind.BARY,7));
		map.edit(8, 12, new MapObject(MapKind.BARY,0));
		map.edit(9, 12, new MapObject(MapKind.FLOOR,36));
		map.edit(10, 12, new MapObject(MapKind.BARY,5));
		
		map.edit(9, 13, new MapDoor(0,5,14,0,false,new Item(2)));
		
		map.edit(8, 4, new MapObject(MapKind.BARY, 8));
		map.edit(9, 4, new MapObject(MapKind.BARY, 0));
		map.edit(10, 4, new MapDoor(4,6,24,0));
		
		map.edit(6, 6, new MapAuto(36,3));
	}
	
	public void woshi1(){
		map = new Map(14,19);
		for(int i =5;i<=10;i++)
			for(int j =6;j<=15;j++)
				map.edit(i, j, new MapObject(MapKind.FLOOR,36));
		//画上面的墙
		for (int j = 6; j <= 15; j++)
			map.edit(3, j, new MapObject(MapKind.BARY, 2));
		//画下面的墙
		for (int j = 6; j <= 15; j++)
			map.edit(11, j, new MapObject(MapKind.BARY, 1));
		//画左面的墙
		for (int j = 4; j <= 10; j++)
			map.edit(j, 5, new MapObject(MapKind.BARY, 4));
		//画右面的墙
		for (int j = 4; j <= 10; j++)
			map.edit(j, 16, new MapObject(MapKind.BARY, 3));
		map.edit(4, 5+1, new MapObject(MapKind.BARY,13));
		map.edit(4, 6+1, new MapObject(MapKind.BARY,0));
		map.edit(5, 5+1, new MapObject(MapKind.BARY,0));
		map.edit(5, 6+1, new MapObject(MapKind.BARY,0));
		map.edit(3, 7+1, new MapObject(MapKind.BARY,33));
		map.edit(3, 11+1, new MapObject(MapKind.BARY,33));
		map.edit(3, 9+1, new MapObject(MapKind.BARY,35));
		map.edit(4, 13+1, new MapObject(MapKind.BARY,14));
		map.edit(4, 14+1, new MapObject(MapKind.BARY,0));
		map.edit(5, 13+1, new MapObject(MapKind.BARY,0));
		map.edit(5, 14+1, new MapObject(MapKind.BARY,0));
		map.edit(8, 14+1, new MapObject(MapKind.BARY,30));
		map.edit(9, 14+1, new MapObject(MapKind.BARY,0));
		map.edit(6, 13+1, new MapEvent(new int[]{1}, Direction.U));
		map.edit(6, 14+1, new MapEvent(new int[]{1}, Direction.U));
		map.edit(5, 9+1, new MapEvent(new int[]{2}, Direction.U,new Item(3)));
		map.edit(5, 7+1, new MapEvent(new int[]{3}, Direction.U));
		map.edit(5, 11+1, new MapEvent(new int[]{3}, Direction.U));
		map.edit(9, 13+1, new MapEvent(new int[]{4}, Direction.R));
		map.edit(9, 6+1, new MapAuto(43,0));
		map.edit(7, 4+1, new MapObject(MapKind.BARY,8));
		map.edit(8, 4+1, new MapObject(MapKind.BARY,0));
		
		map.edit(10, 4+1, new MapObject(MapKind.BARY,6));
		map.edit(9, 4+1, new MapObject(MapKind.FLOOR,36));
		
		map.edit(9, 4, new MapDoor(0,5,16,0));
	}	
	
	public void keting(){
		map = new Map(20,24);
		//画地板
		for(int i = 8 ;i <= 15;i++)
			for(int j = 5 ; j <= 20;j++)
				map.edit(i, j, new MapObject(MapKind.FLOOR, 36));
		//画上面的墙
		for (int j = 5; j <= 20; j++)
			map.edit(6, j, new MapObject(MapKind.BARY, 2));
		//画下面的墙
		for (int j = 5; j <= 20; j++)
			map.edit(16, j, new MapObject(MapKind.BARY, 1));
		//画左面的墙
		for (int j = 7; j <= 15; j++)
			map.edit(j, 4, new MapObject(MapKind.BARY, 4));
		//画右面的墙
		for (int j = 7; j <= 15; j++)
			map.edit(j, 21, new MapObject(MapKind.BARY, 3));
		
		//门的编辑
		map.edit(6, 7, new MapDoor(1,11,8,38,false,new Item(1)));
		map.edit(5, 13, new MapDoor(3,9,12,0));
		map.edit(5, 17, new MapDoor(2,9,5,0));
		
		map.edit(6, 14, new MapObject(MapKind.BARY,9));
		map.edit(6, 16, new MapObject(MapKind.BARY,10));
		map.edit(6, 15, new MapObject(MapKind.FLOOR,36));
		map.edit(3, 14, new MapObject(MapKind.BARY,2));
		map.edit(3, 15, new MapObject(MapKind.BARY,2));
		map.edit(3, 16, new MapObject(MapKind.BARY,2));
		map.edit(5, 14, new MapObject(MapKind.FLOOR,36));
		map.edit(5, 15, new MapObject(MapKind.FLOOR,36));
		map.edit(5, 16, new MapObject(MapKind.FLOOR,36));
		
		map.edit(6, 13, new MapObject(MapKind.BARY,37));
		map.edit(7, 13, new MapObject(MapKind.BARY,0));
		
		map.edit(7, 7, new MapObject(MapKind.FLOOR,36));
		map.edit(7, 15, new MapObject(MapKind.FLOOR,36));
		map.edit(7, 18, new MapObject(MapKind.BARY,32));
		map.edit(7, 19, new MapObject(MapKind.BARY,0));
		map.edit(8, 18, new MapObject(MapKind.BARY,0));
		map.edit(8, 19, new MapObject(MapKind.BARY,0));
		
		map.edit(10, 8, new MapObject(MapKind.BARY,41));
		map.edit(11, 8, new MapObject(MapKind.BARY,0));
		map.edit(10, 9, new MapObject(MapKind.BARY,0));
		map.edit(11, 9, new MapObject(MapKind.BARY,0));
		map.edit(10, 7, new MapObject(MapKind.BARY,17));
		map.edit(11, 7, new MapObject(MapKind.BARY,0));
		map.edit(10, 18, new MapObject(MapKind.BARY,18));
		map.edit(11, 18, new MapObject(MapKind.BARY,0));
		map.edit(10, 19, new MapObject(MapKind.BARY,0));
		map.edit(11, 19, new MapObject(MapKind.BARY,0));
		map.edit(7, 20, new MapObject(MapKind.BARY,31));
		map.edit(8, 20, new MapObject(MapKind.BARY,0));
		
		map.edit(13, 7, new MapObject(MapKind.BARY,26));
		map.edit(13, 8, new MapObject(MapKind.BARY,0));
		map.edit(13, 9, new MapObject(MapKind.BARY,42));
		map.edit(13, 10, new MapObject(MapKind.BARY,0));
		map.edit(14, 10, new MapObject(MapKind.BARY,27));
		map.edit(15, 10, new MapObject(MapKind.BARY,0));
		map.edit(14, 7, new MapObject(MapKind.BARY,34));
		map.edit(14, 8, new MapObject(MapKind.BARY,28));
		map.edit(14, 9, new MapObject(MapKind.BARY,0));
		map.edit(15, 9, new MapObject(MapKind.BARY,29));
		map.edit(12, 18, new MapObject(MapKind.BARY,15));
		map.edit(12, 19, new MapObject(MapKind.BARY,0));
		map.edit(10, 20, new MapObject(MapKind.FLOOR,16));
		map.edit(11, 20, new MapObject(MapKind.FLOOR,0));
		
		map.edit(6, 4, new MapObject(MapKind.BARY,22));
		map.edit(6, 21, new MapObject(MapKind.BARY,23));
		map.edit(16, 4, new MapObject(MapKind.BARY,24));
		map.edit(16, 21, new MapObject(MapKind.BARY,25));
		
		map.edit(16, 13, new MapObject(MapKind.BARY,6));
		
		map.edit(16, 15, new MapObject(MapKind.BARY,5));
		
		map.edit(8, 13, new MapEvent(new int[]{5,9},Direction.U));
		map.edit(12, 8, new MapEvent(new int[]{6},Direction.U));
		map.edit(9, 20, new MapAchievement(new int[]{1,20,21,16,17,18},Direction.U,1));
		map.edit(16, 14, new MapEvent(new int[]{8},Direction.D));
		
		map.edit(15, 8, new MapEvent(new int[]{11,12,13,14},Direction.R,new Item(1)));
		
	}
}
