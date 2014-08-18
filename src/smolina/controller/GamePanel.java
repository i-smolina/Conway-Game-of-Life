package smolina.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Thread.State;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import smolina.Settings;
import smolina.model.Board;
import smolina.model.Cell;
import smolina.model.Status;

public class GamePanel extends JPanel{
	private int sizeCell;
	private Board board;
	
	public GamePanel() {
		setBorder(BorderFactory.createEtchedBorder());	
	}
	
	public void start(Board b) {
		this.board = b;
		sizeCell = (int) Math.min(getWidth() / Settings.countX, getHeight() / Settings.countY);
			
	}
	
	private void paintGrid(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		int w = sizeCell * Settings.countX;
		int h = sizeCell * Settings.countY;
		for (int i = 1; i <= Settings.countY; i++)
			g.drawLine(1, i * sizeCell, w, i * sizeCell);
		
		for (int j = 1; j <= Settings.countX; j++)
			g.drawLine(j * sizeCell, 1, j * sizeCell, h);
	}
	
	private void paintCells(Graphics g) {
		g.setColor(Color.GREEN);
		Cell[][] cells = board.getCells();
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[0].length; j++)
				if (cells[i][j].getStatus() == Status.live) {
					g.fillRect(cells[i][j].getX() * sizeCell, (Settings.countY - cells[i][j].getY() - 1) * sizeCell, sizeCell, sizeCell);
				}
	}
	
	private void paintBox(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Settings.countX * sizeCell, Settings.countY * sizeCell);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (board == null) return;
		
		paintBox(g);
		paintCells(g);
		paintGrid(g);
	}
	
	
}
