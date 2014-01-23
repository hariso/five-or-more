package com.haris.games.fiveormore;

import java.io.IOException;

public class EntryPoint {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Console c = new Console();
		String name = "/home/haris/Desktop/game.ser";
		c.save(name);
		c.load(name);
		c.start();
	}
}
