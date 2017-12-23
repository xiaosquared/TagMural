package houses.block;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import global.Settings;

public enum HouseType {
	ONE_STORY_FOUR_WINDOW(1, 4),
	TWO_STORY_THREE_WINDOW(2, 3),
	THREE_STORY_THREE_WINDOW(3, 3);
	
	private final int num_stories;
	private final int num_windows;
	
	private static final List<HouseType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	HouseType(int num_stories, int num_windows) {
		this.num_stories = num_stories;
		this.num_windows = num_windows;
	}
	
	public int stories() { return num_stories; }
	public int windows() { return num_windows; }
	
	public float getWidth() {
		switch(this) {
		case ONE_STORY_FOUR_WINDOW:
			return Settings.DEFAULT_STORY_HEIGHT * 2.64f;
		case TWO_STORY_THREE_WINDOW:
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
