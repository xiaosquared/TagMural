package music.staff;

import java.util.ArrayList;
import java.util.LinkedList;

import de.looksgood.ani.Ani;
import global.ColorPalette;
import global.Scene;
import music.musicians.WordImage;
import processing.core.PApplet;
import processing.core.PVector;
import words.WordSetsManager;

public class MusicScene implements Scene {
	PApplet parent;
	SineStaff staff;
	float staff_height = 100;
	
	ArrayList<WordImage> musicians; 
	
	float WORD_FONT_SIZE = 60;
	float WORD_FADE_TIME = 2000;
	float fade_start_time = 0;
	boolean fading = false;
	
	float ADD_WORD_INTERVAL = 2000;
	float last_add_time = 0;
	
	float NEXT_FRAME_INTERVAL = 500;
	float last_change_frame = 0;
	
	float opacity = 255;
	
	String next_word = "";
	
	public MusicScene(PApplet parent, float staff_font_size) {
		this.parent = parent;
		staff = new SineStaff(new PVector(0, parent.height*0.35f), parent.width,
								staff_height, -PApplet.PI/20, staff_font_size, parent);
		
		//featured_word_queue = new LinkedList<String>();
		
		initMusicians(parent);
	}
	
	private void initMusicians(PApplet parent) {
		musicians = new ArrayList<WordImage>();
		
		WordImage trumpeter = new WordImage(parent.loadImage("data/musicians/trumpet/trumpet1.jpg"), 5, 8, ColorPalette.CYAN, parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet2.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet3.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet4.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet5.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet6.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet7.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet8.jpg"), parent);

		WordImage piano = new WordImage(parent.loadImage("data/musicians/piano.jpg"), 5, 4, ColorPalette.BLUE, parent);
		WordImage bench = new WordImage(parent.loadImage("data/musicians/bench.jpg"), 5, 3, ColorPalette.BLUE, parent);
				
		WordImage pianist = new WordImage(parent.loadImage("data/musicians/piano/pianist1.jpg"), 10, 8, ColorPalette.CYAN, parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist2.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist3.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist4.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist5.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist6.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist7.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist8.jpg"), parent);
		
		musicians.add(trumpeter);
		musicians.add(piano);
		musicians.add(bench);
		musicians.add(pianist);
		
		trumpeter.setTranslation(1000,  280);
		piano.setTranslation(200, 510);
		bench.setTranslation(730, 660);
		pianist.setTranslation(610, 440);

		trumpeter.setScale(0.4f, 0.4f);
		pianist.setScale(0.39f, 0.39f);
		last_change_frame = parent.millis();
	}

	public void addNextWord(String word) {
		if (!staff.isFull()) {
			boolean added = staff.addWordNote(word, WORD_FONT_SIZE, parent);
			if (added) {
				last_add_time = parent.millis();
				return;
			}
		}
	}
	
	public void addFromQueue(LinkedList<String> featured_word_queue) {
		if (!featured_word_queue.isEmpty())
			addNextWord(featured_word_queue.pop());
	}
	
	public void fade() {
		Ani.to(this, 5, "opacity", 0, Ani.SINE_IN_OUT, "onEnd:changeWordSet");
		staff.fadeWordNotes(WORD_FADE_TIME/1000);
	}
	
	public void changeWordSet() {
		WordSetsManager.switchWordSet();
		for (WordImage m : musicians) {
			m.reset(parent);
		}
		Ani.to(this, 5, "opacity", 255, Ani.SINE_IN_OUT);
	}
	
	public void run() {
		float current_time = parent.millis();

		if (staff.isFull() && fading == false) {
			staff.fadeWordNotes(WORD_FADE_TIME/1000);
			fade_start_time = parent.millis();
			fading = true;
		}
		
		else if (fading && current_time - fade_start_time > WORD_FADE_TIME * 0.6) {
			staff.clearWordNotes();
			fading = false;
		}
		
		// AUTO ADD
//		else if (current_time - last_add_time > ADD_WORD_INTERVAL) {
//			addFeaturedWord(WordSetsManager.getRandomWord().getText());
//			last_add_time = current_time;
//		}
		
		parent.background(0);
		staff.update();
		staff.draw(parent);
		
		for (WordImage m : musicians)
			m.draw(parent, opacity);
		
		if (current_time - last_change_frame > NEXT_FRAME_INTERVAL) {
			for (WordImage m : musicians) {
				if (m.isAnimated())
					m.nextFrame();
			}
			last_change_frame = current_time;
		}
	}
	
}
