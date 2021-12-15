package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

	private static long lastFrameTime;
    private static GameWindow game_window;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float dropV = 200;
    private static int score = 0;

    public static void main(String[] args) throws IOException {

    	backGround = ImageIO.read(GameWindow.class.getResourceAsStream("drops_on_glass.jpg"));
    	gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("GameOver.png"));
    	drop = ImageIO.read(GameWindow.class.getResourceAsStream("waterDrop.png"));

	    game_window = new GameWindow();
	    game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    game_window.setLocation(200,100);
	    game_window.setSize(906,478);
	    game_window.setResizable(false);

	    lastFrameTime = System.nanoTime();

	    GameField gameField = new GameField();
		gameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				float drop_right = drop_left+drop.getWidth(null);
				float drop_bottom = drop_top+drop.getHeight(null);

				boolean is_drop = x>=drop_left&&x<=drop_right&&y>=drop_left&&y<=drop_bottom;

				if (is_drop) {
					drop_top=-100;
					drop_left=(int) (Math.random()*(gameField.getWidth()-drop.getWidth(null)));
					dropV+=20;
					score++;
					game_window.setTitle("Score : "+score);
				}
			}
		});

	    game_window.add(gameField);

	    game_window.setVisible(true);
    }

    private static void onRepaint(Graphics g){
    	long currentTime = System.nanoTime();
    	float deltaTime = (currentTime-lastFrameTime)*0.000000001f;
    	lastFrameTime = currentTime;

    	drop_top +=dropV*deltaTime;

    	g.drawImage(backGround,0,0,null);
		g.drawImage(drop,(int) drop_left,(int) drop_top,null);
		if (drop_top > game_window.getHeight())g.drawImage(gameOver,0,0,null);
	}

    private static class GameField extends JPanel {
    	@Override
		protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		onRepaint(g);
    		repaint();
		}
	}




}
