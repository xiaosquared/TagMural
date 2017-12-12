package water;

import processing.core.PApplet;
import processing.core.PVector;
import words.WordSetsManager;

public class WaveScene {
	private static WaveGroup waves; 
	private static RainGroup rains;
	private static boolean isRaining = false;
	private static Splash splash;
	private static FloatingWaveText featured_word;

	private static final int MIN_FONT_SIZE = 16;
	private static final int MAX_FONT_SIZE = 36;
	private static final int RAIN_FONT_SIZE = 12;
	
	public static void init(PApplet parent, String[] words) {
		
		waves = new WaveGroup(5, new PVector(-20, parent.height-220), new PVector(parent.width+200, parent.height-20), 4, 220, 50, parent);
		waves.initText(words, MIN_FONT_SIZE, MAX_FONT_SIZE);
		
		splash = new Splash(parent);
		rains = new RainGroup(waves, splash, words, RAIN_FONT_SIZE, parent);
	}
	 
	public static void run() {
		waves.update();
		waves.draw(true);
		splash.run(true);
		rains.run(true, isRaining);
	}
	
	public static boolean toggleRain() {
		isRaining = !isRaining;
		return isRaining;
	}
	
	public static void restartRain() {
		rains.restart();
	}
	 
	public static void fadeWave() {
		waves.fadeOut("onEnd:fadeIn");
	}
	
	public static void fadeToSwitchWordSet() {
		waves.fadeOut("onEnd:switchAndFadeIn");
	}
	
	public static void switchWordSet(String[] words) {
		waves.initText(words, MIN_FONT_SIZE, MAX_FONT_SIZE);
		// TODO: reset text for rain also...
	}
	
	public static void initFeaturedWord(PApplet parent) {
		featured_word = new FloatingWaveText(WordSetsManager.getRandomWord().getText(), 
				parent.random(70, 90), parent.random(0, parent.width*.66f), -20f, waves.getWave(0), parent);
	}
	
	public static void updateFeaturedWord(PApplet parent) {
		if (featured_word != null) {
			if (!featured_word.isVisible())
				initFeaturedWord(parent);

			featured_word.update();
			featured_word.draw();

			if (featured_word.hittingWater())
				featured_word.hitWater(splash);
		}
	}
}
