package houses.elements;

import global.ColorPalette;

public class WindowFactory {
	public enum Type {
		RECT, ARCH, POINTED;
	}
	
	public static Window createWindow(WindowFactory.Type type, float x, float y, float width, float height, float layer_thickness, ColorPalette color) {
		switch(type) {
		case RECT:
			return new RectWindow(x, y, width, height, layer_thickness, color);
		case ARCH:
			return new ArchWindow(x, y, width, height, layer_thickness, color);
		case POINTED:
			return new PointedWindow(x, y, width, height, layer_thickness, color);
		default:
			throw new RuntimeException("Unsupported object type!");
		}
	}
}
