package appleyard.bob.maths;



public class Matrix {
	private double data[];
	private int m, n;
	
	public Matrix(int m, int n) {
		this.m = m;
		this.n = n;
		data = new double[m * n];		
	}
	
	public Matrix(int m, int n, double vals[]) {
		this.m = m;
		this.n = n;
		set(vals);
	}
	
	/* Getters */
	public double get(int x, int y) { return data[x + y * m]; }
	public int getM() { return m; };
	public int getN() { return n; };
	
	/* Setters */
	
	public void set(int x, int y, double val) { data[x + y * m] = val; }
	
	public void set(double val[]) { 
		if (val.length != m * n) throw new MatrixException(MatrixException.DIMENSION_FAIL); 
		data = val;
	}
	
	/* Behaviour */
	
	public Matrix multiply(double val) {
		Matrix result = new Matrix(m, n);
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				result.set(i, j, get(i, j) * val);
			}
		}
		return result;
	}
	
	public Vector multiply(Vector val) {
		//System.out.println(val);
		//System.out.println(this);
		
		assertDimensions(val.dim() == m);
		
		Vector result = new Vector(n);
		double acc;
		
		for (int j = 0; j < n; ++j) {
			acc = 0;
			for (int i = 0; i < m; ++i) {
				acc += get(i, j) * val.get(i);
			}
			result.set(j, acc);
		}	
		
		return result;
	}
	
	public Matrix multiply(Matrix val) throws MatrixException {
		assertDimensions(val.getN() == m);
		
		int newM = val.getM();
		int newN = n;
		Matrix result = new Matrix(newM, newN);		
		double acc;
		
		for (int i = 0; i < newM; ++i) {
			for (int j = 0; j < newN; ++j) {
				acc = 0; 
				for (int k = 0; k < m; ++k) {
					acc += get(k, j) * val.get(i, k);
				}
				result.set(i, j, acc);
			}
		}

		return result;		
	}
	
	public Matrix transpose() {
		Matrix result = new Matrix(n, m);
		
		for (int i = 0; i < m; ++i) {		
			for (int j = 0; j < n; ++j) {
				result.set(j, i, get(i, j));
			}
		}
		
		return result;
	}
	
	public String toString() {
		String result = "#matrix(" + m + "," + n + "){";
		String start = "\n";
		
		for (int i = 0; i < result.length(); ++i) {
			start += " ";
		}
		
		for (int j = 0; j < n; ++j) {
			for (int i = 0; i < m; ++i) {		
				result += get(i, j) + (i == m - 1 ? "" : ", "); 
			}
			result += start; 
		}
		
		return result + "}";
	}
	
	/* Support methods */
	
	private void assertDimensions(boolean passes) throws MatrixException {
		if (!passes) throw new MatrixException(MatrixException.DIMENSION_FAIL);
	}
}
