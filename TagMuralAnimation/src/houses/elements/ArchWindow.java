package houses.elements;

import global.ColorPalette;
import global.Settings;
import houses.bricks.Layer;
import processing.core.PApplet;

public class ArchWindow extends Wall implements Window, Fillable {
	
	public ArchWindow(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		super(x, y, width, height, layer_thickness, color);
		
		layers.clear();
		
		float circle_radius = width/2;
		float circle_center_y = y + circle_radius;
		float circle_center_x = x + width/2;
		
		float my_y = y + height;
		boolean entered_circle = false;
		
		while (my_y >= y) {

			// rectangular part
			if (!entered_circle) {
				Layer l = new Layer(getMinX(), getMaxX(), my_y, layer_thickness);
				layers.add(l);
			}
			
			// circular part
			else {
				float val = PApplet.sqrt(PApplet.sq(circle_radius) - PApplet.sq(my_y - circle_center_y));
				float lower_bound = circle_center_x - val;
				float upper_bound = circle_center_x + val;
				if (upper_bound - lower_bound > 5) {
					Layer l = new Layer(lower_bound, upper_bound, my_y, layer_thickness);
					layers.add(l);
				}
			}
			
			// update my_y
			my_y -= layer_thickness;
			if (my_y < circle_center_y)
				entered_circle = true;
		}
	}
	
	public void makeHole(Wall wall) {
		makeHole(wall, Settings.GAP);
	}
	
	// window must be aligned with the bottom of the wall
	public void makeHole(Wall wall, float gap) {
		
		int index = 0;
		Layer current_window_layer;
		Layer current_wall_layer = wall.layers.get(index);
		while(current_wall_layer.getPosition() > getMaxY()) {
		      index++;
		      current_wall_layer = wall.getLayers().get(index);
		}
		
		int num_gap_layers = PApplet.ceil(gap/layer_thickness);
		
		// bottom gap
		current_window_layer = layers.get(0);
		for (int i = 0; i <= num_gap_layers * 1.5f; i++) {
			if (index - i < 0)
				break;
			current_wall_layer = wall.getLayers().get(index - i);
			if (current_wall_layer != null) {
				current_wall_layer.makeHole(getMinX(), getMaxX(), gap); 
			}
		}
	    
		// top gap
		for (int i = layers.size()-1; i > num_gap_layers; i--) {
			current_window_layer = layers.get(i);
			if (index + num_gap_layers + i >= wall.getLayers().size())
				break;
			current_wall_layer = wall.getLayers().get(PApplet.ceil(index + num_gap_layers * 1.2f + i));
			if (current_wall_layer != null) 
				current_wall_layer.makeHole(current_window_layer.getLowerBound(), current_window_layer.getUpperBound(), gap);
		}
		
		// main area of the window
		for (int i = 0; i < getLayers().size(); i++) {
			current_window_layer = layers.get(i);
			current_wall_layer = wall.layers.get(index + i);
			current_wall_layer.makeHole(current_window_layer.getLowerBound(), current_window_layer.getUpperBound(), gap);
		}
	}
}
