package houses.elements;

import java.util.ArrayList;

import houses.bricks.Brick;
import processing.core.PApplet;

public interface Fillable {
	
	public float getMinX();
	public float getMaxX();
	public float getMinY();
	public float getMaxY();
	public float getWidth();
	public float getHeight();
	public ArrayList<Brick> getBricks();
	
	public boolean isFilled();
	public void fillAll(PApplet parent);
	public void fillByLayer(PApplet parent);
	public void fillAll(PApplet parent, boolean visibility);
	public void fillByLayer(PApplet parent, boolean visibility);
	public void unfill();
	public void reset();
	public void draw(boolean outline, boolean layer, boolean words, PApplet parent);
	
	// TODO: unfill?
}
