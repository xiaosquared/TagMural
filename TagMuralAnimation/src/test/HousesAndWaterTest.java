package test;

import de.looksgood.ani.Ani;
import houses.block.ScrollingHouseScene;
import processing.core.PApplet;
import processing.core.PFont;
import water.WaveScene;
import websockets.WebsocketClient;
import words.WordSetsManager;

public class HousesAndWaterTest extends PApplet {
	
	PFont font;
	int font_size = 100;
	
	ScrollingHouseScene hs;
	
	public enum SceneState { WAVE, HOUSES; }
	SceneState current_scene = SceneState.HOUSES;
	
	WebsocketClient client;
	
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
		
//		initClient();
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
	
	void initClient() {
		try {
			client = new WebsocketClient(this, "ws://138.197.115.126:3333");
		} catch (Exception e) {
			println("unable to connect to server");
		} finally {
			println("connected!");
		}
	}
	
	public void webSocketEvent(String msg) {
		try {
			println("received msg: " + msg);

			if (current_scene == SceneState.WAVE) {
				WaveScene.initFeaturedWord(this);
			} else {
				hs.addFeaturedWord();
			}
		} catch(Exception e) {
			println(e);
			initClient();
		}
	}
	
	public void keyPressed() {
		if (key == 'w') {
			if (current_scene == SceneState.WAVE) 
				WaveScene.initFeaturedWord(this);
			else
				hs.addFeaturedWord();
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
