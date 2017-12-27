package global;

import processing.core.PApplet;

public enum ColorPalette {
	RED(0, 150),
	YELLOW(60, 250),
	GREEN(120, 200),
	CYAN(180, 250),
	BLUE(240, 120),
	MAGENTA(300, 150),
	BLACK(0, 0);
	
	private final int hue;
	private final int shade;
	
	ColorPalette(int hue, int shade) {
		this.hue = hue;
		this.shade = shade;
	}
	
	public int hue() { return hue; }
	public int shade() { return shade; }
	
	public Color getColor() {
		Color c = new Color(hue, 100, 100, shade);
		return c;
	}
	
	public Color getColorVariation(PApplet parent) {
		int saturation = PApplet.floor(parent.random(50, 100));
		int lightness = PApplet.floor(parent.random(50, 100));
		int vari_shade = PApplet.floor(parent.random(shade, shade-30));
		vari_shade = PApplet.max(50, shade);
		
		return new Color(hue, saturation, lightness, vari_shade);
	}
}
