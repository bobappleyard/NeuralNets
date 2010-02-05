package appleyard.bob.nets;

import appleyard.bob.maths.Vector;

public abstract class TransferFunction {
	public abstract double apply(double net);
	public abstract double applyDerivative(double error);
	
	public Vector apply(Vector v) {
		int dim = v.dim();
		Vector result = new Vector(dim);
		for (int i = 0; i < dim; ++i) {
			result.set(i, apply(v.get(i)));
		}
		return result;
	}

	public Vector applyDerivative(Vector v) {
		int dim = v.dim();
		Vector result = new Vector(dim);
		for (int i = 0; i < dim; ++i) {
			result.set(i, applyDerivative(v.get(i)));
		}
		return result;
	}
}
