package test;

import de.looksgood.ani.Ani;
import houses.block.ScrollingHouseScene;
import processing.core.PApplet;
import processing.core.PFont;
import water.WaveScene;
import words.WordSetsManager;

public class HousesAndWaterTest extends PApplet {
	
	PFont font;
	int font_size = 100;
	
	ScrollingHouseScene hs;
	
	public enum SceneState { WAVE, HOUSES; }
	SceneState current_scene = SceneState.HOUSES;
	
	public void settings() {
		fullScreen(P2D);
		smooth(4);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		
		Ani.init(this);
		initWords();
		WaveScene.init(this, WordSetsManager.getCurrentWordSet().getTexts());
		
		hs = new ScrollingHouseScene(600, width-100, font, true, this);
		hs.drawOffscreen();
	}
	
	public void draw() {
		if (current_scene == SceneState.HOUSES)
			hs.run();
		else if (current_scene == SceneState.WAVE)
			WaveScene.run(this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		font = createFont("American Typewriter", font_size);
		textFont(font);  
	}
	
	public void keyPressed() {
		if (key == 'w') {
			WaveScene.initFeaturedWord(this);
		}
		else if (key == 'f') {
			if (current_scene == SceneState.WAVE) {
				WordSetsManager.switchWordSet();
				WaveScene.fadeToSwitchWordSet();
			}
			else {
				hs.setIsScrolling(false);
				hs.resetDissolver();
				hs.startDissolve();
			}
		} 
		else if (key == 'r') {
			if (WaveScene.toggleRain()) {
				WaveScene.restartRain();
			}
		}
		else if (key == 32) {
			if (current_scene == SceneState.HOUSES)
				current_scene = SceneState.WAVE;
			else 
				current_scene = SceneState.HOUSES;
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.HousesAndWaterTest"); 
	}
}
