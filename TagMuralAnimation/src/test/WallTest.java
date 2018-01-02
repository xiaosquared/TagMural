package test;

import de.looksgood.ani.Ani;
import houses.block.HouseScene;
import houses.vehicles.RollingWord;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	HouseScene hs;
	RollingWord rw;
	
	PFont font;
	int font_size = 100;
		
	public void settings() {
		//size(1400,1000, P2D);
		fullScreen(P2D);
		smooth(4);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		colorMode(HSB, 360, 100, 100);
		initWords();

		hs = new HouseScene(650, width-100, font, this);
		hs.initBlock(true, this);
		hs.drawOffscreen();
		hs.drawFromOffscreen(this);
		
		Ani.init(this);
	}
	
	public void draw() {
		boolean isFading = hs.fadeInAndOut(this);
		if (isFading) {
			hs.drawOffscreen();
		} 
		hs.updateVehicle(this);
		hs.draw(this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font);  
	}
	
	public void keyPressed() {
		println(keyCode);
		if (keyCode == 38) {
			HouseScene.translateUp();
		} else if (keyCode == 40) {
			HouseScene.translateDown();
		} 
		
		switch(key) {
		case ' ':
			rw = new RollingWord(WordSetsManager.getRandomWord(), 60, 200, height-80, 6, this);
			break;
		case 'f':
			WordSetsManager.switchWordSet();
			break;
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
