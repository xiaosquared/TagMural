package houses.vehicles;

import processing.core.PApplet;
import processing.core.PVector;
import words.Word;

public class RollingWord {
	private Wheel left_wheel;
	private Wheel right_wheel;
	private String word;
	private float word_height;
	private float word_width;
	
	private PVector bottom_left; // bottom left corner
	private float dx;
	
	PApplet parent;
	
	public RollingWord(String word, float word_height, float base_x, float base_y, float thickness, PApplet parent) {
		this(word, word_height, base_x, base_y, thickness, -2, parent);
	}
	
	public RollingWord(String word, float word_height, float base_x, float base_y, float thickness, float dx, PApplet parent) {
		this.parent = parent;
		this.word = word;
		this.word_height = word_height;
		parent.textSize(word_height);
		word_width = parent.textWidth(word); 
		
		bottom_left = new PVector(base_x, base_y);
		
		float radius = PApplet.min(word_height * 0.7f, word_width/6);
		radius = PApplet.max(20, radius);
		float edge = word_width/10;
		
		left_wheel = new Wheel(radius + edge, -radius, radius, thickness, 6, parent);
		right_wheel = new Wheel(word_width - radius - edge, -radius, radius, thickness, 6, parent);
		
		left_wheel.setup(parent);
		right_wheel.setup(parent);
		
		this.dx = dx; // amount to move by
	}
	
	public boolean tooMuchOverlap(float y) {
		return Math.abs(bottom_left.y - y) < 40 && (bottom_left.x + word_width) > parent.width/2;   
	}
	
	public String getText() {
		return word;
	}
	
	public boolean offScreenRight(PApplet parent) {
		return bottom_left.x > parent.width;
	}
	
	public boolean offScreenLeft(PApplet parent) {
		return bottom_left.x < - word_width;
	}
	
	public void translateX() {
		bottom_left.x += dx;
		float d_ang = dx / left_wheel.getRadius();
		left_wheel.turnWheelBy(d_ang);
		right_wheel.turnWheelBy(d_ang);
	}
	
	public void translateX(float dx) {
		bottom_left.x += dx;
		float d_ang = dx / left_wheel.getRadius();
		left_wheel.turnWheelBy(d_ang);
		right_wheel.turnWheelBy(d_ang);
	}
	
	public void draw(float fade_amount, PApplet parent) {
		parent.pushMatrix();
		parent.translate(bottom_left.x, bottom_left.y);
		left_wheel.draw(fade_amount, parent);
		right_wheel.draw(fade_amount, parent);
		
		parent.textSize(word_height);
		parent.fill(255 * fade_amount);
		parent.text(word, 0, -left_wheel.getDiameter()-word_height);
		parent.popMatrix();
	}
	
}
