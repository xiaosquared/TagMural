package water;

import java.util.ArrayList;
import java.util.Iterator;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PVector;

/*
 * A wave is made of springs
 * 
 */

public class Wave {
	PApplet parent;
	
	WaveParticle[] springs;
	float[] leftDeltas;
	float[] rightDeltas;

	float radius;
	float target_height;
	float spread = 0.48f;

	float amplitude = 0;
	float freq_factor = 10;
	int end;  // which end ambient traveling waves start from

	ArrayList<WaveText> texts;
	float font_size = 0;
	int shade = 200;

	public Wave(PVector startPos, PVector endPos, float particle_width, int shade, PApplet parent) {
		this.parent = parent;
		
		this.shade = shade;
		this.radius = particle_width/2;
		target_height = startPos.y;

		int n = (int) ((endPos.x - startPos.x) / particle_width);
		springs = new WaveParticle[n];
		for (int i = 0; i < n; i++) {
			float x = startPos.x + particle_width * i;
			float y = startPos.y;
			springs[i] = new WaveParticle(new PVector(x, y), parent);
		}

		leftDeltas = new float[n];
		rightDeltas = new float[n];
	}

	public void initText(String[] words, float fs, int start_spring, int end_spring) {
		font_size = fs;
		parent.textSize(font_size);
		texts = new ArrayList<WaveText>();

		int selected_spring = start_spring;
		while(selected_spring < end_spring) {
			int w_index = PApplet.floor(parent.random(words.length));
			String word= words[w_index];
			WaveText wt = new WaveText(word, fs, springs[selected_spring].pos.x, target_height, parent);
			float word_width = parent.textWidth(word);
			selected_spring += (int) (word_width/(radius*2));
			if (selected_spring < springs.length)
				texts.add(wt);
			selected_spring +=2;
		}
	}

	public int getNumParticles() {
		return springs.length;
	}
	
	// returns spring closest to x position
	public WaveParticle getSelectedParticle(int x) {
		return springs[PApplet.floor((x-springs[0].pos.x) / (radius * 2))];
	}

	public int getSelectedParticleIndex(int x) {
		return PApplet.floor((x-springs[0].pos.x) / (radius * 2));
	}

	public void startWaving() {
		float max_amp = parent.random(40, 80);
		float stillness = parent.random(3, 8);
		float ramp_up = parent.random(2, 5);
		end = (parent.frameRate%2 ==0) ? 0 : springs.length - 1; // which side of the wave to start on?
		Ani.to(this, ramp_up, stillness, "amplitude", max_amp, Ani.SINE_IN_OUT, "onEnd:endWaving");
	}

	public void endWaving() {
		float wave_duration = parent.random(1, 3);
		float ramp_down = parent.random(2, 5);
		Ani.to(this, ramp_down, wave_duration, "amplitude", 0, Ani.SINE_IN_OUT, "onEnd:startWaving");
	}


	public void perturbRegion(int left, int right, float y, Splash sp) {
		left = PApplet.max(0, left);
		right = PApplet.min(springs.length - 1, right);
		for (int i = left; i < right; i++) {
			springs[i].pos.y = y;
		}
		sp.createSplash(springs[left].pos.x, target_height);
		sp.createSplash(springs[right].pos.x, target_height);
		sp.createSplash(springs[(int)(left + (right-left)/2)].pos.x, target_height);
	}

	public void update() {
		// deal with traveling wave
		if (amplitude != 0) {
			float theta = (float) parent.frameCount/freq_factor;
			springs[end].pos.y = amplitude*PApplet.sin(theta) + target_height;
		}

		// perturbation
		if (parent.random(100+amplitude*100) < 1) { 
			springs[PApplet.floor(parent.random(springs.length))].perturb();
		}

		// first update each spring
		for (int i = 0; i < springs.length; i++) {
			springs[i].update();
		}

		// then do some passes where springs pull on their neighbors
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < springs.length; i++) {
				if (i > 0) {
					leftDeltas[i] = spread * (springs[i].getSpringHeight() - springs[i-1].getSpringHeight());
					springs[i-1].vel.y += leftDeltas[i];
				}
				if (i < springs.length - 1) {
					rightDeltas[i] = spread * (springs[i].getSpringHeight() - springs[i+1].getSpringHeight());
					springs[i+1].vel.y += rightDeltas[i];
				}
			}
			for (int i = 0; i < springs.length; i++) {
				if (i > 0) 
					springs[i-1].pos.y += leftDeltas[i];
				if (i < springs.length - 1)
					springs[i+1].pos.y += rightDeltas[i];
			}
		}
	}

	public void draw(boolean letters) {
		parent.fill(shade);  
		if (letters) {
			if (texts == null) {
				draw(false);
				return;
			} 
			drawTexts();

		} else {
			parent.stroke(shade);
			for (int i = 0; i < springs.length; i++) {
				parent.ellipse(springs[i].pos.x, springs[i].pos.y, radius, radius);
			}
		}
	}

	public void drawTexts() {
		Iterator<WaveText> it = texts.iterator();
		while(it.hasNext()) {
			WaveText wt = it.next();
			wt.draw(springs, radius*2);
		}
	}
}
