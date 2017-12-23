package houses.block;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;

public class HouseInfo {
	public enum PositionType { CENTER, LEFT_EDGE, RIGHT_EDGE; }
	public enum RoofType { STRAIGHT, POINTED, ANGLED; 
		private static final List<RoofType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
	}
	
	private float story_height;
	private float roof_height;
	
	private HouseType h_type;
	private PositionType p_type = PositionType.CENTER;
	private RoofType r_type = RoofType.ANGLED;
	
	public HouseInfo(HouseType type, float story_height, float roof_height) {
		this.h_type = type;
		this.story_height = story_height;
		this.roof_height = roof_height;
	}
	
	public void setPositionType(HouseInfo.PositionType type) { p_type = type; }
	
	public static RoofType pickRandomRoofType() {
		return RoofType.VALUES.get(RoofType.RANDOM.nextInt(RoofType.SIZE));
	}
	
	public int getNumStories() { return h_type.stories(); }
	public int getNumWindows() { return h_type.windows(); }
	public boolean isPortico() { return h_type.isPortico(); }
	public float getWidth() { return h_type.getWidth(); }
	public float getStoryHeight() { return story_height; }
	public float getRoofHeight() {return roof_height; }
	public HouseType getHouseType() { return h_type; }
	public PositionType getPositionType() { return p_type; }
	public RoofType getRoofType() { return r_type; }
}
