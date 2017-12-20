package houses.stories;

import global.ColorPalette;
import houses.bricks.Layer;
import houses.elements.Fillable;
import houses.elements.Wall;
import houses.elements.Window;
import houses.elements.WindowFactory;
import processing.core.PApplet;

public class RoofStory extends PlainStory implements Story {
	
	float angle;
	float x_trap; // top left corner "x" of trapezoid. 
	float width_top;
	
	boolean left_incline = true;
	boolean right_incline = true;
	
	/**
	 * @param angle - from vertical 16 degrees gives a pretty nice incline
	 */
	public RoofStory(float x, float y, float width, float height, float angle, float layer_thickness, ColorPalette color) {
		super(x, y, width, height, layer_thickness, color);
		createIncline(main, x, y, angle);
	}
	
	public RoofStory(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		super(x, y, width, height, layer_thickness, color);
		
		angle = PApplet.atan(width / 2 / height);
		createIncline(main, x, y, angle);
	}
	
	private void createIncline(Wall wall, float x, float y, float angle) {
		createInclineSides(wall, x, y, angle, true, true);
	}
	
	private void createInclineSides(Wall wall, float x, float y, float angle, boolean left, boolean right) {
		this.angle = angle;
		float tan = PApplet.tan(angle);
		float shorter = getHeight() * tan;
		width_top = getWidth() - shorter * 2;
		x_trap = getMinX() + shorter;
		
		for (Layer l : wall.getLayers()) {
			shorter = (y + getHeight() - l.getPosition()) * tan;
			if (left)
				l.extendLower(-shorter);
			if (right)
				l.extendUpper(-shorter);
		}
	}
	
	public Window addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color) {
		Window w = super.addWindow(type, left_margin, top_margin, w_width, w_height, color);
		
		createInclineSides(main, main.getMinX(), main.getMinY(), angle, left_incline, right_incline);
		if (base != null)
			createInclineSides((Wall)base, main.getMinX(), main.getMinY(), angle, left_incline, right_incline);
		
		w.makeHole(main);
		return w;
	}
	
	public void addChimney(float where, float width, float height) {
		Layer top_layer = main.getLayers().get(main.getLayers().size()-1);
		int num_layers = PApplet.floor(height / layer_thickness);
		
		if (top_layer.getLength() < width) {
			float diff = width - top_layer.getLength();
			top_layer.extendLower(-diff/2);
			top_layer.extendLower(diff/2);
			
			for (int i = 0; i < num_layers; i++) {
				Layer l = new Layer(top_layer.getLowerBound(), top_layer.getUpperBound(), 
									top_layer.getPosition() - i * layer_thickness, top_layer.getThickness());
				main.getLayers().add(l);
			}
		} else {
			float lower_bound = PApplet.lerp(top_layer.getLowerBound(), top_layer.getUpperBound() - width, where);
			for (int i = 1; i <= num_layers; i++) {
				Layer l = new Layer(lower_bound, lower_bound + width, top_layer.getPosition() - i * layer_thickness, top_layer.getThickness());
				main.getLayers().add(l);
			}
		}
	}
	
	private void drawOutline(PApplet parent) {
		parent.stroke(255);
		Fillable bw;
		bw = (base == null) ? main : base;
		
		parent.line(x_trap, main.getMinY(), x_trap + width_top, main.getMinY());
		parent.line(bw.getMinX(), bw.getMaxY(), bw.getMaxX(), bw.getMaxY());
		parent.line(x_trap, main.getMinY(), bw.getMinX(), bw.getMaxY());
		parent.line(x_trap+width_top, main.getMinY(), bw.getMaxX(), bw.getMaxY());
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		main.draw(false,  layers,  words, parent);
		if (outline)
			drawOutline(parent);
		
		if (base != null) 
			base.draw(false, layers, words, parent);
			
		for (Window win : windows)
			win.draw(outline, layers, words, parent);
	}
	
}
