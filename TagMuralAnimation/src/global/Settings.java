package global;

public class Settings {
	public static boolean bw_mode = true;
	public static boolean draw_outline = false;
	public static boolean draw_layers = false;
	public static boolean draw_words = true;
	public static boolean draw_brick_border = false;
	
	public static float DEFAULT_STORY_HEIGHT = 110;
	public static float ROOF_ANGLE = 16;
	public static float LAYER_THICKNESS = 4;
	public static float GAP = LAYER_THICKNESS;
	public static float F_GAP = GAP/2;
	
	//TODO: Add some randomness
	public static float getStoryHeight() {
		return DEFAULT_STORY_HEIGHT;
	}
	
	public static float getStoryWidth(int windows) {
		return DEFAULT_STORY_HEIGHT * windows;
	}

	public static float getTopMargin() {
		return DEFAULT_STORY_HEIGHT * 0.2f;
	}
	
	public static float getBottomMargin() {
		return DEFAULT_STORY_HEIGHT * 0.15f;
	}
	
	public static float getSideMargin() {
		return DEFAULT_STORY_HEIGHT * 0.4f;
	}
	
	public static float getInBetween() {
		return DEFAULT_STORY_HEIGHT * 0.4f;
	}
	
	public static float getRailingHeight() {
		return DEFAULT_STORY_HEIGHT * 0.4f;
	}
	
	public static float getRoofAngle() {
		return ROOF_ANGLE;
	}
	
	public static float getRoofHeight() {
		return DEFAULT_STORY_HEIGHT;
	}
	
	public static float getRoofOverhang(float building_width) {
		return building_width * 0.05f;
	}
}
