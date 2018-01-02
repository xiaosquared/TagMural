package test;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import water.WaveScene;
import websockets.WebsocketClient;
import words.WordSetsManager;

public class WaveTest extends PApplet {

	PFont font;
	int font_size = 100;

	WebsocketClient client;

	public void settings(){
		//size(1200,800, P2D);
		fullScreen(P2D);
	}

	public void setup(){    	
		Ani.init(this);
		initWords();
		
		//initClient();		
	}

	public void draw(){
		WaveScene.run(this);
		//WaveScene.updateFeaturedWord(this);
	}

	void initClient() {
		try {
			client = new WebsocketClient(this, "ws://dndrk.media.mit.edu:3333");
		} catch (Exception e) {
			println("unable to connect to server");
		} finally {
			client.sendMessage("jtm dd");
			println("connected!");
		}
	}

	void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}

//	public void webSocketEvent(String msg) {
//		println("received msg: " + msg);
//		featured_word_queue.add(msg);
//	}
	
	public void keyPressed() {
		println(key);
		switch(key) {
		case 'r':
			if (WaveScene.toggleRain()) {
				WaveScene.restartRain();
			}
			break;
		case 'w':
			WaveScene.initFeaturedWord(this);
			break;
		case 't':
			WaveScene.translateDown();
			break;
		case 'y':
			WaveScene.translateUp();
			break;
		case 'f':
			WordSetsManager.switchWordSet();
			WaveScene.fadeToSwitchWordSet();
			break;
//		default:
//			client.sendMessage("hello dd!");

		}
	}

	public static void main(String[] args) { 
		PApplet.main("test.WaveTest"); 
	}
}
