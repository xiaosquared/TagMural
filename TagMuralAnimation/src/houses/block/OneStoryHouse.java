package houses.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import global.Settings;
import houses.elements.WindowFactory;
import houses.stories.Story;
import processing.core.PVector;

public class OneStoryHouse extends House {
	public enum DoorLayout { ALL_DOORS, DOOR_WINDOW, WINDOW_DOOR; 
		private static final List<DoorLayout> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
	}

	public OneStoryHouse(float base_x, float base_y, float width, int num_windows, float layer_thickness) {
		super(base_x, base_y, width, num_windows, layer_thickness);
	}

	public static DoorLayout pickRandomLayout() {
		return DoorLayout.VALUES.get(DoorLayout.RANDOM.nextInt(DoorLayout.SIZE));
	}
	
	public void addDoorwayStory() {
		DoorLayout layout = pickRandomLayout();
		Story story = makePlainStoryHelper(Settings.getStoryHeight());

		WindowFactory.Type type = WindowFactory.Type.RECT;
		if (layout == DoorLayout.ALL_DOORS) {
			story.addWindows(type, num_windows, 
					Settings.getTopMargin(), 0, 
					Settings.getSideMargin(), Settings.getInBetween(), window_color);
		} else {
			ArrayList<PVector> win_origins = getWindowOrigins(story, Settings.getTopMargin(), Settings.getSideMargin(), Settings.getInBetween());
			float win_width = getWindowWidth(story, Settings.getTopMargin(), Settings.getSideMargin(), Settings.getInBetween());

			if (layout == DoorLayout.DOOR_WINDOW) {
				PVector origin = win_origins.get(0);
				story.addWindow(type, origin.x, origin.y, win_width, Settings.getShortWindowHeight(), window_color);
				origin = win_origins.get(3);
				story.addWindow(type, origin.x, origin.y, win_width, Settings.getShortWindowHeight(), window_color);

				origin = win_origins.get(1);
				story.addDoor(type, origin.x, origin.y, win_width, window_color);
				origin = win_origins.get(2);
				story.addDoor(type, origin.x, origin.y, win_width, window_color);
			} else {
				PVector origin = win_origins.get(1);
				story.addWindow(type, origin.x, origin.y, win_width, Settings.getShortWindowHeight(), window_color);
				origin = win_origins.get(2);
				story.addWindow(type, origin.x, origin.y, win_width, Settings.getShortWindowHeight(), window_color);

				origin = win_origins.get(0);
				story.addDoor(type, origin.x, origin.y, win_width, window_color);
				origin = win_origins.get(3);
				story.addDoor(type, origin.x, origin.y, win_width, window_color);
			}
		}

		stories.add(story);
	}

	private float getWindowWidth(Story story, float top_margin, float side_margin, float in_between) {
		return (story.getWidth() - 2 * side_margin - (num_windows-1) * in_between)/num_windows;
	}

	private ArrayList<PVector> getWindowOrigins(Story story, float top_margin, float side_margin, float in_between) {
		float win_width = getWindowWidth(story, top_margin, side_margin, in_between);
		ArrayList<PVector> win_origins = new ArrayList<PVector>();
		for (int i = 0; i < num_windows; i++) {
			float x = side_margin + i * (win_width + in_between);
			win_origins.add(new PVector(x, top_margin));
		}
		return win_origins;
	}
}
