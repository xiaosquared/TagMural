package water;

import processing.core.PApplet;
import processing.core.PVector;

public class WaveGroup {
	PApplet parent;
	
	Wave[] waves;

	public WaveGroup(int n, PVector tl, PVector br, float particle_width, int sShade, int eShade, PApplet parent) {
		this.parent = parent;
		
		waves = new Wave[n];

		float start_x = tl.x;
		float end_x = br.x;

		float start_y = tl.y;
		float total_y = br.y - tl.y;
		float spacing_y = (total_y * 0.8f)/n;

		int subdiv = 0;
		for (int i = 1; i < n; i++) {
			subdiv += i;
		}
		float offset = 0;
		float offset_inc = (total_y * .2f)/subdiv;

		for (int i = 0; i < n; i++) {
			float y = start_y + spacing_y*i + offset*i;
			int shade = sShade + (eShade - sShade)/n * i;
			Wave w = new Wave(new PVector(start_x, y), new PVector (end_x + i*20, y), particle_width, shade, parent);
			offset+=offset_inc;
			waves[i] = w;

			w.startWaving();
		}
	}

	public void initText(String[] words, float small_font_size, float large_font_size) {
		for (int i = 0; i < waves.length; i++) {
			float fs = (large_font_size - small_font_size)/waves.length*i + small_font_size;
			waves[i].initText(words, fs, 0, waves[i].springs.length);
		}
	}

	public void update() {
		for (int i = 0; i < waves.length; i++) {
			waves[i].update();
		}
	}

	public void draw(boolean letters) {
		for (int i = 0; i < waves.length; i++) {
			waves[i].draw(letters);
		}
	} 

	public Wave getWave(int i) {
		return waves[i];
	}
}
