package model.rpg.map.MapObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import model.rpg.Direction;
import model.rpg.map.MapKind;


public class MapInfo extends MapObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Properties properties = new Properties();
	static {
		try {
			properties.load(MapInfo.class.getClassLoader().getResourceAsStream("source/rpg/properties/Info.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected ArrayList<String> info = new ArrayList<String>();
	
	/**
	 * ���ֹ��췽��
	 * Ĭ��ͼƬΪ����
	 * Ĭ��û����ӵ���Ʒ��ͼƬ����
	 * ������Ʒ�Ĺ��췽��
	 * ������Ʒ����ͼƬ���Ա༭�Ĺ��췽��
	 * @param infoNum ������Ϣ����EventInfo.properties�в���
	 * @param direction ʱ�䴥���ķ���
	 * */
	
	public MapInfo(int[] infoNum,Direction direction) {
		super(MapKind.INFO);
		init(infoNum,direction);
	}
	
	private void init(int[] infoNum,Direction direction){
		info.clear();
		for(int i = 0 ; i < infoNum.length ; i++){
			info.add(properties.getProperty("I"+infoNum[i],"..."));
		}
		super.direction = direction;
	}
	/**
	 * �ı����Ԫ�ص�MapKind��������ʹ�á�
	 * @param kind Map������
	 * */
	protected void changeKind(MapKind kind){
		super.kind = kind;
	}
	
	public ArrayList<String> getInfo(){
		return info;
	}
	
	public void clearInfo(){
		info.clear();
		info.add("...");
	}
}
