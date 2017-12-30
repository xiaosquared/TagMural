package houses.elements;

import java.util.ArrayList;

import global.ColorPalette;
import houses.bricks.Brick;
import houses.bricks.Layer;
import houses.bricks.Rectangle;
import houses.bricks.Slot;
import houses.stories.Story;
import processing.core.PApplet;
import processing.core.PGraphics;
import words.Word;
import words.WordSetsManager;

public class Wall implements Fillable {
	protected static float MIN_BRICK_WIDTH = 5;
	protected static float MAX_HEIGHT_SCALE = 5;
	
	Rectangle bounding_box;
	
	protected ArrayList<Layer> layers;
	protected int layer_index = 0;
	protected float layer_thickness;
	
	protected ArrayList<Brick> bricks;
	protected boolean isFilled = false;
	
	protected ColorPalette color = ColorPalette.CYAN;
	
	public Wall(float x, float y, float width, float height) {
		bounding_box = new Rectangle(x, y, width, height);
		bricks = new ArrayList<Brick>();
	}
	
	public Wall(float x, float y, float width, float height, ColorPalette color) {
		this(x, y, width, height);
		this.color = color;
	}
	
	public Wall(float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		this(x, y, width, height);
		this.color = color;
		
		this.layer_thickness = layer_thickness;
		layers = new ArrayList<Layer>();
		int num_layers = PApplet.floor(height/layer_thickness);
		for (int i = 0; i < num_layers; i++) {
			float layer_y = y + height - (i * layer_thickness);
			layers.add(new Layer(x, x+width, layer_y, layer_thickness));
		}
	}
	
	// TODO: SET & GET Methods!!
	// if I want to reset color, don't forget to change all the bricks
	public ArrayList<Layer> getLayers() { return layers; }
	public ArrayList<Brick> getBricks() { return bricks; }
	public float getMinX() { return bounding_box.getMinX(); }
	public float getMinY() { return bounding_box.getMinY(); }
	public float getMaxX() { return bounding_box.getMaxX(); }
	public float getMaxY() { return bounding_box.getMaxY(); }
	public float getWidth() { return bounding_box.getWidth(); }
	public float getHeight() { return bounding_box.getHeight(); }
	public float getLayerThickness() { return layer_thickness; }
	public ColorPalette getColor() { return color; }
	
	// TODO: don't forget about redoing windows. might not be here though....
	public void reset() {
		for (Layer l : layers)
			l.reset();
		bricks.clear();
		isFilled = false;
		layer_index = 0;
	}
	
	public void removeSection(float lower_bound, float upper_bound) {
		for (Layer l : layers) {
			l.makeHole(lower_bound, upper_bound);
		}
	}
	
	public boolean addWordBrick(Word word, boolean featured, PApplet parent) {
		return addWordBrick(word, featured, true, parent);
	}
	
	public boolean addWordBrick(Word word, boolean featured, boolean visibility, PApplet parent) {
		if (isFilled || layers.size() == 0)
			return false;
		
		float height_scale = 1;
		if (featured) 
			height_scale = PApplet.floor(parent.random(1, (PApplet.min(MAX_HEIGHT_SCALE, layers.size()-layer_index))));
		
		float brick_height = layer_thickness * height_scale;
		float brick_width = brick_height * word.getRatio();
		
		Layer current_layer = layers.get(layer_index);
		Brick brick = current_layer.addWordBrick(word, brick_width, MIN_BRICK_WIDTH, brick_height, parent);
		
		if (brick == null)
			return false;
		
		brick.setVisibility(visibility);
		bricks.add(brick);
		brick.setColor(color.getColorVariation(parent));
		
		if (height_scale > 1) {
			for (int i = 0; i < height_scale; i++) {
				Layer upper_layer = layers.get(layer_index + i);
				ArrayList<Slot> overlaps = upper_layer.getOverlappingSlots(brick.getLowerBound(), brick.getUpperBound());
				if (overlaps.size() > 0) {
					for (Slot upper_slot : overlaps)
						upper_layer.subdivideSlot(upper_slot, brick.getLowerBound(), brick.getUpperBound(), MIN_BRICK_WIDTH);
				}
			}
		}
		return true;
	}
	
	public boolean isFilled() {
		return isFilled;
	}
	
	public void fillAll(PApplet parent) {
		fillAll(parent, true);
	}
	
	public void fillAll(PApplet parent, boolean visibility) {
		while(!isFilled) { fillByLayer(parent, visibility); }
	}
	
	public void fillByLayer(PApplet parent) {
		fillByLayer(parent, true);
	}
	
	public void fillByLayer(PApplet parent, boolean visibility) {
		addWord(parent, visibility);
		checkLayer();
	}
	
	public void unfill() {
		if (bricks.size() > 0)
			bricks.remove(0);
//		for (Brick b : bricks) {
//			b.setVisibility(false);
//		}
	}
	
	private void addWord(PApplet parent, boolean visibility) {
		Word word = WordSetsManager.getRandomWord();
		if (!addWordBrick(word, true, visibility, parent))
			addWordBrick(word, false, visibility, parent);
	}
	
	private void checkLayer() {
		if (currentLayerFull())
			advanceLayerIndex();
	}
	
	private boolean currentLayerFull() {
		return layers.get(layer_index).isFilled();
	}
	
	private void advanceLayerIndex() {
		if (layer_index + 1 == layers.size())
			isFilled = true;
		else
			layer_index++;
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		if (outline)
			drawOutline(parent);
		if (layers)
			drawLayers(parent);
		if (words)
			drawWords(parent);
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PGraphics parent) {
		if (outline)
			drawOutline(parent);
		if (layers)
			drawLayers(parent);
		if (words)
			drawWords(parent);
	}
	
	private void drawOutline(PApplet parent) {
		parent.stroke(200);
		parent.noFill();
		bounding_box.draw(parent);
	}
	private void drawOutline(PGraphics parent) {
		parent.stroke(200);
		parent.noFill();
		bounding_box.draw(parent);
	}
	
	private void drawLayers(PApplet parent) {
		parent.noStroke();
		parent.fill(color.hue(), 100, 100);
		for (Layer l : layers) 
			l.draw(parent);
	}
	private void drawLayers(PGraphics parent) {
		parent.noStroke();
		parent.fill(color.hue(), 100, 100);
		for (Layer l : layers) 
			l.draw(parent);
	}
	
	private void drawWords(PApplet parent) {
		for (Brick b : bricks)
			b.draw(parent);
	}
	private void drawWords(PGraphics parent) {
		for (Brick b : bricks)
			b.draw(parent);
	}
}
