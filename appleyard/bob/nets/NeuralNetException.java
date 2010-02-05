package appleyard.bob.nets;

public class NeuralNetException extends Exception {
	public NeuralNetException(String msg) {
		super(msg);
	}
	
	public static final String INDEX_FAIL = "Weight index out of range";
	public static final String LENGTH_MISMATCH = "Wrong number of signals";
	public static final String NO_TRANSFER = "Missing transfer function";
	public static final String TOO_MANY_LISTENERS = "This layer already has a listener";
}
