package filling;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

import processing.core.PApplet;
import processing.core.PFont;

/*
 * 11.7.17
 * 
 * Testing filling a 1 dimensional interval with rectangles of fixed size
 * Recursively places a rectangle at a random location, which subdivides the interval
 * Continues the algorithm until one or both of the split sides become too small
 * 
 */

public class Fill_IntervalWithFixedWidthRects extends PApplet {

	MutableList<Pair> all_spaces;
	int min_space_size = 5;
	
	MutableList<Rectangle> rectangles;
	
	int y = 400;
	int draw_spaces_y_position = 510;
	
	
	PFont font;
	
	public void settings() {
		size(1200, 800, P2D);
	}
	
	public void setup() {
		stroke(250);
		fill(200, 150, 150, 100);
	
		all_spaces = FastList.newListWith(new Pair(100, 1100));
		rectangles = FastList.newList();
		
	}
	
	public void draw() {
		//background(150, 200, 200);
	}
	
	
	public void fillIntervalWithRectangles(Pair space, float min_space_size, float fits_multi_word, float max_word_size) {
		// if space is too small, don't put anything there
		if (space.getDistance() < min_space_size) {
			all_spaces.remove(space);
			return;
		}
		// if space only fits one "word"
		if (space.getDistance() < fits_multi_word) {
			rectangles.add(new Rectangle(space.getLeft() + 3, y, space.getDistance() - 3, 20, this));
			all_spaces.remove(space);
			return;
		}	
		
		// if space fits more than one "word"
		
		int position = (int)random(space.getLeft(), space.getRight()-max_word_size);
		int rect_width = (int) random(min_space_size, space.getRight() - position);
		
		if (position - space.getLeft() > min_space_size) {
			all_spaces.add(new Pair(space.getLeft(), position));
		}
		if (space.getRight() - (position + rect_width) > min_space_size) {
			all_spaces.add(new Pair(position+rect_width, space.getRight()));
		}
		all_spaces.remove(space);
		
		rectangles.add(new Rectangle(position, y, rect_width, 20, this));
	}
	
	public void drawSpace(Pair space) {
		fill(220, 160, 220, 100);
		rect(space.getLeft(), draw_spaces_y_position, space.getRight()-space.getLeft(), 10);
	}
	
	public void drawRectangles() {
		stroke(250);
		fill(200, 150, 150, 100);
		rectangles.each(each -> each.draw());
	}
	
	public void keyPressed() {
		background(150, 200, 200);
		println(key);
		
		fillIntervalWithRectangles(all_spaces.get(0), 100, 400, 200);
		
		println(rectangles.size());
		
		drawRectangles();
		all_spaces.each(each -> drawSpace(each));
	}
	
	
    public static void main(String[] args) { 
		PApplet.main("filling.Fill_IntervalWithFixedWidthRects"); 
    }
}
