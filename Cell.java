public class Cell {
	private int x;
	private int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Object obj) {
		Cell cast = (Cell) obj;
		return cast.x == x && cast.y == y;
	}
	
	public boolean inBounds(int width, int height) {
		return (0 <= x && x < width && 0 <= y && y < height);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}