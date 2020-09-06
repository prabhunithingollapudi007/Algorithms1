
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private boolean grid[];
	private int openSites;
	private int grid_size;
	private WeightedQuickUnionUF weightedQuickUnionUF;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		this.validate(n);
		this.grid = new boolean[n * n];
		this.openSites = 0;
		this.grid_size = n;
		this.weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

		for (int i = 0; i < grid_size; i++) {
			weightedQuickUnionUF.union(n * n , i);
		}

		for (int i = 0; i < grid_size; i++) {
			weightedQuickUnionUF.union(n * n + 1, getCoordinates(grid_size, i + 1));
		}

	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		this.validate(row, col);
		if (isOpen(row, col))
			return;
		this.grid[getCoordinates(row, col)] = true;
		this.openSites++;
		this.checkNeighbourhood(row, col);
	}

	private void checkNeighbourhood(int row, int col) {
		// edge case of first row
		if (row == 1) {
			// check bottom cell
			if (grid[getCoordinates(row + 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row + 1, col), getCoordinates(row, col));
			}
			return;
		}
		// edge case of last row
		else if (row == grid_size) {
			// check top cell
			if (grid[getCoordinates(row - 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row - 1, col));
				//checkPercolated(row, col);
				//weightedQuickUnionUF.union(grid_size * grid_size - 1, getCoordinates(row - 1, col));

			}
		}
		// edge case of first col
		else if (col == 1) {
			// check right
			if (grid[getCoordinates(row, col + 1)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row, col + 1));
				//checkPercolated(row, col);
			}
			// check bottom cell
			if (grid[getCoordinates(row + 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row + 1, col), getCoordinates(row, col));
				//checkPercolated(row, col);
			}
			// check top cell
			if (grid[getCoordinates(row - 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row - 1, col));
				//checkPercolated(row, col);
			}

		}
		// edge case of last col
		else if (col == grid_size) {
			// check left cell
			if (grid[getCoordinates(row, col - 1)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row, col - 1));
				//checkPercolated(row, col);
			}
			// check bottom cell
			if (grid[getCoordinates(row + 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row + 1, col), getCoordinates(row, col));
				//checkPercolated(row, col);
			}
			// check top cell
			if (grid[getCoordinates(row - 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row - 1, col));
				//checkPercolated(row, col);
			}
		} else {
			// check right
			if (grid[getCoordinates(row, col + 1)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row, col + 1));
				//checkPercolated(row, col);
			}
			// check left cell
			if (grid[getCoordinates(row, col - 1)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row, col - 1));
				//checkPercolated(row, col);
			}
			// check bottom cell
			if (grid[getCoordinates(row + 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row + 1, col), getCoordinates(row, col));
				//checkPercolated(row, col);
			}
			// check top cell
			if (grid[getCoordinates(row - 1, col)] == true) {
				weightedQuickUnionUF.union(getCoordinates(row, col), getCoordinates(row - 1, col));
				//checkPercolated(row, col);
			}
		}

	}

	/*private void checkPercolated(int row, int col) {
		if (weightedQuickUnionUF.find(getCoordinates(row, col)) < grid_size) {
			percolated = true;
		}
		if (weightedQuickUnionUF.find(getCoordinates(row, col)) == grid_size * grid_size) {
			percolated = true;
		}
	}*/

	// handles 2D 1 based coordinates to 0 based array notation
	private int getCoordinates(int row, int col) {

		return (grid_size * (row - 1)) + col - 1;
	}

	// does the system percolate?
	public boolean percolates() {
		int headPtr = weightedQuickUnionUF.find(grid_size * grid_size);
		int tailPtr = weightedQuickUnionUF.find(grid_size * grid_size + 1);
		return headPtr == tailPtr;
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		this.validate(row, col);
		return this.grid[getCoordinates(row, col)];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (!isOpen(row, col))
			return false;
		int curPtr = weightedQuickUnionUF.find(getCoordinates(row, col));
		int headPtr = weightedQuickUnionUF.find(grid_size * grid_size);
		return curPtr == headPtr;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return this.openSites;
	}

	// test client (optional)
	public static void main(String[] args) {

	}

	@Override
	public String toString() {
		String ret = "Percolation [grid=\n";
		for (int i = 0; i < grid_size; i++) {
			for (int j = 0; j < grid_size; j++) {
				if (grid[i * grid_size + j])
					ret += " " + 0;
				else
					ret += " " + 1;
			}
			ret += "\n";
		}
		ret += ", openSites=" + openSites + ", n=" + grid_size + "]";
		ret += "\n";
		for (int i = 0; i < grid_size; i++) {
			for (int j = 0; j < grid_size; j++) {
				ret += " " + weightedQuickUnionUF.find(i * grid_size + j);
			}
			ret += "\n";
		}
		ret += " " + weightedQuickUnionUF.find(grid_size * grid_size);
		ret += " " + weightedQuickUnionUF.find(grid_size * grid_size + 1);
		return ret;
	}

	private void validate(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
	}

	private void validate(int row, int col) {
		if (row > this.grid_size || row < 1) {
			throw new IllegalArgumentException();
		}
		if (col > this.grid_size || col < 1) {
			throw new IllegalArgumentException();
		}
	}

}
