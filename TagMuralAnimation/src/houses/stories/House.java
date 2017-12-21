package houses.stories;

import java.util.ArrayList;

import global.ColorPalette;
import global.Settings;
import houses.elements.WindowFactory;
import processing.core.PApplet;
import processing.core.PVector;

public class House {
	private PVector base;
	private PVector trans = new PVector(0, 0);
	
	private float width;
	private float layer_thickness;
	
	private int num_windows;
	
	private ArrayList<Story> stories;
	private int current_story_index;
	
	private ColorPalette wall_color = ColorPalette.CYAN;
	private ColorPalette window_color = ColorPalette.BLUE;
	private ColorPalette railing_color = ColorPalette.YELLOW;
	private ColorPalette roof_color = ColorPalette.RED;
	
	public House(float base_x, float base_y, float width, int num_windows, float layer_thickness) {
		base = new PVector(base_x, base_y);
		this.width = width;
		this.layer_thickness = layer_thickness;
		this.num_windows = num_windows;
		stories = new ArrayList<Story>();
	}
	
	public void addPlainStory() { addPlainStory(WindowFactory.Type.RECT); }
	public void addPlainStory(WindowFactory.Type type) {
		Story story = makePlainStoryHelper(Settings.getStoryHeight());
		story.addWindows(type, num_windows, Settings.getTopMargin(),
						Settings.getBottomMargin(), Settings.getSideMargin(), Settings.getInBetween(), window_color);
		stories.add(story);
	}

	// TYPES OF STORIES	
	
	public void addBalconyStory() { addBalconyStory(WindowFactory.Type.ARCH); }
	public void addBalconyStory(WindowFactory.Type type) {
		Story story = makePlainStoryHelper(Settings.getStoryHeight());
		story.addRailing(Settings.getRailingHeight(), railing_color);
		story.addWindows(type, num_windows, Settings.getTopMargin(), 
						Settings.getBottomMargin(), Settings.getSideMargin(), Settings.getInBetween(), window_color);
		stories.add(story);
	}
	
	public void addDoorwayStory() { addDoorwayStory(WindowFactory.Type.ARCH); }
	public void addDoorwayStory(WindowFactory.Type type) {
		Story story = makePlainStoryHelper(Settings.getStoryHeight());
		story.addWindows(type, num_windows, Settings.getTopMargin(), 0, 
						Settings.getSideMargin(), Settings.getInBetween(), window_color);
		stories.add(story);
	}
	
	private Story makePlainStoryHelper(float story_height) {
		Story story = new PlainStory(base.x, base.y - getBuildingHeight() - story_height, width, story_height, layer_thickness, wall_color);
		return story;
	}
	
	// ROOF
	
	public void addRoof(boolean has_overhang, boolean has_windows) {
		float roof_height = Settings.getRoofHeight();
		float overhang = 0;
		if (has_overhang)
			overhang = Settings.getRoofOverhang(width);
		Story story = new RoofStory(base.x - overhang, base.y - getBuildingHeight() - roof_height,
									width + 2*overhang, roof_height, Settings.getRoofAngle(), layer_thickness, roof_color);
		

		// TODO: this is a total hack!
		if (has_windows) {
			story.addWindows(WindowFactory.Type.POINTED, 1, Settings.getTopMargin() * 2, 
							Settings.getBottomMargin(), Settings.getSideMargin() * num_windows, Settings.getSideMargin(), window_color);
		}
			
		
		stories.add(story);
	}
	
	public void addTriangularRoof(boolean has_overhang) {
		float roof_height = Settings.getRoofHeight();
		float overhang = 0;
		if (has_overhang)
			overhang = Settings.getRoofOverhang(width);
		Story story = new RoofStory(base.x - overhang, base.y - getBuildingHeight() - roof_height,
									width + 2*overhang, roof_height, layer_thickness, roof_color);
		stories.add(story);
	}
	
	public float getBuildingHeight() {
		float h = 0;
		for (Story s : stories)
			h += s.getHeight();
		return h;
	}
	
	public void fillAll(PApplet parent) {
		for (Story s : stories)
			s.fillAll(parent);
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		for (Story s : stories)
			s.draw(outline, layers, words, parent);
	}
}
