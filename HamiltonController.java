public class HamiltonController implements DirectionController {
	private SnakeGame game;

	public Direction getDirection() {
		int x = game.getSnake().getFirst().getX();
		int y = game.getSnake().getFirst().getY();
		System.out.println(x);
		
		if(x==0 && y==0)
			return Direction.EAST;
		else if(x==0)
			return Direction.NORTH;
		else if(y==(SnakeGame.HEIGHT-1))
			return Direction.WEST;
		else if(x == (SnakeGame.WIDTH-1))
			return y%2==0 ? Direction.SOUTH : Direction.WEST;
		else if(x==1)
			return y%2==0 ? Direction.EAST : Direction.SOUTH;
		else
			return y%2==0 ? Direction.EAST : Direction.WEST;
	}

	public void attach(SnakeGame snakeGame) {
		game = snakeGame;
	}
}