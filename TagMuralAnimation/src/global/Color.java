package global;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Color {
	private int hue;
	private int saturation;
	private int lightness;
	
	private int shade;
	
	public static void setHSB(PApplet parent) {
		parent.colorMode(PApplet.HSB, 360, 100, 100);
	}

	public Color(int hue, int saturation, int lightness, int shade) {
		this.hue = hue;
		this.saturation = saturation;
		this.lightness = lightness;
		this.shade = shade;
	}
	
	public int getHue() { return hue; }
	public int getSaturation() { return saturation; }
	public int getLightness() { return lightness; }
	public int getShade() { return shade ; }
	
	public void setHue(int hue) { this.hue = hue;}
	public void setSaturation(int saturation) { this.saturation = saturation;}
	public void setLightness(int lightness) { this.lightness = lightness;}
	
	public void fill(PApplet parent) { parent.fill(hue, saturation, lightness); }
	public void fill(PGraphics parent) { parent.fill(hue, saturation, lightness); }
	public void fill(PApplet parent, float opacity) { parent.fill(hue, saturation, lightness, opacity); }
	
	public void fillBW(PApplet parent) { parent.fill(shade); }
	public void fillBW(PGraphics parent) { parent.fill(shade); }
	public void fillBW(PApplet parent, float opacity) { parent.fill(shade, opacity); }
	
	public void stroke(PApplet parent) { parent.stroke(hue, saturation, lightness); }
	public void stroke(PGraphics parent) { parent.stroke(hue, saturation, lightness); }
	
	public void strokeBW(PApplet parent) { parent.stroke(shade); }
	public void strokeBW(PGraphics parent) { parent.stroke(shade); }
	
}
