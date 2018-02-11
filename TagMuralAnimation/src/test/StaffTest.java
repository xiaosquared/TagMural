package test;

import de.looksgood.ani.Ani;
import global.ColorPalette;
import music.musicians.WordImage;
import music.staff.MusicScene;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class StaffTest extends PApplet {
	
	MusicScene music;
	PFont font;
	
	WordImage pianist;
	WordImage piano;
	WordImage bench;
	
	float next_frame_interval = 500;
	float last_change = 0;
	
	public void settings() {
		fullScreen(P2D);
		//size(1200, 600, P2D);
		smooth(4);
	}	

	public void setup() {
		textAlign(LEFT, TOP);
		background(0);
		initWords();
		Ani.init(this);
		
		music = new MusicScene(this, 6);
		
		piano = new WordImage(loadImage("data/musicians/piano.jpg"), 5, 4, ColorPalette.BLUE, this);
		piano.setTranslation(200, 550);
		
		bench = new WordImage(loadImage("data/musicians/bench.jpg"), 5, 3, ColorPalette.BLUE, this);
		bench.setTranslation(730, 700);
		
		pianist = new WordImage(loadImage("data/musicians/pianist_01.jpg"), 10, 4, ColorPalette.CYAN, this);
		pianist.addImage(loadImage("data/musicians/pianist_02.jpg"), this);
		pianist.addImage(loadImage("data/musicians/pianist_03.jpg"), this);
		pianist.setTranslation(620, 480);
		pianist.setScale(0.65f);
		last_change = millis();
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", 60);
		textFont(font);
	}
	
	public void draw() {
		background(0);
		music.run();
		
		piano.draw(this);
		bench.draw(this);
		pianist.draw(this);
		
		float current_time = millis();
		if (current_time - last_change > next_frame_interval)	 {
			pianist.nextFrame();
			last_change = current_time;
		}
	}
	
	public void mousePressed() {
		println(mouseX + ", " + mouseY);
	}
	
	public void keyPressed() {
		//music.addFeaturedWord(WordSetsManager.getRandomWord().getText());
		pianist.nextFrame();
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.StaffTest"); 
	}	
}
