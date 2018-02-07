package music.notes;

import music.staff.SineStaff;
import processing.core.PApplet;
import processing.core.PVector;

public class Note {
	int line;
	boolean bAboveLine;
	
	float start_x;
	float y_offset;
	float diameter = 0;
	float radius = 0;
	PVector staff_point;
	
	NoteName note;
	
	float font_size = 6;
	float note_head_font_size = 10;
	
	public Note(NoteName note, float start_x, float y_offset) {
		this.note = note;
		this.start_x = start_x;
		this.y_offset = y_offset;
		
		this.line = note.whichLine();
		this.bAboveLine = note.isOnLine();
	}
	
	/////////////////////////////////////////////////////////////////////
	
	// Accessor methods
	public float x() { return start_x; }
	public boolean isOnLine() { return note.isOnLine(); }
	public int stemDirection() { return note.stemDirection(); }
	public PVector getPosition() { return staff_point; }
	public float getRadius() { return radius; }
	
	/////////////////////////////////////////////////////////////////////
	
	public void update() {}
	
	// Drawing
	public boolean draw(SineStaff staff, PApplet parent) {
		return draw(staff, stemDirection(), true, 255, parent);
	}
	
	public boolean draw(SineStaff staff, int direction, boolean draw_stem, float opacity, PApplet parent) {
		// Don't draw the note if it's out of bounds
		int index = (int) x();
		if (index >= staff.getNumPoints())
			return false;
		
		staff_point = staff.getPoint(line, index);
		
		float x = staff_point.x;
		diameter = staff.getLineSpacing((int) x);
		radius = diameter/2;
		if (bAboveLine) {
			staff_point.y -= diameter/2;
		}
		float y = staff_point.y + y_offset;
		
		parent.stroke(250, opacity);
		drawTextSpiral(x, y, radius, parent);
		//parent.ellipse(x, y, diameter, diameter);
		
		return true;
	}
	
	private void drawTextSpiral(float x, float y, float radius, PApplet parent) {
		parent.textSize(note_head_font_size); 
		char c = 'x';
		//char c = (char) (int) (parent.random(97, 122));
		
		float r = 0.02f;
		float arclen = parent.textWidth(c);
		parent.pushMatrix();
		parent.translate(x, y);
		while(r <= radius) {
			float theta = arclen/r;
			parent.rotate(theta);
			parent.text(c, 0, r-note_head_font_size);
			r += 0.15f;
		}
		parent.popMatrix();
	}
}
