package com.haris.games.fiveormore;

public class Pair {
	public int x;
	public int y;

	public Pair(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Pair)) {
			return false;
		}
		
		Pair that = (Pair) obj;
		return that.x == this.x && that.y == this.y;
	}

	@Override
	public String toString() {
		return this.x + ", " + this.y;
	}
	public Pair add(int addX, int addY) {
		return new Pair(this.x + addX, this.y + addY);
	}

	public Pair add(Pair pair) {
		return add(pair.x, pair.y);
	}

	public Pair multiply(int i) {
		return new Pair(i * this.x, i * this.y);
	}

}
