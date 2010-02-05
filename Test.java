import appleyard.bob.maths.*;
import appleyard.bob.nets.Layer;
import appleyard.bob.nets.NeuralNet;
import appleyard.bob.nets.NeuralNetException;
import appleyard.bob.nets.TrainingData;
import appleyard.bob.nets.functions.Bipolar;
import appleyard.bob.nets.functions.HardLim;
import appleyard.bob.nets.functions.Unipolar;

public class Test {
	
	private static void output(Object obj) {
		System.out.println(obj);
	}
	
	private static void runTest(NeuralNet n, double vals[]) throws NeuralNetException {
		output(n.forwards(new Vector(vals)));
	}

	private static void testError(NeuralNet n, double[] vals, double[] des) throws NeuralNetException {
		Vector desired = new Vector(des);
		Vector out = n.forwards(new Vector(vals));
		double err = n.backwards(out, desired);
		output(String.format("desired: %s output: %s\n error: %s", new Object[] {desired, out, err}));
	}
	

	private static double trainUp(NeuralNet n, TrainingData[] data, int epochs) throws NeuralNetException {
		double errVal = n.train(data, epochs);
		output("error: " + errVal);
		return errVal;
	}
	
	public static void main(String args[]) {
		NeuralNet n;
		Layer l;
		
		double errMax = 0.001;
	
		try {
			output("Hardwired XOR");
			n = new NeuralNet();
			n.setInputDim(2);
			n.setBias(-0.1);
			n.setFcn(HardLim.INSTANCE);
			
			n.addHidden(new Layer(new Matrix(3, 2, new double[] {1, -1, 1,
																 -1, 1, 1})));
			n.setOutput(new Layer(new Matrix(3, 1, new double[] {1, 1, 1})));
			runTest(n, new double[] {0, 0});
			runTest(n, new double[] {0, 1});
			runTest(n, new double[] {1, 0});
			runTest(n, new double[] {1, 1});		
			
			output("Learning XOR");
			n = new NeuralNet(new int[] {2, 2, 1});
			n.setBias(-0.1);
			n.setFcn(Unipolar.INSTANCE);
			n.setErrMax(errMax);
		
			/*testError(n, new double[] {0, 0}, new double[] {0});
			testError(n, new double[] {1, 0}, new double[] {1});
			testError(n, new double[] {0, 1}, new double[] {1});
			testError(n, new double[] {1, 1}, new double[] {0});*/
			
			// bipolar training data
/*			TrainingData[] data = {				
					new TrainingData(new Vector(new double[] {-1, -1}), new Vector (new double[] {-1})),
					new TrainingData(new Vector(new double[] {-1, 1}), new Vector (new double[] {1})),
					new TrainingData(new Vector(new double[] {1, -1}), new Vector (new double[] {1})),
					new TrainingData(new Vector(new double[] {1, 1}), new Vector (new double[] {-1})),
			};*/
			TrainingData[] data = {				
					new TrainingData(new Vector(new double[] {0, 0}), new Vector (new double[] {0})),
					new TrainingData(new Vector(new double[] {0, 1}), new Vector (new double[] {1})),
					new TrainingData(new Vector(new double[] {1, 0}), new Vector (new double[] {1})),
					new TrainingData(new Vector(new double[] {1, 1}), new Vector (new double[] {0}))
			};
			
			n.train(data, 10000, 500);
			
			// prior to the method above, below was used
			/*double errVal = 1;
			for (int i = 0; (errVal > errMax) && (i < 500); ++i) {
				errVal = trainUp(n, data, 10000);
			}*/

			output("A run to see what it's doing: ");
			runTest(n, new double[] {0, 0});
			runTest(n, new double[] {0, 1});
			runTest(n, new double[] {1, 0});
			runTest(n, new double[] {1, 1});					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
