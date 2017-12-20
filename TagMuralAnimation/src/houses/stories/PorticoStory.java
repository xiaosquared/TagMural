package houses.stories;

import java.util.ArrayList;

import global.ColorPalette;
import houses.elements.Column;
import houses.elements.Window;
import houses.elements.WindowFactory;
import processing.core.PApplet;

public class PorticoStory implements Story {
	
	ArrayList<PlainStory> panels;
	ArrayList<Column> columns;
	
	public PorticoStory(float x, float y, float width, float height, int num, float col_width, float layer_thickness, 
					ColorPalette wall_color, ColorPalette column_color) {
		
		panels = new ArrayList<PlainStory>();
		float panel_width = (width - (col_width * (num+1))) / num;
		for (int i = 0; i < num; i++) {
			float wall_x = col_width + i * (col_width + panel_width);
			PlainStory panel = new PlainStory(x + wall_x, y, panel_width - layer_thickness, height, layer_thickness, wall_color);
			panels.add(panel);
		}
		
		columns = new ArrayList<Column>();
		for (int i = 0; i < num+1; i++) {
			float col_x = i * (col_width + panel_width);
			float col_height_addition = (num==1) ? height / 8 : 0;
			Column col = new Column(x + col_x, y-col_height_addition, col_width, height + col_height_addition, layer_thickness, column_color);
			columns.add(col);
		}
	}
	
	public Window addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color) {
		return null;
	}
	
	public Window addDoor(WindowFactory.Type type, float left_margin, float top_margin, float d_width, ColorPalette color) {
		return null;
	}
	
	public void addRailing(float r_height, float rail_width, float tb_rail_height, float in_between, ColorPalette color) {
		
	}
	
	public void fillAll(PApplet parent) {
		for (PlainStory p : panels)
			p.fillAll(parent);
		for (Column c : columns)
			c.fillAll(parent);
	}
	
	public void draw(boolean outline, boolean layers, boolean words, PApplet parent) {
		for (PlainStory p : panels)
			p.draw(outline, layers, words, parent);
		for (Column c : columns)
			c.draw(outline, layers, words, parent);
	}
}
