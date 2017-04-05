package model.rpg.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import control.Control;
import control.ImageSets;
import control.Lib;
import model.Game;
import model.GameObject;
import model.over.GameOverObject;
import model.rpg.Direction;
import model.rpg.Player;
import model.rpg.map.MapObjects.MapObject;

public class Map implements Serializable {

	/*
	 * playerX,playerY不画出来但是对player和map间相对定位有相当大帮助的元素
	 * 每次取数组时以playerX,playerY为标准取clip
	 * 两者的值以player在数组中的位置为数据，计算出应画的数组clip左上角为elements数组的第几行第几列
	 * 其初始化应由本地图的MapKind.DOOR所在的位置决定
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* 设定二维数组地图时左上角位置,XY分别是横向、纵向长度*/
	public static int wait = 0,playerX = 6, playerY = 6;
	private MapObject[][] elements;
	private int mapNo = 0;
	
	/**追逐战开始的标志，人物死亡后要记得把这个地方的状态改变*/
	public static boolean startChase = false;
	
	private boolean eventInfoEnd = true;
	private int eventInfoStep = 0;
	private final int RUN_RATE = 2;
	private final int WALK_RATE = 3;
	private ArrayList<String> eventInfo = new ArrayList<String>();
	public boolean showInfo = false;

	public Map(int sizeY, int sizeX) {
		elements = new MapObject[sizeY][sizeX];
		showInfo = false;
		init();
	}

