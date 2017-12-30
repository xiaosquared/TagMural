package houses.block;

import java.util.ArrayList;
import java.util.HashSet;

import global.ColorPalette;
import global.Settings;
import houses.block.OneStoryHouse.DoorLayout;
import houses.bricks.Brick;
import houses.elements.Wall;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class Block {
	
	private int MIN_UNITS = 2;
	private int MAX_UNITS = 5;
	
	private PVector origin;
	private	ArrayList<House> houses;
	private Wall sidewalk;
	
	public Block(float x, float y, float sidewalk_width, float sidewalk_height) {
		origin = new PVector(x, y);
		houses = new ArrayList<House>();
		sidewalk = new Wall(origin.x, origin.y + Settings.LAYER_THICKNESS * 2, sidewalk_width, sidewalk_height, Settings.LAYER_THICKNESS, ColorPalette.RED);
	}
	
	public Block(float y, float sidewalk_width, float sidewalk_height, PApplet parent) {
		this((parent.width - sidewalk_width)/2, y, sidewalk_width, sidewalk_height);
	}
	
	public float getSidewalkWidth() { return sidewalk.getWidth();	}
	
	public void clear() { houses.clear(); }
	
	// fill block with houses
	public void populateBlock(PApplet parent) {
		ArrayList<HouseInfo> block_divisions = divideBlock(getSidewalkWidth(), 80);
		
		
		float x = (parent.width - sumWidths(block_divisions))/2;

		for (HouseInfo info : block_divisions) {
			House h = HouseFactory.makeHouse(info, x, origin.y);
			houses.add(h);
			x += h.getWidth();
		}
	}

	private float sumWidths(ArrayList<HouseInfo> houses_info) {
		int total = 0;
		for (HouseInfo hi : houses_info)
			total += hi.getWidth();
		return total;
	}

	private ArrayList<HouseInfo> divideBlock(float block_width, float min_sides) {
		ArrayList<HouseInfo> divisions = new ArrayList<HouseInfo>();
		while (block_width > min_sides) {
			HouseType type = HouseType.getRandomType();
			float h_width = type.getWidth();
			float h_height = Settings.getStoryHeightWithVar();			

			if (h_width > block_width)
				break;
			
			HouseInfo info = new HouseInfo(type, h_height, Settings.getRoofHeight());
			divisions.add(info);
			
			block_width -= h_width;
		}
		
		divisions.get(0).setPositionType(HouseInfo.PositionType.LEFT_EDGE);
		divisions.get(divisions.size()-1).setPositionType(HouseInfo.PositionType.RIGHT_EDGE);
		
		return divisions;
	}

	public boolean isFilled() {
		for (House h : houses) {
			if (!h.isFilled())
				return false;
		}
		return true;
	}
	
	public void fillAll(PApplet parent) {
		fillAll(parent, true);
	}
	
	public void fillAll(PApplet parent, boolean visibility) {
		for (House h : houses)
			h.fillAll(parent, visibility);
		sidewalk.fillAll(parent, visibility);
	}
	
	public void fillByLayer(PApplet parent) {
		fillByLayer(parent, true);
	}
	
	public void fillByLayer(PApplet parent, boolean visibility) {
		if (sidewalk != null && !sidewalk.isFilled())
			sidewalk.fillByLayer(parent, visibility);
		else {
			for (House h : houses)
				h.fillByLayer(parent, visibility);
		}
	}
	
	public HashSet<Brick> getAllBricks() {
		HashSet<Brick> bricks = new HashSet<Brick>();
		for (House h : houses) {
			bricks.addAll(h.getAllBricks());
		}
		bricks.addAll(sidewalk.getBricks());
		return bricks;
 	}
	
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		for (House h : houses)
			h.draw(outline, layers, words, parent);
		sidewalk.draw(outline, layers, words, parent);
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PGraphics parent) {
		for (House h : houses)
			h.draw(outline, layers, words, parent);
		sidewalk.draw(outline, layers, words, parent);
	}
}
