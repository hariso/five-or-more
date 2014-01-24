package com.haris.games.fiveormore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameEngine implements Serializable {

	private static final long serialVersionUID = -2796373771546992212L;
	
	private int[][] board;
	private int rows;
	private int columns;
	private int figures;

	private int[] nextFigures;
	private Random random;

	private int points;

	private int free;
	private boolean fiveCleared = false;

	private boolean[][] visited;

	// Number of figures which are added by the computer in each move.
	public static final int NUM_OF_FIGURES_ADDED = 3;
	
	// By adding a pair to a coordinate, we get the neighbour of the coordinate.
	private static final Pair[] NEIGHBOURS = new Pair[] {
		new Pair(-1, 0), 	// down 
		new Pair(0, 1), 	// up
		new Pair(1, 0), 	// right
		new Pair(0, -1)};	// left
	
	// Directions in which we check for same-valued fields.
	// Basically, we have 8 directions, but it's enough to have four, since the
	// other four can be calculated, by multiplying the first four with -1.
	private static final Pair[] DIRECTIONS = new Pair[] {
		new Pair(-1, -1), 	// south-est
		new Pair(1, 0),		// east
		new Pair(1, 1),		// north-east
		new Pair(0, 1)		// north
	};

	public GameEngine(int rows, int columns, int figures) {
		super();

		this.rows = rows;
		this.columns = columns;
		this.board = new int[rows][columns];

		this.figures = figures;
		this.nextFigures = new int[NUM_OF_FIGURES_ADDED];
		this.random = new Random();

		this.free = rows * columns;
		
		this.visited = new boolean[this.rows][this.columns];
		
		generateNextFigures();
		putFigures();
	}

	/**
	 * Put (already generated) figures
	 */
	public void putFigures() {
		for (int i = 0; i < nextFigures.length; i++) {
			putOnRandomPlace(nextFigures[i]);
		}

		generateNextFigures();
	}

	private void putOnRandomPlace(int figure) {
		Pair randomCoordinate = nextRandomCoordinate();

		while (getFieldAt(randomCoordinate) != 0) {
			randomCoordinate = nextRandomCoordinate();
		}

		setFieldAt(randomCoordinate, figure);
		checkFiveOrMore(randomCoordinate);
	}

	// Get next radnom coordinate
	private Pair nextRandomCoordinate() {
		int x = random.nextInt(rows), y = random.nextInt(columns);
		return new Pair(x, y);
	}

	// Generate next figures, pseudo-randomly
	private void generateNextFigures() {
		for (int i = 0; i < nextFigures.length; i++) {
			nextFigures[i] = random.nextInt(figures) + 1;
		}
	}

	public int getPoints() {
		return this.points;
	}

	public int[][] getBoard() {
		return this.board;
	}

	public int[] getNextFigures() {
		return this.nextFigures;
	}

	public boolean isCoordinateAvailable(Pair coordinate) {
		return 0 <= coordinate.x && coordinate.x < rows 
				&& 0 <= coordinate.y && coordinate.y < columns;
	}
	
	public boolean isEmptyFieldAt(Pair from) {
		if (!isCoordinateAvailable(from)) {
			throw new IllegalArgumentException("Invalid coordinates:" + from.toString());
		}

		return getFieldAt(from) == 0;
	}

	public int getFieldAt(Pair coord) {
		return board[coord.x][coord.y];
	}

	public int getNumOfEmptyFields() {
		return this.free;
	}

	public boolean isBoardEmpty() {
		return this.free == 0;
	}

	/**
	 * Moves field from field at <code>from</code> to field at <code>to</code>,
	 * if a path exists and returns the path, as a list of coordinates to visit
	 * in order to get to the destination. Otherwise, returns null.
	 * 
	 * @param from
	 *            A 0-based coordinate.
	 * @param to
	 *            A 0-based coordinate.
	 * @return
	 */
	public List<Pair> move(Pair from, Pair to) {
		// Check if we can move the figure
		List<Pair> path = discoverShortestPath(from, to);
		if (path == null) {
			return path;
		}
		
		// We have a path, lets move the figure
		setFieldAt(to, getFieldAt(from));
		setFieldAt(from, 0);

		// Check if the user managed to put 
		// an array of five or more same figures
		setFiveCleared(checkFiveOrMore(to));
		if (!fiveCleared()) {
			putFigures();
		}
		
		return path;
	}

	private void setFiveCleared(boolean b) {
		this.fiveCleared = b;
	}

	private boolean checkFiveOrMore(Pair start) {
		boolean cleared = false;
		
		int count;
		for (int i = 0; i < DIRECTIONS.length; i++) {
			 count = 1;
			 
			 Pair move = start.add(DIRECTIONS[i]);
			 while (isCoordinateAvailable(move)
					 &&  getFieldAt(move) == getFieldAt(start)) {
				 count++;
				 move = move.add(DIRECTIONS[i]);
			 }
			 
			 Pair oppositeDirection = DIRECTIONS[i].multiply(-1);
			 move = start.add(oppositeDirection);
			 while (isCoordinateAvailable(move)
					 && getFieldAt(move) == getFieldAt(start)) {
				 count++;
				 move = move.add(oppositeDirection);
			 }
			 
			 if (count >= 5) {
				// move is currently at an unavailable index, or at an index
				// where the field doesn't equal the one at "start"
				// we start from move.add(DIRECTIONS[i])
				for (int j = 1; j <= count; j++) {
					move = move.add(DIRECTIONS[i]);
					setFieldAt(move, 0);
					points++;
				}
				
				cleared = true;
			 }
		}
		
		return cleared;
	}

	private List<Pair> discoverShortestPath(Pair from, Pair to) {
		List<Pair> path = null;
		
		Queue<Pair> queue = new LinkedList<Pair>();
		queue.add(from);
		
		while (!queue.isEmpty()) {
			Pair head = queue.poll();
			markAsVisited(head);
			if (head.equals(to)) {
				path = extractPath(head);
				break;
			} else {
				for (Pair neighbour : getNeighbours(head)) {
					if (!isVisited(neighbour) && isEmptyFieldAt(neighbour)) {
						neighbour.data = head;
						queue.add(neighbour);
					}
				}
			}
		}
		
		clearVisitedMarks();
		return path;
	}

	private void clearVisitedMarks() {
		for (int i = 0; i < this.visited.length; i++) {
			for (int j = 0; j < this.visited[i].length; j++) {
				this.visited[i][j] = false;
			}
		}
	}

	private boolean isVisited(Pair coord) {
		return this.visited[coord.x][coord.y];
	}

	private void markAsVisited(Pair coord) {
		this.visited[coord.x][coord.y] = true;
	}

	private List<Pair> extractPath(Pair to) {
		List<Pair> path = new ArrayList<Pair>();
		
		for (Pair current = to; current != null; current = (Pair) current.data) {
			path.add(current);
		}
		
		Collections.reverse(path);
		return path;
	}

	private List<Pair> getNeighbours(Pair start) {
		List<Pair> neighbours = new ArrayList<Pair>();
		
		Pair possibleNeighbour;
		for (int i = 0; i < NEIGHBOURS.length; i++) {
			possibleNeighbour = start.add(NEIGHBOURS[i]);
			if (isCoordinateAvailable(possibleNeighbour)) {
				neighbours.add(possibleNeighbour);				
			}
		}
		
		return neighbours;
	}

	private void setFieldAt(Pair coord, int figure) {
		setFigureAt(coord.x, coord.y, figure);
	}

	private void setFigureAt(int x, int y, int figure) {
		this.free += (figure == 0 ? 1 : -1);
		board[x][y] = figure;		
	}

	public boolean fiveCleared() {
		return this.fiveCleared;
	}

}
