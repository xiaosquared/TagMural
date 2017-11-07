package water;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PVector;

public class Splash {
	PApplet parent;
	
	ArrayList<SplashParticle> droplets;
	int pps = 8;  // particles per splash
	float splash_speed = 4.5f;

	public Splash(PApplet parent) {
		this.parent = parent;
		droplets = new ArrayList<SplashParticle>();
	}

	public void run(boolean letters) {
		Iterator<SplashParticle> it = droplets.iterator();
		while (it.hasNext()) {
			SplashParticle p = it.next();
			p.update();
			p.draw(letters);
			if (p.isDead()) {
				it.remove();
			}
		}
	}

	public void createSplash(float x, float y) {
		for (int i = 0; i < pps; i ++) {
			float radians = parent.random(0, PApplet.PI);
			PVector v = new PVector(splash_speed* PApplet.cos(radians)/2 + parent.random(-1, 1),
					-splash_speed * PApplet.sin(radians) + parent.random(-1, 1));
			PVector p = new PVector(x + parent.random(-5, 5), y + parent.random(-12, 12));
			PVector a = new PVector(0, .3f);

			char c = (char) (int) (parent.random(97, 122));
			droplets.add(new SplashParticle(p, v, a, c, parent));
		}
	}
}
