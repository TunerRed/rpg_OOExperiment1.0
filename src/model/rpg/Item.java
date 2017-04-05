package model.rpg;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
/**
 * 人物的物品
 * */
public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Properties properties = new Properties();
	static{
		try {
			properties.load(Item.class.getClassLoader().getResourceAsStream("source/rpg/properties/Item.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String name;
	private int number;
	private Item other;
	/**
	 * 构造方法
	 * 根据数字和配置文件为人物添加道具
	 * @param number 在Item.properties中根据数字选择物品
	 * */
	public Item(int number){
		this.name = properties.getProperty("I"+number);
		this.number = number;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * 重写equals，条件是判断两者的number（理解为ID）是否相同
	 * */
	public boolean equals(Object obj){
		other = (Item) obj;
		if(other.number == this.number)
			return true;
		else
			return false;
	}
}
