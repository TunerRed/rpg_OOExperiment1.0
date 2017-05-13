package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import control.*;
import model.rpg.map.Map;
/**
 * ��Ϸ�����࣬����main����
 * */
public class GameMain extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * ������
	 * */
	public static void main(String[] args) {
		ImageSets.init();
		new GameMain();
	}
	
	public GameMain(){
		//������ּ�ʹ���˲��˳���Ϸ����Ҳ��رյ����
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new closeWindowsListener());
		this.setSize(Lib.gameWIDTH+6,Lib.gameHEIGHT+25);
		this.setLocationRelativeTo(null);
		this.setTitle("����������");
		this.setResizable(false);
		this.getContentPane().add(new GamePanel());
		this.setVisible(true);
	}
	
	/**
	 * �ڲ��࣬�������ڹر�
	 * */
	private class closeWindowsListener extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showConfirmDialog(null,"ȷ���˳���Ϸ��")==JOptionPane.OK_OPTION)
				System.exit(0);
		}
		
	}
	
}
