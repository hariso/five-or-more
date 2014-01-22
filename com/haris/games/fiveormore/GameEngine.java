package com.haris.games.fiveormore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameEngine {

	private int[][] board;
	private int rows;
	private int columns;
	private int figures;

	private int[] nextFigures;
	private Random random;

	private int points;

	private int free;

	// Number of figures which are added by the computer in each move.
	public static final int NUM_OF_FIGURES_ADDED = 3;
	
	private static final Pair[] NEIGHBOURS = new Pair[] {new Pair(-1, 0), new Pair(0, 1), new Pair(1, 0), new Pair(0, -1)};

	public GameEngine(int rows, int columns, int figures) {
		super();

		this.rows = rows;
		this.columns = columns;
		this.board = new int[rows][columns];

		this.figures = figures;
		this.nextFigures = new int[NUM_OF_FIGURES_ADDED];
		this.random = new Random();

		this.free = rows * columns;
		
		generateNextFigures();
		putFigures();
	}

	public void putFigures() {
		for (int i = 0; i < nextFigures.length; i++) {
			putOnRandomPlace(nextFigures[i]);
		}

		generateNextFigures();
	}

	private void putOnRandomPlace(int figure) {
		int x = random.nextInt(rows), y = random.nextInt(columns);

		while (board[x][y] != 0) {
			x = random.nextInt(rows);
			y = random.nextInt(columns);
		}

		setFigureAt(x, y, figure);
	}

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

	private int getFieldAt(Pair coord) {
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
	 * if a path exists and returns true. Otherwise, returns false.
	 * 
	 * @param from
	 *            A 0-based coordinate.
	 * @param to
	 *            A 0-based coordinate.
	 * @return
	 */
	public boolean move(Pair from, Pair to) {
		List<Pair> path = discoverShortestPath(from, to);
		if (path == null) {
			return false;
		}
		
		setFieldAt(to, getFieldAt(from));
		setFieldAt(from, 0);
		return true;
	}

	private List<Pair> discoverShortestPath(Pair from, Pair to) {
		List<Pair> path = null;
		
		Queue<Pair> queue = new LinkedList<Pair>();
		queue.add(from);
		
		while (!queue.isEmpty()) {
			Pair head = queue.poll();
			if (head.equals(to)) {
				path = new ArrayList<Pair>();
				break;
			} else {
				for (Pair neighbour : getNeighbours(head)) {
					if (!queue.contains(neighbour) && isEmptyFieldAt(neighbour)) {
						queue.add(neighbour);
					}
				}
			}
		}
		
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

	// TODO Implement this.
	public boolean fiveCleared() {
		return false;
	}

}
