package model.start;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.GameObject;
import model.rpg.Item;
import model.rpg.Player;
import control.Control;
/**
 * 人物的物品栏
 * */
public class ItemObject extends GameObject {
	ArrayList<Item> items;
	private boolean show = false;
	private static Image item,itemChoose;
	private int pointer = 0;
	
	static {
		try {
			item = ImageIO.read(ItemObject.class.getClassLoader().getResourceAsStream("source/rpg/item.png"));
			itemChoose = ImageIO.read(ItemObject.class.getClassLoader().getResourceAsStream("source/rpg/itemChoose.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 构造方法，自动调取静态Player获得最新的物品栏。
	 * */
	public ItemObject(){
		items = Player.getInstance().getItems();
	}
	
	/**
	 * 实现选择框在不同物品栏之间变动
	 * */
	@Override
	protected void keyResponse() {
		if(Control.X && super.checkTimeGap(70)){
			super.click();
			die();
		}	
		if(Control.DOWN && super.checkTimeGap(70) && pointer+2 < items.size()){
			super.change();
			pointer += 2;
		}
		else if(Control.UP && super.checkTimeGap(70) && pointer-2 >= 0){
			super.change();
			pointer -= 2;
		}
		else if(Control.LEFT && super.checkTimeGap(70) && pointer > 0){
			super.change();
			pointer -- ;
		}
		else if(Control.RIGHT && super.checkTimeGap(70) && pointer+1 < items.size()){
			super.change();
			pointer ++ ;
		}
	}
	
	/**
	 * 画出物品栏和物品选择框
	 * */
	private void paint(Graphics g){
		g.drawImage(item, 0, 0, null);
		g.setColor(Color.PINK);
		g.setFont(new Font("YouYuan",1,25));
		for(int i = 0 ; i < items.size() ; i++){
			g.drawString(items.get(i).getName(),75+(i%2)*240,487+(i/2)*37);
		}
		if(!items.isEmpty())
			g.drawImage(itemChoose, 65+(pointer%2)*240 , 465+(pointer/2)*37 , null);
	}
	
	@Override
	public void draw(Graphics g) {
		keyResponse();
		paint(g);
	}
	
	public void show(){
		show = true;
	}
	public boolean isShow(){
		return show;
	}
	
	@Override
	public void die() {
		this.show = false;
	}

}
