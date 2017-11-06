package particles;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	
	protected PApplet parent;
	
	public Particle(PVector pos, PApplet p) {
		this.pos = pos;
		this.vel = new PVector(0, 10);
		this.acc = new PVector(0, 2);
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

	/*
	 * if off the screen left, bottom, right - ok to be offscreen form top
	 */
	public boolean isDead() {
		return pos.x < 0 || pos.x > parent.width || pos.y > parent.height;
	}

	public void respawn() {}

}
