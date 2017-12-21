package houses.stories;

import java.util.ArrayList;

import global.ColorPalette;
import global.Settings;
import houses.elements.Fillable;
import houses.elements.Railing;
import houses.elements.Wall;
import houses.elements.Window;
import houses.elements.WindowFactory;
import processing.core.PApplet;

public class PlainStory implements Story {
	protected Wall main;
	protected Fillable base;
	protected ArrayList<Window> windows;
	
	protected float height;
	protected float layer_thickness;
	
	public PlainStory(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		main = new Wall(x, y, width, height, layer_thickness, color);
		windows = new ArrayList<Window>();
		this.height = height;
		this.layer_thickness = layer_thickness;
	}

	// TODO: Get & Set Methods
	public float getLayerThickness() { return main.getLayerThickness(); }
	public float getWidth() { return main.getWidth(); }
	public float getHeight() { return height; }
	public float getMinX() { return main.getMinX(); }
	public float getMinY() { return main.getMinY(); }
	
	/**
	 * @param type
	 * @param win_x: x of window relative to main.x 
	 * @param win_y: y of window relative to main.y
	 */
	
	//TODO: probably make this private... need to change the interface!!
	// What about for roof?
	
	public Window addWindow(WindowFactory.Type type, float win_x, float win_y, float w_width, float w_height, ColorPalette color) {
		if (base != null) {
			w_height = main.getHeight() - win_y;
		} else {
			float new_y = main.getMinY() + win_y + w_height;
			createBaseWall(new_y);
			shortenMainWall(new_y);
		}
		Window w = WindowFactory.createWindow(type, main.getMinX() + win_x, main.getMinY() + win_y, w_width, w_height, layer_thickness, color);
		windows.add(w);
		w.makeHole(main);
		
		return w;
	}
	
	public ArrayList<Window> addWindows(WindowFactory.Type type, int num, float top_margin, float bot_margin, float side_margin, float in_between, ColorPalette color) {
		float win_width = (main.getWidth() - 2 * side_margin - (num-1) * in_between)/num;
		float win_height = main.getHeight() - top_margin - bot_margin;
		
		ArrayList<Window> windows = new ArrayList<Window>();
		for (int i = 0; i < num; i++) {
			float win_x = side_margin + i * (in_between + win_width);
			Window w = (bot_margin == 0) ? 
					addDoor(type, win_x, top_margin, win_width, color) :
					addWindow(type, win_x, top_margin, win_width, win_height, color);
			windows.add(w);
		}
		return windows;
	}

	public Window addDoor(WindowFactory.Type type, float d_x, float d_y, float d_width, ColorPalette color) {
		float d_height;
		Window door;
		if (base != null) {
			d_height = main.getHeight() + base.getHeight() - d_y;
			door = WindowFactory.createWindow(type, main.getMinX() + d_x, main.getMinY() + d_y, 
													d_width, d_height, layer_thickness, color);
			windows.add(door);
			door.makeHole(main);
			if (base instanceof Wall) {
				float left_bound = ((Wall) base).getMinX() + d_x;
				float right_bound = left_bound + d_width;
				((Wall) base).removeSection(left_bound, right_bound);
			}
		} else {
			d_height = main.getHeight() - d_y;
			door = WindowFactory.createWindow(type, main.getMinX() + d_x, main.getMinY() + d_y, 
					d_width, d_height, layer_thickness, color);
			windows.add(door);
			door.makeHole(main);
		}
		
		return door;
	}
	
	public void addRailing(float rail_height, ColorPalette color) {
		float rail_width = Settings.LAYER_THICKNESS * 2;
		float in_between = rail_width * 1.5f;
		addRailing(rail_height, rail_width, rail_width, in_between, color);
	}
	
	public void addRailing(float rail_height, float rail_width, float tb_rail_height, float in_between, ColorPalette color) {
		// if base already exists, replace it with railing. ignore r_height
		if (base != null) {
			float rx = base.getMinX();
			float ry = base.getMinY();
			float rw = base.getWidth();
			float rh = base.getHeight();
			//float 
			base = new Railing(rx, ry, rw, rh, rail_width, tb_rail_height, in_between, layer_thickness, color);
		} 
		
		// if base doesn't already exits, have to shorten wall
		else {
			float new_y = main.getMaxY() - rail_height;
			shortenMainWall(new_y);
			base = new Railing(main.getMinX(), main.getMaxY() + Settings.GAP, main.getWidth(), rail_height, 
								rail_width, tb_rail_height, in_between, layer_thickness, color);	
		}
	}
	
	
	/**
	 * helper method to create base wall from y to bottom of the main wall
	 * Run shortenMainWall right after to prevent overlap
	 * @param y
	 */
	private void createBaseWall(float y) {
		float new_height_base = main.getMaxY() - y;
		if (new_height_base > main.getLayerThickness()) {
			base = new Wall(main.getMinX(), y, 
					main.getWidth(), new_height_base, 
					main.getLayerThickness(), main.getColor());	
		}
	}
	
	/**
	 * Helper method to make main shorter. y is new bottom edge
	 * @param y
	 */
	private void shortenMainWall(float y) {
		float new_height_main = y - main.getMinY();
		main = new Wall(main.getMinX(), main.getMinY(), main.getWidth(), 
						new_height_main, getLayerThickness(), main.getColor());
	}
	
	public void reset() {
		main.reset();
		if (base != null)
			base.reset();
		for (Window win : windows) {
			win.reset();
			win.makeHole(main);
			if (win.getMaxY() > main.getMaxY())
				((Wall) base).removeSection(win.getMinX(), win.getMaxX());
		}
	}
	
	public void fillAll(PApplet parent) {
		main.fillAll(parent);
		if (base != null)
			base.fillAll(parent);
		for (Window win : windows)
			win.fillAll(parent);
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		main.draw(outline, layers, words, parent);
		if (base != null)
			base.draw(outline, layers, words, parent);
		for (Window win : windows) {
			win.draw(outline, layers, words, parent);
		}
	}
}
