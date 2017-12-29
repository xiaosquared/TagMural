package houses.vehicles;

import java.util.ArrayList;

import global.Settings;
import houses.bricks.Brick;
import houses.bricks.Layer;
import processing.core.PApplet;
import processing.core.PVector;
import words.Word;
import words.WordSetsManager;

public class Wheel {
	private float MIN_SPOKES_RADIUS = 40;
	
	private float r;
	private float d;
	private float inner_r;
	private PVector trans;
	private float rot = 0;
	private float thickness;
	int num_spokes;
	
	private Layer circumference;
	private Layer spoke;
	private ArrayList<Brick> c_bricks; // words on the circumference
	private ArrayList<Brick> r_bricks; // words on radii (e.g. spokes)
	
	public Wheel(float x, float y, float radius, float thickness, int num_spokes, PApplet parent) {
		r = radius;
		inner_r = r/20;
		d = radius * 2;
		this.thickness = thickness;
		trans = new PVector(x, y);
		
		this.circumference = new Layer(0, d*PApplet.PI, y + radius + 20, thickness);
		this.spoke = new Layer(0, r-inner_r, 0, thickness);
		this.num_spokes = num_spokes;
	
		c_bricks = new ArrayList<Brick>();
		r_bricks = new ArrayList<Brick>();
	}
	
	
	public float getRadius() { return r; }
	public float getDiameter() { return d; }
	
	public void setup(PApplet parent) {
		fillCircumference(parent);
		if (r > MIN_SPOKES_RADIUS)
			fillSpokes(parent);
	}
	
	public void reset(PApplet parent) {
		circumference.reset();
		spoke.reset();
		c_bricks.clear();
		
		if (r > MIN_SPOKES_RADIUS)
			r_bricks.clear();
		setup(parent);
	}
	
	public void draw(PApplet parent) {
		parent.textSize(thickness);
		parent.pushMatrix();
		parent.translate(trans.x, trans.y);
		parent.rotate(rot);
		
		drawBricksCircumference(parent);
		if (r > MIN_SPOKES_RADIUS)
			drawBricksSpokes(parent);
		
		parent.popMatrix();
	}
	
	public void translateX(float dx) {
		trans.x += dx;
		rot += dx / r;
		rot %= PApplet.TWO_PI;
	}
	
	public void turnWheelBy(float d_ang) {
		rot += d_ang;
		rot %= PApplet.TWO_PI;
	}
	
	private void fillCircumference(PApplet parent) {
		while (!circumference.isFilled()) {
			addWordBrick(circumference, c_bricks, Settings.GAP/3, parent);
		}
	}
	
	private void fillSpokes(PApplet parent) {
		for (int i = 0; i < num_spokes; i++) {
			addWordBrick(spoke, r_bricks, 0, parent);
			spoke.reset();
		}
	}
	
	private boolean addWordBrick(Layer l, ArrayList<Brick> bricks, float gap, PApplet parent) {
		Word word = WordSetsManager.getRandomWord();
		float brick_height = thickness;
		float brick_width = thickness * word.getRatio();
		Brick b = l.addWordBrick(word, brick_width + gap, 0, brick_height, parent);
		if (b != null) {
			bricks.add(b);
			return true;
		}
		return false;
	}
	
	private void drawBricksCircumference(PApplet parent) {
		for (Brick b : c_bricks) {
			 float letter_angle =  b.getMinX() / r;
			 String str = b.getText();
			 int str_len = str.length();
			 int index = 0;
			 
			 while (index < str_len) {
				 char next_letter = str.charAt(index);
				 float letter_width = parent.textWidth(next_letter);
				 float letter_x = r * PApplet.cos(letter_angle);
				 float letter_y = r * PApplet.sin(letter_angle);
				 
				 parent.pushMatrix();
				 parent.translate(letter_x, letter_y);
				 parent.rotate(letter_angle + PApplet.PI/2);
				 parent.text(next_letter, 0, 0);
				 parent.popMatrix();
				 
				 letter_angle += letter_width / r;
				 index++;
			 }
		}
	}
	
	private void drawBricksSpokes(PApplet parent) {
		if (r_bricks.size() == 0)
			return;
			
		for (int i = 0; i < num_spokes; i++) {
			float angle = PApplet.radians(360/num_spokes * i);
			parent.pushMatrix();
			parent.rotate(angle);
			parent.translate(inner_r, 0);
			parent.text(r_bricks.get(i%r_bricks.size()).getText(), 0, 0);
			parent.popMatrix();
		}
	}
	
	
	private void drawCircle(PApplet parent) {
		parent.pushMatrix();
		parent.translate(trans.x, trans.y);
		parent.rotate(rot);
		parent.noFill();
		parent.strokeWeight(thickness);
		parent.stroke(255);
		parent.ellipse(0, 0, d, d);
		
		parent.noStroke();
		parent.fill(255);
	    for (int i = 0; i < 360; i+=60) {
	    		parent.pushMatrix();
	    		parent.rotate(PApplet.radians(i));
	    		parent.rect(0, 0, r, thickness);
	    		parent.popMatrix();
	    }
	    parent.fill(0);
	    parent.ellipse(0, 0, 10, 10);
		
		parent.popMatrix();
	}
}
