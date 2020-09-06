import edu.princeton.cs.algs4.StdRandom;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 7;
		/*
		 * percolation.open(1, 1); percolation.open(1, 4); percolation.open(2, 1);
		 * percolation.open(2, 4); percolation.open(2, 4); percolation.open(3, 4);
		 * percolation.open(4, 4); System.out.print(percolation);
		 */
		int trials = 2;
		for(int trial = 0; trial < trials; trial++) {
			Percolation percolation = new Percolation(n);
			//System.out.println(percolation);
			while(!percolation.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				percolation.open(row, col);
			}
			System.out.println((double)percolation.numberOfOpenSites() / (n * n));
			System.out.println(percolation);
		}
		/*
		while (!percolation.percolates()) {
			int row = StdRandom.uniform(1, n + 1);
			int col = StdRandom.uniform(1, n + 1);
			percolation.open(row, col);
		}
		System.out.print(percolation);*/
	}
}
