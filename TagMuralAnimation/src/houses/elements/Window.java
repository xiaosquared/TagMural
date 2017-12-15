package houses.elements;

import processing.core.PApplet;

public interface Window {
	
	public static float GAP = 5;
	
	public void makeHole(Wall wall);
	public void fillAll(PApplet parent);
	public void draw(boolean outline, boolean layer, boolean words, PApplet parent);
}
