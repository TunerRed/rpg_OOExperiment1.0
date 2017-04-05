package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import control.*;
import model.Game;
/**
 * 游戏面板，带有一个重画线程和一个键盘监听和一个鼠标监听。
 * 当某个键按下或松开时，此类会改变Control类中变量的值。
 * */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener,MouseListener{
	private BufferedImage doubleImg = new BufferedImage(Lib.gameWIDTH,Lib.gameHEIGHT,1);
	private Graphics offScreen = doubleImg.getGraphics();
	private Image cursor;
	public GamePanel(){
		this.addKeyListener(this);
		this.addMouseListener(this);
		//鼠标指针的形状
		try {
			cursor = ImageIO.read(this.getClass().getClassLoader()
					.getResourceAsStream("source/cursor.png"));
			setCursor(Toolkit.getDefaultToolkit()
					.createCustomCursor(cursor, new Point(10, 10), "norm"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Game.getInstance().init();
		
		//重画线程
		new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
			}
		}.start();
		
		this.setFocusable(true);
		
	}
	
	@Override
	public void paint(Graphics g){
		Game.getInstance().paint(offScreen);
		g.drawImage(doubleImg, 0, 0, null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			Control.UP=true;
		}
		if(key == KeyEvent.VK_DOWN){
			Control.DOWN=true;
		}
		if(key == KeyEvent.VK_LEFT){
			Control.LEFT=true;
		}
		if(key == KeyEvent.VK_RIGHT){
			Control.RIGHT=true;
		}
		if(key == KeyEvent.VK_Z){
			Control.Z=true;
		}
		if(key == KeyEvent.VK_X){
			Control.X=true;
		}
		if(key == KeyEvent.VK_C){
			Control.C=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			Control.UP=false;
		}
		if(key == KeyEvent.VK_DOWN){
			Control.DOWN=false;
		}
		if(key == KeyEvent.VK_LEFT){
			Control.LEFT=false;
		}
		if(key == KeyEvent.VK_RIGHT){
			Control.RIGHT=false;
		}
		if(key == KeyEvent.VK_Z){
			Control.Z=false;
		}
		if(key == KeyEvent.VK_X){
			Control.X=false;
		}
		if(key == KeyEvent.VK_C){
			Control.C=false;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Control.clickX = e.getX();
		Control.clickY = e.getY();
		Control.isPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Control.isPressed = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
