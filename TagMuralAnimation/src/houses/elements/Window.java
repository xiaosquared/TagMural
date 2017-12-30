package houses.elements;

import java.util.ArrayList;

import houses.bricks.Brick;
import processing.core.PApplet;
import processing.core.PGraphics;

public interface Window {
	
	public float getMinX();
	public float getMaxX();
	public float getMinY();
	public float getMaxY();
	public float getWidth();
	public float getHeight();
	public ArrayList<Brick> getBricks();
	
	public void makeHole(Wall wall);
	public void fillAll(PApplet parent);
	public void fillByLayer(PApplet parent);
	public void fillAll(PApplet parent, boolean visibility);
	public void fillByLayer(PApplet parent, boolean visibility);
	public boolean isFilled();
	public void unfill();
	public void draw(boolean outline, boolean layer, boolean words, PApplet parent);
	public void draw(boolean outline, boolean layer, boolean words, PGraphics parent);
	public void reset();
}
