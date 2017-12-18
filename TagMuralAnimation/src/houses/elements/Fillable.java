package houses.elements;

import processing.core.PApplet;

public interface Fillable {
	public boolean isFilled();
	public void fillAll(PApplet parent);
	public void fillByLayer(PApplet parent);
	public void reset();
	
	// TODO: unfill?
}
