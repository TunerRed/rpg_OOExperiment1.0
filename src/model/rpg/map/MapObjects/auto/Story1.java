package model.rpg.map.MapObjects.auto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import control.Lib;
import model.rpg.RpgObject;
import model.rpg.SaveData;
import model.rpg.map.Map;
import model.rpg.map.MapKind;
import model.rpg.map.MapObjects.MapAuto;
import model.rpg.map.MapObjects.MapObject;
import model.start.LoadObject;
/**追逐战前奏*/
public class Story1 extends Story {
	private ArrayList<Map> maps;
	public Story1(BufferedImage map) {
		super(map);
		// TODO 自动生成的构造函数存根
		super.DISTANCE_TO_DIE = 6;
		super.initial = "A";
		super.musicStop();
		super.musicStart("source/rpg/chase.wav");
		maps = LoadObject.read(3).getMaps();
		//关闭图书室，追逐战时不能再进入
		maps.get(0).edit(6, 7, new MapObject(MapKind.BARY,38));
		//此处激活门，让人物可以通过门
		//达成成就时客厅地图左上角属性变为floor，此处检验，如果是floor，说明已经达成真结局的条件
		if(maps.get(0).getSomewhere(0, 0).canGo())
			maps.get(0).edit(16, 14, new MapAuto(36,4));
		else
			maps.get(0).edit(16, 14, new MapAuto(36,5));
		LoadObject.read(3).getCurrent();
		LoadObject.read(3).getCurrent();
		LoadObject.write(new SaveData(maps , 
				LoadObject.read(3).getItem() , LoadObject.read(3).getCurrent(),Map.playerX,Map.playerY , LoadObject.read(3).getDirection(),LoadObject.read(3).getLastTime()));
	}
	
	protected void paint(Graphics g){
		g.setColor(Color.red);
		g.setFont(Lib.terror);
		super.paint(g);
	}
	
	@Override
	public void die(){
		new RpgObject(LoadObject.read(3),0);
		Map.startChase = true;
	}
	
}
