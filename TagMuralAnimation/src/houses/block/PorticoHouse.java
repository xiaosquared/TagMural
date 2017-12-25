package houses.block;

public class PorticoHouse extends House {
	
	public PorticoHouse(float base_x, float base_y, float width, int num_windows, float layer_thickness) {
		super(base_x, base_y, width, num_windows, layer_thickness);
	}
	
	public void addStories(int num_stories) {
		for (int i = 0; i < num_stories; i++)
			addPorticoStory();
	}
	
	public void addStories(int num_stories, HouseInfo.PositionType p_type) {
		for (int i = 0; i < num_stories; i++)
			addPorticoStory(p_type);
	}
}
