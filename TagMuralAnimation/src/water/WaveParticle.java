package water;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PVector;

public class WaveParticle extends Particle {
	float k = 0.0025f;
	float target_height;
	float damping = 0.03f;
	
	public WaveParticle(PVector pos, PApplet parent) {
		super(pos, parent);
		target_height = pos.y;
	}
	
	public void update() {
		float dist_y = pos.y - target_height;
	    acc.y = (-k * dist_y) - (damping * vel.y);
	    super.update();
	}
	
	void perturb() {
		Ani.to(this.pos, 0.1f, "y", parent.random(target_height - 50, target_height + 50));
	}
	
	void perturbRain() {
		Ani.to(this.pos, 0.1f, "y", parent.random(target_height + 10, target_height + 40));
	}
	
	public float getSpringHeight() {
		return pos.y;
	}
	
	float getHeightDiff() {
		return pos.y - target_height;
	}
}
