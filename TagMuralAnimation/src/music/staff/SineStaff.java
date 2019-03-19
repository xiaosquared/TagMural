package music.staff;

import java.util.ArrayList;
import java.util.Iterator;

import music.notes.WordNote;
import music.clef.TrebleClef;
import processing.core.PApplet;
import processing.core.PVector;

public class SineStaff {
	ArrayList<SineWave> staff_lines;
	float angle;
	int main_line_id = 2;
	float space_between_lines;
	
	ArrayList<WordNote> word_notes;
	float word_start_x = 300; // where to place the next word
	boolean bFull = false;
	
	TrebleClef clef;
	
	float TAPER_WIDTH = 300;
	float MIN_TAPER = 10;
	float FEATURED_WORD_SPACING = 60;

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

		word_notes = new ArrayList<WordNote>();
		
		clef = new TrebleClef(TAPER_WIDTH * .6f, staff_lines.get(1).points[(int) TAPER_WIDTH/2].y, parent);
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
		clef.update();
		
		Iterator<WordNote> it = word_notes.iterator();
		while (it.hasNext()) {
			it.next().update();
		}
		
		updateStaffLines();
		clef.setOriginY(staff_lines.get(1).points[(int)TAPER_WIDTH/2].y);
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
		System.out.println("Test: " + text);
		System.out.println("word width: " + word_width);
		System.out.println("word font size: " + font_size);
		
		if (word_start_x + word_width >= getMaxX()) {
			bFull = true;
			return false;
		}
		
		float y_offset = getStaffFontSize()/2;
		WordNote wn = new WordNote(text, word_width, word_start_x, y_offset, parent);
		word_notes.add(wn);
		word_start_x += word_width + FEATURED_WORD_SPACING;
		return true;
	}
	
	public void clearWordNotes() { 
		word_notes.clear(); 
		word_start_x = 300;
		bFull = false;
	}
	
	public boolean isFull() {
		return bFull;
	}
	
	public void fadeWordNotes(float fade_time) {
		Iterator<WordNote> it = word_notes.iterator();
		while (it.hasNext()) {
			it.next().fade(fade_time);
		}
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
	
		Iterator<WordNote> it = word_notes.iterator();
		while (it.hasNext()) {
			it.next().draw(this, parent);
		}
		
		clef.draw(parent);
		parent.popMatrix();
		parent.popMatrix();
	}
}
