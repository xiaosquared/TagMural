package houses.block;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import global.Settings;

public enum HouseType {
	ONE_STORY_FOUR_WINDOW(1, 4, false),
	TWO_STORY_THREE_WINDOW(2, 3, false),
	TWO_STORY_THREE_WINDOW_PORTICO(2, 3, true),
	THREE_STORY_THREE_WINDOW(3, 3, false);
	
	private final int num_stories;
	private final int num_windows;
	private final boolean columns;
	
	private static final List<HouseType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	HouseType(int num_stories, int num_windows, boolean columns) {
		this.num_stories = num_stories;
		this.num_windows = num_windows;
		this.columns = columns;
	}
	
	public int stories() { return num_stories; }
	public int windows() { return num_windows; }
	public boolean isPortico() { return columns; }
	
	public float getWidth() {
		switch(this) {
		case ONE_STORY_FOUR_WINDOW:
			return Settings.DEFAULT_STORY_HEIGHT * 2.64f;
		case TWO_STORY_THREE_WINDOW:
		case TWO_STORY_THREE_WINDOW_PORTICO:
			return Settings.DEFAULT_STORY_HEIGHT * 1.73f;
		case THREE_STORY_THREE_WINDOW:
			return Settings.DEFAULT_STORY_HEIGHT * 2.46f;
		default:
			throw new AssertionError("unknown type " + this);
		}
	}
	
	public static HouseType getRandomType() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
