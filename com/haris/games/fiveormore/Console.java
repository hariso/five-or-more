package com.haris.games.fiveormore;

import java.util.Arrays;
import java.util.Scanner;

public class Console {
	private static int rows, columns, figures;
	private static Pair from, to;
	private static GameEngine game;
	private static Scanner in;
	
	public static void main(String[] args) {
		in = new Scanner(System.in);

		// Enter size
		System.out.println("Please enter size of the game board (number of rows, then whitespace, then number of columns)");
		rows = in.nextInt();
		columns = in.nextInt();

		// Enter number of figures
		System.out.println("Please enter number of figures");
		figures = in.nextInt();

		game = new GameEngine(rows, columns, figures);

		do {
			System.out.println("POINTS: " + game.getPoints());
			write(game.getBoard());

			System.out.println("Next figures: " + Arrays.toString(game.getNextFigures()));

			readFromCoordinates();

			readToCoordinates();

			boolean moved = game.move(from.add(-1, -1), to.add(-1, -1)); 
			if (!moved) {
				System.out.println("You cannot move the figure to the given destination.");
			} else {
				if (!game.fiveCleared()) {
					game.putFigures();
				}
			}
		} while (!game.isBoardEmpty() && game.getNumOfEmptyFields() >= GameEngine.NUM_OF_FIGURES_ADDED);

		System.out.println("POINTS: " + game.getPoints());
		write(game.getBoard());

		if (game.isBoardEmpty())
			System.out.println("Congratulations, you won!");

		if (game.getNumOfEmptyFields() < figures)
			System.out.println("Unfortunately, you lost.");

		in.close();
	}

	private static void readFromCoordinates() {
		boolean validInput = true;

		do {
			from = readPair();

			Pair from0 = from.add(-1, -1);
			validInput = game.isCoordinateAvailable(from0)
						&& !game.isEmptyFieldAt(from0);

			if (!game.isCoordinateAvailable(from0)) {
				System.out.println(String.format(
						"Row number must be between %d and %d, and column number must be between %d and %d",
						1, rows, 1, columns));
			}

			if (game.isCoordinateAvailable(from0) && game.isEmptyFieldAt(from0)) {
				System.out.println("No figure to move!");
			}
		} while (!validInput);
	}
	
	private static void readToCoordinates() {
		boolean validInput = true;
		
		do {
			to = readPair();
			Pair to0 = to.add(-1, -1);
			
			validInput = game.isCoordinateAvailable(to0)
					&& !from.equals(to)
					&& game.isEmptyFieldAt(to0);
			
			if (!game.isCoordinateAvailable(to0)) {
				System.out.println(String.format(
						"Row number must be between %d and %d, and column number must be between %d and %d",
						1, rows, 1, columns));
			}
			
			if (game.isCoordinateAvailable(to0) && !game.isEmptyFieldAt(to0)) {
				System.out.println("The destination is not empty.");
			}
			
			if (from.equals(to)) {
				System.out.println("Source and destination are the same.");
			}
			
		} while (!validInput);
	}

	private static Pair readPair() {
		return new Pair(in.nextInt(), in.nextInt());
	}

	/**
	 * Print a matrix to standard output.
	 * 
	 * @param m
	 */
	public static void write(int[][] m) {
		if (m == null) {
			return;
		}

		for (int i = 0; i <= m.length; i++) {
			// Board header, indicating column numbers
			if (i == 0) {
				System.out.print("\\  ");
				for (int k = 1; k <= m[i].length; k++) {
					System.out.print(k + " ");
				}
				System.out.println();

			} else {
				// Row numbers
				System.out.print(i + "  ");

				for (int j = 0; j < m[i - 1].length; j++) {
					System.out.print(
							(m[i - 1][j] != 0 ? m[i - 1][j] : ".") 
							+ " ");
				}

				System.out.println();
			}

		}
	}
}
