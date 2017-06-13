package Model;

import java.util.Random;


public class Board {
	private static final int SIZE = 10;
	private Status boardState [][] = new Status[SIZE][SIZE];;
	private Status enemyBoardState [][] = new Status[SIZE][SIZE];
	private int noOfShipsActive = 6;
	private enum Direction {
		HORIZONTAL,
		VERTICAL
	}
	
	public Board(){
		for(int i = 0; i < SIZE; ++i)
			for(int j = 0; j < SIZE; ++j){
				boardState[i][j] = Status.ISNOTSHIP;
				enemyBoardState[i][j] = Status.UNKNOWN;
			}
		noOfShipsActive = 6;
	}
	
	public Status getStatus(int x, int y){
		return boardState[x][y];
	}
	public Status getEnemyStatus(int x, int y){
		return enemyBoardState[x][y];
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
	public void clearBoard(){
		for(int i = 0; i < SIZE; ++i)
			for(int j = 0; j < SIZE; ++j)
				boardState[i][j] = Status.ISNOTSHIP; 
	}
	public void clearEnemyBoard(){
		for(int i = 0; i < SIZE; ++i)
			for(int j = 0; j < SIZE; ++j)
				enemyBoardState[i][j] = Status.UNKNOWN; 
	}
	public void setBoardRandom(){
		Random generator = new Random();
		boolean isOk = true;
		int x, y, length = 0, upperLeftX, upperLeftY, lowerRightX, lowerRightY;
		Direction direction;
		for(int i = 0; i < 6;){
			if(i < 1)
				length = 3;
			else if(i >=1 && i < 3)
				length = 2;
			else if(i >= 3 && i < 6)
				length = 1;
			direction = (generator.nextInt(2) == 0 ? Direction.HORIZONTAL:Direction.VERTICAL);
//			Horizontal:
			if(direction == Direction.HORIZONTAL){
				x = generator.nextInt(SIZE-length);
				y = generator.nextInt(SIZE);
				upperLeftX = Math.max(0,x-1);
				upperLeftY = Math.max(0,y-1);
				lowerRightX = Math.min(SIZE-1,x+length+1);
				lowerRightY = Math.min(SIZE-1,y+1);
			}
//			Vertical:
			else{
				x = generator.nextInt(SIZE);
				y = generator.nextInt(SIZE-length);
				upperLeftX = Math.max(0,x-1);
				upperLeftY = Math.max(0,y-1);
				lowerRightX = Math.min(SIZE-1,x+1);
				lowerRightY = Math.min(SIZE-1,y+length+1);
			}
			for(int m = upperLeftX; m <= lowerRightX;++m)
				for(int k = upperLeftY; k <= lowerRightY;++k){
					if(boardState[m][k] != Status.ISNOTSHIP){
						isOk = false;
						break;
					}
					else{
						isOk =true;
					}
				}
			if(isOk == true){
				for (int m = 0; m < length; ++m)
					if (direction == Direction.HORIZONTAL) {
						boardState[x + m][y] = Status.ISSHIP;
					} else {
						boardState[x][y + m] = Status.ISSHIP;
					}
			++i;
			}
			System.out.println(i);
		}
	}

	public ShotStatus checkShot(int x, int y	) {
		if (boardState[x][y] == Status.ISSHIP) {
			if (checkIfDestroyed(x, y)) {
				return ShotStatus.SHOT_AND_DESTROYED;
			} else {
				return ShotStatus.SHOT;
			}
		} else {
			return ShotStatus.MISSED;
		}
	}

	public void setShot(int x, int y, ShotStatus result) {
		if (result == ShotStatus.MISSED) {
			boardState[x][y] = Status.MISSED;
		} else {
			boardState[x][y] = Status.SHOT;

			if (result == ShotStatus.SHOT_AND_DESTROYED) {
				setDestroyed(x, y);
			}
		}
	}

	private boolean checkIfDestroyed(int x, int y) {
		int i = x;
		while (--i >= 0
				&& (boardState[i][y] == Status.ISSHIP || boardState[i][y] == Status.SHOT))
			if (boardState[i][y] == Status.ISSHIP)
				return false;

		i = x;
		while (++i < SIZE
				&& (boardState[i][y] == Status.ISSHIP || boardState[i][y] == Status.SHOT))
			if (boardState[i][y] == Status.ISSHIP)
				return false;

		i = y;
		while (--i >= 0
				&& (boardState[x][i] == Status.ISSHIP || boardState[x][i] == Status.SHOT))
			if (boardState[x][i] == Status.ISSHIP)
				return false;

		i = y;
		while (++i < SIZE
				&& (boardState[x][i] == Status.ISSHIP || boardState[x][i] == Status.SHOT))
			if (boardState[x][i] == Status.ISSHIP)
				return false;

		return true;
	}

	private void setDestroyed(int x, int y) {
		int x1 = x;
		int x2 = x;
		int y1 = y;
		int y2 = y;

		while (x1 > 0 && boardState[x1][y] == Status.SHOT) {
			x1--;
		}

		while (x2 < 14 && (boardState[x2][y] == Status.SHOT)) {
			x2++;
		}

		while (y1 > 0 && boardState[x][y1] == Status.SHOT) {
			y1--;
		}

		while (y2 < 14 && (boardState[x][y2] == Status.SHOT)) {
			y2++;
		}

		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				if (boardState[i][j] == Status.SHOT)
					boardState[i][j] = Status.SHIPDESTROYED;
	}
	
}
