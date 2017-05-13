package model.rpg;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Properties;

import control.Control;
import control.Lib;
import model.Game;
import model.GameObject;
import model.rpg.map.Map;
import model.rpg.map.MapEditer;
import model.rpg.map.MapObjects.MapObject;
import model.start.ItemObject;
import model.start.LoadObject;
/**
 * ��Ϸ����ʱ��Ҫ�ػ����༴�ڲ��ĵ�ͼ�����
 * �������Ӧ����һ��saveData�࣬
 * ��������ArrayList Map �� Item ��currentMap����Ӧ�ô���һ��ObjectWriter��������saveData���ļ������浵��
 * Ϊ�ˣ�����Ӧӵ��һ������saveData�βεĹ��췽����������LoadObject�����saveData
 * */
public class RpgObject extends GameObject{
	
	//һ�ζ�ȡ���е�ͼ
	protected ArrayList<Map> maps = new ArrayList<Map>();
	protected Map currentMap;
	protected MapObject doorInfo;
	private ItemObject itemObject;
	//��ȡ��ͼ�ļ�
	private ObjectInputStream read;
	//��¼Ŀǰ״̬
	private boolean isRunning = false,isStanding = true,isDoor=false;
	protected Direction direction = Direction.R;
	private long startTime = System.currentTimeMillis();
	private long lastTimeBefore = 0;
	
	/**
	 * Ĭ�Ϲ��췽����
	 * ��ȡȫ����ͼ��
	 * */
	public RpgObject(){
		super.clearStatic();
		loadMaps();
		itemObject = new ItemObject();
		super.musicStart("source/rpg/ʱ��.wav");
	}
	/**
	 * ѡ�����ʱʹ�õĹ��췽��
	 * @param save Ҫ��ȡ�Ĵ浵
	 * */
	public RpgObject(SaveData save){
		this(save,0);
		super.musicStart("source/rpg/ʱ��.wav");
	}
	/**
	 * ѡ�����ʱʹ�õĹ��췽�������������֣�����ģʽ֮��ר��
	 *  @param save Ҫ��ȡ�Ĵ浵
	 *  @param doNotPlayLoopAgain ֻҪʹ�ô˹��췽�������ٴβ��ű�������
	 * */
	public RpgObject(SaveData save,int doNotPlayLoopAgain){
		maps = save.getMaps();
		currentMap = save.getCurrent();
		Map.playerX = save.getX();
		Map.playerY = save.getY();
		direction = save.getDirection();
		Player.getInstance().updateItems(save.getItem());
		lastTimeBefore = save.getLastTime();
		itemObject = new ItemObject();
	}
	
	public void loadMaps(){
		try {
			Properties properties = new Properties();
			properties.load(MapEditer.class.getClassLoader().getResourceAsStream("source/rpg/properties/Maps.properties"));
			maps = new ArrayList<Map>();
			int createCount = 0;
			while(true){
				String createProperty = properties.getProperty("M"+createCount);
				if(createProperty==null)
					break;
				else{
					String createInfo = createProperty.split("_")[1];
					//create map from file information
					read=new ObjectInputStream(this.getClass()
							.getClassLoader().getResourceAsStream("source/rpg/maps/"+createInfo+".map"));
					maps.add((Map)read.readObject());
					
				}
				createCount++;
			}
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		this.currentMap = maps.get(0);
		
	}
	
	@Override
	protected void keyResponse() {
		//�ߡ��ܺ;�ֹ������Ĭ������Ϊ�߶�
		isStanding = false;
		//�����浵�ͷ��ر����������
		if(Control.C&&super.checkTimeGap(100)&&!Map.startChase){
				Game.getInstance().setSaving(true);
				//��ȥ����C����������߶�������BUG
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
	 * �ػ�������
	 * �����ֶ�����������
	 * ���ӵķ��⣬�Ƿ���������������Ʒ���ŵļ�顢Autoģ��ļ�顣
	 * */
	public void paint(Graphics g){
		//��������
		g.clearRect(0, 0, Lib.gameWIDTH, Lib.gameHEIGHT);
		
		//վ������ʱ����
		if(isStanding){
			currentMap.paint(g);
			Player.getInstance().draw(g,direction);
			currentMap.checkShell(g);
			
			if(!itemObject.isShow()){
				//������Ʒ��ʾ��Ϣ
				currentMap.keyResponse(g, direction);
				//����Ƿ���ĳ������
				if(currentMap.isEvent(direction) && Control.Z)
					currentMap.editMapList(maps);
			}
		}
		//�߶�ʱ����
		else{
			//�ŵĲ������ͼת��
			if(currentMap.isDoor(direction)){
				super.tempSoundPlay("source/rpg/door.wav");
				doorInfo = currentMap.getDoor(direction);
				isDoor = true;
			}
			if(isDoor){
				isDoor = false;
				currentMap = maps.get(doorInfo.toWhich);
				currentMap.setLocation(doorInfo.playerX, doorInfo.playerY);
				return;
			}
			currentMap.paint(g,direction,isRunning);
			Player.getInstance().draw(g,isRunning,direction);
			currentMap.checkShell(g);
		}
		
		//�����Ƿ�Ϊ����������
		currentMap.checkAuto(g);
		currentMap.chase(g);
		
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
			//��ʾ��Ϣʱ���ﲻ���߶�
			if(!currentMap.showInfo ){
				keyResponse();
			}
			paint(g);
		}catch(Exception e){
			e.getStackTrace();
		}	
	}
	
	/**
	 * ��ȡ��ǰ��Ϸ���ȣ��浵��
	 * */
	public SaveData getNowStatus(){
		return new SaveData(maps,Player.getInstance().getItems(),currentMap,Map.playerX,Map.playerY,direction,lastTimeBefore + ( System.currentTimeMillis() - startTime ));
	}
	
	@Override
	public void die() {
		super.musicStop();
	}

}
