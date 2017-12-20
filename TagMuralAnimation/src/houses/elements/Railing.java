package houses.elements;

import java.util.ArrayList;

import global.ColorPalette;
import houses.bricks.Rectangle;
import processing.core.PApplet;

public class Railing implements Fillable {
	private Rectangle bounding_box;
	private ArrayList<Column> rails;
	private Wall top_rail;
	private Wall bottom_rail;
	private int current_rail = 0;
	
	public Railing(float x, float y, float width, float height, 
					float rail_width, float tb_rail_height, float in_between,
					float layer_thickness, ColorPalette color) {
		
		bounding_box = new Rectangle(x, y, width, height);
		
		// find how many rails, round down
		float err = rail_width % layer_thickness;
		int num_rails = PApplet.floor((width + err - rail_width) / (rail_width + in_between)) + 1;
		
		// adjust in between distance
		in_between = (width + err - (rail_width * num_rails)) / (num_rails - 1);
		
		rails = new ArrayList<Column>();
		for (int i = 0; i < num_rails; i++) {
			Column rail = new Column(x + i * (rail_width + in_between),
							y + tb_rail_height, rail_width, height-2 * tb_rail_height, layer_thickness, color);
			rails.add(rail);
		}
		
		top_rail = new Wall(x, y, width, tb_rail_height, layer_thickness, color);
		bottom_rail = new Wall(x, y + height - tb_rail_height, width, tb_rail_height, layer_thickness, color);
		
	}
	
	public float getMinX() { return bounding_box.getMinX(); }
	public float getMinY() { return bounding_box.getMinY(); }
	public float getMaxX() { return bounding_box.getMaxX(); }
	public float getMaxY() { return bounding_box.getMaxY(); }
	public float getWidth() { return bounding_box.getWidth(); }
	public float getHeight() { return bounding_box.getHeight(); }
	
	public void reset() {
		for (Column rail : rails)
			rail.reset();
		top_rail.reset();
		bottom_rail.reset();
		current_rail = 0;
	}
	
	public void fillAll(PApplet parent) {
		bottom_rail.fillAll(parent);
		for (Column rail : rails) {
			rail.fillAll(parent);
		}
		top_rail.fillAll(parent);;
	}
	
	public void fillByLayer(PApplet parent) {
		if (!bottom_rail.isFilled()) 
			bottom_rail.fillByLayer(parent);
		else if (verticalFilled() && !top_rail.isFilled())
			top_rail.fillByLayer(parent);
		else {
			Column my_rail = rails.get(current_rail);
			if (!my_rail.isFilled())
				my_rail.fillByLayer(parent);
			else if (current_rail < rails.size())
				current_rail++;
		}
	}
	
	public boolean isFilled() {
		return top_rail.isFilled();
	}
	
	/**
	 * @return true if the vertical rails are all filled with words
	 */
	private boolean verticalFilled() {
		return rails.get(rails.size()-1).isFilled();
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		for (Column rail : rails)
			rail.draw(outline, layers, words, parent);
		top_rail.draw(outline, layers, words, parent);
		bottom_rail.draw(outline, layers, words, parent);
	}
}
