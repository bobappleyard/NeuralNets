package appleyard.bob.nets.functions;

import appleyard.bob.nets.TransferFunction;

public class Unipolar extends TransferFunction {

	public double apply(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public double applyDerivative(double x) {
		return x * (1 - x);
	}

	public static final TransferFunction INSTANCE = new Unipolar();

}
