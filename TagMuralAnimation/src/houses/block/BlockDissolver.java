package houses.block;

import java.util.HashSet;
import java.util.Iterator;

import houses.bricks.Brick;

public class BlockDissolver {
	
	private HashSet<Brick> bricks;
	private Iterator<Brick> bricksIt;
	private boolean isVisible;
	
	boolean isDissolving;
	private int NUM_TO_FADE = 20;
	private int pause_start_time = 0;
	
	private int visible_count = 0; // how many bricks are visible
	private int total_bricks = 100;
	
	public enum DissolveState { FADING_IN, FADING_OUT, PAUSED, DONE_FADING_IN, DONE_FADING_OUT; }
	
	public BlockDissolver(boolean visibility) {
		this.isVisible = visibility;
		bricks = new HashSet<Brick>();
	}
	
	public BlockDissolver(Block block) {
		bricks = block.getAllBricks();
	}
	
	public void addBlock(Block block) {
		bricks.addAll(block.getAllBricks());
	}
	
	public void clearBricks() {
		bricks.clear();
	}
	
	public float getPercentageVisible() {
		return (float) visible_count / (float) total_bricks;
	}
	
	public void startDissolve() {
		bricksIt = bricks.iterator();
		total_bricks = bricks.size();
	
		if (isVisible) 
			visible_count = total_bricks;
		else
			visible_count = 0;

		isDissolving = true;
	}
	
	public boolean isDissolving() {
		return isDissolving;
	}
	
	// return boolean for if it's done
	public DissolveState runDissolve() {
		if (!isDissolving)
			return DissolveState.PAUSED;
		
		for (int i = 0; i < NUM_TO_FADE; i++) {
			if (bricksIt.hasNext()) {
				bricksIt.next().setVisibility(!isVisible);
				if (isVisible) {
					visible_count --;
		 		}
				else {
					visible_count ++;
				}
			} else {
				isDissolving = false;
				
				isVisible = !isVisible;
				
				return isVisible ? DissolveState.DONE_FADING_IN : DissolveState.DONE_FADING_OUT;
			}
		}		
		return isVisible ? DissolveState.FADING_OUT : DissolveState.FADING_IN;
	}
	
}
