import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SnakeGame implements Runnable {
	private Random rand = new Random();
	private DirectionController dirController;
	private LinkedList<Cell> snake = new LinkedList<>();
	private Direction curDirection = Direction.EAST;
	private Cell apple;

	public SnakeGame(DirectionController controller) {
		this.dirController = controller;
		snake.add(new Cell(4,4));
		snake.add(new Cell(3,4));
		apple = new Cell(rand.nextInt(17), rand.nextInt(15));
	}

	public LinkedList<Cell> getSnake() {
		return snake;
	}

	public void run() {
		Cell head = snake.getFirst();
		Cell newHead = null;

		Direction newDirection = dirController.getDirection();
		Direction fixedDir = (curDirection == newDirection.opposite()) ? curDirection : newDirection;

		switch(fixedDir) {
		case NORTH:
			newHead = new Cell(head.getX(), head.getY()-1);
			break;
		case SOUTH:
			newHead = new Cell(head.getX(), head.getY()+1);
			break;
		case EAST:
			newHead = new Cell(head.getX()+1, head.getY());
			break;
		case WEST:
			newHead = new Cell(head.getX()-1, head.getY());
			break;
		};
		
		curDirection = fixedDir;

		synchronized(snake) {	
			if(!newHead.equals(getApple()))
				snake.removeLast();
			else
				apple = new Cell(rand.nextInt(17), rand.nextInt(15));
			
			if(newHead.getX() < 0 || newHead.getX() > 16 || newHead.getY() < 0 || newHead.getY() > 14 || snake.contains(newHead))
				System.exit(0);
			
			snake.add(0, newHead);
		}
	}

	public void start() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(this, 0, 300, TimeUnit.MILLISECONDS);

	}

	public Cell getApple() {
		return apple;
	}
}