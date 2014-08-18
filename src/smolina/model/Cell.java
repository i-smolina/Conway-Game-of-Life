package smolina.model;

import java.util.LinkedList;

public class Cell{
	private Status status;
	private LinkedList<Cell> neigh;
	private int countLiveNeigh;
	private int x, y;
	private boolean needNotify;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		neigh = new LinkedList<Cell>();
		countLiveNeigh = 0;
		needNotify = false;
		status = Status.empty;
	}
	
	// neighbor says that he changed his status
	public void changedNeighbor(Status status) {
		if (status == Status.born)
			countLiveNeigh++;
		if (status == Status.live)
			countLiveNeigh++;
		if (status == Status.dead)
			countLiveNeigh--;		
	}

	public void addNeighFlat(Cell c) {
		neigh.add(c);
	}

	public void removeNeighFlat(Cell c) {
		neigh.remove(c);		
	}
	
	// notify neighbors that I changed status
	public void notifyNeighbors() {
		if (needNotify) {		
			for (Cell cell : neigh) {
				cell.changedNeighbor(status);
			}			
			needNotify = false;
		}
	}	
	
	// set new status
	public void setStatus(Status stat) {
		if (this.status == stat) 
			return;
		
		this.status = stat;
		needNotify = true;
	}
	
	// fix status after notification
	public void fixStatus() {
		if (status == Status.dead)
			status = Status.empty;
		if (status == Status.born)
			status = Status.live;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Status getStatus() {
		return status;		
	}
	
	public int getCountNeigh() {
		return countLiveNeigh;
	}	
}
