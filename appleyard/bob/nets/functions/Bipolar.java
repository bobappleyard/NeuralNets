package appleyard.bob.nets.functions;

import appleyard.bob.nets.TransferFunction;

public class Bipolar extends TransferFunction {
	
	public double apply(double x) {
		return 2 * Unipolar.INSTANCE.apply(x) - 1;
	}

	public double applyDerivative(double x) {
		return (1 + x) * (1 - x);
	}

	public static final TransferFunction INSTANCE = new Bipolar();
}
