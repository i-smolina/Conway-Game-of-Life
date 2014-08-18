package smolina.model;

import smolina.Settings;

public class Board {
	Cell[][] cells;
	int countX;
	int countY;
	
	public Board() {
		countX = Settings.countX;
		countY = Settings.countY;
				
		cells = new Cell[countX][countY];
		for (int i = 0; i < countX; i++)
			for (int j = 0; j < countY; j++)
				cells[i][j] = new Cell(i, j);
		
		// add flat neighbors above and below
		for (int i = 0; i < countX; i++)
			cells[i][0].addNeighFlat(cells[i][1]);
		
		for (int i = 0; i < countX; i++)
			for (int j = 1; j < countY - 1; j++) {
				cells[i][j].addNeighFlat(cells[i][j - 1]);
				cells[i][j].addNeighFlat(cells[i][j + 1]);
			}					
		
		for (int i = 0; i < countX; i++)
			cells[i][countY - 1].addNeighFlat(cells[i][countY - 2]);
		
		// add flat neighbors left and right
		for (int j = 0; j < countY; j++)
			cells[0][j].addNeighFlat(cells[1][j]);
		
		for (int i = 1; i < countX - 1; i++)
			for (int j = 0; j < countY; j++) {
				cells[i][j].addNeighFlat(cells[i - 1][j]);
				cells[i][j].addNeighFlat(cells[i + 1][j]);
			}		
		
		for (int j = 0; j < countY; j++)
			cells[countX - 1][j].addNeighFlat(cells[countX-2][j]);
		
		// add left top diagonal
		for (int i = 1; i < countX; i++)
			for (int j = 0; j < countY - 1; j++)
				cells[i][j].addNeighFlat(cells[i - 1][j + 1]);
		
		// add left bottom diagonal
		for (int i = 0; i < countX - 1; i++)
			for (int j = 1; j < countY; j++)
				cells[i][j].addNeighFlat(cells[i + 1][j - 1]);
		
		// add right top diagonal
		for (int i = 0; i < countX - 1; i++)
			for (int j = 0; j < countY - 1; j++)
				cells[i][j].addNeighFlat(cells[i + 1][j + 1]);
		
		for (int i = 1; i < countX; i++)
			for (int j = 1; j < countY; j++)
				cells[i][j].addNeighFlat(cells[i - 1][j - 1]);
		
//		for (int i =0; i < countX; i++) {
//			for (int j = 0; j < countY; j++)
//				System.out.print(cells[i][j].getCountNeigh()+" ");
//			System.out.println();
//		}
	}	

	public void addCell(int i, int j) {
		try {
			cells[i][j].setStatus(Status.live);
			cells[i][j].notifyNeighbors();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nextGeneration() {
		// 1) change status
		for (int i = 0; i < countX; i++)
			for (int j = 0; j < countY; j++) {				
				if (cells[i][j].getStatus() == Status.live && cells[i][j].getCountNeigh() > 3) 
					cells[i][j].setStatus(Status.dead);
				
				if (cells[i][j].getStatus() == Status.live && cells[i][j].getCountNeigh() < 2) 
					cells[i][j].setStatus(Status.dead);
				
				if (cells[i][j].getStatus() == Status.empty && cells[i][j].getCountNeigh() == 3) 
					cells[i][j].setStatus(Status.born);				
			}
		// 2) notify neighbors
		for (int i = 0; i < countX; i++)
			for (int j = 0; j < countY; j++)
				cells[i][j].notifyNeighbors();
		
		// 3) fix status
		for (int i = 0; i < countX; i++)
			for (int j = 0; j < countY; j++)
				cells[i][j].fixStatus();
	}
	
	public Cell[][] getCells() {
		return cells;
	}
}
