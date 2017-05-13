package model.rpg.map.MapObjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import model.rpg.map.MapKind;
import model.start.LoadObject;

/**
 * �������ߵ�����ط�ʱ
 * ִ������ط���die����
 * ����������ʽ�������ļ�������һЩ������������
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
	
	/**�Ѿ��������ĵط������ٴ����ڶ���
	 * �����;���ģʽ��Ȼ��ͬ
	 * ������ÿ�ξ���ʱ��Ҫ����
	 * ������һ����Ϸ��ֻ����һ��
	 * ����ֻҪ����һ�Σ���ͼ�ͻ�ָ���ԭ����״̬
	 * ����ʵ�����Ƿ񴥷�����Boolean����ֻ�ǶԾ���ģ������������
	 * 
	 * ʹ�÷���new��Auto���󣬲�����ʱ�浵��������һ������ģ�飬���������Ӵ˴�������Ϸ
	 * */
	public void die(BufferedImage map){
		try {
			if(!trigger){
				//ֻ����һ��
				trigger = true;
				LoadObject.write(3);
				Class.forName(reflectClassName).getConstructor(BufferedImage.class).newInstance(map);
			}
		}
		//����֪��catch��ʲô����
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
