package appleyard.bob.nets.data;

import java.util.*;

public abstract class DataDescription {
	protected boolean bipolar;
	
	public DataDescription() {
		bipolar = false;
	}
	
	public void setPolarity(boolean bp) {
		bipolar = bp;
	}
	
	public abstract List<Double> convert(List<String> ins);
}
