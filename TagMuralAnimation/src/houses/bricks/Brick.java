package houses.bricks;

import processing.core.PApplet;
import words.Word;

public class Brick extends Rectangle{
	protected Word word;
	protected Color color;
	
	public Brick (float x, float y, float width, float height, Word word) {
		super(x, y, width, height);
		this.word = word;
	}
	
	public float getLowerBound() {
		return getMinX();
	}
	
	public float getUpperBound() {
		return getMinX() + width;
	}
	
	public void setColor(boolean bw_mode, PApplet parent) {
		if (bw_mode) 
			color.fillBW(parent); 
		else
			color.fill(parent);
	}
	
	public void draw(boolean draw_border, boolean bw_mode, PApplet parent) {
		setColor(bw_mode, parent);
		
		if (draw_border) {
			super.draw(parent);
		}
		word.draw(getMinX(), getMinY(), height);
	}
	
	// TODO: Grow height function?
	void growHeight(float new_height) {
		height = new_height;
		tl.y = br.y - new_height;
		tr.y = tl.y;
	}
}
