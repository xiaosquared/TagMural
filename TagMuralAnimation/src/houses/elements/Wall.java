package houses.elements;

import java.util.ArrayList;

import houses.bricks.Brick;
import houses.bricks.Layer;
import houses.bricks.Rectangle;
import houses.bricks.Slot;
import processing.core.PApplet;
import words.Word;
import words.WordSetsManager;

public class Wall {
	private static float MIN_BRICK_WIDTH = 5;
	private static float MAX_HEIGHT_SCALE = 5;
	
	Rectangle bounding_box;
	
	private ArrayList<Layer> layers;
	private int layer_index = 0;
	private float layer_thickness;
	
	private ArrayList<Brick> bricks;
	private boolean isFilled = false;
	
	// TODO: Deal with color... prob make it an enum for a set pallett?
	
	public Wall(float x, float y, float width, float height) {
		bounding_box = new Rectangle(x, y, width, height);
		bricks = new ArrayList<Brick>();
	}
	
	public Wall(float x, float y, float width, float height, float layer_thickness) {
		this(x, y, width, height);
		
		this.layer_thickness = layer_thickness;
		layers = new ArrayList<Layer>();
		int num_layers = PApplet.floor(height/layer_thickness);
		for (int i = 0; i < num_layers; i++) {
			float layer_y = y + height - (i * layer_thickness);
			layers.add(new Layer(x, x+width, layer_y, layer_thickness));
		}
	}
	
	// TODO: SET & GET Methods!!
	
	// TODO: don't forget about redoing windows. might not be here though....
	public void reset() {
		for (Layer l : layers)
			l.reset();
		bricks.clear();
		isFilled = false;
		layer_index = 0;
	}
	
	public boolean addWordBrick(Word word, boolean featured, PApplet parent) {
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
		
		bricks.add(brick);
		// TODO: deal with color HERE!
		
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
	
	public void fillAll(PApplet parent) {
		while(!isFilled) { fillByLayer(parent); }
	}
	
	public void fillByLayer(PApplet parent) {
		addWord(parent);
		checkLayer();
	}
	
	private void addWord(PApplet parent) {
		Word word = WordSetsManager.getRandomWord();
		if (!addWordBrick(word, true, parent))
			addWordBrick(word, false, parent);
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
	
	private void drawOutline(PApplet parent) {
		parent.stroke(200);
		parent.noFill();
		bounding_box.draw(parent);
	}
	
	// TODO: COLOR!!
	private void drawLayers(PApplet parent) {
		parent.noStroke();
		parent.fill(100, 100, 100);
		for (Layer l : layers) 
			l.draw(parent);
	}
	
	// TODO: COLOR!! for b.draw - also where do I set bw mode??
	private void drawWords(PApplet parent) {
		for (Brick b : bricks)
			b.draw(false, false, parent);
	}
}
