package smolina.model;

import java.io.IOException;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import smolina.Settings;
import smolina.controller.GamePanel;
import smolina.rle.RleReader;



public class Game {
	private GamePanel gamePanel;
	private Board board;
	private Timer timer;
	
	public Game(GamePanel gpanel) {
		this.gamePanel = gpanel;
		timer = new Timer();
	}
	
	public void play() {
	
//		board.addCell(50, 50);
//		board.addCell(50, 51);
//		board.addCell(50, 52);
//		board.addCell(51, 52);
//		board.addCell(52, 50);
		
		board = new Board();
		
		try {
			BitSet[] bits = RleReader.read("C:\\Users\\SmolinaIS\\Desktop\\test.txt");
			for (int j = 0; j < bits.length; j++) {
				for (int i = 0; i < bits[j].length(); j++)
					if (bits[j].get(i))
					board.addCell(i, j);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		board.addCell(50, 50);
//		board.addCell(50, 51);
//		board.addCell(50, 52);
//		board.addCell(51, 52);
//		board.addCell(52, 50);
//		board.addCell(52, 51);
//		board.addCell(52, 52);
		
//		board.addCell(50, 50);
//		board.addCell(50, 51);
//		board.addCell(50, 52);
//		board.addCell(51, 52);
//		board.addCell(49, 51);
		
//		try {
//			RleReader.read("C:\\Users\\SmolinaIS\\Desktop\\test.txt");
//			int countX = RleReader.getWidthTempl();
//			int countY = RleReader.getHeightTempl();
//			int count = Math.max(countX, countY);
//			//Settings.countX = countX;
//			//Settings.countY = countY;
//			board = new Board();
//			
//			LinkedList<LinkedList<Boolean>> tmpl = RleReader.getTemplate();
//			int i = 0, j = 0;
//			for (LinkedList<Boolean> row: tmpl) {
//				i = 0;
//				for (Boolean val: row) {
//					if (val)
//						board.addCell(i, j);
//					i++;
//				}
//				j++;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		

		gamePanel.start(board);
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				board.nextGeneration();
				gamePanel.repaint();
			}
		};
		timer.scheduleAtFixedRate(task, 300, 100);		
	}
	
	public Board getBoard() {
		return board;
	}
	
}
