package houses.bricks;

import processing.core.PApplet;
import processing.core.PGraphics;

//TODO: better abstraction for failure_count & to_remove

public class Slot {
	private float left;
	private float right;
	private float distance;
	private int failure_count;

	public Slot(float left, float right) {
		this.left = PApplet.min(left, right);
		this.right = PApplet.max(left, right);
		distance = this.right - this.left;
	}
	
	public float getLeft() {
		return left;
	}
	
	public float getRight() {
		return right;
	}
	
	public void setLeft(float val) {
		left = val;
		distance = right - left;
	}
	
	public void setRight(float val) {
		right = val;
		distance = right - left;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void draw(float y, float thickness, PApplet parent) { parent.rect(left,  y, distance,  thickness); }
	public void draw(float y, float thickness, PGraphics parent) { parent.rect(left,  y, distance,  thickness); }
	
	public boolean contains(float val) {
		return val >=left && val <=right;
	}
	
	public void incFailure() {
		failure_count ++;
	}
	
	public boolean failedTooMuch() {
		return failure_count > 3;
	}
}
