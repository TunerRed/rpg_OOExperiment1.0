package model.over;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameObject;
import model.start.StartObject;
import control.Control;
import control.Lib;

public class GameEndObject extends GameObject {
	int step = 0,backNo=0,imgBounds=Lib.gameHEIGHT;
	
	Image roll,back[];
	
	public GameEndObject(){
		try {
			roll = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("source/end/roll.png"));
			back = new Image[3];
			for(int i=0;i<back.length;i++){
				back[i] = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("source/end/"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.clearStatic();
	}
	
	@Override
	protected void keyResponse() {
		if(step<999)
			step++;
		if(step >960&&(Control.Z||Control.X||Control.isPressed))
				die();
	}
	
	private void paint(Graphics g){
		if(imgBounds > -roll.getHeight(null)+0.5*Lib.gameHEIGHT)
			imgBounds -= 3;
		g.drawImage(back[step/400], 0, 0, null);
		g.drawImage(roll, 100, imgBounds, null);
	}
	
	@Override
	public void draw(Graphics g) {
		keyResponse();
		paint(g);
	}

	@Override
	public void die() {
		musicStop();
		new StartObject();
	}

}
