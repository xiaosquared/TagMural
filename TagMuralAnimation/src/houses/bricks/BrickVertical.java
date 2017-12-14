package houses.bricks;

import processing.core.PApplet;
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
	
	public void draw(boolean draw_border, boolean bw_mode, PApplet parent) {
		setFill(bw_mode, parent);
		
		if (draw_border) {
			parent.pushMatrix();
			parent.translate(getMinX() + width, super.getMinY());
			parent.rect(0, 0, width, height);
			parent.rotate(PApplet.HALF_PI);
			parent.popMatrix();
		}
		word.draw(getMinX(), getMinY(), width, true);
	}
}