package model;

import java.awt.Graphics;

import model.start.LoadObject;
import model.start.StartObject;

public class Game {
	private static Game game = new Game();
	private boolean isSaving = false;
	private GameObject currentObject,loadObject;
	/**
	 * 返回一个静态的Game对象
	 * */
	public static Game getInstance(){
		return game;
	}
	/**
	 * 驱动方法
	 * */
	public void init(){
		new StartObject();
		loadObject = new LoadObject();
	}
	/**
	 * 获得当前正在重画的GameObject，不包括物品栏和存档读档界面
	 * */
	public GameObject getCurrent(){
		return currentObject;
	}
	/**
	 * 设置当前GameObject
	 * @param object 设置目前的
	 * */
	public void setCurrent(GameObject object){
		currentObject = object;
	}
	/**
	 * 设置存档读档界面是否出现
	 *  @param isSaving 是否进入存档读档界面
	 * */
	public void setSaving(boolean isSaving){
		this.isSaving = isSaving;
		if(isSaving)
			loadObject = new LoadObject(); 
	}
	/**
	 * 判断存档读档界面是否已经调出
	 * */
	public boolean isSaving(){
		return isSaving;
	}
	
	/**
	 * 正常情况下重画当前的GameObject，
	 * 当存档界面调出时重画存档界面
	 *  @param g 画笔
	 * */
	public void paint(Graphics g){
		if(isSaving)
			loadObject.draw(g);
		else{
			currentObject.draw(g);
		}
			
	}
	
}