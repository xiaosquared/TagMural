package music.staff;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * Word that lives on a SineWave and moves with the wave
 *
 */
public class SineText {
	String text;
	int len;
	float font_size;
	
	int start_point_index; // index of point on the SineWave
	
	float SHADE = 200;
	
	public SineText(String t, float fs, int start) {
		text = t;
		len = t.length();
		font_size = fs;
		start_point_index = start;
	}
	
	public void draw(PVector[] wave_points, PApplet parent) {
		draw(wave_points, 0, parent);
	}
	
	public void draw(PVector[] wave_points, float taper_width, PApplet parent) {
		parent.textSize(font_size);
		
		float caret_x = 0;
		int selected_index = 0;
		for (int i = 0; i < len; i++) {
			
			// get next letter, figure out which wave point determines its position
			char next_letter = text.charAt(i);
			selected_index = start_point_index + (int) caret_x;
			if (selected_index >= wave_points.length)
				return;
			float x = wave_points[selected_index].x;
			float y = wave_points[selected_index].y;
			
			// color
			int letter_index = i + start_point_index; 
			if (letter_index <= taper_width) {
				float alpha = (float) (i + start_point_index) / taper_width * SHADE;
				parent.fill(SHADE, alpha + 50);
			} 
			else if (letter_index > wave_points.length - taper_width) {
				float alpha = (float) SHADE - ((letter_index - (wave_points.length - taper_width)) / taper_width * SHADE);
				parent.fill(SHADE, alpha + 50);
			}
			else
				parent.fill(SHADE);
			
			// draw it
			parent.text(next_letter, x, y);
			caret_x += parent.textWidth(next_letter);
		}
	}
}
