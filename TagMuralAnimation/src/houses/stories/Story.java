package houses.stories;

import global.ColorPalette;
import houses.elements.Window;
import houses.elements.WindowFactory;

public interface Story {
	public Window addWindow(WindowFactory.Type type, float left_margin, float top_margin, float w_width, float w_height, ColorPalette color);
	public Window addDoor(WindowFactory.Type type, float left_margin, float top_margin, float d_width, ColorPalette color);
	public void addRailing(float r_height, float rail_width, float tb_rail_height, float in_between, ColorPalette color);
}
