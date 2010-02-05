package appleyard.bob.nets;

import java.util.List;
import java.util.ArrayList;
import appleyard.bob.maths.*;

public class NeuralNet {
	private int inputDim;
	private Layer outputLayer;
	private List<Layer> hidden;
	
	private static final MersenneTwisterFast selector = new MersenneTwisterFast();
	
	// this is to keep the layers in sync
	private double bias;
	private TransferFunction fcn;
	private double errMax;
	
	public NeuralNet() {
		inputDim = 0;
		outputLayer = null;
		hidden = new ArrayList<Layer>();
		errMax = 0;
		
		bias = 0;
		fcn = null;
	}

	/* Standard init */
	public NeuralNet(int[] spec) throws NeuralNetException {
		this();
		if (spec.length < 2) {
			throw new NeuralNetException("Improper spec");
		}
		Layer l;
		inputDim = spec[0];
		for (int i = 1; i < spec.length - 1; ++i) {
			l = new Layer(new RandomMatrix(spec[i- 1] + 1, spec[i]));
			l.name = "Hidden layer " + i;
			hidden.add(l);			
		}
		l = new Layer(new RandomMatrix(spec[spec.length - 2] + 1, spec[spec.length - 1]));
		l.name = "Output layer";
		outputLayer = l;
	}
	
	public void addHidden(Layer l) {
		l.setBias(bias);
		l.setFcn(fcn);
		hidden.add(l);
	}
	
	public Layer getHidden(int index) {
		return hidden.get(index);
	}
	
	public Layer getOutput() {
		return outputLayer;
	}
	
	public void setInputDim(int inputDim) { 
		this.inputDim = inputDim; 
	}
	
	public void setOutput(Layer output) { 
		output.setBias(bias);
		output.setFcn(fcn);
		this.outputLayer = output; 
	}
	
	public void setBias(double bias) {
		this.bias = bias;
		for (Layer l: hidden) {
			l.setBias(bias);
		}
		if (outputLayer != null) {
			outputLayer.setBias(bias);
		}
	}

	public void setFcn(TransferFunction fcn) {
		this.fcn = fcn;
		for (Layer l: hidden) {
			l.setFcn(fcn);
		}
		if (outputLayer != null) {
			outputLayer.setFcn(fcn);
		}
	}
	
	public void setErrMax(double errMax) {
		this.errMax = errMax;
	}
	
	public Vector forwards(Vector input) throws NeuralNetException {
		if (input.dim() != inputDim) throw new NeuralNetException(NeuralNetException.LENGTH_MISMATCH);
		Vector result = input;
		for (Layer l: hidden) {
			result = l.output(result);
		}
		return outputLayer.output(result);		
	}
	
	public double backwards(Vector output, Vector expected) throws NeuralNetException {
		if (output.dim() != expected.dim()) throw new NeuralNetException(NeuralNetException.LENGTH_MISMATCH);
		
		// the error of the network
		Vector error = expected.subtract(output);
		Vector deltas = fcn.applyDerivative(output).multiply(error);
		double errVal = error.multiply(error).sum() / 2;
		
		// backpropagate through the layers
		Vector blame = outputLayer.backProp(deltas);
		for (int i = hidden.size() - 1; i >= 0; --i) {
			blame = hidden.get(i).backProp(blame);
		}
		// then adjust the weights
		for (Layer l: hidden) {
			l.train();
		}
		outputLayer.train();
		
		return errVal;
	}

	public double train(TrainingData[] data, int epochLength) throws NeuralNetException {
		double avgError = errMax + 1;
		for (int tick = 0; avgError > errMax && tick < epochLength; ++tick) {
			int c = data.length;
			// cross validation
			
			// set up the test set and the training set
			TrainingData[] copy = new TrainingData[c];
			for (int i = 0; i < c; ++i) {
				copy[i] = data[i]; 
			}
			TrainingData[] jumbled = new TrainingData[c];
			for (int i = 0; i < c; ++i) {
				do {
					int rand = selector.nextInt(c);
					if (copy[rand] != null) {
						jumbled[i] = copy[rand];
						copy[rand] = null;
					}
				} while (jumbled[i] == null);
			}
			int t = c / 10; // the testing set is 10% of the data
			if (t == 0) {
				t = 1;
			}
			int u = c - t;
			TrainingData[] testSet = new TrainingData[t];
			TrainingData[] trainSet = new TrainingData[u];
			for (int i = 0; i < t; ++i) {
				testSet[i] = jumbled[i];
			}
			for (int i = t; i < c; ++i) {
				trainSet[i - t] = jumbled[i];
			}			
			
			// do the training
			for (int i = 0; i < u; ++i) {				
				Vector output = forwards(trainSet[i].getIn());
				backwards(output, trainSet[i].getOut());				
			}
			
			// do the testing
			double sum = 0;
			int n = 0;
			for (int i = 0; i < t; ++i) {
				Vector output = forwards(testSet[i].getIn());
				Vector error = testSet[i].getOut().subtract(output);
				// accumulate an average error
				sum += error.multiply(error).sum();
				++n;
			}
			avgError = sum / n;
		}
		return avgError;
	}

	/*
	 * Toplevel training method
	 */
	public int train(TrainingData[] data, int epochLength, int numEpochs) throws NeuralNetException {
		double errVal = errMax + 1;
		int i;
		for (i = 0; (errVal > errMax) && (i < numEpochs); ++i) {
			errVal = train(data, epochLength);
			System.out.println("Net training, error: " + errVal);
		}
		System.out.println("Training complete.");
		return errVal > errMax ? -1 : i;
	}
}
 