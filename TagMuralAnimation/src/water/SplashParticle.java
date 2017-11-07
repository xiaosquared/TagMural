package water;

import processing.core.PApplet;
import processing.core.PVector;

public class SplashParticle extends Particle {
	PApplet parent;
	
	float lifespan = 20;
	char letter;
	int font_size = 9;

	public SplashParticle(PVector p, PVector v, PVector a, char l, PApplet parent) {
		super(p, v, a, parent);
		this.parent = parent;
		letter = l;
	}

	public void update() {
		lifespan --;
		super.update();
	}

	public void draw(boolean letters) {
		if (letters) {
			float theta = PApplet.map(pos.x, 0, parent.width, 0, PApplet.TWO_PI*2);
			parent.fill(200, PApplet.min(255, lifespan*30));
			parent.textSize(font_size);
			parent.pushMatrix();
			parent.translate(pos.x, pos.y);
			parent.rotate(theta);
			parent.text(letter, 0, 0);
			parent.popMatrix(); 
		} else {
			parent.noStroke();
			parent.fill(200, PApplet.min(255, lifespan*30));
			parent.ellipse(pos.x, pos.y, 2, 2);
		}
	}

	public boolean isDead() {
		return lifespan < 0;
	}
}
