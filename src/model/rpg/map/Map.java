package model.rpg.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import control.Control;
import control.ImageSets;
import control.Lib;
import model.Game;
import model.GameObject;
import model.over.GameOverObject;
import model.rpg.Direction;
import model.rpg.Player;
import model.rpg.map.MapObjects.MapObject;

public class Map implements Serializable {

	/*
	 * playerX,playerY�����������Ƕ�player��map����Զ�λ���൱�������Ԫ��
	 * ÿ��ȡ����ʱ��playerX,playerYΪ��׼ȡclip
	 * ���ߵ�ֵ��player�������е�λ��Ϊ���ݣ������Ӧ��������clip���Ͻ�Ϊelements����ĵڼ��еڼ���
	 * ���ʼ��Ӧ�ɱ���ͼ��MapKind.DOOR���ڵ�λ�þ���
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* �趨��ά�����ͼʱ���Ͻ�λ��,XY�ֱ��Ǻ������򳤶�*/
	public static int wait = 0,playerX = 6, playerY = 6;
	private MapObject[][] elements;
	private int mapNo = 0;
	
	/**׷��ս��ʼ�ı�־������������Ҫ�ǵð�����ط���״̬�ı�*/
	public static boolean startChase = false;
	
	private boolean eventInfoEnd = true;
	private int eventInfoStep = 0;
	private final int RUN_RATE = 2;
	private final int WALK_RATE = 3;
	private ArrayList<String> eventInfo = new ArrayList<String>();
	public boolean showInfo = false;

	public Map(int sizeY, int sizeX) {
		elements = new MapObject[sizeY][sizeX];
		showInfo = false;
		init();
	}

