package particles;

import processing.core.PApplet;
import processing.core.PVector;

public class RainDrop extends Particle{
	
	String word;
	int font_size = 8;
	float word_width;
	int shade;
	
	public RainDrop(PVector pos, PVector vel, PVector acc, String w, PApplet parent) {
		super(pos, vel, acc, parent);
		shade = (int) parent.random(70, 170);
		word = w;
		parent.textSize(font_size);
		word_width = parent.textWidth(w);
	}
	
	public void respawn(boolean stop) {
		pos.x = parent.random(4, parent.width-4);
		pos.y = -parent.random(50); 
		vel.y = stop? 0 : 10;
		acc.y = stop? 0 : 0.1f; 
	}
	
	/* 
	 * Draws as small dot if letter = false. Draws word if letters = true 
	 */
	public void draw (boolean letters) {
		if (letters) {
			parent.fill(shade);;
			parent.textSize(font_size);
			parent.noStroke();
			
			parent.pushMatrix();
			parent.translate(pos.x, pos.y);
			parent.rotate(-PApplet.HALF_PI);
			parent.text(word, 0, 0);
			parent.popMatrix();
		} else {
			parent.fill(200);
			parent.stroke(200);
			parent.ellipse(pos.x, pos.y, 2, 2);
		}
	}
}
