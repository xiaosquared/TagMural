package houses.block;

import global.ColorPalette;
import global.Settings;
import houses.elements.WindowFactory;
import houses.stories.RoofStory;

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
		
		HouseInfo.RoofType r_type;
		RoofStory roof;
		
		// Make the main roof
		// if multi-story house
		if (info.getNumStories() > 1) {
			HouseInfo.PositionType position_type = info.getPositionType(); 
			
			// if house is in the center, make roof angled if it's a portico house, 
			// otherwise pick random roof
			if (position_type == HouseInfo.PositionType.CENTER) {
				if (info.isPortico()) {
					r_type = HouseInfo.RoofType.ANGLED;
				}
				else {
					r_type = HouseInfo.pickRandomRoofType();	
				}
				roof = h.addRoof(r_type, false);
			}
			
			// if house is on the edges, must be angled, not pointed
			else {
				r_type = HouseInfo.RoofType.ANGLED;
				roof = h.addRoof(r_type, position_type);
			}
		}  
		
		// if single story, just pick a random roof type
		else { 
			r_type = HouseInfo.pickRandomRoofType();
			roof = h.addRoof(r_type, false);
		}
		
		
		// Deal with windows
		if (r_type != HouseInfo.RoofType.POINTED) { 
//			if (info.getNumWindows() > 4) {
//				roof.addWindows(WindowFactory.Type.POINTED, 2, Settings.getTopMarginRoof(), Settings.getBottomMargin(), 
//						Settings.getSideMarginRoof(), Settings.getInBetweenRoof(), ColorPalette.BLUE);
//			}
//			else if (info.getNumWindows() > 2) {
//			roof.addWindows(WindowFactory.Type.POINTED, 1, Settings.getTopMarginRoof(), Settings.getBottomMargin(), 
//					Settings.getSideMarginRoof(), Settings.getInBetweenRoof(), ColorPalette.BLUE);
//			}
			
			if (Settings.getRandomBoolean())
				roof.addChimney(0);
			else if (Settings.getRandomBoolean())
				roof.addChimney(1);
		}

		
		return h;
	}
}
