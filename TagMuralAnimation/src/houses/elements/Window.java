package houses.elements;

import processing.core.PApplet;

public interface Window {
	
	public float getMinX();
	public float getMaxX();
	public float getMinY();
	public float getMaxY();
	public float getWidth();
	public float getHeight();
	
	public void makeHole(Wall wall);
	public void fillAll(PApplet parent);
	public void draw(boolean outline, boolean layer, boolean words, PApplet parent);
	public void reset();
}
