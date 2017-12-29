package houses.vehicles;

import processing.core.PApplet;
import processing.core.PVector;
import words.Word;

public class RollingWord {
	private Wheel left_wheel;
	private Wheel right_wheel;
	private Word word;
	private float word_height;
	private float word_width;
	
	private PVector bottom_left; // bottom left corner
	
	public RollingWord(Word word, float word_height, float base_x, float base_y, float thickness, PApplet parent) {
		this.word = word;
		this.word_height = word_height;
		parent.textSize(word_height);
		word_width = parent.textWidth(word.getText()); 
		
		bottom_left = new PVector(base_x, base_y);
		
		float radius = word_height * 0.75f;
		
		left_wheel = new Wheel(radius, -radius, radius, thickness, 6, parent);
		right_wheel = new Wheel(word_width - radius, -radius, radius, thickness, 6, parent);
		
		left_wheel.setup(parent);
		right_wheel.setup(parent);
	}
	
	public void translateX(float dx) {
		bottom_left.x += dx;
		float d_ang = dx / left_wheel.getRadius();
		left_wheel.turnWheelBy(d_ang);
		right_wheel.turnWheelBy(d_ang);
	}
	
	public void draw(PApplet parent) {
		parent.pushMatrix();
		parent.translate(bottom_left.x, bottom_left.y);
		left_wheel.draw(parent);
		right_wheel.draw(parent);
		
		parent.textSize(word_height);
		parent.fill(255);
		parent.text(word.getText(), 0, -left_wheel.getDiameter()-word_height);
		parent.popMatrix();
	}
	
}
