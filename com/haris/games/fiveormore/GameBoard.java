package com.haris.games.fiveormore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel {

	private static final long serialVersionUID = 3844198344852765182L;

	// private int pathLength;
	private int blic = 3;

	private JButton[][] buttons;

	private boolean clicked = false;

	private Color[] colors;

	private int columns;

	private int figures;
	private GameEngine game; // = new Igra (duzina, visina, figure);

	private MouseListener mouseListener;
	private Timer pathFlasher, errorBlinker;
	private int rows;

	private Pair start, end;

	private boolean useNumbers = true;

	public GameBoard(int numOfRows, int numOfColumns, int numOfFigures) {
		this.columns = numOfColumns;
		this.rows = numOfRows;
		this.figures = numOfFigures;

		this.colors = new Color[figures];

		this.buttons = new JButton[rows][columns];

		this.game = new GameEngine(rows, columns, figures);

		setLayout(new GridLayout(rows, columns));
		this.mouseListener = new MouseListenerImpl();
		createRandomColorsArray();

		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++) {
				buttons[i][j] = createNewButton();
				add(buttons[i][j]);
			}

		putNextRandomFigures();
	}

	private JButton createNewButton() {
		JButton btn = new JButton("");
		btn.setPreferredSize(new Dimension(50, 50));
		btn.setBackground(Color.lightGray);

		btn.setVisible(true);
		btn.addMouseListener(mouseListener);

		return btn;
	}

	private void createRandomColorsArray() {
		Random r = new Random();
		for (int i = 0; i < figures; i++) {
			int a, b, c;
			a = r.nextInt(256);
			b = r.nextInt(256);
			c = r.nextInt(256);
			colors[i] = new Color(a, b, c);
		}
	}

	public void draw() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				refreshButton(i, j);
			}
		}
	}

	public JButton getButton(Pair index) {
		return this.buttons[index.x][index.y];
	}
	public JButton[][] getButtons() {
		return buttons;
	}

	public Pair getEnd() {
		return end;
	}

	public GameEngine getGame() {
		return game;
	}

	public Pair getStart() {
		return start;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void putNextRandomFigures() {
		game.putFigures();
		draw();
	}

	private void refreshButton(int i, int j) {
		if (this.useNumbers) {
			if (game.getBoard()[i][j] != 0) {
				buttons[i][j].setText("" + game.getBoard()[i][j]);
			} else {
				buttons[i][j].setText("");
			}
			buttons[i][j].setBackground(Color.lightGray);
		} else {
			if (game.getBoard()[i][j] != 0) {
				buttons[i][j].setBackground(colors[game.getBoard()[i][j] - 1]);
				buttons[i][j].setText("");
			} else {
				buttons[i][j].setBackground(Color.lightGray);
				buttons[i][j].setText("");
			}
		}
	}

	private void resetButtonUI(Pair coordinate) {
		if (useNumbers) {
			this.buttons[coordinate.x][coordinate.y]
					.setBackground(Color.lightGray);
		} else {
			this.buttons[coordinate.x][coordinate.y]
					.setBackground(this.colors[this.game.getFieldAt(coordinate) - 1]);
		}

	}

	public void setButtons(JButton[][] buttons) {
		this.buttons = buttons;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void setEnd(Pair end) {
		this.end = end;
	}

	public void setGame(GameEngine game) {
		this.game = game;
	}

	// --------------------------------------------------------------

	// Use numbers theme
	public void setNumbersTheme(boolean arg) {
		this.useNumbers = arg;
	}

	public void setStart(Pair start) {
		this.start = start;
	}
	
	private class ErrorBlinker implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			errorBlinker.stop();
		}
	}

	// -------------------------------------------
	//
	// -------------------------------------------
	private class MouseListenerImpl implements MouseListener {

		public void mouseClicked(MouseEvent event) {
			if (!clicked) {
				System.out.println("Clicked first time");
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						if (event.getSource() == buttons[i][j]) {
							setStart(new Pair(i, j));
						}
					}
				}

				if (getGame().getFieldAt(getStart()) != 0) {
					buttons[getStart().x][getStart().y]
							.setBackground(Color.yellow);
					setClicked(true);
				}

			} else {
				System.out.println("Clicked 2nd time");
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						if (event.getSource() == buttons[i][j]) {
							setEnd(new Pair(i, j));
						}
					}
				}

				if (getStart().equals(getEnd())) {
					System.out.println("same source and destination");
					resetButtonUI(getStart());
					setClicked(false);
				} else {
					List<Pair> path = getGame().move(getStart(), getEnd());
					draw();
					if (path != null) {
						System.out.println("Have path");
						pathFlasher = new Timer(250, new PathFlasher(path));
						pathFlasher.start();
					} else {
						System.out.println("No path");
						buttons[getStart().x][getStart().y]
								.setBackground(Color.red);
						blic = 3;
//						errorBlinker = new Timer(300, new ErrorBlinker());
//						errorBlinker.start();
					}
					
					setClicked(false);
				}
			}
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}

		public void mousePressed(MouseEvent event) {
		}

		public void mouseReleased(MouseEvent event) {
		}
	}

	// -------------------------------------------
	private class PathFlasher implements ActionListener {
		private List<Pair> path;
		private int pathElementIndex;
		
		public PathFlasher(List<Pair> path) {
			super();
			this.path = path;
			this.pathElementIndex = 0;
		}

		public void actionPerformed(ActionEvent event) {
			if (pathElementIndex == path.size()) {
				Pair previousIndex = path.get(pathElementIndex - 1);
				refreshButton(previousIndex.x, previousIndex.y);
				pathFlasher.stop();
				return;
			}
			
			if (useNumbers) {
				getButton(path.get(pathElementIndex))
						.setBackground(Color.green);
			} else {
				int figure = game.getFieldAt(getEnd());
				getButton(path.get(pathElementIndex))
					.setBackground(colors[figure - 1]);
			}
			
			if (pathElementIndex > 0) {
				Pair previousIndex = path.get(pathElementIndex - 1);
				refreshButton(previousIndex.x, previousIndex.y);
			}
			
			pathElementIndex++;
		}
	}
}
