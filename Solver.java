import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	// find a solution to the initial board (using the A* algorithm)
	private MyBoard initial;
	private MyBoard twinInitial;
	private MinPQ<MyBoard> minPQ;
	private MinPQ<MyBoard> twinMinPQ;
	private boolean solvable;
	private List<Board> path;
	
	public Solver(Board initial) {
		if (initial == null)
			throw new IllegalArgumentException();
		
		this.initial = new MyBoard(initial, null);
		this.twinInitial = new MyBoard(initial.twin(), null);
		
		this.minPQ = new MinPQ<MyBoard>();
		this.twinMinPQ = new MinPQ<MyBoard>();
		
		this.path = new ArrayList<Board>();
		this.solvable = false;
		
		startSolving();
	}
	
	private class MyBoard implements Comparable<MyBoard>{

		int moves = 0;
		Board board;
		int priority;
		MyBoard previous;
		
		public MyBoard(Board initial, MyBoard previous) {
			board = initial;
			if(previous != null) {
				this.moves = previous.moves + 1;
			}
			this.previous = previous;
			priority = board.manhattan() + moves;
		}

		@Override
		public int compareTo(MyBoard o) {
			if(this.priority< o.priority)
				return -1;
			if(this.priority == o.priority)
				return 0;
			return 1;
		}
		
	}
	
	private void startSolving() {
		
		minPQ.insert(initial);
		twinMinPQ.insert(twinInitial);
		
		
		while(!initial.board.isGoal() && !twinInitial.board.isGoal()) {
		
			initial = minPQ.delMin();
			twinInitial = twinMinPQ.delMin();
			
			for(Board neighbor: initial.board.neighbors()) {
				if(initial.previous == null || !neighbor.equals(initial.previous.board))
					minPQ.insert(new MyBoard(neighbor, initial));
			}
			
			for(Board neighbor: twinInitial.board.neighbors()) {
				if(twinInitial.previous == null || !neighbor.equals(twinInitial.previous.board))
					twinMinPQ.insert(new MyBoard(neighbor, twinInitial));
			}
			
		}
		if(initial.board.isGoal())
			this.solvable = true;
	}
	
	// is the initial board solvable? (see below)
	public boolean isSolvable() {
		return solvable;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		if (!isSolvable())
			return -1;
		return initial.moves;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if(initial.board.isGoal()) {
			path.clear();
			path.add(initial.board);
			return new PathIterator();
		}
		if (!isSolvable())
			return null;
		return new PathIterator();
	}
	
	private class PathIterator implements Iterable<Board>{

		@Override
		public Iterator<Board> iterator() {
			MyBoard temp = new MyBoard(initial.board, initial.previous);
			while(temp != null) {
				path.add(temp.board);
				temp = temp.previous;
			}
			Collections.reverse(path);
			return path.iterator();
		}
		
	}
	
	// test client (see below)
	public static void main(String[] args) {

	}
}
