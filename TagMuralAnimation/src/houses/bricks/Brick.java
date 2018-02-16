package houses.bricks;

import global.Color;
import global.ColorPalette;
import global.Settings;
import processing.core.PApplet;
import processing.core.PGraphics;
import words.Word;

public class Brick extends Rectangle{
	protected Word word;
	protected Color color = ColorPalette.YELLOW.getColor();
	boolean isVisible = true;
	
	public Brick (float x, float y, float width, float height, Word word) {
		super(x, y, width, height);
		this.word = word;
	}
	
	public void setVisibility(boolean visibility) { this.isVisible = visibility; }
	
	public boolean getVisibility() { return isVisible; }
	
	public String getText() {
		return word.getText();
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
	
	public void setFill(PGraphics parent) {
		if (Settings.bw_mode) 
			color.fillBW(parent); 
		else
			color.fill(parent);
	}
	
	public void setFill(PApplet parent, float opacity) {
		if (Settings.bw_mode)
			color.fillBW(parent, opacity);
		else
			color.fill(parent, opacity);
	}
	
	public void draw(PApplet parent, float opacity) {
		if (!isVisible) 
			return;
			
		setFill(parent, opacity);
		
		if (Settings.draw_brick_border) {
			super.draw(parent);
		}
		word.draw(getMinX(), getMinY(), height, parent);
	}
	
	public void draw(PApplet parent) {
		draw(parent, 255);
	}
	
	public void draw(PGraphics parent) {
		if (!isVisible) 
			return;
			
		setFill(parent);
		
		if (Settings.draw_brick_border) {
			super.draw(parent);
		}
		word.draw(getMinX(), getMinY(), height, parent);
	}
	
	// TODO: Grow height function?
	void growHeight(float new_height) {
		height = new_height;
		tl.y = br.y - new_height;
		tr.y = tl.y;
	}
}
