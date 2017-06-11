package Model;


public class Board {
	private static final int SIZE = 15;
	private boolean isVisible = true;
	private Status boardState [][];
	private int noOfShipsActive = 0;
	
	public Status getStatus(int x, int y){
		return boardState[x][y];
	}
	public void setStatus(int x, int y, Status newStatus){
		boardState[x][y] = newStatus;
	}
	public int getNoOfShipsActive(){
		return noOfShipsActive;
	}
	public void setNoOfShipsActive(int noOfShips){
		noOfShipsActive = noOfShips;
	}
	public boolean getVisibility(){
		return isVisible;
	}
	public void setVisibility(boolean visibility){
		isVisible = visibility;
	}
	
}
