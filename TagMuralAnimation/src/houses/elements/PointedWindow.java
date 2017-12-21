package houses.elements;

import global.ColorPalette;
import global.Settings;
import houses.bricks.Layer;
import processing.core.PApplet;

public class PointedWindow extends Wall implements Window, Fillable {
	private static float DEFAULT_ANGLE = 55;
	
	public PointedWindow(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		this(x, y, width, height, layer_thickness, color, DEFAULT_ANGLE);
	}
	
	public PointedWindow(float x, float y, float width, float height, float layer_thickness, ColorPalette color, float angle) {
		super(x, y, width, height, layer_thickness, color);
		
		float tan_angle = PApplet.tan(PApplet.radians(angle));
		float triangle_height = width/2 / tan_angle;
		
		for (int i = layers.size() - 1; i >= 0; i--) {
			Layer l = layers.get(i);
			
			if (l.getPosition() > y + triangle_height)
				break;
			float adjacent = triangle_height - (l.getPosition() - y);
			float opposite = tan_angle * adjacent;
			
			l.extendLower(-opposite);
			l.extendUpper(-opposite);
		}
	}
	
	public void makeHole(Wall wall) {
		makeHole(wall, Settings.GAP);
	}
	
	public void makeHole(Wall wall, float gap) {
		Layer current_window_layer;
		Layer current_wall_layer;
		int i;
		for (i = 0; i < layers.size(); i++) {
			current_window_layer = layers.get(i);
			current_wall_layer = wall.getLayers().get(i);
			
			current_wall_layer.makeHole(current_window_layer.getLowerBound(), current_window_layer.getUpperBound(), gap);
		}
		
		int num_gap_layers = PApplet.floor(gap / layer_thickness);
		for (i = layers.size()-1; i > num_gap_layers; i--) {
			current_window_layer = layers.get(i);
			if (num_gap_layers + i >= wall.getLayers().size())
				break;
			current_wall_layer = wall.getLayers().get(PApplet.ceil(num_gap_layers * 1.2f + i));
			if (current_wall_layer != null) 
				current_wall_layer.makeHole(current_window_layer.getLowerBound(), current_window_layer.getUpperBound(), gap);
		}
	}
}
