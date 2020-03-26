public class HamiltonController implements DirectionController {
	private SnakeGame game;

	public Direction getDirection() {
		int x = game.getSnake().getFirst().getX();
		int y = game.getSnake().getFirst().getY();
	//	System.out.println(x);
		
		Direction curDir = game.getCurDirection();
		Cell curCell = new Cell(x,y);
		int min = Integer.MAX_VALUE;
		Direction ret = null;

		for(Direction dir : Direction.values()) {
			if(dir.opposite() != curDir) {
				Cell candidate = dir.moveInDirection(curCell);
				if(!candidate.inBounds(game.WIDTH, game.HEIGHT) 
						|| game.getSnake().contains(candidate) 
						|| (game.getSnake().peekLast() != null &&  game.getDist(candidate, game.getSnake().peekLast()) + 1 > game.getLength())
						)
					continue;
				
				
				int dist =  game.getDist(candidate, game.getApple());
				if(dist < min) {
					ret = dir;
					min = dist;
				}
			}
		}
		
		
		if(ret != null) return ret;
		
		System.out.println("??");
		
		if(x==0 && y==0)
			ret = Direction.EAST;
		else if(x==0)
			ret = Direction.NORTH;
		else if(y==(SnakeGame.HEIGHT-1))
			ret = Direction.WEST;
		else if(x == (SnakeGame.WIDTH-1))
			ret = y%2==0 ? Direction.SOUTH : Direction.WEST;
		else if(x==1)
			ret = y%2==0 ? Direction.EAST : Direction.SOUTH;
		else
			ret = y%2==0 ? Direction.EAST : Direction.WEST;
		
		return ret;
		
	}
	
	public Cell next(Cell cur) {
		int x = cur.getX();
		int y = cur.getY();
		if(x==0 && y==0)
			return new Cell(x+1, y);
		else if(x==0)
			return new Cell(x, y-1);
		else if(y==(SnakeGame.HEIGHT-1))
			return new Cell(x-1, y);
		else if(x == (SnakeGame.WIDTH-1))
			return y%2==0 ? new Cell(x,y+1) : new Cell(x-1,y);
		else if(x==1)
			return y%2==0 ? new Cell(x+1, y) : new Cell(x,y+1);
		else
			return y%2==0 ? new Cell(x+1, y) : new Cell(x-1, y);
	}

	public void attach(SnakeGame snakeGame) {
		game = snakeGame;
	}
}