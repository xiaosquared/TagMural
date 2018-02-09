package music.clef;

import processing.core.PApplet;
import processing.core.PVector;

public class BezierLetter {
	char letter;
	float size;
	boolean isVisible = true;

	PVector position;
	float angle;

	public BezierLetter(char letter, float size, float x, float y, float angle, boolean isVisible) {
		this.letter = letter;
		this.size = size;
		position = new PVector(x, y);
		this.angle = angle;
		this.isVisible = isVisible;
	}

	public BezierLetter(char letter, float size, float x, float y, float angle) {
		this(letter, size, x, y, angle, true);
	}
	public void setVisibility(boolean b) { isVisible = b; }
	public boolean isVisible() { return isVisible; }

	public void draw(PApplet parent) {
		if (!isVisible)
			return;

		parent.textSize(size);
		parent.pushMatrix();
		parent.translate(position.x, position.y + size);
		parent.rotate(angle);
		parent.text(letter, 0, 0);
		parent.popMatrix();
	}
}