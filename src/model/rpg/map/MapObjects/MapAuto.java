package model.rpg.map.MapObjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import model.rpg.map.MapKind;
import model.start.LoadObject;

/**
 * 当人物走到这个地方时
 * 执行这个地方的die方法
 * 具体死亡方式由配置文件和其他一些更具体的类决定
 * */

public class MapAuto extends MapObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reflectClassName;
	private boolean trigger = false;
	
	public MapAuto(String reflectClassName) {
		super(MapKind.AUTO);
		setName(reflectClassName);
	}
	
	public void setName(String reflectClassName){
		this.reflectClassName = reflectClassName;
		return;
	}
	
	/**已经触发过的地方不会再触发第二遍
	 * 死亡和剧情模式虽然不同
	 * 死亡在每次经过时都要触发
	 * 剧情在一次游戏中只触发一次
	 * 但是只要死亡一次，地图就会恢复到原来的状态
	 * 所以实际上是否触发过的Boolean属性只是对剧情模块有限制作用
	 * 
	 * 使用反射new出Auto对象，并且临时存档，可能是一个剧情模块，剧情结束后从此处继续游戏
	 * */
	public void die(BufferedImage map){
		try {
			if(!trigger){
				//只触发一次
				trigger = true;
				LoadObject.write(3);
				Class.forName(reflectClassName).getConstructor(BufferedImage.class).newInstance(map);
			}
		}
		//并不知道catch了什么玩意
		catch (ClassNotFoundException |
				NoSuchMethodException | 
				SecurityException | 
				InstantiationException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
