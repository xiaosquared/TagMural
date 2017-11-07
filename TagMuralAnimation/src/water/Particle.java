package water;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
	protected PApplet parent;
	
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	
	public static PVector g = new PVector(0, 0.1f); // gravity constant	
		
	public Particle(PVector pos, PApplet p) {
		this.pos = pos;
		this.vel = new PVector(0, 0);
		this.acc = new PVector(0, 0);
		parent = p;
	}

	public Particle (PVector pos, PVector vel, PApplet p) {
		this.pos = pos;
		this.vel = vel;
		this.acc = new PVector(0, 0);
		parent = p;
	}

	public Particle (PVector pos, PVector vel, PVector acc, PApplet p) {
		this.pos = pos;
		this.vel = vel;
		this.acc = acc;
		parent = p;
	}
	
	public void run() {
		update();
		draw();
	}

	public void update() {
		vel.add(acc);
		pos.add(vel);
	}

	public void draw() {
		parent.fill(255);
		parent.noStroke();
		parent.ellipse(pos.x, pos.y, 5, 5);
	}


	public boolean isDead() {
		return pos.x < 0 || pos.x > parent.width || pos.y < 0 || pos.y > parent.height;
	}

	public void respawn() {}

}
