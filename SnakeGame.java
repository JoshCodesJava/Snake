import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SnakeGame implements Runnable {
	private Random rand = new Random();
	private DirectionController dirController;
	private LinkedList<Cell> snake = new LinkedList<>();
	private Direction curDirection = null;
	private Cell apple;
	public static final int WIDTH = 18;
	public static final int HEIGHT = 16;

	public SnakeGame(DirectionController controller) {
		this.dirController = controller;
		snake.add(new Cell(4,4));
		apple = new Cell(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
		controller.attach(this);
	}

	public LinkedList<Cell> getSnake() {
		return snake;
	}

	public void run() {
		Cell head = snake.getFirst();
		Cell newHead = null;

		Direction newDirection = dirController.getDirection();
		curDirection = (curDirection == newDirection.opposite()) ? curDirection : newDirection;

		switch(curDirection) {
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

		synchronized(snake) {	
			if(!newHead.equals(getApple()))
				snake.removeLast();
			else
				do {
					apple = new Cell(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
				}
				while(snake.contains(apple) || newHead.equals(apple));

			if(newHead.getX() < 0 || newHead.getX() >= WIDTH || newHead.getY() < 0 || newHead.getY() >= HEIGHT || snake.contains(newHead))
				System.exit(0);

			snake.add(0, newHead);
		}
	}

	public void start() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(this, 0, 100, TimeUnit.MILLISECONDS);

	}

	public Cell getApple() {
		return apple;
	}
}