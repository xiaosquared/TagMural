package water;

import processing.core.PApplet;
import processing.core.PVector;

public class WaveText {
	PApplet parent;
	
	String text;
	int len;
	float font_size;

	PVector start_pos;
	PVector vel;

	public WaveText(String t, float fs, float x, float y, PApplet parent) {
		this.parent = parent;
		
		text = t;
		len = t.length();
		font_size = fs;

		start_pos = new PVector(x, y);
		vel = new PVector(2, 0);
	}

	public void update() {
		start_pos.add(vel);
	}

	public void draw(WaveParticle[] springs, float spring_width) {
		parent.textSize(font_size);

		float caret_x = 0;
		int selected_spring;

		for (int i = 0; i < len; i++) {
			char next_letter = text.charAt(i);
			float x = start_pos.x - springs[0].pos.x + caret_x;

			// find the spring associated with the letter
			selected_spring = (int) (x / spring_width);
			if (selected_spring >= springs.length || selected_spring < 0) {
				return;
			}

			float y = start_pos.y + springs[selected_spring].getHeightDiff();
			parent.text(next_letter, x + springs[0].pos.x, y);
			caret_x += parent.textWidth(next_letter);
		}
	}
}
