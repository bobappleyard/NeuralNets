package appleyard.bob.maths;


public class MatrixException extends Error {
	public MatrixException(String msg) {
		super(msg);
	}
	
	public static final String DIMENSION_FAIL = "Matrix encountered incorrectly dimensioned object";

}
