package model.rpg.map;
/**
 * 地图元素的属性
 * */
public enum MapKind {
	FLOOR,							//可以走
	BLOCK,							//不能走
	EVENT,INFO,AUTO,DOOR,SHELL		//需要特殊处理的Object
}
