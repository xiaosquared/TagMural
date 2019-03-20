package test;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import de.looksgood.ani.Ani;
import global.Scene;
import houses.block.ScrollingHouseScene;
import music.staff.MusicScene;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.JSONObject;
import water.WaveScene;
import words.WordSetsManager;

public class TagMuralTest extends PApplet {
	
	PFont font;
	int font_size = 100;
	
	ScrollingHouseScene hs;
	MusicScene ms;
	WaveScene ws;
	
	Scene current_scene;
	
//	public enum SceneState { WAVE, HOUSES, MUSIC; }
//	SceneState current_scene = SceneState.MUSIC;
	
	float lastSceneChange = 0;
	float changeSceneTime = 600000;
	
	public void settings() {
		fullScreen(P2D);
		smooth(4);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		
		initWebSocket();
		
		Ani.init(this);
		initWords();
		
		ws = new WaveScene(this, WordSetsManager.getCurrentWordSet().getTexts());
		hs = new ScrollingHouseScene(580, width-100, font, true, this);
		hs.drawOffscreen();
		
		current_scene = hs;
		
		ms = new MusicScene(this, 6);
	}
	
	public void draw() {
		
		float currentTime = millis();
//		if (currentTime - lastSceneChange > changeSceneTime) {
//			if (current_scene == SceneState.HOUSES)
//				current_scene = SceneState.WAVE;
//			else if (current_scene == SceneState.WAVE) {
//				current_scene = SceneState.MUSIC;
//			} else {
//				current_scene = SceneState.HOUSES;
//				hs.clearFeaturedWords();
//			}
//			
//			lastSceneChange = currentTime;
//		}
		current_scene.run();
		
//		if (current_scene == SceneState.HOUSES)
//			hs.run();
//		else if (current_scene == SceneState.WAVE)
//			ws.run();
//		else if (current_scene == SceneState.MUSIC)
//			ms.run();
	}
	
	private void initWebSocket() {
		try {
			WebSocketClient mWs = new WebSocketClient(new URI( "ws://138.197.115.126:3333" ), new Draft_6455() )
			{
				@Override
				public void onMessage( String message ) {
					JSONObject json = parseJSONObject(message);
					println(json.getString("dev_id"));
					if (current_scene != null)
						current_scene.addFeaturedWord();
				}
				@Override
				public void onOpen( ServerHandshake handshake ) {
					System.out.println( "opened connection" );
				}
				@Override
				public void onClose( int code, String reason, boolean remote ) {
					System.out.println( "closed connection" );
				}
				@Override
				public void onError( Exception ex ) {
					ex.printStackTrace();
				}
			};
			mWs.connect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		font = createFont("American Typewriter", font_size);
		textFont(font);  
	}
		
	// Simulating server messages with key presses 
	
	public void keyPressed() {
		switch(key) {
			
			// EVENT: New featured word
		
			case 'w':
				current_scene.addFeaturedWord();
			break;
			
			// EVENT: Switch which words are in the background
			
			case 'f':
				WordSetsManager.switchWordSet();
				current_scene.changeWordSet();
			break;
			
			case 'r':
				if (ws.toggleRain()) {
					ws.restartRain();
				}
			break;
			
			
			// SWITCH SCENES
			
			case ' ':
				if (current_scene instanceof WaveScene)
					current_scene = hs;
				else if (current_scene instanceof ScrollingHouseScene)
					current_scene = ms;
				else if (current_scene instanceof MusicScene)
					current_scene = ws;
//				if (current_scene == SceneState.HOUSES)
//					current_scene = SceneState.WAVE;
//				else if (current_scene == SceneState.WAVE)
//					current_scene = SceneState.MUSIC;
//				else
//					current_scene = SceneState.HOUSES;
			break;
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.TagMuralTest"); 
	}
}
