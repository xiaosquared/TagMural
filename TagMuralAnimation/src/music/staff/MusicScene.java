package music.staff;

import java.util.LinkedList;

import global.ColorPalette;
import music.musicians.WordImage;
import processing.core.PApplet;
import processing.core.PVector;
import words.WordSetsManager;

public class MusicScene {
	PApplet parent;
	SineStaff staff;
	float staff_height = 100;
	
	WordImage trumpeter, pianist, piano, bench;
	
	LinkedList<String> featured_word_queue;
	float WORD_FONT_SIZE = 60;
	float WORD_FADE_TIME = 7000;
	float fade_start_time = 0;
	boolean fading = false;
	
	float ADD_WORD_INTERVAL = 2000;
	float last_add_time = 0;
	
	float NEXT_FRAME_INTERVAL = 500;
	float last_change_frame = 0;
	
	public MusicScene(PApplet parent, float staff_font_size) {
		this.parent = parent;
		staff = new SineStaff(new PVector(0, parent.height*0.35f), parent.width,
								staff_height, -PApplet.PI/20, staff_font_size, parent);
		
		featured_word_queue = new LinkedList<String>();
		
		initMusicians(parent);
	}
	
	private void initMusicians(PApplet parent) {
		trumpeter = new WordImage(parent.loadImage("data/musicians/trumpet/trumpet1.jpg"), 5, 8, ColorPalette.CYAN, parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet2.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet3.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet4.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet5.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet6.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet7.jpg"), parent);
		trumpeter.addImage(parent.loadImage("data/musicians/trumpet/trumpet8.jpg"), parent);
		
		piano = new WordImage(parent.loadImage("data/musicians/piano.jpg"), 5, 4, ColorPalette.BLUE, parent);
		bench = new WordImage(parent.loadImage("data/musicians/bench.jpg"), 5, 3, ColorPalette.BLUE, parent);
		
		pianist = new WordImage(parent.loadImage("data/musicians/piano/pianist1.jpg"), 10, 8, ColorPalette.CYAN, parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist2.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist3.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist4.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist5.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist6.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist7.jpg"), parent);
		pianist.addImage(parent.loadImage("data/musicians/piano/pianist8.jpg"), parent);
		
		trumpeter.setTranslation(1000,  320);
		piano.setTranslation(200, 550);
		bench.setTranslation(730, 700);
		pianist.setTranslation(610, 480);

		trumpeter.setScale(0.4f, 0.4f);
		pianist.setScale(0.39f, 0.39f);
		last_change_frame = parent.millis();
	}
	
	public void addFeaturedWord(String word) {
		if (!staff.isFull()) {
			boolean added = staff.addWordNote(word, WORD_FONT_SIZE, parent);
			if (added) {
				last_add_time = parent.millis();
				return;
			}
		}
		featured_word_queue.add(word);
	}
	
	private String popWordFromQueue() {
		return featured_word_queue.pop();
	}
	
	private void addFromQueue() {
		if (!featured_word_queue.isEmpty())
			addFeaturedWord(popWordFromQueue());
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
			addFromQueue();
		}
		
		else if (current_time - last_add_time > ADD_WORD_INTERVAL) {
			addFeaturedWord(WordSetsManager.getRandomWord().getText());
			last_add_time = current_time;
		}
		
		parent.background(0);
		staff.update();
		staff.draw(parent);
		
		trumpeter.draw(parent);
		piano.draw(parent);
		bench.draw(parent);
		pianist.draw(parent);
		
		if (current_time - last_change_frame > NEXT_FRAME_INTERVAL) {
			pianist.nextFrame();
			trumpeter.nextFrame();
			last_change_frame = current_time;
		}
	}
	
}
