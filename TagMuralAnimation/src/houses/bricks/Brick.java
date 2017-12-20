package houses.bricks;

import global.Color;
import global.Settings;
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
	
	public void setColor(Color c ) { this.color = c; }
	
	public void setFill(PApplet parent) {
		if (Settings.bw_mode) 
			color.fillBW(parent); 
		else
			color.fill(parent);
	}
	
	public void draw(PApplet parent) {
		setFill(parent);
		
		if (Settings.draw_brick_border) {
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
