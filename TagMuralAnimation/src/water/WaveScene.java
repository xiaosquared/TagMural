package water;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import de.looksgood.ani.Ani;
import global.Scene;
import processing.core.PApplet;
import processing.core.PVector;

public class WaveScene implements Scene {
	private final int MIN_FONT_SIZE = 16;
	private final int MAX_FONT_SIZE = 36;
	private final int RAIN_FONT_SIZE = 12;
	private int DOWN_POSITION;
	
	private int num_waves = 5;
	private WaveGroup waves; 
	private RainGroup rains;
	private boolean isRaining = true;
	private Splash splash;
	
	private ArrayList<FloatingWaveText> featured_words;

	private PVector trans = new PVector(0, 0);
	
	private PApplet parent;
	
	public WaveScene(PApplet parent, String[] words) {
		this.parent = parent;
		
		featured_words = new ArrayList<FloatingWaveText>();
		
		waves = new WaveGroup(num_waves, new PVector(-20, parent.height-265), new PVector(parent.width+200, parent.height-65), 4, 220, 50, parent);
		waves.initText(words, MIN_FONT_SIZE, MAX_FONT_SIZE);
		splash = new Splash(parent);
		rains = new RainGroup(waves, splash, words, RAIN_FONT_SIZE, parent);
		
		DOWN_POSITION = 320;
		rains.restart();
	}
	 
	
	public void addFromQueue(LinkedList<String> featured_word_queue) {
		if (!featured_word_queue.isEmpty()) {
			addFeaturedWord(featured_word_queue.pop());
		}
	}
	
	public void addFeaturedWord(String w) {
		featured_words.add(new FloatingWaveText(w, 
				parent.random(70, 90), parent.random(0, parent.width*.66f), -20f, 
					waves.getWave(PApplet.floor(parent.random(num_waves-1))), parent));
	}
	
	public void run() {
		parent.background(0);
		
		rains.run(true, true);
		
		if (trans.y >= DOWN_POSITION)
			return;
		
		waves.update();
		
		parent.pushMatrix();
		parent.translate(trans.x, trans.y);
		waves.draw(true);
		splash.run(true);
		runFeaturedWord();
		parent.popMatrix();
	}
	
	private void runFeaturedWord() {
		Iterator<FloatingWaveText> fwt = new ArrayList<>(featured_words).iterator();
		while(fwt.hasNext()) {
			try {
				FloatingWaveText ft = fwt.next();
				if (!ft.isVisible()) {
					featured_words.remove(ft);
					continue;
				}
				ft.update();
				ft.draw();
				if (ft.hittingWater())
					ft.hitWater(splash);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean toggleRain() {
		isRaining = !isRaining;
		return isRaining;
	}
	
	public void restartRain() {
		rains.restart();
	}
		
	public void translateDown() {
		Ani.to(trans, 10, "y", DOWN_POSITION, Ani.SINE_IN_OUT);
	}
	
	public void translateUp() {
		Ani.to(trans, 10, "y", 0, Ani.SINE_IN_OUT);
	}
	
	public void fadeWave() {
		waves.fadeOut("onEnd:fadeIn");
	}
	
	public void changeWordSet() {
		waves.fadeOut("onEnd:switchAndFadeIn");
	}
	
	public void clearFeaturedWords() {
		
	}
	
//	public void switchWordSet(String[] words) {
//		waves.initText(words, MIN_FONT_SIZE, MAX_FONT_SIZE);
//		// TODO: reset text for rain also...
//	}
	
}
