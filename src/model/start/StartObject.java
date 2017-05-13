package model.start;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import control.Control;
/**
 * 开始界面
 * 即包含游戏开始、继续、结束选项的界面
 * */
public class StartObject extends GameObject{
	
	private static Image bac,help,pointers[];
	private int x=400,y=260,pointer=0;
	private boolean showHelp = false;
	/**
	 * 记录当前指针到了哪个选项的前面，绘画并处理确认键按下后的选择
	 * */
	private int step = 0;
	static{
		pointers = new Image[25];
		try {
			bac = ImageIO.read(StartObject.class.getClassLoader().getResourceAsStream("source/start/title.png"));
			help = ImageIO.read(StartObject.class.getClassLoader().getResourceAsStream("source/start/help.png"));
			for(int i=0;i<pointers.length;i++){
				pointers[i] = ImageIO.read(StartObject.class.
						getClassLoader().getResourceAsStream("source/start/pointer/pointer"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 启动时自动播放音乐
	 * */
	public StartObject(){
		super.musicStart("source/start/title.wav");
		super.clearStatic();
	}
	
	/**
	 * 具体实现绘画的方法，包括背景和阴阳鱼的旋转
	 *  @param g 画笔
	 * */
	public void paint(Graphics g){
		g.drawImage(bac, 0, 0, null);
		g.drawImage(pointers[pointer++], x, y+step*75, null);
		if(pointer==25)
			pointer = 0;
		if(Control.isPressed && super.checkTimeGap(100))
			showHelp = !showHelp;
		if(showHelp)
			g.drawImage(help, 0, 0, null);
	}
	
	/**
	 * 当键盘按下上下时，操作类中的int类型step变化。
	 * 当键盘按下Z时，执行die方法
	 * */
	@Override
	protected void keyResponse() {
		if(Control.UP&&super.checkTimeGap(160)){
			step--;
			if(step<0)
				step = 3;
			super.change();
		}
		else if(Control.DOWN&&super.checkTimeGap(160)){
			step++;
			if(step > 3)
				step = 0;
			super.change();
		}
		else if(Control.Z&&super.checkTimeGap(200))
			die();
	}
	
	/**
	 * 相应键盘并绘画
	 * */
	@Override
	public  void draw(Graphics g) {
		keyResponse();
		paint(g);
	}

	/**
	 * 根据当前选择的选项不同
	 * 进行相应的操作。
	 * */
	@Override
	public void die() {
		
		if(super.checkStartGap(200))
		switch(step){
			case 0:
				super.click();
				musicStop();
				new LoadingObject(null);
				break;
			case 1:
				super.click();
				Game.getInstance().setSaving(true);
				break;
			case 2:
				super.click();
				System.exit(0);
			case 3:
				super.click();
				new ProblemObject();
		}
	}	

}
