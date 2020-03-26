public interface DirectionController {
	public Direction getDirection();
	public void attach(SnakeGame snakeGame);
	public Cell next(Cell cur);
}