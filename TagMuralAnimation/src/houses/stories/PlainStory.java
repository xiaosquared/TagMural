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

public class PlainStory {
	protected Wall main;
	protected Fillable base;
	protected ArrayList<Window> windows;
	
	protected float height;
	private float layer_thickness;
	
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
	 * @param left_margin: x of window relative to main.x 
	 * @param top_margin: y of window relative to main.y
	 */
	public Window addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color) {
		if (base != null) {
			w_height = main.getHeight() - top_margin;
		} else {
			float new_y = main.getMinY() + top_margin + w_height;
			createBaseWall(new_y);
			shortenMainWall(new_y);
		}
		Window w = WindowFactory.createWindow(type, main.getMinX() + left_margin, main.getMinY() + top_margin, w_width, w_height, layer_thickness, color);
		windows.add(w);
		w.makeHole(main);
		
		return w;
	}
	
	public Window addDoor(WindowFactory.Type type, float left_margin, float top_margin, float d_width, ColorPalette color) {
		float d_height;
		Window door;
		if (base != null) {
			d_height = main.getHeight() + base.getHeight() - top_margin;
			door = WindowFactory.createWindow(type, main.getMinX() + left_margin, main.getMinY() + top_margin, 
													d_width, d_height, layer_thickness, color);
			windows.add(door);
			door.makeHole(main);
			if (base instanceof Wall) {
				float left_bound = ((Wall) base).getMinX() + left_margin;
				float right_bound = left_bound + d_width;
				((Wall) base).removeSection(left_bound, right_bound);
			}
		} else {
			d_height = main.getHeight() - top_margin;
			door = WindowFactory.createWindow(type, main.getMinX() + left_margin, main.getMinY() + top_margin, 
					d_width, d_height, layer_thickness, color);
			windows.add(door);
			door.makeHole(main);
		}
		
		return door;
	}
	
	public void addRailing(float r_height, float rail_width, float tb_rail_height, float in_between, ColorPalette color) {

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
			float new_y = main.getMaxY() - r_height;
			shortenMainWall(new_y);
			base = new Railing(main.getMinX(), main.getMaxY() + Settings.GAP, main.getWidth(), r_height, 
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
