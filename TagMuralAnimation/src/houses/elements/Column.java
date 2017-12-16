package houses.elements;

import java.util.ArrayList;

import houses.bricks.Layer;
import processing.core.PApplet;

public class Column extends Wall {
	public Column(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		super(x, y, width, height, color);
		
		this.layer_thickness = layer_thickness;
		layers = new ArrayList<Layer>();
		int num_layers = PApplet.floor(width/layer_thickness);
		for (int i = 0; i < num_layers; i++) {
			float layer_x = x + i * layer_thickness;
			layers.add(new Layer(y, y+height, layer_x, layer_thickness, true));
		}
	}
}
