import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PVector;
import particles.Particle;

public class ProcessingTest extends PApplet {
	
	ArrayList<Particle> particles;
	

	public void settings(){
        size(1200,800);
    }

    public void setup(){
        background (0);
        
        particles = new ArrayList<Particle>();
    }

    public void draw(){
    		background(0);
    	
    		Iterator<Particle> it = particles.iterator();
    		while (it.hasNext()) {
    			Particle p = it.next();
    			p.run();
    			if (p.isDead()) {
    				it.remove();
    			}
    		}
    	
    		if (random(10) < 1) {
    			Particle p = new Particle(new PVector(random(width), -20), this);
    			particles.add(p);
    		}
    }
    
    public static void main(String[] args) { PApplet.main("ProcessingTest"); }
}
