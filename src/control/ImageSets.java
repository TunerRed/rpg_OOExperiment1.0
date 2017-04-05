package control;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import model.rpg.map.MapObjects.MapObject;

/**
 * 加载图片资源的类，使用了静态代码块加载图片资源。
 * */

public class ImageSets {
	static private List<BufferedImage> maps;
	static private BufferedImage chase,chaseU,chaseD,chaseL,chaseR;
	static private Image imageU, imageD,imageD0, imageD1,imageL, imageR, imageUL, imageUR, imageDL, imageDR, imageDRU, imageDLU ;
	static private Image book1, book2, guizi1, guizi2,guiziL,guiziR, soft1, soft2;
	static private Image desk,desk1, desk2, desk3, desk4, desk5, pot1, pot2, pot3, pot4;
	static private Image die,wall1,wall11, wall2, wall3, wall4, pan, bed, door,
	floor, shell, tv, window, mirror,mirrorL,mirrorR,mirrorF,mirrorFL,mirrorFR,mirrorFD,
	paint,clock,knife,shadow,problem;
	static private Image thisImg;
	private static String path = "source/rpg/elements/";

	static {
		try {
			maps = new ArrayList<BufferedImage>();
			imageU = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryu.jpg"));
			imageD = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryd.jpg"));
			imageD0 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryd0.jpg"));
			imageD1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryd1.jpg"));
			imageL = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryl.jpg"));
			imageR = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryr.jpg"));
			imageUL = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryul.jpg"));
			imageUR = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "baryur.jpg"));
			imageDL = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "barydl.jpg"));
			imageDR = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "barydr.jpg"));
			imageDRU = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "barydru.jpg"));
			imageDLU = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "barydlu.jpg"));
			
			chase = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream("source/rpg/player/chase.png"));
			chaseU=chase.getSubimage(0, chase.getHeight()/2, chase.getWidth(), chase.getHeight()/4);
			chaseD=chase.getSubimage(0, 0, chase.getWidth(), chase.getHeight()/4);
			chaseL=chase.getSubimage(0, chase.getHeight()/4, chase.getWidth(), chase.getHeight()/4);
			chaseR=chase.getSubimage(0, chase.getHeight()*3/4, chase.getWidth(), chase.getHeight()/4);

			book1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "book1.jpg"));
			book2 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "book2.jpg"));
			guizi1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "guizi1.jpg"));
			guizi2 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "guizi2.jpg"));
			guiziL = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "guiziL.jpg"));
			guiziR = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "guiziR.jpg"));
			soft1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "soft1.jpg"));
			soft2 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "soft2.jpg"));
			
			desk = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "desk.jpg"));
			desk1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "desk1.jpg"));
			desk2 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "desk2.jpg"));
			desk3 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "desk3.jpg"));
			desk4 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "desk4.jpg"));
			desk5 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "desk5.jpg"));

			pot1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "pot1.jpg"));
			pot2 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "pot2.jpg"));
			pot3 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "pot3.jpg"));
			pot4 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "pot4.jpg"));

			wall1 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "001.jpg"));
			wall11 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "0011.jpg"));
			wall2 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "002.jpg"));
			wall3 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "003.jpg"));
			wall4 = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "004.jpg"));
			pan = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "pan.jpg"));

			bed = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "bed.jpg"));
			door = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "door.jpg"));
			floor = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "floor.jpg"));
			shell = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "shell.jpg"));
			tv = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "tv.jpg"));
			window = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "window.jpg"));
			mirror = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirror.jpg"));
			mirrorL = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirrorL.jpg"));
			mirrorR = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirrorR.jpg"));
			mirrorF = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirrorF.jpg"));
			mirrorFL = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirrorFL.jpg"));
			mirrorFR = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirrorFR.jpg"));
			mirrorFD = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "mirrorFD.jpg"));
			paint = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "paint.jpg"));
			clock = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "clock.jpg"));
			die = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "die.jpg"));
			knife = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream(path + "小刀.png"));
			shadow = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream("source/rpg/遮罩.png"));
			
			problem = ImageIO.read(ImageSets.class.getClassLoader().getResourceAsStream("source/start/problem.png"));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "图片资源缺失！");
			System.exit(0);
		}
	}
	
	public static void addMapImage(BufferedImage map){
		maps.add(map);
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
			thisImg = imageU;
			break;
		case 2:
			thisImg = imageD;
			break;
		case 3:
			thisImg = imageL;
			break;
		case 4:
			thisImg = imageR;
			break;
		case 5:
			thisImg = imageUL;
			break;
		case 6:
			thisImg = imageUR;
			break;
		case 7:
			thisImg = imageDL;
			break;
		case 8:
			thisImg = imageDR;
			break;
		case 9:
			thisImg = imageDRU;
			break;
		case 10:
			thisImg = imageDLU;
			break;
		case 11:
			thisImg = book1;
			break;
		case 12:
			thisImg = book2;
			break;
		case 13:
			thisImg = guizi1;
			break;
		case 14:
			thisImg = guizi2;
			break;
		case 15:
			thisImg = soft1;
			break;
		case 16:
			thisImg = soft2;
			break;
		case 17:
			thisImg = desk1;
			break;
		case 18:
			thisImg = desk2;
			break;
		case 19:
			thisImg = desk3;
			break;
		case 20:
			thisImg = desk4;
			break;
		case 21:
			thisImg = desk5;
			break;
		case 22:
			thisImg = pot1;
			break;
		case 23:
			thisImg = pot2;
			break;
		case 24:
			thisImg = pot3;
			break;
		case 25:
			thisImg = pot4;
			break;
		case 26:
			thisImg = wall1;
			break;
		case 27:
			thisImg = wall2;
			break;
		case 28:
			thisImg = wall3;
			break;
		case 29:
			thisImg = wall4;
			break;
		case 30:
			thisImg = bed;
			break;
		case 31:
			thisImg = shell;
			break;
		case 32:
			thisImg = tv;
			break;
		case 33:
			thisImg = window;
			break;
		case 34:
			thisImg = pan;
			break;
		case 35:
			thisImg = paint;
			break;
		case 36:
			thisImg = floor;
			break;
		case 37:
			thisImg = clock;
			break;
		case 38:
			thisImg = door;
			break;
		case 39:
			thisImg = knife;
			break;
		case 40:
			thisImg = mirror;
			break;
		case 41:
			thisImg = desk;
			break;
		case 42:
			thisImg = wall11;
			break;
		case 43:
			thisImg = die;
			break;
		case 44:
			thisImg = guiziL;
			break;
		case 45:
			thisImg = guiziR;
			break;
		case 46:
			thisImg = mirrorL;
			break;
		case 47:
			thisImg = mirrorR;
			break;
		case 48:
			thisImg = mirrorF;
			break;
		case 49:
			thisImg = mirrorFL;
			break;
		case 50:
			thisImg = mirrorFR;
			break;
		case 51:
			thisImg = mirrorFD;
			break;
		case 52:
			thisImg = shadow;
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
		case 65:
			thisImg = problem;
			break;
		case 66:
			thisImg = imageD0;
			break;
		case 67:
			thisImg = imageD1;
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
