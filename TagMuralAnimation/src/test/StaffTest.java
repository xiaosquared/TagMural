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
	
	WordImage trumpeter, trumpeter2;
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
		
		trumpeter = new WordImage(loadImage("data/musicians/trumpet/trumpet1.jpg"), 5, 8, ColorPalette.CYAN, this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet2.jpg"), this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet3.jpg"), this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet4.jpg"), this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet5.jpg"), this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet6.jpg"), this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet7.jpg"), this);
		trumpeter.addImage(loadImage("data/musicians/trumpet/trumpet8.jpg"), this);
		
		trumpeter2 = new WordImage(loadImage("data/musicians/trumpet/trumpet1.jpg"), 5, 8, ColorPalette.CYAN, this);
		trumpeter2.addImage(loadImage("data/musicians/trumpet/trumpet2.jpg"), this);
		
		piano = new WordImage(loadImage("data/musicians/piano.jpg"), 5, 4, ColorPalette.BLUE, this);
		
		bench = new WordImage(loadImage("data/musicians/bench.jpg"), 5, 3, ColorPalette.BLUE, this);
		
		pianist = new WordImage(loadImage("data/musicians/piano/pianist1.jpg"), 10, 8, ColorPalette.CYAN, this);
		pianist.addImage(loadImage("data/musicians/piano/pianist2.jpg"), this);
		pianist.addImage(loadImage("data/musicians/piano/pianist3.jpg"), this);
		pianist.addImage(loadImage("data/musicians/piano/pianist4.jpg"), this);
		pianist.addImage(loadImage("data/musicians/piano/pianist5.jpg"), this);
		pianist.addImage(loadImage("data/musicians/piano/pianist6.jpg"), this);
		pianist.addImage(loadImage("data/musicians/piano/pianist7.jpg"), this);
		pianist.addImage(loadImage("data/musicians/piano/pianist8.jpg"), this);

//		trumpeter.setTranslation(1000,  320);
//		piano.setTranslation(200, 550);
//		bench.setTranslation(730, 700);
//		pianist.setTranslation(610, 480);
	
		trumpeter.setTranslation(1000, 320);
		trumpeter2.setTranslation(700, 380);
		piano.setTranslation(755, 550);
		bench.setTranslation(220, 700);
		pianist.setTranslation(350, 480);
		trumpeter2.setScale(0.37f, 0.37f);
		bench.setScale(-1f, 1f);
		piano.setScale(-1f, 1f);
		
		trumpeter.setScale(0.4f, 0.4f);
		pianist.setScale(-0.39f, 0.39f);
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
		
		trumpeter.draw(this);
		trumpeter2.draw(this);
		piano.draw(this);
		bench.draw(this);
		pianist.draw(this);
		
		float current_time = millis();
		if (current_time - last_change > next_frame_interval)	 {
			pianist.nextFrame();
			trumpeter.nextFrame();
			trumpeter2.nextFrame();
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
