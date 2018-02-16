package music.musicians;

import java.util.ArrayList;

import global.ColorPalette;
import houses.bricks.Layer;
import houses.bricks.Slot;
import houses.elements.Wall;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/*
 * Basically a wrapper around the existing Wall class, 
 * which already has the functionality for filling with words
 */
public class WordImage {
	PVector trans = new PVector(0, 0);
	
	ArrayList<PImage> imgs;
	ArrayList<Wall> imgWalls;
	ColorPalette color;
	float x_unit;
	float y_unit;
	float x_scale = 1f;
	float y_scale = 1f;
	
	int current_img = 0;
	
	boolean bAnimate = false;
	
	public WordImage(PImage img, float x_unit, float y_unit, ColorPalette color, PApplet parent) {
		imgs = new ArrayList<PImage>();
		imgWalls = new ArrayList<Wall>();
		this.color = color;
		this.x_unit = x_unit;
		this.y_unit = y_unit;
		Wall w = new Wall(imgToLayers(img, x_unit, y_unit), color);
		w.fillAll(parent);
		
		imgs.add(img);
		imgWalls.add(w);
	}
	
	public void addImage(PImage img, PApplet parent) {
		Wall w = new Wall(imgToLayers(img, x_unit, y_unit), color);
		w.fillAll(parent);
		imgs.add(img);
		imgWalls.add(w);
		bAnimate = true;
	}
	
	public boolean isAnimated() { return bAnimate; }
	
	public void reset(PApplet parent) {
		imgWalls.clear();
		for (int i = 0; i < imgs.size(); i++) {
			Wall w = new Wall(imgToLayers(imgs.get(i), x_unit, y_unit), color);
			w.fillAll(parent);
			imgWalls.add(w);
		}
	}
	
	public void nextFrame() {
		current_img ++;
		current_img %= imgWalls.size();
	}
	
	public void setTranslation(float x, float y) {
		trans.x = x; trans.y = y;
	}
	
	public void setScale(float x_scale, float y_scale) { 
		this.x_scale = x_scale;
		this.y_scale = y_scale;
	}
	
	private ArrayList<Layer> imgToLayers(PImage img, float x_unit, float y_unit) {
		ArrayList<Layer> layers = new ArrayList<Layer>();
		for (int y = img.height-1; y >= 0; y -= y_unit) {
			ArrayList<Slot> slots = new ArrayList<Slot>();
			
			// start with a placeholder slot
			Slot s = null;
			boolean isPrevBlack = false;
			
			for (int x = 0; x < img.width; x+= x_unit) {
				int c = img.pixels[y * img.width + x];
				boolean isBlack = isBlack(c);
				
				// if we are at the end of the row, create the slot
				if (x + x_unit >= img.width && s != null) {
					slots.add(new Slot(s.getLeft(), s.getRight()));
				}
				
				// otherwise, if the current unit is black
				else if (isBlack) {
					if (s == null)
						s = new Slot(x, x + x_unit);
					else
						s.setRight(x + x_unit);
				}
				
				// if current unit is white, we don't care unless it just transitioned from black
				// then it's time to close the previous slot and start a new placeholder slot
				else if (isPrevBlack) {
					if (s != null) {
						slots.add(new Slot(s.getLeft(), s.getRight()));
						s = null;
					}
				}
				isPrevBlack = isBlack;
			}
			if (slots.size() > 0)
				layers.add(new Layer(slots, y, y_unit));
		}
		return layers;
	}
	
	private boolean isBlack(int c) {
		return (c >> 16 & 0xFF) == 0;
	}
	
	public void draw(PApplet parent) {
		draw(parent, 255);
	}
	
	public void draw(PApplet parent, float opacity) {
		parent.pushMatrix();
		parent.translate(trans.x, trans.y);
		parent.scale(x_scale, y_scale);
		imgWalls.get(current_img).draw(parent, opacity);
		parent.popMatrix();
	}
}
