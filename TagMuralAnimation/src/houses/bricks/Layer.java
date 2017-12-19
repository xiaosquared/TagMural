package houses.bricks;

import java.util.ArrayList;

import processing.core.PApplet;
import words.Word;

/*
 * @position specifies y if layer is horizontal (isVertical = false),
 * 			 specifies x if layer is vertical (isVertical = true)
 */
public class Layer {
	private final float GAP = 6;
	private float lower_bound;
	private float upper_bound;
	private float position;
	private float thickness;
	private float length;
	
	ArrayList<Slot> slots;
	
	boolean isVertical = false;
	
	public Layer(float lower_bound, float upper_bound, float position, float thickness, boolean isVertical) {
		this.lower_bound = PApplet.min(lower_bound, upper_bound);
		this.upper_bound = PApplet.max(lower_bound, upper_bound);
		this.position = position;
		this.thickness = thickness;
		this.length = upper_bound - lower_bound;
		this.isVertical = isVertical;
		
		slots = new ArrayList<Slot>();
		if (isVertical)
			slots.add(new SlotVertical(lower_bound, upper_bound));
		else 
			slots.add(new Slot(lower_bound, upper_bound));
	}
	
	public Layer(float lower_bound, float upper_bound, float position, float thickness) {
		this(lower_bound, upper_bound, position, thickness, false);
	}
	
	public float getLowerBound() { return lower_bound; }
	public float getUpperBound() { return upper_bound; }
	public float getPosition() { return position; }
	public float getThickness() { return thickness; }
	public float getLength() { return length; }
	
	public void extendLower(float amount) {
		lower_bound -= amount;
		length = upper_bound - lower_bound;

		slots = new ArrayList<Slot>();
		slots.add(new Slot(lower_bound, upper_bound));
	}

	public void extendUpper(float amount) {
		upper_bound += amount;
		length = upper_bound - lower_bound;

		slots = new ArrayList<Slot>();
		slots.add(new Slot(lower_bound, upper_bound));
	}
	
	public void reset() {
		slots.clear();
		if (isVertical)
			slots.add(new SlotVertical(lower_bound, upper_bound));
		else
			slots.add(new Slot(lower_bound, upper_bound));
	}
	
	public boolean isFilled() {
		return slots.isEmpty();
	}
	
	public void makeHole(float left_bound, float right_bound, float gap) {
		ArrayList<Slot> overlaps = getOverlappingSlots(left_bound, right_bound);
		if (overlaps.size() > 0) {
			for (Slot s : overlaps)
				subdivideSlot(s, left_bound - gap, right_bound + gap, 1);
		}
	}
	
	public ArrayList<Slot> getOverlappingSlots(float left_bound, float right_bound) {
		ArrayList<Slot> overlaps = new ArrayList<Slot>();
		for (Slot s : slots) {
			if (s.getLeft() <= right_bound && s.getRight() >= left_bound) {
				overlaps.add(s);
			}
		}
		return overlaps;
	}
	
	public Brick addWordBrick(Word word, float brick_width, float min_brick_width, float brick_height, PApplet parent) {
		if (isFilled())
			return null;
		
		Brick brick;
		for (int i = 0; i < slots.size(); i++) {
			Slot s = slots.get(i);
			float slot_size = s.getDistance();
			
			if (slot_size < min_brick_width || s.failedTooMuch()) {
				slots.remove(s);
			}
			else if (slot_size >= brick_width) {
				float brick_position = parent.random(s.getLeft(), s.getRight() - brick_width);
				brick = isVertical ?
						new BrickVertical(position - brick_height, brick_position, brick_height, brick_width, word)
					  : new Brick(brick_position, position - brick_height, brick_width, brick_height, word);
						
				subdivideSlot(s, brick_position, brick_position + brick_width, min_brick_width);
				return brick;
			}
			else
				s.incFailure();
		}
		return null;
	}
	
	public void subdivideSlot(Slot s, float brick_left, float brick_right, float min_width) {
		if (brick_left - s.getLeft() > min_width) {
			if (s instanceof SlotVertical)
				slots.add(new SlotVertical(s.getLeft(), brick_left - GAP));
			else
				slots.add(new Slot(s.getLeft(), brick_left - GAP));
		}
		if (s.getRight() - brick_right > min_width) {
			if (s instanceof SlotVertical)
				slots.add(new SlotVertical(brick_right + GAP, s.getRight()));
			else
				slots.add(new Slot(brick_right + GAP, s.getRight()));
		}
		slots.remove(s);
	}
	
	public void draw(PApplet parent) {
		for (Slot s : slots) {
			s.draw(position, 2, parent);
		}
	}
}
