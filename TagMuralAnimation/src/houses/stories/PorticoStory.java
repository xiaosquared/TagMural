package houses.stories;

import java.util.ArrayList;

import global.ColorPalette;
import houses.elements.Column;
import houses.elements.Window;
import houses.elements.WindowFactory;
import houses.stories.HouseInfo.PositionType;
import processing.core.PApplet;

public class PorticoStory implements Story {
	
	ArrayList<PlainStory> panels;
	ArrayList<Column> columns;
	
	HouseInfo.PositionType p_type; 
	
	public PorticoStory(float x, float y, float width, float height, int num, float col_width, float layer_thickness, 
					ColorPalette wall_color, ColorPalette col_color) {
		
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
			Column col = new Column(x + col_x, y-col_height_addition, col_width, height + col_height_addition, layer_thickness, col_color);
			columns.add(col);
		}
	}
	
	public PorticoStory(float x, float y, float width, float height, 
					int num, float col_width, float layer_thickness, 
					HouseInfo.PositionType p_type, ColorPalette wall_color, ColorPalette col_color) {
		
		this.p_type = p_type;
		if (p_type == HouseInfo.PositionType.CENTER) 
			new PorticoStory(x, y, width, height, num, col_width, layer_thickness, wall_color, col_color);
		
		else {
			panels = new ArrayList<PlainStory>();
			float panel_width = (width - (col_width * (num+1))) / num;

			int start = 0;
			int end = num;

			// if left is edge, make the shorter wall
			if (p_type == HouseInfo.PositionType.LEFT_EDGE) {
				start = 1;
				PlainStory panel = new PlainStory(x + (col_width * 2 + panel_width)/2, y,
						panel_width/2 - layer_thickness, height,
						layer_thickness, wall_color);
				panels.add(panel);
			} else {
				end--;
			}

			// make all the middle walls
			for (int i = start; i < end; i++) {
				float wall_x = col_width + i * (col_width + panel_width);
				PlainStory panel = new PlainStory(x + wall_x, y, panel_width - layer_thickness,
						height, layer_thickness, wall_color);
				panels.add(panel);
			}
			
			// TODO: if right side is edge
			
		}
	}
	
	public float getHeight() {
		return panels.get(0).getHeight();
	}
	
	public ArrayList<Window> addWindows(WindowFactory.Type type, int num, float top_margin, float bot_margin,
													float side_margin, float in_between, ColorPalette color) {
		ArrayList<Window> windows = new ArrayList<Window>();
		for (PlainStory panel : panels) {
			ArrayList<Window> ws = panel.addWindows(type, 1, top_margin, bot_margin, side_margin, 0, color);
			windows.addAll(ws);
		}
		return windows;
	}
	
	public Window addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color) {
		return null;
	}
	
	public Window addDoor(WindowFactory.Type type, float left_margin, float top_margin, float d_width, ColorPalette color) {
		return null;
	}
	
	public void addRailing(float r_height, float rail_width, float tb_rail_height, float in_between, ColorPalette color) {
		int start = 0;
		int end = panels.size();

		for (int i = start; i < end; i++) {
			panels.get(i).addRailing(r_height, rail_width, tb_rail_height, in_between, color);
		}
		
	}
	
	public void addRailing(float r_height, ColorPalette color) {

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
