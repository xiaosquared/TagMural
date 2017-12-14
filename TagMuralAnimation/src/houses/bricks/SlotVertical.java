package houses.bricks;

import processing.core.PApplet;

public class SlotVertical extends Slot {
	public SlotVertical(float left, float right) {
		super(left, right);
	}
	
	public void draw(float x, float thickness, PApplet parent) {
		parent.rect(x, getLeft(), thickness, getDistance());
	}
}
