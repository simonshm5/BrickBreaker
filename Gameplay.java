import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener, KeyListener {

	/**
	 * 
	 */
	
	private Random rand = new Random();
	private static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 25;

	private Timer timer;
	private int delay = 8;

	private int playerX = 310;

	private int ballPosX = rand.nextInt(700);
	private int ballPosY = rand.nextInt(20)+300;
	private int ballXDir = -1;
	private int ballYDir = -2;

	private MapGenerator mg;

	public Gameplay() {
		mg = new MapGenerator(5, 5);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

	}

	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		// bricks
		mg.draw((Graphics2D) g);

		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);

		// paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		// ball
		g.setColor(Color.white);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if (totalBricks <= 0) {
			play = false;
			ballXDir = 0; 
			ballYDir = 0;
			
			g.setColor(Color.red);
			g.setFont(new Font("Impact", Font.ITALIC, 70));
			g.drawString("You won!", 190, 300);
	
			
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter To Play Again", 130, 400);
			
			
		}
		
		if (ballPosY > 570) {
			play = false;
			ballXDir = 0; 
			ballYDir = 0;
			
			g.setColor(Color.red);
			g.setFont(new Font("Impact", Font.ITALIC, 30));
			g.drawString("GAME OVER, YOUR SCORE: " + score, 190, 300);

			
			
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Press Enter To Play Again", 190, 400);
			
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
			ballYDir = -ballYDir;
		}

		A: for (int i = 0; i < mg.map.length; i++) {
			for (int j = 0; j < mg.map[0].length; j++) {
				if (mg.map[i][j] > 0) {
					int brickX = j * mg.brickWidth + 80;
					int brickY = i * mg.brickHeight + 50;
					int brickWidth = mg.brickWidth;
					int brickHeight = mg.brickHeight;

					Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
					Rectangle brickRect = rect;

					if (ballRect.intersects(brickRect)) {
						mg.setBrickValue(0, i, j);
						totalBricks--;
						score++;

						if (ballPosX + 19 <= brickRect.x || ballPosY + 1 > brickRect.x + brickRect.width) {
							ballXDir = -ballXDir;
						} else {
							ballYDir = -ballYDir;
						}
						
						break A;
					}
				}
			}
		}
		if (play) {
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			if (ballPosX < 0) {
				ballXDir = -ballXDir;
			}
			if (ballPosY < 0) {
				ballYDir = -ballYDir;
			}
			if (ballPosX > 670) {
				ballXDir = -ballXDir;
			}
		}
		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}

		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballPosX = rand.nextInt(700); 
				ballPosY = rand.nextInt(20)+300;
				ballXDir = -1;
				ballYDir = -2; 
				playerX = 310; 
				score = 0; 
				totalBricks = 21;
				mg = new MapGenerator(7, 7);
				
				repaint();
			}
		}
		
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
	
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		
		
	}

}
