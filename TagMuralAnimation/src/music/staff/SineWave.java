package music.staff;

import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PVector;
import words.Word;
import words.WordSetsManager;

public class SineWave {
	PVector origin;
	float width;
	float trans_x = 0;
	
	SineTerm[] terms;
	PVector[] points; 
	
	LinkedList<SineText> texts;
	float font_size = 0;
	
	int NUM_TERMS = 5;
	float SHADE = 200;
	float GAP = 5;
	
	public SineWave(PVector origin, float width, float font_size, SineTerm[] sine_terms, PApplet parent) {
		this.origin = origin;
		this.width = width;
		this.terms = sine_terms;
		initPoints();
		initText(font_size, parent);
	}
	
	public SineWave(PVector origin, float width, float font_size, PApplet parent) {
		this.origin = origin;
		this.width = width;
		initSineTerms(parent);
		initPoints();
		initText(font_size, parent);
	}
	
	/////////////////////////////////////////////////////////////////////	
	
	private void initPoints() {
		points = new PVector[PApplet.floor(width)];
		for (int i = 0; i < width; i++) {
			PVector p = new PVector(i + origin.x, 0);
			points[i] = p;
		}
	}
	
	private void initSineTerms(PApplet parent) {
		terms = new SineTerm[NUM_TERMS];
		for (int i = 0; i < NUM_TERMS; i++) {
			SineTerm st = new SineTerm(parent.random(0.1f, 0.2f), parent.random(200, 500), parent.random(0, PApplet.TAU));
			terms[i] = st;
		}
	}
	
	private void initText(float font_size, PApplet parent) {
		this.font_size = font_size;
		parent.textSize(font_size);
		texts = new LinkedList<SineText>();
		
		int selected_pt = 0;
		while(selected_pt < width) {
			Word w = WordSetsManager.getRandomWord();
			SineText st = new SineText(w.getText(), font_size, selected_pt);
			selected_pt += (int) parent.textWidth(w.getText());
			if (selected_pt < points.length)
				texts.add(st);
			selected_pt += GAP;
		}
	}
	
	/////////////////////////////////////////////////////////////////////
	
	public SineTerm[] getSineTerms() { return terms; }
	public int getNumPoints() { return points.length; }
	float getTransY() { return origin.y; }
	public float getMaxX() { return origin.x + width; }
	public float getFontSize() { return font_size; }
	
	/////////////////////////////////////////////////////////////////////
	
	public void update() {
		trans_x--;
		float y = 0;
		for (int i = 0; i < width; i++) {
			float x = i + origin.x + trans_x;
			y = evalSines(x, y);
			points[i].set(x-trans_x, y);
		}
	}
	
	private float evalSines(float x, float y) {
		for (int i = 0; i < terms.length; i++) {
			y += terms[i].evaluate(x);
		}
		return y;
	}
	
	public void draw(float taper_width, PApplet parent) {
		parent.pushMatrix();
		parent.translate(0, origin.y);
		
		//drawPoints(taper_width, parent);
		drawText(taper_width, parent);
		
		parent.popMatrix();
	}
	
	private void drawText(float taper_width, PApplet parent) {
		if (texts != null) {
			for (SineText st : texts)
				st.draw(points, taper_width, parent);
		}
	}
	
	private void drawPoints(float taper_width, PApplet parent) {
		for (int i = 0; i < points.length; i++) {
			if (i <= taper_width) {
				float alpha = (float) i/taper_width * 255;
				parent.stroke(SHADE, alpha);
				parent.fill(SHADE, alpha);
			} 
			
			else if (i > points.length - taper_width) {
				float alpha = (float) SHADE - ((i - (points.length - taper_width)) / taper_width * SHADE);
				parent.stroke(SHADE, alpha);
				parent.fill(SHADE, alpha);
			}
			
			else {
				parent.stroke(SHADE);
				parent.fill(SHADE);
			}
			parent.ellipse(points[i].x, points[i].y, 1, 1);
		}
	}
}
