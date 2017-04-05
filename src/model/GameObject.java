package model;

import java.applet.AudioClip;
import java.awt.Graphics;

import control.Control;
import model.rpg.Player;
import model.rpg.SaveData;
import model.rpg.map.Map;
import model.start.ItemObject;
import model.start.LoadObject;
/**
 * Game在运作时重画此类和其子类的对象
 * */
public abstract class GameObject {
	/**
	 * 音效
	 * */
	private static AudioClip click,change,sound;
	/**
	 * 背景音乐
	 * */
	private static AudioClip music = null;
	private double startTime = 0;
	private static long mill = 0;

	static {
		try {
			click = java.applet.Applet.newAudioClip(GameObject.class.getClassLoader().getResource("source/start/确认.wav"));
			change = java.applet.Applet.newAudioClip(GameObject.class.getClassLoader().getResource("source/start/切换.wav"));
		} catch (Exception ef) {
		}
	}
	
	/**
	 * 构造方法，new的时候将被自动更新到Game中的currentObject中
	 * */
	public GameObject() {
		startTime = System.currentTimeMillis();
		if (!(this instanceof LoadObject || this instanceof ItemObject)) {
			Game.getInstance().setCurrent(this);
		}
	}
	
	/**
	 * 循环播放背景音乐
	 * @param music 播放音乐的路径
	 * */
	public void musicStart(String music) {
		try {
			GameObject.music = java.applet.Applet.newAudioClip(this.getClass().getClassLoader().getResource(music));
			GameObject.music.loop();// 循环播放
		} catch (Exception ef) {
			javax.swing.JOptionPane.showMessageDialog(null, "音乐播放失败");
		}
	}

	protected abstract void keyResponse();

	public abstract void draw(Graphics g);

	public abstract void die();
	
	/**
	 * 为了避免因keyPressed连续触发而造成的不便，在某些地方需要检查这次按下和上次按下之间是否有时间间隔，即是否松开过手
	 * @param gap 松开与按下之间的时间间隔
	 * */
	public static boolean checkTimeGap(int gap) {
		boolean timeGap = false;
		if (Math.abs(mill - System.currentTimeMillis()) > gap) {
			timeGap = true;
		}
		mill = System.currentTimeMillis();
		return timeGap;
	}
	
	/**为了避免因keyPressed连续触发而造成的不便，需要检查这次按下和刚刚启动之间是否有时间间隔，即是否new之后没有及时松手*/
	protected boolean checkStartGap(int gap) {
		long mill = System.currentTimeMillis();
		if (mill - startTime > gap)
			return true;
		return false;
	}

	public GameObject getType() {
		return this;
	}
	
	/**
	 * Rpg专用
	 * */
	public SaveData getNowStatus(){
		return new SaveData();
	}
	
	/**
	 * 各种音效的播放
	 * @param path 音乐文件的路径
	 * */
	public void tempSoundPlay(String path){
		try {
			sound = java.applet.Applet.newAudioClip(this.getClass().getClassLoader().getResource(path));
			sound.play();
		} catch (Exception ef) {
		}
	}
	/**由于是静态对象，只要游戏不关闭窗口，得到的Item就不会丢失，所以必须清空*/
	protected void clearStatic(){
		
				Player.getInstance().getItems().clear();
				Map.startChase = false;
				Map.chaseReady = 0;
				Control.chaseX = 6;
				Control.chaseY = 6;
				Map.readyForDoor = false;
				Map.playerX = 6;
				Map.playerY = 6;
				Player.getInstance().clearDirection();
	}
	/**
	 * 点击音效
	 * */
	public void click(){
		click.play();
	}
	/**
	 * 上下切换音效
	 * */
	public void change(){
		change.play();
	}
	/**
	 * 背景音乐停止
	 * */
	public static void musicStop() {
		music.stop();
	}
}
