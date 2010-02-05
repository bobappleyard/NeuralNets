package appleyard.bob.nets;

import appleyard.bob.maths.*;

public class Layer {
	public String name;
	private Matrix weights;
	private TransferFunction fcn;
	private Vector inputs, outputs, derivs, errors;
	private double bias;
	private double learning;
	
	public Layer() { bias = 0; learning = 0.1; }
	
	public Layer(Matrix weights) {
		this();
		this.weights = weights;
	}
	
	public TransferFunction getFcn() { return fcn; }
	public Matrix getWeights() { return weights; }	
	public double getBias() { return bias; }
	public double getLearning() { return learning; }

	public void setWeights(Matrix weights) { this.weights = weights; }
	public void setFcn(TransferFunction fcn) { this.fcn = fcn; }
	public void setBias(double bias) { this.bias = bias; }
	public void setLearning(double learning) { this.learning = learning; }
		
	public Vector output(Vector inputs) {
		Vector in = inputs.append(bias);
		this.inputs = in;
		in = weights.multiply(in);
		outputs = fcn.apply(in);
		return outputs;
	}
	
	public Vector backProp(Vector error) {
		derivs = fcn.applyDerivative(outputs);
		errors = derivs.multiply(error);
		Vector blame = weights.transpose().multiply(errors);
		// take off the bias signal, the previous layer need not know
		return blame.slice(blame.dim() - 1);
	}
	
	public void train() {
		for (int i = 0; i < weights.getM(); ++i) {
			for (int j = 0; j < weights.getN(); ++j) {				
				weights.set(i, j, weights.get(i, j) + (learning * inputs.get(i) * errors.get(j)));
			}
		}
	}
	
}
