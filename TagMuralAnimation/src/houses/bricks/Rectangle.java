package houses.bricks;

import processing.core.PApplet;
import processing.core.PVector;

public class Rectangle {
	protected PVector tl;  // top left
	protected PVector tr;  // top right
	protected PVector bl;  // bottom left
	protected PVector br;  // bottom right
	protected float width;
	protected float height;

	public Rectangle(float x, float y, float width, float height) {
		tl = new PVector(x, y);
		tr = new PVector(tl.x + width, tl.y);
		bl = new PVector(tl.x, tl.y + height);
		br = new PVector(tr.x, tr.y + height);
		this.width = width;
		this.height = height;
	}

	public Rectangle(PVector tl, PVector br) {
		System.out.println(tl.x + ", " + tl.y);
		System.out.println(br.x + ", " + br.y);
		this.tl = tl;
		this.tr = new PVector(br.x, tl.y);
		this.bl = new PVector(tl.x, br.y);
		this.br = br;

		this.width = br.x - tl.x;
		this.height = br.y - tl.y;
	}

	public float getMinX() { return tl.x; }
	public float getMinY() { return tl.y; }
	public float getMaxX() { return tr.x; }
	public float getMaxY() { return bl.y; }

	public void draw(PApplet parent) { 
		parent.rect(tl.x, tl.y, width, height); 
	}

	public boolean contains(PVector p) {
		return this.contains(p.x, p.y);
	}

	public boolean contains(float x, float y) {
		return (x > tl.x) && (x < tr.x) && (y > tl.y) && (y < bl.y);
	}

	public void printInfo() {
		System.out.println("Top Left: " + tl.x + ", " + tl.y);
		System.out.println("Top Right: " + tr.x + ", " + tr.y);
		System.out.println("Bottom Left: " + bl.x + ", " + bl.y);
		System.out.println("Bottom Right: " + br.x + ", " + br.y);
		System.out.println("width: " + width + ", height: " + height); 
	}
}