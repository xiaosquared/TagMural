package music.notes;

import java.util.Random;

import music.staff.SineStaff;
import processing.core.PApplet;
import processing.core.PVector;

public class NoteGroup {
	Note[] notes;
	float width;
	float in_between;
	float start_x;
	boolean staffAbove = false;
	PVector connector_start = new PVector(0, 0);
	
	float MIN_BETWEEN = 60;
	
	public NoteGroup(float width, float start_x, float y_offset) {
		this.width = width;
		this.start_x = start_x;
		
		int how_many_notes = PApplet.ceil(width / MIN_BETWEEN);
		in_between = width / (float) how_many_notes;
		
		notes = new Note[how_many_notes+1];
		NoteName name = NoteName.getRandomNote();
		
		Random r = new Random();
		boolean up = r.nextBoolean();
		for (int i= 0; i <= how_many_notes; i++) {
			NoteName next_name = nextNoteHelper(up, name);
			if (next_name == null) {
				next_name = nextNoteHelper(!up, name);
			}
			Note n = new Note(next_name, start_x + width * i/(how_many_notes+0.5f), y_offset);
		    notes[i] = n;
		    name = next_name;
		}
	}
	
	private NoteName nextNoteHelper(boolean up, NoteName current) {
		NoteName next_name;
		if (up) 
			next_name = current.nextNoteUp();
		else
			next_name = current.nextNoteDown();
		return next_name;
	}
	
	/////////////////////////////////////////////////////////////////////

	public PVector getConnectorStart() { return connector_start; }

	/////////////////////////////////////////////////////////////////////

	public void update() {
		for (int i = 0; i < notes.length; i++)
			notes[i].update();
	}
	
	public void draw(SineStaff staff, boolean draw_connector, boolean draw_stems, float opacity, float big_word_size, PApplet parent) {
		parent.fill(255, opacity);
		float max_y = 0;
		float min_y = parent.height;
		int direction_count = 0;
		
		// try to draw all the notes, keep track of which ones actually get drawn
		boolean[] notes_drawn = new boolean[notes.length];
		for (int i = 0; i < notes.length; i++) {
			notes_drawn[i] = notes[i].draw(staff, -1, notes.length == 1 ? true : false,  opacity, parent);
			float y = notes[i].getPosition().y;
			max_y = PApplet.max(max_y, y);
			min_y = PApplet.min(min_y, y);
			direction_count += notes[i].stemDirection();
		}
		
		if (notes.length == 1) return;
		
		// draw the connecting line and stems
		float start_x = notes[0].getPosition().x;
		float start_r = notes[0].diameter/2;
		float end_x = notes[notes.length-1].getPosition().x;
		float end_r = notes[notes.length-1].diameter/2;
		
		float end_y = max_y + 60;
		
		if (direction_count < 0) {
			end_y = min_y - 60;
			start_r = -start_r;
			end_r = -end_r;
			staffAbove = true;
		}
		
		PVector start = new PVector(start_x - start_r, end_y);
		connector_start = start;
		PVector end = new PVector(end_x - end_r, end_y);
	
		if (!draw_stems) return;
		
		for (int i = 0; i < notes.length; i++) {
			if (!notes_drawn[i])
				return;
			PVector note_position = notes[i].getPosition();
			float note_r = notes[i].getRadius();
			if (start_r < 0) note_r = -note_r;
			float x = note_position.x - note_r;
			float y = evaluateLine(start, end, x);
			
			float start_adjust = staff.getStaffFontSize();
			float end_adjust = -big_word_size*0.25f;
			if (direction_count < 0) {
				end_adjust = big_word_size*0.1f;
				start_adjust = 0;
			}
			 
			parent.line(x, note_position.y + start_adjust, x, y + end_adjust);
		}
	}
	
	// find y of line between p1 & P2 at x
	private float evaluateLine(PVector p1, PVector p2, float x) {
		float m = (p2.y - p1.y)/(p2.x - p1.x);
		return m * (x - p1.x) + p1.y;
	}
		
}
