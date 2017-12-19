package houses.stories;

import java.util.ArrayList;

import houses.elements.ColorPalette;
import houses.elements.Fillable;
import houses.elements.Wall;
import houses.elements.Window;
import houses.elements.WindowFactory;
import processing.core.PApplet;

public class PlainStory {
	private Wall main;
	private Fillable base;
	private ArrayList<Window> windows;
	
	private float height;
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
	
	/**
	 * @param type
	 * @param left_margin: x of window relative to main.x 
	 * @param top_margin: y of window relative to main.y
	 */
	public void addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color) {
		Window w; 
		if (base != null) {
			w_height = main.getHeight() - top_margin;
		} else {
			float new_y = main.getMinY() + top_margin + w_height;
			createBaseWall(new_y);
			shortenMainWall(new_y);
		}
		w = WindowFactory.createWindow(type, main.getMinX() + left_margin, main.getMinY() + top_margin, w_width, w_height, layer_thickness, color);
		windows.add(w);
		w.makeHole(main);
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
