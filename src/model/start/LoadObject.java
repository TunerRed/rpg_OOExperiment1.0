package model.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import model.Game;
import model.GameObject;
import model.rpg.RpgObject;
import model.rpg.SaveData;
import control.Control;

public class LoadObject extends GameObject {
	private static Image image, pointers[];
	private int pointer = 0, step = 0;
	private static File save;
	private static SaveData temp,saveData[] = new SaveData[3];
	static {
		try {
			image = ImageIO.read(LoadObject.class.getClassLoader().getResourceAsStream("source/sl.png"));
			pointers = new Image[25];
			for (int i = 0; i < pointers.length; i++) {
				pointers[i] = ImageIO.read(LoadObject.class.getClassLoader()
						.getResourceAsStream("source/start/pointer/pointer" + i + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LoadObject(){
		for(int i = 0 ; i < 3 ; i++){
			if(saveExists(i))
				saveData[i] = read(i);
		}
	}

	@Override
	protected void keyResponse() {
		if (Control.UP  && super.checkTimeGap(100)) {
			step--;
			if(step<0)
				step = 3;
			super.change();
		} else if (Control.DOWN && super.checkTimeGap(100)) {
			step++;
			if(step > 3)
				step = 0;
			super.change();
		} else if ((Control.X || Control.Z) && super.checkTimeGap(150))
			die();
	}
	
	/**
	 * 具体绘画，
	 * 包括指针和存档的游戏 时间
	 * @param g 画笔
	 * */
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);

		g.setColor(Color.RED);
		g.setFont(new Font("Vivaldi", 1, 30));
		for(int i = 0 ; i < 3 ; i++){
			if(saveExists(i))
				g.drawString(
							((saveData[i].getLastTime()/1000)/3600)+":"+
						   (((saveData[i].getLastTime()/1000)%3600)/60)+":"+
							((saveData[i].getLastTime()/1000)%3600%60),
							500, 140+i*160);
		}
			
		if (step == 3)
			g.drawImage(pointers[pointer++], 700, 525, null);
		else
			g.drawImage(pointers[pointer++], 700, 80 + step * 150, null);
		if (pointer == 25)
			pointer = 0;
	}

	@Override
	public void draw(Graphics g) {
		keyResponse();
		paint(g);
	}
	
	/**
	 * 根据当前状态的不同，
	 * 决定是存档还是读档。
	 * 一般而言，
	 * 游戏中时为存档功能，开始界面下为读档功能
	 * */
	@Override
	public void die() {
		if (super.checkStartGap(150)) {
			if (Control.Z && (Game.getInstance().getCurrent() instanceof StartObject)) {
				// 主界面状态下读档处理
				super.click();
				if(step == 3)
					//返回标题画面
					Game.getInstance().setSaving(false);
				else {
					//读档，存在有存档和没有存档的情况
					if(saveExists(step)){
						GameObject.musicStop();
						Game.getInstance().setSaving(false);
						new LoadingObject(read(step));
					}
				}
			} else if (Control.Z && (Game.getInstance().getCurrent() instanceof RpgObject)) {
				// rpg状态下存档处理,和读档处理不同
				super.click();
				if (step == 3) {
					//返回标题画面
					Game.getInstance().setSaving(false);
					Game.getInstance().getCurrent().die();
					Game.getInstance().setCurrent(new StartObject());
				} else {
					//存档
					write(step);
				}
			} else if (Control.X || (Control.C && (Game.getInstance().getCurrent() instanceof RpgObject))) {
				// 取消存档处理
				super.click();
				Game.getInstance().setSaving(false);
			}
		}
	}
	
	/**
	 * 存档写入SaveData对象
	 * 每次存档后都要重新读档一次
	 * 确保LoadObject中存档时间显示正确
	 * @param step 要写入哪个存档
	 * */
	public static void write(int step) {
		try {
			//首先检查文件夹是否存在
			save = new File(".saveData");
			if(!save.exists())
				save.mkdirs();
			//故事模式中临时存储当前状态
			if(step == 3){
				save = new File(save.getPath()+"/publicData.dat");
			}else{
				save = new File(save.getPath()+"/saveData"+step+".dat");
			}
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(save));
			output.writeObject(Game.getInstance().getCurrent().getNowStatus());
			read(step);
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 故事模式中临时存储当前状态的专用方法
	 * @param savedata 要写入publicData的存档
	 * */
	public static void write(SaveData savedata) {
		try {
			//首先检查文件夹是否存在
			save = new File(".saveData");
			if(!save.exists())
				save.mkdirs();
			
			save = new File(save.getPath()+"/publicData.dat");
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(save));
			output.writeObject(savedata);
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 检查存档是否存在
	 * @param step 检查第step个存档是否存在
	 * @return
	 * */
	public static boolean saveExists(int step){
		//首先检查文件夹是否存在
		save = new File(".saveData");
		if(!save.exists())
			save.mkdirs();
		//故事模式中临时存储当前状态
		if(step == 3){
			save = new File(save.getPath()+"/publicData.dat");
		}else{
			save = new File(save.getPath()+"/saveData"+step+".dat");
		}
		if(save.exists())
			return true;
		else
			return false;
	}
	/**
	 * 根据数字进行不同存档的读取
	 * @param step 要读取哪个存档
	 * @return
	 * */
	public static SaveData read(int step){
		ObjectInputStream input = null;
		try {
			if(step == 3){
				//故事模式中临时存储当前状态
				input = new ObjectInputStream(new FileInputStream(save));
				temp = (SaveData)(input.readObject());
				input.close();
				return temp;
			}else{
				input = new ObjectInputStream(new FileInputStream(save));
				saveData[step] = (SaveData)(input.readObject());
				input.close();
				return saveData[step];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
