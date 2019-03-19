package music.notes;

import de.looksgood.ani.Ani;
import music.staff.SineStaff;
import processing.core.PApplet;
import processing.core.PVector;

public class WordNote {
	String text;
	float font_size = 60;
	NoteGroup notes;
	float opacity = 255;
	
	public WordNote(String text, float text_width, float start_x, float y_offset, PApplet parent) {
		this.text = text.trim();
		notes = new NoteGroup(text_width, start_x, y_offset);
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
