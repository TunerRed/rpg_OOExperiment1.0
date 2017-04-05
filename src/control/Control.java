package control;
/**
 * 内含一些会随着游戏的变化而变化的值。
 * 包括相应键盘鼠标事件的boolean类型，
 * 以及记录追逐战中敌人坐标的int类型。
 * 当其他类想要确认是否某个键被按下或者鼠标是否点击时，
 * 只需要检查这个类中的变量，
 * 而不用额外添加监听器。
 * */
public class Control {
	public static boolean 
		UP =false,
		DOWN=false,
		LEFT=false,
		RIGHT=false,
		
		Z=false,
		X=false,
		C=false,
	
		isPressed = false;
	
	public static int chaseX=6,chaseY=6,clickX = 0,clickY = 0;
}
