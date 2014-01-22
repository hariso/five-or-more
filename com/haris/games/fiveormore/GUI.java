package com.haris.games.fiveormore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1347735409768172477L;

	private GameBoard board;
	private MessagesPanel messages;
	private JMenuBar menuBar;

	private static final int DEF_NUM_OF_ROWS = 10;
	private static final int DEF_NUM_OF_COLUMNS = 10;
	private static final int DEF_NUM_OF_FIGURES = 5;
	
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		setBackground(Color.green);
		
		createMenuBar();
		add(menuBar, BorderLayout.NORTH);
		
		initGameBoard();
		initMessagesPanel();
		
		pack();
		setVisible(true);
	}

	private void initGameBoard() {
		this.board = new GameBoard(DEF_NUM_OF_ROWS, DEF_NUM_OF_COLUMNS, DEF_NUM_OF_FIGURES);
		getContentPane().add(board, BorderLayout.CENTER);
	}
	
	private void initMessagesPanel() {
		this.messages = new MessagesPanel();		
		getContentPane().add(messages, BorderLayout.SOUTH);
	}

	private void createMenuBar() {
		this.menuBar = new JMenuBar();
		this.menuBar.add(createGamesMenu());
		this.menuBar.add(createOptionsMenu());
		this.menuBar.add(createHelpMenu());
	}

	private JMenu createGamesMenu() {
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		
		// New game
		JMenuItem newGame = new JMenuItem("New game");
		newGame.setToolTipText("Start new game");
		newGame.setMnemonic('N');
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(board);
				initGameBoard();			
				board.updateUI();
				
				getContentPane().remove(messages);				
				initMessagesPanel();
				messages.updateUI();
			}
		});
		newGame.setEnabled(false);
		gameMenu.add(newGame);
		
		// Save game
		JMenuItem saveGame = new JMenuItem("Save game");
		saveGame.setToolTipText("Save this game");
		saveGame.setMnemonic('S');
		saveGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implement this
			}

		});
		saveGame.setEnabled(false);
		gameMenu.add(saveGame);
		
		// Load game
		JMenuItem loadGame = new JMenuItem("Load game");
		loadGame.setToolTipText("Load a saved game");
		loadGame.setMnemonic('L');
		loadGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implement this
			}
		});
		loadGame.setEnabled(false);
		gameMenu.add(loadGame);
		
		// Highscores
		JMenuItem highscores = new JMenuItem("Highscores");
		highscores.setToolTipText("See highscores");
		highscores.setEnabled(false);
		gameMenu.add(highscores);
		
		// Exit game
		JMenuItem exitGame = new JMenuItem("Exit");
		exitGame.setToolTipText("Exit game");
		exitGame.setMnemonic('x');
		exitGame.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_X, ActionEvent.ALT_MASK));
		exitGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		gameMenu.add(exitGame);
		
		return gameMenu;
	}
	
	private JMenu createOptionsMenu() {
		JMenu optionsMenu = new JMenu();
		
		// Settings
		JMenuItem settings = new JMenuItem("Settings");
		settings.setToolTipText("Settings for this game");
		settings.setMnemonic('S');
		settings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implement this
			}
		});
		settings.setEnabled(false);
		optionsMenu.add(settings);
		
		return optionsMenu;
	}
}
