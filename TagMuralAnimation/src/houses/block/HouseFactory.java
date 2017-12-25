package houses.block;

import global.Settings;

public class HouseFactory {
	
	public static House makeHouse(HouseInfo info, float x, float y) {
		House h;
		
		// main body of the house
		
		if (info.isPortico()) {
			h = new PorticoHouse(x, y, info.getWidth(), info.getNumWindows(), Settings.LAYER_THICKNESS);
			h.addStories(info.getNumStories(), info.getPositionType());
		} else {
			if (info.getHouseType() == HouseType.ONE_STORY_FOUR_WINDOW) {
				h = new OneStoryHouse(x, y, info.getWidth(), info.getNumWindows(), Settings.LAYER_THICKNESS);
				h.addDoorwayStory();
			}
			else {
				h = new House(x, y, info.getWidth(), info.getNumStories(), Settings.LAYER_THICKNESS);
				h.addStories(info.getNumStories());
			}
		}

		// roof
		if (info.getNumStories() > 1) {
			h.addRoof(info.getRoofType(), info.getPositionType());
		} else { 
			h.addRoof(HouseInfo.pickRandomRoofType(), false, false);
		}
		return h;
	}
}
