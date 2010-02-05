package appleyard.bob.input;

import java.io.*;
import java.util.*;

public class CSVTable {
	List<String> headings;
	List<List<String>> values;
	
	public CSVTable() {
		headings = new ArrayList<String>();
		values = new ArrayList<List<String>>();
	}
	
	public int getWidth() { return headings.size(); }
	public List<String> getValues(String nm) { return getValues(headings.indexOf(nm)); }
	public List<String> getValues(int pos) { return values.get(pos); }
	
	public void loadFromFile(String fn) throws CSVException {
		LineReader r;
		try {
			r = new LineReader(new BufferedReader(new FileReader(fn)));
		} catch (FileNotFoundException e) {
			throw new CSVException("Error opening file: " + e.getMessage());
		}
		
		headings.clear();
		values.clear();
		
		try {
			int lineNo = 1;
			String lineText = r.readLine();
			String[] line = lineText.split(",");
			int lineLength = line.length;
			
			// read the first line as headings
			for (String item: line) {
				headings.add(item);
				values.add(new ArrayList<String>());
			}
			// read the rest into the values
			do {
				++lineNo;
				lineText = r.readLine();
				if ("".equals(lineText)) {
					// finished
					return;
				}
				line = lineText.split(",");
				if (line.length != lineLength) {
					throw new CSVException("Error on line " + lineNo 
							+ ": expected number of values: " + lineLength 
							+ ", actual number of values: " + line.length);
				}
				for (int i = 0; i < line.length; ++i) {
					values.get(i).add(line[i]);
				}
			} while (true);
			
		} catch (IOException e) {
			throw new CSVException("Error reading file: " + e.getMessage());
		}
	}
	
	public String toString() { return "#csv{" + headings + ", " + values + "}";	}
	
}
