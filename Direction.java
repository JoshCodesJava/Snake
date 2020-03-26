public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;

	public Direction opposite() {
		switch(this) {
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		}
		
		return null;
	}
	
	public Cell moveInDirection(Cell c) {
		int x = c.getX();
		int y = c.getY();
		switch(this) {
		case NORTH:
			return new Cell(x, y-1);
		case SOUTH:
			return new Cell(x, y+1);
		case EAST:
			return new Cell(x+1, y);
		case WEST: 
			return new Cell(x-1, y);
		}
		
		return null;
	}
}