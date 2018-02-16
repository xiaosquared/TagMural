package music.notes;

import de.looksgood.ani.Ani;
import music.staff.SineStaff;
import processing.core.PApplet;
import processing.core.PVector;

public class WordNote {
	String text;
	float font_size;
	NoteGroup notes;
	float opacity = 255;
	
	public WordNote(String text, float font_size, float start_x, float y_offset, PApplet parent) {
		this.text = text.trim();
		this.font_size = font_size;
		parent.textSize(font_size);
		notes = new NoteGroup(parent.textWidth(text), start_x, y_offset);
	}

	public void fade(float fade_time) {
		Ani.to(this, fade_time, "opacity", 0);
	}
	
	public void update() {
		notes.update();
	}
	
	public void draw(SineStaff staff, PApplet parent) {
		notes.draw(staff,  false,  true,  opacity, font_size, parent);
		
		PVector text_origin = notes.getConnectorStart();
		parent.fill(230, opacity);
		parent.textSize(font_size);

		parent.text(text, text_origin.x, text_origin.y - font_size*0.75f);
	}
}
