package water;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PVector;

public class Rain {
	PApplet parent;
	
	ArrayList<RainParticle> rain;
	Wave target_wave;
	int n = 8;
	
	public Rain(String[] words, int n, Wave target_wave, PApplet parent) {
		this.parent = parent;
		
		this.target_wave = target_wave;
		this.n = n;
		rain = new ArrayList<RainParticle>();
		for (int i = 0; i < n; i++) {
			int w_index = PApplet.floor(parent.random(words.length));
			String word = words[w_index];

			RainParticle r = new RainParticle(new PVector(parent.random(4, parent.width-4), -i*150),
								new PVector(0, 0),
								new PVector(0, 0), word, parent);
			rain.add(r);                          
		}
	}

	void run(Splash sp, boolean letters, boolean raining) {
		Iterator<RainParticle> it = rain.iterator();
		while(it.hasNext()) {
			RainParticle r = it.next();

			r.update();
			r.draw(letters);

			if (r.pos.y > target_wave.target_height) {
				WaveParticle s = target_wave.getSelectedParticle((int)r.pos.x);
				r.respawn(!raining);
				s.perturbRain();
				sp.createSplash(s.pos.x, s.pos.y);
			}
		}    
	}

	void restart() {
		for (int i = 0; i < n; i++) {
			RainParticle r = rain.get(i);
			if (r.pos.y <= 0) {
				r.pos.y -= i*150;
				r.vel.y = 10;
				r.acc.y = Particle.g.y;
			}
		}
	}
}
