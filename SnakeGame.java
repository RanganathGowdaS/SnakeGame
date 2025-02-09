import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 20;
    private int GRID_WIDTH;
    private int GRID_HEIGHT;
    private int WIDTH;
    private int HEIGHT;
    private static final int DELAY = 150;
    
    private ArrayList<Point> snake;
    private Point food;
    private char direction;
    private boolean running;
    private Timer timer;
    private Random random;
    
    public SnakeGame() {
        updateDimensions();
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyHandler());
        random = new Random();
        startGame();
    }
    
    private void updateDimensions() {
        Dimension screenSize = getSize();
        if (screenSize.width == 0 || screenSize.height == 0) {
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        }
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height;
        GRID_WIDTH = WIDTH / TILE_SIZE;
        GRID_HEIGHT = HEIGHT / TILE_SIZE;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        revalidate();
    }
    
    private void startGame() {
        updateDimensions();
        snake = new ArrayList<>();
        snake.add(new Point(GRID_WIDTH / 2, GRID_HEIGHT / 2));
        direction = 'R';
        running = true;
        spawnFood();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void spawnFood() {
        do {
            food = new Point(random.nextInt(GRID_WIDTH), random.nextInt(GRID_HEIGHT));
        } while (snake.contains(food));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            repaint();
        }
    }

    private void move() {
        Point head = snake.get(0);
        Point newHead;

        switch (direction) {
            case 'U': newHead = new Point(head.x, (head.y - 1 + GRID_HEIGHT) % GRID_HEIGHT); break;
            case 'D': newHead = new Point(head.x, (head.y + 1) % GRID_HEIGHT); break;
            case 'L': newHead = new Point((head.x - 1 + GRID_WIDTH) % GRID_WIDTH, head.y); break;
            case 'R': newHead = new Point((head.x + 1) % GRID_WIDTH, head.y); break;
            default: return;
        }
        
        if (snake.contains(newHead)) {
            running = false;
            timer.stop();
            gameOver();
            return;
        }

        snake.add(0, newHead);
        if (newHead.equals(food)) {
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void checkCollision() {
        if (!running) {
            gameOver();
        }
    }

    private void gameOver() {
        int response = JOptionPane.showConfirmDialog(this, "Game Over! Restart?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            startGame();
        } else {
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateDimensions();
        
        if (running) {
            g.setColor(Color.RED);
            g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            
            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP: if (direction != 'D') direction = 'U'; break;
                case KeyEvent.VK_DOWN: if (direction != 'U') direction = 'D'; break;
                case KeyEvent.VK_LEFT: if (direction != 'R') direction = 'L'; break;
                case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
