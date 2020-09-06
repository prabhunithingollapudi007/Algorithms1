import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Board {

	final private int dimension;
	final private int tiles[][];

	// create a board from an n-by-n array of tiles,
	// where tiles[row][col] = tile at (row, col)

	public Board(int[][] tiles) {
		this.tiles = tiles;
		this.dimension = tiles[0].length;
	}

	// string representation of this board
	public String toString() {
		String ret = "" + dimension + "\n";
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				ret += " ";
				ret += tiles[i][j];
			}
			ret += "\n";
		}
		return ret;

	}

	// board dimension n
	public int dimension() {
		return this.dimension;
	}

	// number of tiles out of place
	public int hamming() {
		int ret = 0;
		int temp = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if(tiles[i][j] == 0) {
					temp++;
					continue;
				}
				if (tiles[i][j] != temp)
					ret++;
				temp++;
			}
		}
		return ret;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan() {
		int x, y;
		int count = 1;
		int ret = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++, count++) {
				if (tiles[i][j] != count) {
					if (tiles[i][j] == 0)
						continue;
					x = tiles[i][j] / dimension;
					y = tiles[i][j] % dimension;
					y -= 1;
					if (y == -1) {
						x -= 1;
						y = dimension - 1;
					}

					ret += Math.abs(i - x) + Math.abs(j - y);
				}
			}
		}
		return ret;

	}

	// is this board the goal board?
	public boolean isGoal() {
		if (tiles[dimension - 1][dimension - 1] != 0)
			return false;
		int temp = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if(i == dimension - 1 && j == dimension - 1)
					return true;
				if (tiles[i][j] != temp )
					return false;
				temp++;
			}
		}
		return true;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if(y == null)
			return false;
		if(y instanceof String)
			return false;
		try {
			Board temp = (Board) y;
			if (temp.dimension != this.dimension)
				return false;
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if (tiles[i][j] != temp.tiles[i][j])
						return false;
				}
			}
		}
		catch(ClassCastException e) {
			return false;
		}
		
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		return new NeighbourIterator();
	}

	private class NeighbourIterator implements Iterable<Board> {

		private boolean swapArray(int[][] tempArray, int src_x, int src_y, int dest_x, int dest_y) {

			if (dest_x < 0 || dest_x >= dimension)
				return false;
			if (dest_y < 0 || dest_y >= dimension)
				return false;

			int temp = tempArray[src_x][src_y];
			tempArray[src_x][src_y] = tempArray[dest_x][dest_y];
			tempArray[dest_x][dest_y] = temp;
			return true;
		}

		private int[][] getClone(int[][] tempArray) {
			return Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
		}

		@Override
		public Iterator<Board> iterator() {
			List<Board> boards = new ArrayList<Board>();
			int src_x = 0;
			int src_y = 0;

			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if (tiles[i][j] == 0) {
						src_x = i;
						src_y = j;
						i = dimension;
						j = dimension;
					}
				}
			}

			int[][] tempArray;

			// add left neighbor
			if (swapArray(tiles, src_x, src_y, src_x - 1, src_y)) {
				tempArray = getClone(tiles);
				boards.add(new Board(tempArray));
				swapArray(tiles, src_x, src_y, src_x - 1, src_y);
			}

			// add right neighbor
			if (swapArray(tiles, src_x, src_y, src_x + 1, src_y)) {
				tempArray = getClone(tiles);
				boards.add(new Board(tempArray));
				swapArray(tiles, src_x, src_y, src_x + 1, src_y);
			}

			// add bottom neighbor
			if (swapArray(tiles, src_x, src_y, src_x, src_y + 1)) {
				tempArray = getClone(tiles);
				boards.add(new Board(tempArray));
				swapArray(tiles, src_x, src_y, src_x, src_y + 1);

			}

			// add top neighbor
			if (swapArray(tiles, src_x, src_y, src_x, src_y - 1)) {
				tempArray = getClone(tiles);
				boards.add(new Board(tempArray));
				swapArray(tiles, src_x, src_y, src_x, src_y - 1);

			}

			return boards.iterator();
		}
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin() {
		
		int[][]tempArray = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
		int temp;
		
		if(tempArray[0][0] == 0) {
			temp = tempArray[0][1];
			tempArray[0][1] = tempArray[dimension- 1][dimension - 1];
			tempArray[dimension - 1][dimension - 1] = temp;
			return new Board(tempArray);
		}
		
		if(tempArray[dimension- 1][dimension - 1] == 0) {
			temp = tempArray[0][1];
			tempArray[0][1] = tempArray[0][0] ;
			tempArray[0][0]  = temp;
			return new Board(tempArray);
		}
		
		temp = tempArray[0][0];
		tempArray[0][0] = tempArray[dimension- 1][dimension - 1];
		tempArray[dimension - 1][dimension - 1] = temp;

		return new Board(tempArray);
	}

	// unit testing (not graded)
	public static void main(String[] args) {

	}
}
