package music.staff;

public class SineTerm {
	private float amplitude;
	private float wave_length;
	private float phase_diff;

	public SineTerm(float amplitude, float wave_length, float phase_diff) {
		this.amplitude = amplitude;
		this.wave_length = wave_length;
		this.phase_diff = phase_diff;
	}

	public float evaluate(float x) {
		return amplitude * (float) Math.sin(2 * Math.PI * x / wave_length + phase_diff);
	}
}
