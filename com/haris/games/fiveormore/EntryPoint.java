package com.haris.games.fiveormore;

import java.io.IOException;

public class EntryPoint {
	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		final GUI gui = new GUI();
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				gui.setVisible(false);
				gui.setVisible(true);
				gui.toFront();
			}
		});
	}
}
