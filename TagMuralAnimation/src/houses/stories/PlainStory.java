package houses.stories;

import java.util.ArrayList;

import houses.elements.ColorPalette;
import houses.elements.Fillable;
import houses.elements.Wall;
import houses.elements.Window;

public class PlainStory {
	private Wall main;
	private Fillable base;
	private ArrayList<Window> windows;
	
	private float width;
	private float height;
	private float layer_thickness;
	private int layer_index;
	
	public PlainStory(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		main = new Wall(x, y, width, height, layer_thickness, color);
		windows = new ArrayList<Window>();
		this.layer_thickness = layer_thickness;
		this.width = width;
		this.height = height;
	}

	/**
	 * Helper method to make main shorter. y is new bottom edge
	 * @param y
	 */
	private void shortenWall(float y) {
		float new_height_main = y - main.getMinY();
		main = new Wall(main.getMinX(), main.getMinY(), main.getWidth(), 
						new_height_main, layer_thickness, main.getColor());
	}
	
	// TODO: Get & Set Methods
	
	public void reset() {
		main.reset();
		if (base != null)
			base.reset();
		for (Window win : windows) {
			win.reset();
			win.makeHole(main);
		}
	}
}
