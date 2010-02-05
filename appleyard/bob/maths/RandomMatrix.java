package appleyard.bob.maths;

public class RandomMatrix extends Matrix {
	private static final MersenneTwisterFast twister = new MersenneTwisterFast(); 

	public RandomMatrix(int m, int n) {
		super(m, n);
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < n; ++j) {
				set(i, j, (twister.nextDouble() * 2)  - 1);
			}
		}
	}
}
