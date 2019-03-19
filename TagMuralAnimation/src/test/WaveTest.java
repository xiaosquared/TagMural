package test;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import water.WaveScene;
import words.WordSetsManager;

public class WaveTest extends PApplet {

	PFont font;
	int font_size = 100;

	WaveScene ws;
	
	public void settings(){
		//size(1200,800, P2D);
		fullScreen(P2D);
	}

	public void setup(){    	
		Ani.init(this);
		initWords();
		ws = new WaveScene(this, WordSetsManager.getCurrentWordSet().getTexts());
	}

	public void draw(){
		ws.run();
		//WaveScene.updateFeaturedWord(this);
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
			if (ws.toggleRain()) {
				ws.restartRain();
			}
			break;
		case 'w':
			ws.addFeaturedWord();
			break;
		case 't':
			ws.translateDown();
			break;
		case 'y':
			ws.translateUp();
			break;
		case 'f':
			WordSetsManager.switchWordSet();
			ws.fadeToSwitchWordSet();
			break;
//		default:
//			client.sendMessage("hello dd!");

		}
	}

	public static void main(String[] args) { 
		PApplet.main("test.WaveTest"); 
	}
}
