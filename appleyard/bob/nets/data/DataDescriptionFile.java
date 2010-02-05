package appleyard.bob.nets.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import appleyard.bob.input.CSVException;
import appleyard.bob.input.CSVTable;
import appleyard.bob.input.LineReader;
import appleyard.bob.maths.Vector;
import appleyard.bob.nets.TrainingData;

public class DataDescriptionFile {
	
	private List<DataDescription> inputs, outputs;
	
	private static final Pattern header = Pattern.compile("(\\d+)\\s+(\\d+)");
	
	public DataDescriptionFile(String fn) throws IOException {
		LineReader r = new LineReader(new BufferedReader (new FileReader (fn)));
		
		String headLine = r.readLine();
		Matcher m = header.matcher(headLine);
		m.find();
		int inputCount = Integer.valueOf(m.group(1));
		int outputCount = Integer.valueOf(m.group(2));
		
		inputs = new ArrayList<DataDescription>();
		outputs = new ArrayList<DataDescription>();
		
		for (int i = 0; i < inputCount; ++i) {
			inputs.add(readDesc(r));
		}
		
		for (int i = 0; i < outputCount; ++i) {
			outputs.add(readDesc(r));
		}
	}

	private DataDescription readDesc(LineReader r) throws IOException {
		String declLine = r.readLine();
		DataDescription result;
		
		switch (declLine.charAt(0)) {
		
			case 'd':
				result = new DiscreteData(declLine, r);
				break;

			case 'u':
				result = new UniformData();
				break;
			
			case 'n':
				result = new NormalData();
				break;

			default:
				throw new IOException("Could not read data declaration.");
		}
		
		return result;
	}
	
	public TrainingData[] loadData(String fn) throws CSVException {
		CSVTable tbl = new CSVTable();
		tbl.loadFromFile(fn);
		if (tbl.getWidth() != inputs.size() + outputs.size()) {
			throw new CSVException("CSV is the wrong width");
		}
	
		List<List<Double>> inL = new ArrayList<List<Double>>();
		List<List<Double>> outL = new ArrayList<List<Double>>();
		for (int i = 0; i < inputs.size(); ++i) {
			inL.add(inputs.get(i).convert(tbl.getValues(i)));
		}
		for (int i = 0; i < outputs.size(); ++i) {
			outL.add(outputs.get(i).convert(tbl.getValues(i + inputs.size())));
		}

		int c = tbl.getValues(0).size();
		
		TrainingData[] result = new TrainingData[c];
		
		for (int j = 0; j < c; ++j) {
			Vector inV = new Vector(inputs.size());
			for (int i = 0; i < inputs.size(); ++i) {
				inV.set(i, inL.get(i).get(j));
			}
			Vector outV = new Vector(outputs.size());
			for (int i = 0; i < outputs.size(); ++i) {
				outV.set(i, outL.get(i).get(j));			
			}
			result[j] = new TrainingData(inV, outV);
		}
		
		return result;
	}
}
