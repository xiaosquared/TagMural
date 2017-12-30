package houses.bricks;

import global.Settings;
import processing.core.PApplet;
import processing.core.PGraphics;
import words.Word;

public class BrickVertical extends Brick {
	public BrickVertical(float x, float y, float width, float height, Word word) {
		super(x, y, width, height, word);
	}
	
	public float getLowerBound() {
		return getMinY();
	}
	
	public float getUpperBound() {
		return getMinY() + height;
	}
	
	public void draw(PApplet parent) {
		if (!isVisible) 
			return;
		
		setFill(parent);
		
		if (Settings.draw_brick_border) {
			parent.pushMatrix();
			parent.translate(getMinX() + width, super.getMinY());
			parent.rect(0, 0, width, height);
			parent.rotate(PApplet.HALF_PI);
			parent.popMatrix();
		}
		word.draw(getMinX(), getMinY(), width, true, parent);
	}
	
	public void draw(PGraphics parent) {
		if (!isVisible) 
			return;
		
		setFill(parent);
		
		if (Settings.draw_brick_border) {
			parent.pushMatrix();
			parent.translate(getMinX() + width, super.getMinY());
			parent.rect(0, 0, width, height);
			parent.rotate(PApplet.HALF_PI);
			parent.popMatrix();
		}
		word.draw(getMinX(), getMinY(), width, true, parent);
	}
}