	private void init() {
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[i].length; j++)
				elements[i][j] = new MapObject(MapKind.BARY, 0);
		Control.chaseX = 6;
		Control.chaseY = 6;
		chaseReady = 0;
	}
	
	/**人物新进入地图时初始化位置，传入人物在elements中的位置，
	 * 改变选定数组clip的区域,
	 * 注意传入X,Y的是数组坐标位置，
	 * 改变的X,Y是面板坐标，两者刚好相反
	 * 这里没写好，不想改了
	 * @param playerX 数组行数
	 * @param playerY 数组列数
	 * */
	public void setLocation(int playerX,int playerY){
		Map.playerX = playerY;
		Map.playerY = playerX;
	}
	
	/**改变二维数组中某个位置的信息
	 * @param x 数组行数
	 * @param y 数组列数
	 * @param object 传入一个目标MapObject
	 * */
	public void edit(int row, int col, MapObject object) {
		elements[row][col] = object;
	}
	
	public void merge(){
		mapNo = ImageSets.getMapsSize();
		BufferedImage map = new BufferedImage(Lib.boundsPerImg*elements[0].length,
				Lib.boundsPerImg*elements.length,BufferedImage.TYPE_INT_RGB);
		Graphics g = map.getGraphics();
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[0].length; j++)
				g.drawImage(ImageSets.getImg(elements[i][j]), 
						j * Lib.boundsPerImg,i * Lib.boundsPerImg, null);
		ImageSets.addMapImage(map);
	}
	
	public void remerge(){
		BufferedImage map = new BufferedImage(Lib.boundsPerImg*elements[0].length,
				Lib.boundsPerImg*elements.length,BufferedImage.TYPE_INT_RGB);
		Graphics g = map.getGraphics();
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[0].length; j++)
				g.drawImage(ImageSets.getImg(elements[i][j]), 
						j * Lib.boundsPerImg,i * Lib.boundsPerImg, null);
		ImageSets.changeMapImage(map,mapNo);
	}
	
	/**人物移动并防止数组越界
	 * @param dir 要移动的方向
	 * */
	public boolean canMove(Direction dir) {
		if (dir == Direction.U) {
			if (playerY > 0 && elements[playerY - 1][playerX].canGo()) {
				return true;
			}
		} else if (dir == Direction.D) {
			if (playerY < elements.length - 1 
					&& elements[playerY + 1][playerX].canGo()) {
				return true;
			}
		} else if (dir == Direction.L) {
			if (playerX > 0 && elements[playerY][playerX - 1].canGo()) {
				return true;
			}
		} else if (dir == Direction.R) {
			if (playerX < elements[0].length - 1
					&& elements[playerY][playerX + 1].canGo()) {
				return true;
			}
		}
		return false;
	}
	
	/**所在区域为事件时，显示物品信息
	 * @param g 画笔
	 * @param direction 人物移动方向
	 * */
	public void keyResponse(Graphics g, Direction direction) {
		//决定是否开始显示信息，当信息开始显示后这一部分应当不再起作用
		if (	!showInfo && Control.Z 
				&& ((elements[playerY][playerX].getKind() == MapKind.EVENT)
					||(elements[playerY][playerX].getKind() == MapKind.ACHIEVEMENT))
				&& elements[playerY][playerX].getDirection() == direction
				&& eventInfoEnd && GameObject.checkTimeGap(100)) {
			eventInfo = elements[playerY][playerX].getInfo();
			showInfo = true;
		}
		//进行读取信息，并判断信息是否已经读完，当信息读完后，这一部分不再起作用
		else if (showInfo) {
			//强制转换Graphics2D设置不透明度
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(0.5f));
			g.setColor(Color.WHITE);
			g.setFont(Lib.regular);
			g.fillRect(Lib.gameWIDTH / 11, Lib.gameHEIGHT - Lib.gameWIDTH / 7, Lib.gameWIDTH - 2 * Lib.gameWIDTH / 11,
					Lib.gameHEIGHT / 7);
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(1.0f));
			g.setColor(Color.BLACK);
			g.drawString(eventInfo.get(eventInfoStep),
					Lib.gameWIDTH / 10, (int) (Lib.gameHEIGHT * (9.0 / 10)));
			//读取下一条信息
			if(Control.Z && GameObject.checkTimeGap(200)){
				eventInfoStep++;
				//全部读取完毕
				if(eventInfoStep == eventInfo.size()){
					eventInfoStep = 0;
					showInfo = false;
				}
			}
		}
	}
	
	/**人物不动时的画图方法
	 * @param g 画笔
	 * */
	public void paint(Graphics g) {
		wait = WALK_RATE;
		g.drawImage(ImageSets.getMapImage(mapNo),
				Player.getInstance().getPaintX()-playerX*Lib.boundsPerImg + Lib.adjustX,
				Player.getInstance().getPaintY()-playerY*Lib.boundsPerImg + Lib.adjustY,null);
		//追逐战过程
		if(startChase){
			chase(g);
		}
	}
	/**人物移动时的画图方法
	 * @param g 画笔
	 * @param direction 移动方向
	 * @param isRunning 是否在奔跑，若在奔跑则为true，否则为false
	 * */
	public void paint(Graphics g, Direction direction, boolean isRunning) {

			wait++;
			boolean can_move = canMove(direction);
			int x = 0,y = 0;
			if(can_move){
				if (wait >= (isRunning?WALK_RATE:RUN_RATE)) {
					wait = 0;
					switch(direction){
					case U:
						playerY--;
						break;
					case D:
						playerY++;
						break;
					case L:
						playerX--;
						break;
					case R:
						playerX++;
						break;
					default:
						break;
					}
				}
				else{
					//为消除卡顿，每一帧计算wait值并使地图进行少量的位移，避免多帧后地图移动一格造成的卡顿感
					switch(direction){
					case U:
						y = wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					case D:
						y = -wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					case L:
						x = wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					case R:
						x = -wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					default:
						break;
					}
				}
			}
			g.drawImage(ImageSets.getMapImage(mapNo),
					Player.getInstance().getPaintX()-playerX*Lib.boundsPerImg + x + Lib.adjustX,
					Player.getInstance().getPaintY()-playerY*Lib.boundsPerImg + y + Lib.adjustY,
					null);
			
			//追逐战过程
			if(startChase){
				chase(g);
			}
			paint(g);
	}
	
	/**检查人物下方元素是否为书架，如果是，需要重画人物下半身
	 * @param g 画笔
	 * */
	public void checkShell(Graphics g){
		if(elements[playerY + 1][playerX].getKind() == MapKind.SHELL){
			g.drawImage(ImageSets.getImg(elements[playerY + 1 ][playerX]), Player.getInstance().getPaintX()+Lib.adjustX,
					Player.getInstance().getPaintY() + Lib.adjustY, null);
		}
	}
	/**检查所在地是否为Auto属性
	 * @param g 画笔
	 * */
	public void checkAuto(Graphics g){
		if(elements[playerY][playerX].getKind() == MapKind.AUTO){
			elements[playerY][playerX].die(ImageSets.getMapImage(mapNo));
		}
	}
	/**获得制定位置的单个MapObject*/
	public MapObject getUnderfoot(){
		return elements[playerY][playerX];
	}
	public MapObject getSomewhere(int x,int y){
		return elements[x][y];
	}
	/**下一步是不是门
	 * @param direction 人物移动的方向
	 * */
	public boolean isDoor(Direction direction){
		switch(direction){
			case U:
				if(elements[playerY - 1][playerX].getKind() == MapKind.DOOR 
					&&elements[playerY - 1][playerX].canGo()){
					if(startChase){	//启动追逐战
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY - 1][playerX];
					}
					return true;
				}
				break;	
			case D:
				if(elements[playerY + 1][playerX].getKind() == MapKind.DOOR
						&&elements[playerY + 1][playerX].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY + 1][playerX];
					}
					return true;
				}
				break;	
			case L:
				if(elements[playerY][playerX - 1].getKind() == MapKind.DOOR
						&&elements[playerY][playerX - 1].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY][playerX - 1];
					}
					return true;
				}	
				break;
			case R:
				if(elements[playerY][playerX + 1].getKind() == MapKind.DOOR
						&&elements[playerY][playerX + 1].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY][playerX + 1];
					}
					return true;
				}
				break;
			default:	
		}
		return false;
	}
	/**获得即将到达的位置的门
	 * @param direction 人物现在的方向
	 * */
	public MapObject getDoor(Direction direction){
		switch(direction){
		case U:
			return elements[playerY - 1][playerX];
		case D:
			return elements[playerY + 1][playerX];
		case L:
			return elements[playerY][playerX - 1];
		case R:
			return elements[playerY][playerX + 1];
		default:
			return null;
		}
	
	
	}

	/**
	 * 检验是否为可以达成成就的点
	 * @param direction 人物现在的方向
	 * */
	public boolean isAchievement(Direction direction){
		if(elements[playerY][playerX].getKind() == MapKind.ACHIEVEMENT 
			&& direction == elements[playerY][playerX].getDirection()){
			return true;
		}
		return false;
	}
	/**
	 * 编辑整个地图
	 * @param mapList 全部的地图对象
	 * */
	public void editMapList(ArrayList<Map> mapList){
		elements[playerY][playerX].editMapList(mapList);
	}
	
	/*
	 * 此处以下全部为追逐战专用的方法
	 * 以chase为基本
	 * 可能有其他的方法
	 * 包括追逐的算法
	 * 门的处理和转换
	 * 等等
	 * */
	public static int chaseReady = 0;
	private static int speed = 3,chaseWait = 0;
	public static boolean readyForDoor = false;
	private static MapObject chaseDoor;
	private Direction direction;
	/*
	 * elements[playerY][meX]
	 * 敌人纵坐标Control.chaseY
	 * 敌人横坐标Control.chaseX
	 * elements[Control.chaseY][Control.chaseX]
	 * */
	/**
	 * 追逐战更新静态变量
	 * */
	public static boolean initChase(){
		//门的转换与等待
		if(readyForDoor){
			Control.chaseX = chaseDoor.playerY;
			Control.chaseY = chaseDoor.playerX;
			if(chaseReady++>15){
				readyForDoor = false;
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * 追逐战分析坐标拉近距离，并负责画图
	 * @param g 画笔
	 * */
	public void chase(Graphics g){
		//如果现在在等待门相应，那么久没有必要画敌人，把画图部分放进判断里
		if(!initChase()){
			chaseWait++;
				if(playerX > Control.chaseX && elements[Control.chaseY][Control.chaseX+1].canGo()){
					//判断该不该移动
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX++;
					}
					//无论是否移动，方向（趋势还是要表现出来的）
					direction = Direction.R;
				}else if(playerX < Control.chaseX && elements[Control.chaseY][Control.chaseX-1].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX--;
					}
					direction = Direction.L;
				}else if(playerY > Control.chaseY && elements[Control.chaseY+1][Control.chaseX].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseY++;
					}
					direction = Direction.D;
				}else if(playerY < Control.chaseY && elements[Control.chaseY-1][Control.chaseX].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseY--;
					}
					direction = Direction.U;
				}else{
					direction = Direction.U;
				}
				if(playerX == Control.chaseX && playerY == Control.chaseY){
					Game.getInstance().setCurrent(new GameOverObject());
				}
				
				switch(direction){
				case U:
					g.drawImage(ImageSets.getImg(61), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case D:
					g.drawImage(ImageSets.getImg(62), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case L:
					g.drawImage(ImageSets.getImg(63), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case R:
					g.drawImage(ImageSets.getImg(64), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				default:
					g.drawImage(ImageSets.getImg(61), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
				}
		}
		
		
	}
	
	
}