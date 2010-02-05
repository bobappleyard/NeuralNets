package appleyard.bob.nets.data;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import appleyard.bob.input.LineReader;

public class DiscreteData extends DataDescription {
	private List<String> possVals;
	private int numVals;
	
	private static final Pattern declaration = Pattern.compile("d\\s+(\\d+)");
	
	public DiscreteData(String decl, LineReader r) throws IOException {
		super();
		possVals = new ArrayList<String>();
		Matcher m = declaration.matcher(decl); 
		m.find();
		numVals = Integer.parseInt(m.group(1));
		for (int i = 0; i < numVals; ++i) {
			possVals.add(r.readLine());
		}
	}

	@Override
	public List<Double> convert(List<String> ins) {
		double sum = 0;
		int n = 0;
		List<Double> result = new ArrayList<Double>();
		for (int i = 0; i < ins.size(); ++i) {
			int pos = possVals.indexOf(ins.get(i));
			// Things that aren't in the list are considered typos, they'll be set
			// to the average of the column.
			if (pos == -1) {
				result.add(-3.0);
			} else {
				double res = pos / (possVals.size() - 1);
				if (bipolar) {
					res = (2 * res) - 1;
				}
				// For calculating the average.
				sum += res;
				++n;
				result.add(res);
			}
		}
		// Set the typos to the average
		double avg = sum / (double) n;
		for (int i = 0; i < result.size(); ++i) {
			if (result.get(i) < -1.5) {
				result.set(i, avg);
			}
		}
		return result;
	}
	
}
