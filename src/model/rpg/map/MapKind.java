package model.rpg.map;
/**
 * 地图元素的属性
 * */
public enum MapKind {
	FLOOR,							//可以走
	BARY,							//不能走
	EVENT,ACHIEVEMENT,AUTO,DOOR,SHELL		//需要特殊处理的Object
}
