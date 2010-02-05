package appleyard.bob.nets.functions;

import appleyard.bob.nets.TransferFunction;

public class HardLim extends TransferFunction {

	public double apply(double net) {
		return net >= 0 ? 1 : 0;
	}

	public double applyDerivative(double error) {
		return 0; // doesn't work the other way
	}

	public static final TransferFunction INSTANCE = new HardLim();
}
