package control;

import java.awt.Font;
import java.io.BufferedInputStream;

/**
 * 各种常用数据存放在这里，避免多次修改
 * */
public final class Lib {
	public final static int 
			gameWIDTH = 800,gameHEIGHT = 600,
			boundsPerImg = 45
			,adjustX = 20,adjustY =  + boundsPerImg + 7;
			// 设定画地图时左上角位置并根据实际情况进行相应的调整
	public final static Font regular = loadFont("source/FONT.TTF",23);
	public final static Font terror = loadFont("source/字体2.ttf",25);
	
	/**
	 * 使用jar包内的字体，不依赖于系统字体
	 * 第一个参数是外部字体名，第二个是字体大小
	 * */
	private static Font loadFont(String fontFileName, float fontSize){
        try
        {
            BufferedInputStream aixing = new BufferedInputStream(
            		Lib.class.getClassLoader().getResourceAsStream(fontFileName));
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;
        }
        catch(Exception e){
            e.printStackTrace();
            return new java.awt.Font("宋体", Font.PLAIN, (int)fontSize);
        }
    }
	
}
