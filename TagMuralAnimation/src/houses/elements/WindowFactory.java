package houses.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import global.ColorPalette;

public class WindowFactory {
	public enum Type {
		RECT, ARCH, POINTED;
		private static final List<Type> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
	}
	
	public static Type pickRandomWindowType() {
		return Type.VALUES.get(Type.RANDOM.nextInt(Type.SIZE));
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
