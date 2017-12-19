package houses.elements;

import processing.core.PApplet;

public interface Fillable {
	
	public float getWidth();
	public float getHeight();
	
	public boolean isFilled();
	public void fillAll(PApplet parent);
	public void fillByLayer(PApplet parent);
	public void reset();
	public void draw(boolean outline, boolean layer, boolean words, PApplet parent);
	
	// TODO: unfill?
}
