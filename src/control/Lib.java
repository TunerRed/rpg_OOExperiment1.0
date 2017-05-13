package control;

import java.awt.Font;
import java.io.BufferedInputStream;

/**
 * ���ֳ������ݴ��������������޸�
 * */
public final class Lib {
	public final static int 
			gameWIDTH = 800,gameHEIGHT = 600,
			boundsPerImg = 45
			,adjustX = 17,adjustY =  40
			,playerX = 5,playerY = 16
			,chaseX = 2,chaseY = 5;
			// �趨����ͼʱ���Ͻ�λ�ò�����ʵ�����������Ӧ�ĵ���
	public final static Font regular = loadFont("source/FONT.TTF",23);
	public final static Font terror = loadFont("source/����2.ttf",25);
	
	/**
	 * ʹ��jar���ڵ����壬��������ϵͳ����
	 * ��һ���������ⲿ���������ڶ����������С
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
            return new java.awt.Font("����", Font.PLAIN, (int)fontSize);
        }
    }
	
}
