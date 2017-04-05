package model.rpg;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import control.Lib;
/**
 * 人物类，包含了人物获得的物品
 * */
public class Player {
	/**使用静态Player，使用Player时只使用此对象*/
	private static Player player = new Player();
	//不让图片或坐标更换太快设置的wait
	private int imgChange=0,wait=0;
	//绘制人物的坐标
	private int x,y;
	//人物的状态
	private Direction direction = Direction.R;
	private ArrayList<Item> items = new ArrayList<Item>();
	//图片数组，读入两张图，分成32个小图
	private BufferedImage walk,run;
	private BufferedImage[] walku = new BufferedImage[4];
	private BufferedImage[] walkd = new BufferedImage[4];
	private BufferedImage[] walkl = new BufferedImage[4];
	private BufferedImage[] walkr = new BufferedImage[4];
	private BufferedImage[] runu = new BufferedImage[4];
	private BufferedImage[] rund = new BufferedImage[4];
	private BufferedImage[] runl = new BufferedImage[4];
	private BufferedImage[] runr = new BufferedImage[4];
		
	
	/**返回静态player，确保所有类操作的都是同一个Player对象*/
	public static Player getInstance(){
		return player;
	}
	/**
	 * 构造方法，
	 * 读取图片，同时意味着这个对象不能被存档写入文件。
	 * */
	public Player(){
		try {
			walk = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("source/rpg/player/sakuya_walk.png"));
			run = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("source/rpg/player/sakuya_run.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0;i<walku.length;i++){
			walku[i] = walk.getSubimage(i*(walk.getWidth()/4), 3*(walk.getHeight()/4), walk.getWidth()/4, walk.getHeight()/4);
		}
		for(int i = 0;i<walkd.length;i++){
			walkd[i] = walk.getSubimage(i*(walk.getWidth()/4), 0, walk.getWidth()/4, walk.getHeight()/4);
		}
		for(int i = 0;i<walkl.length;i++){
			walkl[i] = walk.getSubimage(i*(walk.getWidth()/4), walk.getHeight()/4, walk.getWidth()/4, walk.getHeight()/4);
		}
		for(int i = 0;i<walkr.length;i++){
			walkr[i] = walk.getSubimage(i*(walk.getWidth()/4), walk.getHeight()/2, walk.getWidth()/4, walk.getHeight()/4);
		}
		
		for(int i = 0;i<runu.length;i++){
			runu[i] = run.getSubimage(i*(run.getWidth()/4), 3*(run.getHeight()/4), run.getWidth()/4, run.getHeight()/4);
		}
		for(int i = 0;i<rund.length;i++){
			rund[i] = run.getSubimage(i*(run.getWidth()/4), 0, run.getWidth()/4, run.getHeight()/4);
		}
		for(int i = 0;i<runl.length;i++){
			runl[i] = run.getSubimage(i*(run.getWidth()/4), run.getHeight()/4, run.getWidth()/4, run.getHeight()/4);
		}
		for(int i = 0;i<runr.length;i++){
			runr[i] = run.getSubimage(i*(run.getWidth()/4), run.getHeight()/2, run.getWidth()/4, run.getHeight()/4);
		}
		x = Lib.gameWIDTH/2-walk.getWidth()/8;
		y = Lib.gameHEIGHT/2-walk.getHeight()/8;
	}
	/**
	 * 更新Item
	 * @param items 要更新的物品栏
	 * */
	public void updateItems(ArrayList<Item> items){
		this.items = items;
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	public void addItem(Item item){
		items.add(item);
	}
	/**根据数字removeArrayList中相应的项目*/
	public void removeItem(int i){
		items.remove(i);
	}
	/**Map绘画时取绘画中心区域为以(x,Lib.gameHEIGHT/2)为左上角，边长为Lib.boundsPerImg的方块*/
	public int getPaintX(){
		return x;
	}
	
	/**Map绘画时取绘画中心区域为以(x,Lib.gameHEIGHT/2)为左上角，边长为Lib.boundsPerImg的方块*/
	public int getPaintY(){
		return y;
	}
	
	/**人物移动时的绘制，包括是不是在奔跑
	 *   @param g 画笔
	 *   @param isRun 是否在奔跑，true为跑动，false为走动
	 *   @param playerDirection 人物的方向
	 * */
	public void draw(Graphics g,boolean isRun,Direction playerDirection) {
		direction = playerDirection;
		if(!isRun){
			if(direction==Direction.U)
				g.drawImage(walku[imgChange], x, y, null);
			if(direction==Direction.D)
				g.drawImage(walkd[imgChange], x, y, null);
			if(direction==Direction.L)
				g.drawImage(walkl[imgChange], x, y, null);
			if(direction==Direction.R)
				g.drawImage(walkr[imgChange], x, y, null);
		}
		else{
			if(direction==Direction.U)
				g.drawImage(runu[imgChange], x, y, null);
			if(direction==Direction.D)
				g.drawImage(rund[imgChange], x, y, null);
			if(direction==Direction.L)
				g.drawImage(runl[imgChange], x, y, null);
			if(direction==Direction.R)
				g.drawImage(runr[imgChange], x, y, null);
		}
		wait++;
		if(wait>=3){
			wait = 0;
			imgChange++;	
		}
		if(imgChange==4)
			imgChange = 0;
	}
	/**人物静止不动时的绘制*/
	public void draw(Graphics g, Direction direction) {
		wait = 2;
		if(direction==Direction.U)
			g.drawImage(walku[0], x, y, null);
		if(direction==Direction.D)
			g.drawImage(walkd[0], x, y, null);
		if(direction==Direction.L)
			g.drawImage(walkl[0], x, y, null);
		if(direction==Direction.R)
			g.drawImage(walkr[0], x, y, null);
	}
	
	public Direction getDirection(){
		return direction;
	}
	public void clearDirection(){
		direction = Direction.R;
	}
}
