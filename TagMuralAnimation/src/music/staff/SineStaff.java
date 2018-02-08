package music.staff;

import java.util.ArrayList;
import music.notes.WordNote;
import processing.core.PApplet;
import processing.core.PVector;

public class SineStaff {
	ArrayList<SineWave> staff_lines;
	float angle;
	int main_line_id = 2;
	float space_between_lines;
	
	ArrayList<WordNote> word_notes;
	float word_start_x = 300; // where to place the next word
	float word_end_x;

	float TAPER_WIDTH = 300;
	float MIN_TAPER = 10;

	public SineStaff(PVector origin, float width, float height, float angle, float font_size, PApplet parent) {
		word_end_x = parent.width;
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
		
		word_notes = new ArrayList<WordNote>();
	}

	/////////////////////////////////////////////////////////////////////
	
	public int getNumPoints() { return staff_lines.get(2).getNumPoints(); }
	public PVector getPoint(int which_line, int index) { return staff_lines.get(which_line).points[index];	}
	public float getLineSpacing(int x) { return staff_lines.get(0).points[x].y - staff_lines.get(1).points[x].y; }
	public float getTransY() { return staff_lines.get(2).getTransY();	 }
	public float getMaxX() { return staff_lines.get(main_line_id).getMaxX(); }
	public float getStaffFontSize() { return staff_lines.get(main_line_id).getFontSize(); }
	
	/////////////////////////////////////////////////////////////////////
		
	public void update() {		
		for (WordNote wn : word_notes) {
			wn.update();
		}
		
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
	
	public boolean addWordNote(String text, float font_size, PApplet parent) {
		parent.textSize(font_size);
		float word_width = parent.textWidth(text);
		if (word_start_x + word_width >= PApplet.min(word_end_x, getMaxX())) {
			word_end_x = word_start_x;
			return false;
		}
		float y_offset = getStaffFontSize()/2;
		WordNote wn = new WordNote(text, font_size, word_start_x, y_offset, parent);
		word_notes.add(wn);
		word_start_x += word_width + font_size*3;
		return true;
	}
	
	public void clearWordNotes() { 
		word_notes.clear(); 
		word_start_x = 300;
	}
	
	/////////////////////////////////////////////////////////////////////
	
	public void draw(PApplet parent) {
		parent.pushMatrix();
		parent.rotate(angle);
		for (SineWave line : staff_lines) {
			line.draw(TAPER_WIDTH, parent);
		}
		
		parent.pushMatrix();
		parent.translate(0, getTransY());
		for (WordNote wn: word_notes) {
			wn.draw(this, parent);
		}
		parent.popMatrix();
		parent.popMatrix();
	}
}
