package appleyard.bob.nets.data;

import java.util.*;

import appleyard.bob.nets.functions.*;

public class NormalData extends DataDescription {

	@Override
	public List<Double> convert(List<String> ins) {
		List<Double> result = new ArrayList<Double>();
		
		double sum, sumsq;
		int n = 0;
		sum = sumsq = 0;
		
		for (String item: ins) {
			double x = Double.valueOf(item);
			sum += x;
			sumsq += x*x;
			++n;
			result.add(x);
		}
		
		double mean = sum / n;
		double stdDev = Math.sqrt(sumsq - mean*mean);	
		
		/*
		 * Now, I should probably use the proper cumulative distribution here.
		 * 
		 * But that means either pulling in some huge maths library just for that
		 * one function, or writing one myself, which I'm not too confident about.
		 * 
		 * So until compelled otherwise, I'll bodge it with a sigmoid function.
		 * 
		 */
		if (bipolar) {
			for (int i = 0; i < result.size(); ++i) {
				result.set(i, Bipolar.INSTANCE.apply((result.get(i) - mean) / (stdDev * 3)));
			}		
		} else {
			for (int i = 0; i < result.size(); ++i) {
				result.set(i, Unipolar.INSTANCE.apply(0.5 + ((result.get(i) - mean) / (stdDev * 6))));
			}
		}		
		
		return result;
	}


	
	
}
