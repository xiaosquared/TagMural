package water;

import processing.core.PApplet;

public class RainGroup {
	Rain[] rains;
	Splash sp;

	public RainGroup(WaveGroup mw, Splash sp, String[] words, int n, PApplet parent) {
		this.sp = sp;
		rains = new Rain[mw.waves.length];
		for (int i = 0; i < mw.waves.length; i++) {
			Rain r = new Rain(words, n, mw.waves[i], parent);
			rains[i] = r;
		}
	}

	public void run(boolean letters, boolean raining) {
//		if (!raining)
//			return;
		for (int i = 0; i < rains.length; i++) {
			rains[i].run(sp, letters, raining);
		}
	}

	public void restart() {
		for (int i = 0; i < rains.length; i++) {
			rains[i].restart();
		}
	}
}
