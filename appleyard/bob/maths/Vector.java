package appleyard.bob.maths;


public class Vector {
	private int d;
	private double data[];
	
	public Vector(int dim) {
		this.d = dim;
		data = new double[dim];
	}
	
	public Vector(double vals[]) {
		this.d = vals.length;
		set(vals);
	}
	
	public double get(int i) { return data[i]; }
	public int dim() { return d; }	
	public void set(int i, double val) { data[i] = val; }
	
	public void set(double val[]) { 
		if (val.length != dim()) throw new MatrixException(MatrixException.DIMENSION_FAIL); 
		data = val;
	}
	
	public Vector append(double val) {
		Vector result = new Vector(dim() + 1);
		for (int i = 0; i < dim(); ++i) {
			result.set(i, get(i));
		}
		result.set(dim(), val);
		return result;
	}
	
	public Vector slice(int upto) {
		Vector result = new Vector(upto);
		for (int i = 0; i < upto; ++i) {
			result.set(i, get(i));
		}
		return result;
	}
	
	public String toString() {
		String res = "#vector{";
		
		for (int i = 0; i < dim(); ++i) {
			res += data[i] + (i == dim() - 1 ? "" : ", ");
		}
			
		return res + "}";
	}
	
	public Vector subtract(Vector x) {
		if (x.dim() != dim()) throw new MatrixException(MatrixException.DIMENSION_FAIL); 
		Vector result = new Vector(dim());
		for (int i = 0; i < dim(); ++i) {
			result.set(i, get(i) - x.get(i));
		}
		return result;
	}

	public Vector multiply(Vector x) {
		if (x.dim() != dim()) throw new MatrixException(MatrixException.DIMENSION_FAIL); 	
		Vector result = new Vector(dim());
		for (int i = 0; i < dim(); ++i) {		
			result.set(i, get(i) * x.get(i));
		}
		return result;
	}

	public double sum() {
		double result = 0;
		for (int i = 0; i < dim(); ++i) {		
			result += get(i);
		}
		return result;
	}
	
}
