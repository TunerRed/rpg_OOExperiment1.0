package model.rpg;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import model.Game;
import model.GameObject;
import model.rpg.map.Map;
import model.rpg.map.MapObjects.MapObject;
import model.start.ItemObject;
import control.*;
/**
 * 游戏运行时主要重画此类即内部的地图和人物，
 * 这个类中应存在一个saveData类，
 * 里面设置ArrayList Map 、 Item 和currentMap，还应该存在一个ObjectWriter负责生成saveData的文件，即存档，
 * 为此，此类应拥有一个带有saveData形参的构造方法，负责传入LoadObject传入的saveData
 * */
public class RpgObject extends GameObject{
	
	//一次读取所有地图
	protected ArrayList<Map> map = new ArrayList<Map>();
	protected Map currentMap;
	protected MapObject doorInfo;
	private ItemObject itemObject;
	//读取地图文件
	private ObjectInputStream read;
	//记录目前状态
	private boolean isRunning = false,isStanding = true;
	protected Direction direction = Direction.R;
	private long startTime = System.currentTimeMillis();
	private long lastTimeBefore = 0;
	
	/**
	 * 默认构造方法，
	 * 读取全部地图。
	 * */
	public RpgObject(){
		super.clearStatic();
		try {
			read=new ObjectInputStream(this.getClass()
					.getClassLoader().getResourceAsStream("source/rpg/maps/keting.map"));
			map.add((Map)read.readObject());
			read=new ObjectInputStream(this.getClass()
					.getClassLoader().getResourceAsStream("source/rpg/maps/tushushi.map"));
			map.add((Map)read.readObject());
			read=new ObjectInputStream(this.getClass()
					.getClassLoader().getResourceAsStream("source/rpg/maps/woshi1.map"));
			map.add((Map)read.readObject());
			read=new ObjectInputStream(this.getClass()
					.getClassLoader().getResourceAsStream("source/rpg/maps/woshi2.map"));
			map.add((Map)read.readObject());
			read=new ObjectInputStream(this.getClass()
					.getClassLoader().getResourceAsStream("source/rpg/maps/guidaqiang.map"));
			map.add((Map)read.readObject());
			read=new ObjectInputStream(this.getClass()
					.getClassLoader().getResourceAsStream("source/rpg/maps/final.map"));
			map.add((Map)read.readObject());
			for(int i = 0; i < map.size(); i++){
				map.get(i).merge();
			}
			
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		this.currentMap = map.get(3);
		itemObject = new ItemObject();
		Player.getInstance().addItem(new Item(4));
		super.musicStart("source/rpg/时钟.wav");
	}
	/**
	 * 选择读档时使用的构造方法
	 * @param save 要读取的存档
	 * */
	public RpgObject(SaveData save){
		this(save,0);
		super.musicStart("source/rpg/时钟.wav");
	}
	/**
	 * 选择读档时使用的构造方法，不播放音乐，故事模式之后专用
	 *  @param save 要读取的存档
	 *  @param doNotPlayLoopAgain 只要使用此构造方法，则不再次播放背景音乐
	 * */
	public RpgObject(SaveData save,int doNotPlayLoopAgain){
		super.clearStatic();
		map = save.getMaps();
		currentMap = save.getCurrent();
		Map.playerX = save.getX();
		Map.playerY = save.getY();
		direction = save.getDirection();
		Player.getInstance().updateItems(save.getItem());
		lastTimeBefore = save.getLastTime();
		itemObject = new ItemObject();
	}
	
	@Override
	protected void keyResponse() {
		//走、跑和静止操作，默认人物为走动
		isStanding = false;
		//调出存档和返回标题界面的面板
		if(Control.C&&super.checkTimeGap(100)&&!Map.startChase){
				Game.getInstance().setSaving(true);
				//除去按下C人物会做出走动动画的BUG
				isStanding = true;
				return;
		}else if(Control.X&&Game.getInstance().isSaving()){
			Game.getInstance().setSaving(false);
			return;
		}
		if(Control.UP){
			direction = Direction.U;
		}
		else if(Control.DOWN){
			direction = Direction.D;
		}
		else if(Control.LEFT){
			direction = Direction.L;
		}
		else if(Control.RIGHT){
			direction = Direction.R;
		}
		else {
			isStanding = true;
		}
		if(Control.Z){
			isRunning = true;
		}
		else{
			isRunning = false;
		}
		if(Control.X && super.checkTimeGap(100)){
			super.click();
			itemObject.show();
		}
	}
	
	/**
	 * 重画操作。
	 * 检查各种东西，包括：
	 * 镜子的反光，是否达成条件、调查物品、门的检查、Auto模块的检查。
	 * */
	public void paint(Graphics g){
		//清屏操作
		g.clearRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		
		//站立不动时调用
		if(isStanding){
			currentMap.paint(g);
			Player.getInstance().draw(g,direction);
			currentMap.checkShell(g);
			//最后加上阴影
			//g.drawImage(ImageSets.getImg(52), 0, 0, null);
			
			//检查是否达成某个条件
			if(currentMap.isAchievement(direction) && Control.Z ){
				currentMap.editMapList(map);
				currentMap.remerge();
				//调查物品显示信息
				currentMap.keyResponse(g, direction);
				//显示对话框时焦点不在JPanel上，不能自动将Z设为false
				Control.Z = false;
			}else{
				currentMap.keyResponse(g, direction);
			}
			
		}
		
		//走动时调用
		else{
			//门的操作与地图转换
			if(currentMap.isDoor(direction)){
				super.tempSoundPlay("source/rpg/door.wav");
				doorInfo = currentMap.getDoor(direction);
				currentMap = map.get(doorInfo.toWhich);
				currentMap.setLocation(doorInfo.playerX, doorInfo.playerY);
				return;
			}
			currentMap.paint(g,direction,isRunning);
			Player.getInstance().draw(g,isRunning,direction);
			currentMap.checkShell(g);
			//g.drawImage(ImageSets.getImg(52), 0, 0, null);
		}
		
		//检验是否为死亡点或剧情
		currentMap.checkAuto(g);
		
	}
	
	@Override
	public void draw(Graphics g) {
		try{
			isStanding = true;
			
			if(itemObject.isShow()){
				paint(g);
				itemObject.draw(g);
				return;
			}
			//显示信息时人物不能走动
			if(!currentMap.showInfo ){
				keyResponse();
			}
			paint(g);
		}catch(Exception e){
			e.getStackTrace();
		}	
	}
	
	/**
	 * 获取当前游戏进度，存档用
	 * */
	public SaveData getNowStatus(){
		return new SaveData(map,Player.getInstance().getItems(),currentMap,Map.playerX,Map.playerY,direction,lastTimeBefore + ( System.currentTimeMillis() - startTime ));
	}
	
	@Override
	public void die() {
		super.musicStop();
	}

}
