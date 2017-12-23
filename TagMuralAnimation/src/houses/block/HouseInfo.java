package houses.block;

import processing.core.PApplet;

public class HouseInfo {
	public enum PositionType { CENTER, LEFT_EDGE, RIGHT_EDGE; }
	public enum RoofType { STRAIGHT, POINTED, ANGLED; }
	
	public int num_windows;
	public int num_stories;
	public float story_width;
	public float story_height;
	public float roof_height;
	
	public PositionType p_type = PositionType.CENTER;
	public RoofType r_type = RoofType.ANGLED;
	
	public HouseInfo(int num_windows, int num_stories, float story_width, float story_height, float roof_height) {
		this.num_windows = num_windows;
		this.num_stories = num_stories;
		this.story_width = story_width;
		this.story_height = story_height;
		this.roof_height = roof_height;
	}
	
	public static RoofType pickRandomRoofType(PApplet parent) {
		int id = PApplet.floor(parent.random(3));
		switch(id) {
		case 0:
			return RoofType.STRAIGHT;
		case 1:
			return RoofType.POINTED;
		default:
			return RoofType.ANGLED;
		}
	}
}
