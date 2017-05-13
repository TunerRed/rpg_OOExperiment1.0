package control;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import model.rpg.Item;
import model.rpg.map.MapObjects.MapObject;

/**
 * 加载图片资源的类，使用了静态代码块加载图片资源。
 * */

public class ImageSets {
	static private List<BufferedImage> maps,images;
	static private Properties properties;
	static private BufferedImage chase,chaseU,chaseD,chaseL,chaseR;
	static private Image problem;
	static private Image thisImg;
	private static String path = "source/rpg/mapImages/";

	static {
		try {
			chase = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream("source/rpg/player/chase.png"));
			chaseU=chase.getSubimage(0, chase.getHeight()/2, chase.getWidth(), chase.getHeight()/4);
			chaseD=chase.getSubimage(0, 0, chase.getWidth(), chase.getHeight()/4);
			chaseL=chase.getSubimage(0, chase.getHeight()/4, chase.getWidth(), chase.getHeight()/4);
			chaseR=chase.getSubimage(0, chase.getHeight()*3/4, chase.getWidth(), chase.getHeight()/4);
			
			problem = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream("source/start/problem.png"));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "图片资源缺失！");
			System.exit(0);
		}
	}
	
	public static void init(){
		properties = new Properties();
		maps = new ArrayList<BufferedImage>();
		images = new ArrayList<BufferedImage>();
		try {
			properties.load(Item.class.getClassLoader().getResourceAsStream("source/rpg/properties/Maps.properties"));
			int mapCount = 0;
			while(true){
				String mapName = properties.getProperty("M"+mapCount);
				if(mapName==null)
					break;
				else{
					//debug
					//System.out.println(mapName);
					mapName = mapName.split("_")[1];
					BufferedImage map = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + mapName + ".png"));
					maps.add(map);
				}
				mapCount++;
			}
			int imageCount = 0;
			while(true){
				String imageName = properties.getProperty("I"+imageCount);
				//debug
				//System.out.println(imageName);
				if(imageName==null)
					break;
				else{
					BufferedImage image = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + imageName + ".png"));
					images.add(image);
				}
				imageCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void changeMapImage(BufferedImage map,int mapNo){
		if(maps.size() > mapNo)
			maps.set(mapNo, map);
		else
			System.out.println("out");
	}
	
	public static BufferedImage getMapImage(int mapNo){
		if(maps.size() > mapNo)
			return maps.get(mapNo);
		System.out.println("out");
		return null;
	}
	
	public static BufferedImage getBlockImage(int blockNo){
		if(images.size() > blockNo)
			return images.get(blockNo);
		System.out.println("out");
		return null;
	}
	
	public static BufferedImage getBlockImage(MapObject object){
		int blockNo = object.getType();
		if(images.size() > blockNo)
			return images.get(blockNo);
		System.out.println("out");
		return null;
	}
	
	public static int getMapsSize(){
		return maps.size();
	}
	
	/**
	 * 根据传入的数字返回相应代号的图片
	 * */
 	private static void choose(int number) {
		switch (number) {
		case 0:
			thisImg = null;
			break;
		case 1:
			thisImg = problem;
			break;
		case 61:
			thisImg = chaseU;
			break;
		case 62:
			thisImg = chaseD;
			break;
		case 63:
			thisImg = chaseL;
			break;
		case 64:
			thisImg = chaseR;
			break;
		default:
			break;
		}
	}
	
	/**
	 * 根据传入MapObject所携带的信息返回对应的图片
	 * @param object 根据传入的MapObject选择对象默认的图片
	 * */
	public static Image getImg(MapObject object) {
		choose(object.getType());
		return thisImg;
	}
	/**
	 * 根据传入的数字返回对应的图片
	 * @param number 根据数字返回一张图片对象
	 * */
	public static Image getImg(int number) {
		choose(number);
		return thisImg;
	}
}
