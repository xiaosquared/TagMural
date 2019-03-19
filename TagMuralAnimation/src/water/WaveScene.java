package water;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PVector;
import words.WordSetsManager;

public class WaveScene {
	private final int MIN_FONT_SIZE = 16;
	private final int MAX_FONT_SIZE = 36;
	private final int RAIN_FONT_SIZE = 12;
	private int DOWN_POSITION;
	
	private WaveGroup waves; 
	private RainGroup rains;
	private boolean isRaining = true;
	private Splash splash;
	private FloatingWaveText featured_word;
	
	private PVector trans = new PVector(0, 0);
	
	private boolean isFading = false; 
	
	private PApplet parent;
	
	public WaveScene(PApplet parent, String[] words) {
		this.parent = parent;
		waves = new WaveGroup(5, new PVector(-20, parent.height-265), new PVector(parent.width+200, parent.height-65), 4, 220, 50, parent);
		waves.initText(words, MIN_FONT_SIZE, MAX_FONT_SIZE);
		splash = new Splash(parent);
		rains = new RainGroup(waves, splash, words, RAIN_FONT_SIZE, parent);
		
		DOWN_POSITION = 320;
		rains.restart();
	}
	 
	public void addFeaturedWord() {
		String w = WordSetsManager.getRandomWord().getText();
		PApplet.println(w);
		featured_word = new FloatingWaveText(w, 
				parent.random(70, 90), parent.random(0, parent.width*.66f), -20f, waves.getWave(0), parent);
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
		if (featured_word != null) {
			if (!featured_word.isVisible()) {
				//initFeaturedWord(parent);
				if (featured_word == null)
					return;
			}
			featured_word.update();
			featured_word.draw();
			if (featured_word.hittingWater())
				featured_word.hitWater(splash);
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
	
	public void fadeToSwitchWordSet() {
		featured_word.fadeOut();
		waves.fadeOut("onEnd:switchAndFadeIn");
	}
	
	public void switchWordSet(String[] words) {
		waves.initText(words, MIN_FONT_SIZE, MAX_FONT_SIZE);
		// TODO: reset text for rain also...
	}
	
}
