package global;

public class Settings {
	public static boolean bw_mode = true;
	public static boolean draw_outline = false;
	public static boolean draw_layers = false;
	public static boolean draw_words = true;
	public static boolean draw_brick_border = false;
	
	public static float DEFAULT_STORY_HEIGHT = 100;
	public static float HEIGHT_VARIATION = 0.1f;
	public static float WIDTH_VARIATION = 0.3f;
	public static float ROOF_ANGLE = 16;
	public static float LAYER_THICKNESS = 4;
	public static float GAP = LAYER_THICKNESS;
	public static float F_GAP = GAP/2;
	public static float SIDEWALK_HEIGHT = 30;
	
	//TODO: Add some randomness
	public static float getStoryHeight() {
		return DEFAULT_STORY_HEIGHT;
	}
	
	public static float getStoryHeightWithVar() {
		return DEFAULT_STORY_HEIGHT - (DEFAULT_STORY_HEIGHT * (float) Math.random() * HEIGHT_VARIATION)  ;
	}
	
	public static float getStoryWidth(int windows) {
		return DEFAULT_STORY_HEIGHT * windows;
	}
	
	public static float getStoryWidthWithVar(int windows) {
		float basic_width = getStoryHeightWithVar() * windows;
		return basic_width - (basic_width * (float) Math.random() * WIDTH_VARIATION);
	}

	// TODO: this should probably change
	public static float getUnitWidth() {
		return DEFAULT_STORY_HEIGHT;
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
	
	public static float getShortWindowHeight() {
		return DEFAULT_STORY_HEIGHT * 0.3f;
	}
	
	public static float getRailingHeight() {
		return DEFAULT_STORY_HEIGHT * 0.4f;
	}
	
	public static float getRailingWidth() {
		return LAYER_THICKNESS * 2;
	}
	
	public static float getRailingInbetween() {
		return LAYER_THICKNESS * 1.5f;
	}
	
	public static float getColumnWidth() {
		return LAYER_THICKNESS * 2; 
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
