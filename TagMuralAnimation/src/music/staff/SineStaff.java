package music.staff;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class SineStaff {
	ArrayList<SineWave> staff_lines;
	float angle;
	int main_line_id = 2;
	float space_between_lines;
	
	float TAPER_WIDTH = 300;
	float MIN_TAPER = 10;

	public SineStaff(PVector origin, float width, float height, float angle, float font_size, PApplet parent) {
		this.angle = angle;
		staff_lines = new ArrayList<SineWave>();
		SineWave main_line = new SineWave(origin, width, font_size, parent);
		
		for (int i = 0; i < 5; i++) {
			if (i != main_line_id) {
				space_between_lines = height / 4;
				SineWave sw = new SineWave(new PVector(origin.x, origin.y), width, font_size, main_line.getSineTerms(), parent);
				staff_lines.add(sw);
			} else
				staff_lines.add(main_line);
		}
	}

	/////////////////////////////////////////////////////////////////////
	
	public void update() {
		updateStaffLines();
	}
	
	private void updateStaffLines() {
		SineWave main = staff_lines.get(main_line_id);
		main.update();
		
		for (int i = 0; i < 5; i++) {
			if (i != main_line_id) {
				SineWave side_line = staff_lines.get(i);
				for (int pt = 0; pt < main.points.length; pt++) {
					
					// taper
					float taper = 1;
					if (pt < TAPER_WIDTH) 
						taper = (pt + MIN_TAPER)/TAPER_WIDTH;
					
					taper = PApplet.min(taper, 1);
					
					float y_offset = (i -2) * space_between_lines * taper;
					side_line.points[pt].y = main.points[pt].y - y_offset;
				}
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////
	
	public void draw(PApplet parent) {
		parent.pushMatrix();
		parent.rotate(angle);
		for (SineWave line : staff_lines) {
			line.draw(TAPER_WIDTH, parent);
		}
		parent.popMatrix();
	}
}
