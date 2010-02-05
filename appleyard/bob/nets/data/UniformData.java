package appleyard.bob.nets.data;

import java.util.*;


public class UniformData extends DataDescription {

	@Override
	public List<Double> convert(List<String> ins) {
		double min, max;
		min = max = 0;
		List<Double> result = new ArrayList<Double>();
		
		for (String item: ins) {
			double x = Double.valueOf(item);
			result.add(x);
			if (x < min) { 
				min = x;
			}
			if (x > max) {
				max = x;
			}
		}
		
		double range = max - min;
		
		for (int i = 0; i < result.size(); ++i) {
			double x = (result.get(i) - min) / range;
			if (bipolar) {
				x = 2 * x  - 1;
			}
			result.set(i, x);			
		}
		
		return result;
	}

}
