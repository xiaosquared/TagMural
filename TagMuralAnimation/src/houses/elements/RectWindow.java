package houses.elements;

import houses.bricks.Layer;
import processing.core.PApplet;

public class RectWindow extends Wall implements Window {

	private float gap = 5;
	
	public RectWindow(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		super(x, y, width, height, layer_thickness, color);
	}
	
	public void makeHole(Wall wall) {
		int top_index = 0;
		int bottom_index = wall.getLayers().size();
		
		// main hole
		for (int i = 0; i < wall.getLayers().size(); i++) {
			Layer l = wall.getLayers().get(i);
			if (l.getPosition() > getMinY() && l.getPosition() < getMaxY()) {
				bottom_index = PApplet.min(bottom_index, i);
				top_index = PApplet.max(top_index, i);
				
				l.makeHole(getMinX(), getMaxX(), gap);
			}
		}
		
		// top gaps
		int num_gap_layers = PApplet.ceil(gap * 1.5f/wall.getLayerThickness());
		for (int i = 0; i < num_gap_layers; i++) {
			if (top_index + i >= wall.getLayers().size()) 
				break;
			Layer l = wall.getLayers().get(top_index + i);
			l.makeHole(getMinX(), getMaxX(), gap);
		}
		
		// bottom gap
		num_gap_layers = PApplet.ceil(gap * 1.5f/wall.getLayerThickness());
		for (int i = 0; i < num_gap_layers; i++) {
			if (bottom_index - i < 0 || bottom_index - i >= wall.getLayers().size())
				break;
			Layer l = wall.getLayers().get(bottom_index - i);
			l.makeHole(getMinX(), getMaxX(), gap);
		}
	}
}