	private void init() {
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[i].length; j++)
				elements[i][j] = new MapObject(MapKind.BARY, 0);
		Control.chaseX = 6;
		Control.chaseY = 6;
		chaseReady = 0;
	}
	
	/**�����½����ͼʱ��ʼ��λ�ã�����������elements�е�λ�ã�
	 * �ı�ѡ������clip������,
	 * ע�⴫��X,Y������������λ�ã�
	 * �ı��X,Y��������꣬���߸պ��෴
	 * ����ûд�ã��������
	 * @param playerX ��������
	 * @param playerY ��������
	 * */
	public void setLocation(int playerX,int playerY){
		Map.playerX = playerY;
		Map.playerY = playerX;
	}
	
	/**�ı��ά������ĳ��λ�õ���Ϣ
	 * @param x ��������
	 * @param y ��������
	 * @param object ����һ��Ŀ��MapObject
	 * */
	public void edit(int row, int col, MapObject object) {
		elements[row][col] = object;
	}
	
	public void merge(){
		mapNo = ImageSets.getMapsSize();
		BufferedImage map = new BufferedImage(Lib.boundsPerImg*elements[0].length,
				Lib.boundsPerImg*elements.length,BufferedImage.TYPE_INT_RGB);
		Graphics g = map.getGraphics();
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[0].length; j++)
				g.drawImage(ImageSets.getImg(elements[i][j]), 
						j * Lib.boundsPerImg,i * Lib.boundsPerImg, null);
		ImageSets.addMapImage(map);
	}
	
	public void remerge(){
		BufferedImage map = new BufferedImage(Lib.boundsPerImg*elements[0].length,
				Lib.boundsPerImg*elements.length,BufferedImage.TYPE_INT_RGB);
		Graphics g = map.getGraphics();
		for (int i = 0; i < elements.length; i++)
			for (int j = 0; j < elements[0].length; j++)
				g.drawImage(ImageSets.getImg(elements[i][j]), 
						j * Lib.boundsPerImg,i * Lib.boundsPerImg, null);
		ImageSets.changeMapImage(map,mapNo);
	}
	
	/**�����ƶ�����ֹ����Խ��
	 * @param dir Ҫ�ƶ��ķ���
	 * */
	public boolean canMove(Direction dir) {
		if (dir == Direction.U) {
			if (playerY > 0 && elements[playerY - 1][playerX].canGo()) {
				return true;
			}
		} else if (dir == Direction.D) {
			if (playerY < elements.length - 1 
					&& elements[playerY + 1][playerX].canGo()) {
				return true;
			}
		} else if (dir == Direction.L) {
			if (playerX > 0 && elements[playerY][playerX - 1].canGo()) {
				return true;
			}
		} else if (dir == Direction.R) {
			if (playerX < elements[0].length - 1
					&& elements[playerY][playerX + 1].canGo()) {
				return true;
			}
		}
		return false;
	}
	
	/**��������Ϊ�¼�ʱ����ʾ��Ʒ��Ϣ
	 * @param g ����
	 * @param direction �����ƶ�����
	 * */
	public void keyResponse(Graphics g, Direction direction) {
		//�����Ƿ�ʼ��ʾ��Ϣ������Ϣ��ʼ��ʾ����һ����Ӧ������������
		if (	!showInfo && Control.Z 
				&& ((elements[playerY][playerX].getKind() == MapKind.EVENT)
					||(elements[playerY][playerX].getKind() == MapKind.ACHIEVEMENT))
				&& elements[playerY][playerX].getDirection() == direction
				&& eventInfoEnd && GameObject.checkTimeGap(100)) {
			eventInfo = elements[playerY][playerX].getInfo();
			showInfo = true;
		}
		//���ж�ȡ��Ϣ�����ж���Ϣ�Ƿ��Ѿ����꣬����Ϣ�������һ���ֲ���������
		else if (showInfo) {
			//ǿ��ת��Graphics2D���ò�͸����
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(0.5f));
			g.setColor(Color.WHITE);
			g.setFont(Lib.regular);
			g.fillRect(Lib.gameWIDTH / 11, Lib.gameHEIGHT - Lib.gameWIDTH / 7, Lib.gameWIDTH - 2 * Lib.gameWIDTH / 11,
					Lib.gameHEIGHT / 7);
			((Graphics2D) g).setComposite(AlphaComposite.SrcOver.derive(1.0f));
			g.setColor(Color.BLACK);
			g.drawString(eventInfo.get(eventInfoStep),
					Lib.gameWIDTH / 10, (int) (Lib.gameHEIGHT * (9.0 / 10)));
			//��ȡ��һ����Ϣ
			if(Control.Z && GameObject.checkTimeGap(200)){
				eventInfoStep++;
				//ȫ����ȡ���
				if(eventInfoStep == eventInfo.size()){
					eventInfoStep = 0;
					showInfo = false;
				}
			}
		}
	}
	
	/**���ﲻ��ʱ�Ļ�ͼ����
	 * @param g ����
	 * */
	public void paint(Graphics g) {
		wait = WALK_RATE;
		g.drawImage(ImageSets.getMapImage(mapNo),
				Player.getInstance().getPaintX()-playerX*Lib.boundsPerImg + Lib.adjustX,
				Player.getInstance().getPaintY()-playerY*Lib.boundsPerImg + Lib.adjustY,null);
		//׷��ս����
		if(startChase){
			chase(g);
		}
	}
	/**�����ƶ�ʱ�Ļ�ͼ����
	 * @param g ����
	 * @param direction �ƶ�����
	 * @param isRunning �Ƿ��ڱ��ܣ����ڱ�����Ϊtrue������Ϊfalse
	 * */
	public void paint(Graphics g, Direction direction, boolean isRunning) {

			wait++;
			boolean can_move = canMove(direction);
			int x = 0,y = 0;
			if(can_move){
				if (wait >= (isRunning?WALK_RATE:RUN_RATE)) {
					wait = 0;
					switch(direction){
					case U:
						playerY--;
						break;
					case D:
						playerY++;
						break;
					case L:
						playerX--;
						break;
					case R:
						playerX++;
						break;
					default:
						break;
					}
				}
				else{
					//Ϊ�������٣�ÿһ֡����waitֵ��ʹ��ͼ����������λ�ƣ������֡���ͼ�ƶ�һ����ɵĿ��ٸ�
					switch(direction){
					case U:
						y = wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					case D:
						y = -wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					case L:
						x = wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					case R:
						x = -wait * Lib.boundsPerImg / (isRunning? RUN_RATE:WALK_RATE);
						break;
					default:
						break;
					}
				}
			}
			g.drawImage(ImageSets.getMapImage(mapNo),
					Player.getInstance().getPaintX()-playerX*Lib.boundsPerImg + x + Lib.adjustX,
					Player.getInstance().getPaintY()-playerY*Lib.boundsPerImg + y + Lib.adjustY,
					null);
			
			//׷��ս����
			if(startChase){
				chase(g);
			}
			paint(g);
	}
	
	/**��������·�Ԫ���Ƿ�Ϊ��ܣ�����ǣ���Ҫ�ػ������°���
	 * @param g ����
	 * */
	public void checkShell(Graphics g){
		if(elements[playerY + 1][playerX].getKind() == MapKind.SHELL){
			g.drawImage(ImageSets.getImg(elements[playerY + 1 ][playerX]), Player.getInstance().getPaintX()+Lib.adjustX,
					Player.getInstance().getPaintY() + Lib.adjustY, null);
		}
	}
	/**������ڵ��Ƿ�ΪAuto����
	 * @param g ����
	 * */
	public void checkAuto(Graphics g){
		if(elements[playerY][playerX].getKind() == MapKind.AUTO){
			elements[playerY][playerX].die(ImageSets.getMapImage(mapNo));
		}
	}
	/**����ƶ�λ�õĵ���MapObject*/
	public MapObject getUnderfoot(){
		return elements[playerY][playerX];
	}
	public MapObject getSomewhere(int x,int y){
		return elements[x][y];
	}
	/**��һ���ǲ�����
	 * @param direction �����ƶ��ķ���
	 * */
	public boolean isDoor(Direction direction){
		switch(direction){
			case U:
				if(elements[playerY - 1][playerX].getKind() == MapKind.DOOR 
					&&elements[playerY - 1][playerX].canGo()){
					if(startChase){	//����׷��ս
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY - 1][playerX];
					}
					return true;
				}
				break;	
			case D:
				if(elements[playerY + 1][playerX].getKind() == MapKind.DOOR
						&&elements[playerY + 1][playerX].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY + 1][playerX];
					}
					return true;
				}
				break;	
			case L:
				if(elements[playerY][playerX - 1].getKind() == MapKind.DOOR
						&&elements[playerY][playerX - 1].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY][playerX - 1];
					}
					return true;
				}	
				break;
			case R:
				if(elements[playerY][playerX + 1].getKind() == MapKind.DOOR
						&&elements[playerY][playerX + 1].canGo()){
					if(startChase){
						readyForDoor = true;
						chaseReady = 0;
						chaseDoor = elements[playerY][playerX + 1];
					}
					return true;
				}
				break;
			default:	
		}
		return false;
	}
	/**��ü��������λ�õ���
	 * @param direction �������ڵķ���
	 * */
	public MapObject getDoor(Direction direction){
		switch(direction){
		case U:
			return elements[playerY - 1][playerX];
		case D:
			return elements[playerY + 1][playerX];
		case L:
			return elements[playerY][playerX - 1];
		case R:
			return elements[playerY][playerX + 1];
		default:
			return null;
		}
	
	
	}

	/**
	 * �����Ƿ�Ϊ���Դ�ɳɾ͵ĵ�
	 * @param direction �������ڵķ���
	 * */
	public boolean isAchievement(Direction direction){
		if(elements[playerY][playerX].getKind() == MapKind.ACHIEVEMENT 
			&& direction == elements[playerY][playerX].getDirection()){
			return true;
		}
		return false;
	}
	/**
	 * �༭������ͼ
	 * @param mapList ȫ���ĵ�ͼ����
	 * */
	public void editMapList(ArrayList<Map> mapList){
		elements[playerY][playerX].editMapList(mapList);
	}
	
	/*
	 * �˴�����ȫ��Ϊ׷��սר�õķ���
	 * ��chaseΪ����
	 * �����������ķ���
	 * ����׷����㷨
	 * �ŵĴ����ת��
	 * �ȵ�
	 * */
	public static int chaseReady = 0;
	private static int speed = 3,chaseWait = 0;
	public static boolean readyForDoor = false;
	private static MapObject chaseDoor;
	private Direction direction;
	/*
	 * elements[playerY][meX]
	 * ����������Control.chaseY
	 * ���˺�����Control.chaseX
	 * elements[Control.chaseY][Control.chaseX]
	 * */
	/**
	 * ׷��ս���¾�̬����
	 * */
	public static boolean initChase(){
		//�ŵ�ת����ȴ�
		if(readyForDoor){
			Control.chaseX = chaseDoor.playerY;
			Control.chaseY = chaseDoor.playerX;
			if(chaseReady++>15){
				readyForDoor = false;
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * ׷��ս���������������룬������ͼ
	 * @param g ����
	 * */
	public void chase(Graphics g){
		//��������ڵȴ�����Ӧ����ô��û�б�Ҫ�����ˣ��ѻ�ͼ���ַŽ��ж���
		if(!initChase()){
			chaseWait++;
				if(playerX > Control.chaseX && elements[Control.chaseY][Control.chaseX+1].canGo()){
					//�жϸò����ƶ�
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX++;
					}
					//�����Ƿ��ƶ����������ƻ���Ҫ���ֳ����ģ�
					direction = Direction.R;
				}else if(playerX < Control.chaseX && elements[Control.chaseY][Control.chaseX-1].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseX--;
					}
					direction = Direction.L;
				}else if(playerY > Control.chaseY && elements[Control.chaseY+1][Control.chaseX].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseY++;
					}
					direction = Direction.D;
				}else if(playerY < Control.chaseY && elements[Control.chaseY-1][Control.chaseX].canGo()){
					if(chaseWait >= speed){
						chaseWait = -1;
						Control.chaseY--;
					}
					direction = Direction.U;
				}else{
					direction = Direction.U;
				}
				if(playerX == Control.chaseX && playerY == Control.chaseY){
					Game.getInstance().setCurrent(new GameOverObject());
				}
				
				switch(direction){
				case U:
					g.drawImage(ImageSets.getImg(61), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case D:
					g.drawImage(ImageSets.getImg(62), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case L:
					g.drawImage(ImageSets.getImg(63), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				case R:
					g.drawImage(ImageSets.getImg(64), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
					break;
				default:
					g.drawImage(ImageSets.getImg(61), 
							Player.getInstance().getPaintX()-(playerX-Control.chaseX)*Lib.boundsPerImg+Lib.adjustX, Lib.gameHEIGHT/2-(playerY-Control.chaseY)*Lib.boundsPerImg-20, null);
				}
		}
		
		
	}
	
	
}