import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGameGui extends JPanel implements DirectionController {
	private volatile Direction dir = Direction.EAST;
	private volatile SnakeGame game;

	public SnakeGameGui(){
		setPreferredSize(new Dimension(1700, 1500));
		JFrame frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				key(key.getKeyCode());
			}
		});

		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable painter = new Runnable() {
			public void run() {
				repaint();
			}
		};

		executor.scheduleAtFixedRate(painter, 0, 1, TimeUnit.MILLISECONDS);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void paint(Graphics g) {
		for(int i = 0; i < 17; i++) {
			for(int j = 0; j < 15; j++) {
				g.setColor((i+j)%2==0?Color.DARK_GRAY:Color.GRAY);
				g.fillRect(i*100, j*100, 100, 100);
			}
		}

		if(game!=null) {
			g.setColor(Color.GREEN);
			Cell apple = game.getApple();
			g.fillRect(apple.getX()*100, apple.getY()*100, 100, 100);
			
			g.setColor(Color.RED);

			LinkedList<Cell> snake = game.getSnake();
			
			synchronized(snake) {
				for(Cell cell : snake)
					g.fillRect(cell.getX()*100, cell.getY()*100, 100, 100);
			}
		}
	}

	private void key(int keyCode) {
		if(keyCode == KeyEvent.VK_UP)
			dir = Direction.NORTH;
		else if(keyCode == KeyEvent.VK_RIGHT)
			dir = Direction.EAST;
		else if(keyCode == KeyEvent.VK_DOWN)
			dir = Direction.SOUTH;
		else if(keyCode == KeyEvent.VK_LEFT)
			dir = Direction.WEST;
	}

	public Direction getDirection() {
		return dir;
	}

	public void attachGame(SnakeGame game) {
		this.game = game;
	}

	public static void main(String[] args) {
		SnakeGameGui gui = new SnakeGameGui();
		SnakeGame game = new SnakeGame(gui);
		gui.attachGame(game);
		game.start();
	}
}