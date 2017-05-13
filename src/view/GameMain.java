package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import control.*;
import model.rpg.map.Map;
/**
 * 游戏的主类，含有main方法
 * */
public class GameMain extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * 主方法
	 * */
	public static void main(String[] args) {
		ImageSets.init();
		new GameMain();
	}
	
	public GameMain(){
		//避免出现即使点了不退出游戏窗口也会关闭的情况
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new closeWindowsListener());
		this.setSize(Lib.gameWIDTH+6,Lib.gameHEIGHT+25);
		this.setLocationRelativeTo(null);
		this.setTitle("东方梦黎明");
		this.setResizable(false);
		this.getContentPane().add(new GamePanel());
		this.setVisible(true);
	}
	
	/**
	 * 内部类，监听窗口关闭
	 * */
	private class closeWindowsListener extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showConfirmDialog(null,"确定退出游戏吗？")==JOptionPane.OK_OPTION)
				System.exit(0);
		}
		
	}
	
}
