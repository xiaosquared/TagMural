package test;

import java.net.URI;
import java.util.LinkedList;

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
	
	LinkedList<String> featured_word_queue;
	
	float last_scene_change = 0;
	float CHANGE_SCENE_INTERVAL = 60000;
	float ADD_WORD_INTERVAL = 7000;
	float last_add_time = 0;
	
	
	public void settings() {
		fullScreen(P2D);
		smooth(4);
	}
	
	public void setup() {
		noCursor();
		textAlign(LEFT, TOP);
		
		initWebSocket();
		
		Ani.init(this);
		initWords();

		WordSetsManager.switchWordSet("buildings");	
		hs = new ScrollingHouseScene(580, width-100, font, true, this);
		hs.drawOffscreen();

		WordSetsManager.switchWordSet("transportation");	
		ws = new WaveScene(this, WordSetsManager.getCurrentWordSet().getTexts());		

		current_scene = ws;
		
		ms = new MusicScene(this, 6);
		
		featured_word_queue = new LinkedList<String>();
	}
	
	public void draw() {
		
		float current_time = millis();
		
		if (current_time - last_add_time > ADD_WORD_INTERVAL) {
			featured_word_queue.add(WordSetsManager.getRandomWord().getText());
			current_scene.addFromQueue(featured_word_queue);
			last_add_time = current_time;
		}
		
		if (current_time - last_scene_change > CHANGE_SCENE_INTERVAL) {
			changeScene();
			last_scene_change = current_time;
		}
		
		current_scene.run();
	
		drawMask();

		line(0, height, width, height);
		
	}
	
	private void drawMask() {
		stroke(0);
		strokeWeight(1);
		fill(0);
		
		//top
		beginShape();
		vertex(0, 0);
		vertex(0, 35);
		vertex(width, 35);
		vertex(width, 0);
		endShape();
		
		// left
		beginShape();
		vertex(0, 0);
		vertex(44, 0);
		vertex(0, height);
		endShape();
		
		// right
		beginShape();
		vertex(width, 0);
		vertex(width-32, 0);
		vertex(width-14, height);
		vertex(width, height);
		endShape();;
	}
	
	private void initWebSocket() {
		try {
			WebSocketClient mWs = new WebSocketClient(new URI( "ws://138.197.115.126:3333" ), new Draft_6455() )
			{
				@Override
				public void onMessage( String message ) {
					JSONObject json = parseJSONObject(message);
					String id = json.getString("dev_id");
					System.out.println("Sensor Triggered: " + id);
					if (featured_word_queue != null) {
						featured_word_queue.add(WordSetsManager.getRandomWord(id).getText());
						current_scene.addFromQueue(featured_word_queue);
						last_add_time = millis();
					}
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
		
	
	private void changeScene() {
		if (current_scene instanceof WaveScene) {
			WordSetsManager.switchWordSet("buildings");
			hs.changeWordSet();
			current_scene = hs;
		}
		else if (current_scene instanceof ScrollingHouseScene) {
			WordSetsManager.switchWordSet("culture");
			ms.changeWordSet();
			current_scene = ms;
		}
		else if (current_scene instanceof MusicScene) {
			WordSetsManager.switchWordSet("transportation");
			ws.changeWordSet();
			current_scene = ws;
		}
	}
	
	// Simulating server messages with key presses 
	
	public void keyPressed() {
		switch(key) {
			
			// EVENT: New featured word
		
			case 'w':
				//featured_word_queue.add(WordSetsManager.getRandomWord().getText());
				String newWord = WordSetsManager.getRandomWord("0x0006").getText();
				println("Test add " + newWord);
				featured_word_queue.add(newWord);
				current_scene.addFromQueue(featured_word_queue);
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
				changeScene();
			break;
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.TagMuralTest"); 
	}
}
