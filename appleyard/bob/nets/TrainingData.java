package appleyard.bob.nets;

import appleyard.bob.maths.Vector;

public class TrainingData {
	private Vector in, out;
	
	public TrainingData(Vector in, Vector out) {
		this.in = in;
		this.out = out;
	}
	
	public Vector getIn() {
		return in;
	}
	
	public Vector getOut() {
		return out;		
	}
}
