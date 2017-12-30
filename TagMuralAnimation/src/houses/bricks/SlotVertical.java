package houses.bricks;

import processing.core.PApplet;
import processing.core.PGraphics;

public class SlotVertical extends Slot {
	public SlotVertical(float left, float right) {
		super(left, right);
	}
	
	public void draw(float x, float thickness, PApplet parent) { parent.rect(x, getLeft(), thickness, getDistance()); }
	public void draw(float x, float thickness, PGraphics parent) { parent.rect(x, getLeft(), thickness, getDistance()); }
}
