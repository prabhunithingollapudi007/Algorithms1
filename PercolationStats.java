import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	// perform independent trials on an n-by-n grid
	private int trials;
	private double confidence, results[];
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
		this.confidence = 1.96;
		this.results = new double[trials];
		this.trials = trials;
		for(int trial = 0; trial < trials; trial++) {
			Percolation percolation = new Percolation(n);
			while(!percolation.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				percolation.open(row, col);
			}
			results[trial] = (double)percolation.numberOfOpenSites() / (double)(n * n);
		}
	}

    // sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(results);
	}

    // sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(results);
	}

    // low endpoint of 95% confidence interval
	public double confidenceLo() {
		double mean = this.mean();
		double s = this.stddev();
		double trials = Math.sqrt(this.trials);
		return (mean - (confidence * s)/trials);
	}

    // high endpoint of 95% confidence interval
	public double confidenceHi() {
		double mean = this.mean();
		double s = this.stddev();
		double trials = Math.sqrt(this.trials);
		return (mean + (confidence * s)/trials);
	}

   // test client (see below)
	public static void main(String[] args) {
		PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println(p);
	}

	@Override
	public String toString() {
		String ret = "mean                    = " + mean();
		ret += "\n";
		ret += "stddev                  = " + stddev();
		ret += "\n";
		ret += "95% confidence interval =  [" + confidenceLo() + ", " + confidenceHi() + "]";
		return ret;
	}

	
	
}
