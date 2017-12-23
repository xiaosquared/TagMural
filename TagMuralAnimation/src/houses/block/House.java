package houses.block;

import java.util.ArrayList;

import global.ColorPalette;
import global.Settings;
import houses.elements.WindowFactory;
import houses.stories.PlainStory;
import houses.stories.PorticoStory;
import houses.stories.RoofStory;
import houses.stories.Story;
import processing.core.PApplet;
import processing.core.PVector;

public class House {
	protected PVector base;
	protected PVector trans = new PVector(0, 0);
	
	protected float width;
	protected float layer_thickness;
	
	protected int num_windows;
	
	protected ArrayList<Story> stories;
	protected int current_story_index;
	
	protected ColorPalette wall_color = ColorPalette.CYAN;
	protected ColorPalette window_color = ColorPalette.BLUE;
	protected ColorPalette railing_color = ColorPalette.YELLOW;
	protected ColorPalette roof_color = ColorPalette.RED;
	
	public House(float base_x, float base_y, float width, int num_windows, float layer_thickness) {
		base = new PVector(base_x, base_y);
		this.width = width;
		this.layer_thickness = layer_thickness;
		this.num_windows = num_windows;
		stories = new ArrayList<Story>();
	}
	
	public void addStories(int num_stories) {
		for (int i = 0; i < num_stories; i++)
			addPlainStory();
	}
	
	
	// TYPES OF STORIES	
	
	public void addPlainStory() { addPlainStory(WindowFactory.Type.RECT); }
	public void addPlainStory(WindowFactory.Type type) {
		Story story = makePlainStoryHelper(Settings.getStoryHeight());
		story.addWindows(type, num_windows, Settings.getTopMargin(),
						Settings.getBottomMargin(), Settings.getSideMargin(), Settings.getInBetween(), window_color);
		stories.add(story);
	}
	
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
	
	protected Story makePlainStoryHelper(float story_height) {
		Story story = new PlainStory(base.x, base.y - getBuildingHeight() - story_height, width, story_height, layer_thickness, wall_color);
		return story;
	}
	
	public void addPorticoStory() { addPorticoStory(WindowFactory.Type.ARCH); }
	public void addPorticoStory(WindowFactory.Type type) {
		float story_height = Settings.getStoryHeight();
		Story story = new PorticoStory(base.x, base.y - getBuildingHeight() - story_height, width, story_height, 
										num_windows, Settings.getColumnWidth(), layer_thickness, wall_color, ColorPalette.YELLOW);
		story.addRailing(Settings.getRailingHeight(), Settings.getRailingWidth(), Settings.getRailingWidth(), Settings.getRailingWidth(), ColorPalette.GREEN);
		story.addWindows(type, num_windows, Settings.getTopMargin(), Settings.getBottomMargin(), Settings.getSideMargin(), 0, window_color);
		stories.add(story);
	}
	
	// ROOF
	
	public void addRoof(HouseInfo.RoofType type, boolean has_overhang, boolean has_windows) {
		float roof_height = Settings.getRoofHeight();
		float overhang = 0;
		if (has_overhang)
			overhang = Settings.getRoofOverhang(width);
		
		Story story;
		switch(type) {
		case POINTED:
			story = new RoofStory(base.x - overhang, base.y - getBuildingHeight() - roof_height,
					width + 2*overhang, roof_height, layer_thickness, roof_color);
			break;
		default:
			story = new RoofStory(base.x - overhang, base.y - getBuildingHeight() - roof_height,
					width + 2*overhang, roof_height, Settings.getRoofAngle(), layer_thickness, roof_color);
			break;
		}
		

		// TODO: this is a total hack!
//		if (has_windows) {
//			story.addWindows(WindowFactory.Type.POINTED, 1, Settings.getTopMargin() * 2, 
//							Settings.getBottomMargin(), Settings.getSideMargin() * num_windows, Settings.getSideMargin(), window_color);
//		}
			
		
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
	
	public float getWidth() { return width; }
	
	public void fillAll(PApplet parent) {
		for (Story s : stories)
			s.fillAll(parent);
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		for (Story s : stories)
			s.draw(outline, layers, words, parent);
	}
}
