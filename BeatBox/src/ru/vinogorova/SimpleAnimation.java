package ru.vinogorova;

import java.awt.*;
import javax.swing.*;

public class SimpleAnimation {
	
	JFrame frame = new JFrame();
	static int coord1 = 40;
	static int coord2 = 40;
	
	public static void main(String[] args) {
			new SimpleAnimation().go();
	}
	
	public void go() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyDrawPanel2 drawPanel = new MyDrawPanel2();
		frame.getContentPane().add(drawPanel);
		frame.setSize(700, 800);
		frame.setVisible(true);
		
		for (int i = 0; i < 100; i++) {
			coord1 +=5;
			coord2 +=5;
			
			drawPanel.repaint();
			
			try {
				Thread.sleep(50);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public class MyDrawPanel2 extends JPanel{
		
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			GradientPaint gradient = new GradientPaint(100, 100, randomColor(), 200, 200, randomColor());
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g2d.setPaint(gradient);
			g2d.fillOval(coord1, coord2, 100, 100);
		}
		
		private Color randomColor() {
			int red = (int) (Math.random()*255);
			int blue = (int) (Math.random()*255);
			int green = (int) (Math.random()*255);
			
			Color color = new Color(red, green, blue);
			return color;
		}
	}
}
