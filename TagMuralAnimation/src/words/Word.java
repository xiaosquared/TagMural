package words;

import processing.core.PApplet;

public class Word {
	PApplet parent;
	
	String text;
	float width_height_ratio;
	float default_font_size = 20;
	
	public Word(String text, PApplet parent) {
		this.text = text;
		this.parent = parent;
		
		parent.textSize(default_font_size);
		width_height_ratio = parent.textWidth(text)/default_font_size;
	}
	
	public float getRatio() {
		return width_height_ratio;
	}
	
	public String getText() {
		return text;
	}
	
	public void draw(float x, float y, float font_size) {
		parent.textSize(font_size);
		parent.text(text, x, y);
	}
	
	public void draw(float x, float y, float font_size, boolean isVertical) {
		parent.textSize(font_size);;
		if (isVertical) {
			parent.pushMatrix();
			parent.translate(x+font_size*2, y);
			parent.rotate(PApplet.HALF_PI);
			parent.text(text, 0, 0);
			parent.popMatrix();
		} else
			draw(x, y, font_size);
	}
}


