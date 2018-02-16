package test;

import de.looksgood.ani.Ani;
import houses.block.ScrollingHouseScene;
import music.staff.MusicScene;
import processing.core.PApplet;
import processing.core.PFont;
import water.WaveScene;
import words.WordSetsManager;

public class TagMuralTest extends PApplet {
	
	PFont font;
	int font_size = 100;
	
	ScrollingHouseScene hs;
	MusicScene ms;
	
	public enum SceneState { WAVE, HOUSES, MUSIC; }
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
		
		hs = new ScrollingHouseScene(630, width-100, font, true, this);
		hs.drawOffscreen();
		
		ms = new MusicScene(this, 6);
	}
	
	public void draw() {
		if (current_scene == SceneState.HOUSES)
			hs.run();
		else if (current_scene == SceneState.WAVE)
			WaveScene.run(this);
		else if (current_scene == SceneState.MUSIC)
			ms.run();
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		font = createFont("American Typewriter", font_size);
		textFont(font);  
	}
	

//	public void webSocketEvent(String msg) {
//		try {
//			println("received msg: " + msg);
//
//			if (current_scene == SceneState.WAVE) {
//				WaveScene.initFeaturedWord(this);
//			} else {
//				hs.addFeaturedWord();
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void keyPressed() {
		switch(key) {
			case 'w':
				if (current_scene == SceneState.WAVE) 
					WaveScene.initFeaturedWord(this);
				else
					hs.addFeaturedWord();
			break;
			
			case 'f':
				if (current_scene == SceneState.WAVE) {
					WordSetsManager.switchWordSet();
					WaveScene.fadeToSwitchWordSet();
				}
				else if (current_scene == SceneState.HOUSES) {
					hs.setIsScrolling(false);
					hs.resetDissolver();
					hs.startDissolve();
				} else {
					ms.fade();
				}
			break;
			
			case 'r':
				if (WaveScene.toggleRain()) {
					WaveScene.restartRain();
				}
			break;
			
			default:
				if (current_scene == SceneState.HOUSES)
					current_scene = SceneState.WAVE;
				else if (current_scene == SceneState.WAVE)
					current_scene = SceneState.MUSIC;
				else
					current_scene = SceneState.HOUSES;
			break;
		}
		
		
		//
		
//		if (key == 'w') {
//			if (current_scene == SceneState.WAVE) 
//				WaveScene.initFeaturedWord(this);
//			else
//				hs.addFeaturedWord();
//		}
//		else if (key == 'f') {
//			if (current_scene == SceneState.WAVE) {
//				WordSetsManager.switchWordSet();
//				WaveScene.fadeToSwitchWordSet();
//			}
//			else {
//				hs.setIsScrolling(false);
//				hs.resetDissolver();
//				hs.startDissolve();
//			}
//		} 
//		else if (key == 'r') {
//			if (WaveScene.toggleRain()) {
//				WaveScene.restartRain();
//			}
//		}
//		else if (key == 32) {
//			if (current_scene == SceneState.HOUSES)
//				current_scene = SceneState.WAVE;
//			else 
//				current_scene = SceneState.HOUSES;
//		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.TagMuralTest"); 
	}
}
