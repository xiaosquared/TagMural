package test;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import water.FloatingWaveText;
import water.RainGroup;
import water.Splash;
import water.WaveGroup;

public class WaveTest extends PApplet {
	
	WaveGroup waves; 
	RainGroup rains;
	Splash splash;
	boolean isRaining = false;
	
	String phrase = "Vieux carré\nMardi Gras\nMississippi River\nLake Pontchartrain\nWar of 1812\nSt. Louis Cathedral\nCongo Square\nbamboula\nTreme\nMarigny\nlevee\nStoryville\nLouis Armstrong\nJelly-Roll Morton\nCreole\nBayou St. John\nJean Lafitte\nFrench Market\nUptown\nhurricane";
	String[] words = new String[20];
	PFont font;
	int font_size = 100;
	
	FloatingWaveText featured_word;
	
	public void settings(){
        //size(1200,800, P2D);
        fullScreen(P2D);
    }

    public void setup(){    	
        background(0);
        
        Ani.init(this);
        
        initWords();
        waves = new WaveGroup(5, new PVector(-20, height-220), new PVector(width+200, height-20), 4, 220, 50, this);
        waves.initText(words, 16, 36);
        
        splash = new Splash(this);
        rains = new RainGroup(waves, splash, words, 12, this);
        
        initFeaturedWord();
    }

    public void draw(){
    		background(0);
    	
   
    		waves.update();
    		waves.draw(true);
    		splash.run(true);
    		rains.run(true, isRaining);
    		
    		updateFeaturedWord();
    }
    
    
    void initWords() {
    	  words = split(phrase, '\n');
    	  font = createFont("American Typewriter", font_size);
    	  textFont(font, font_size);  
    	}
    
    void initFeaturedWord() {
    	  featured_word = new FloatingWaveText(words[(floor(random(words.length)))], random(70, 90), random(0, width*.66f), -20, waves.getWave(0), this);
    	}
    
    void updateFeaturedWord() {
    		if (featured_word != null) {
            if (!featured_word.isVisible())
              initFeaturedWord();
            
            featured_word.update();
            featured_word.draw();
            
            if (featured_word.hittingWater())
            	featured_word.hitWater(splash);
          }
    }
    
    public void keyPressed() {
    		println(key);
    		switch(key) {
    			case 'r':
    				isRaining = !isRaining;
    				if (isRaining)
    					rains.restart();
    			break;
    			
    		}
    }
    
    public static void main(String[] args) { 
    		PApplet.main("test.WaveTest"); 
    }
}
