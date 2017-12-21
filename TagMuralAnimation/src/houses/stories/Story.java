package houses.stories;

import java.util.ArrayList;

import global.ColorPalette;
import houses.elements.Window;
import houses.elements.WindowFactory;
import processing.core.PApplet;

public interface Story {
	public float getHeight();

	public ArrayList<Window> addWindows(WindowFactory.Type type, int num, 
							float top_margin, float bot_margin, float side_margin, float in_between, ColorPalette color);
	
	public Window addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color);
	public Window addDoor(WindowFactory.Type type, float left_margin, float top_margin, float d_width, ColorPalette color);
	public void addRailing(float r_height, float rail_width, float tb_rail_height, float in_between, ColorPalette color);
	public void addRailing(float r_height, ColorPalette color);
	
	public void fillAll(PApplet parent);
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent);
}
