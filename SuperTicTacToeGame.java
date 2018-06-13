package package1;

import java.util.Random;


/**********************************************************************
 * This class handles all of the logic for our Super TicTacToe game
 * 
 * @author Jarod Collier and Ben Burger
 * @version 5/31/18
 *********************************************************************/

public class SuperTicTacToeGame {

	/** Creates the 2D array */
	private CellStatus[][] board;

	/** Tells the status of the game */
	private GameStatus status;

	/** Creates an integer called size for the board */
	private int size;

	/** Used for the cells specific turn on the board */
	private CellStatus turn;

	/** Number of connections needed to win the game */
	private int connections;

	/******************************************************************
	 * Constructor for the TicTacToe Game
	 * @param int size which is the number of rows and columns
	 * @param int connections which is how many in a row are needed
	 * @throws IllegalArgumentException for having board size <2
	 * @throws IllegalArgumentException for having board size >10
	 * @throws IllegalArgumentException for having board connections
	 * less than 3
	 * @throws IllegalArgumentException for having board connections
	 * greater than the size of the board 
	 *****************************************************************/
	public SuperTicTacToeGame(int size, int connections) {
		if (size <= 2)
			throw new IllegalArgumentException("Must " +
					"have board size greater than 2");

		if (size >= 10)
			throw new IllegalArgumentException("Must " +
					"have board size smaller than 10");

		if (connections <= 2)
			throw new IllegalArgumentException("Must " +
					"have connections greater than 2");

		if (connections > size)
			throw new IllegalArgumentException("Connections " +
					"can not be greater than board size");

		//Let's the game now it has started playing
		status = GameStatus.IN_PROGRESS;

		this.connections = connections;

		this.size = size;
		board = new CellStatus[size][size];

		//Sets each cell to empty
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++)
				board[row][col] = CellStatus.EMPTY;
		}
	}

	/******************************************************************
	 * Returns the TicTacToe board
	 * @param none
	 * @return CellStatus[][] - a board for the TicTacToe game
	 * @throws none
	 *****************************************************************/
	public CellStatus[][] getBoard() {
		return board;
	}

	/******************************************************************
	 * Marks a cell as "X" or "O"
	 * @param row of the TicTacToe board
	 * @param col of the TicTacToe board
	 *****************************************************************/
	public void select (int row, int col) {

		//Makes sure the cell being clicked is empty
		if (board[row][col] != CellStatus.EMPTY)
			return;

		status = isWinner();

		board[row][col] = turn;

		//Once a selection has been made, change who's turn it is
		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;

		status = isWinner();
	}

	/******************************************************************
	 * Creates the logic on how to win the TicTacToe game
	 * @param none
	 * @return GameStatus of the game - who won, a tie, or in progress
	 * @throws none
	 *****************************************************************/
	private GameStatus isWinner() {


		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				int numToWin = 0;

				//Checks X for horizontal win
				for (int xHor = 0; xHor < connections; xHor++) {
					if (board[row][(col + xHor) % size] == 
							CellStatus.X)
						numToWin++;
				}
				if (numToWin == connections)
					return GameStatus.X_WON;

				numToWin = 0;

				//Checks O for horizontal win
				for (int oHor = 0; oHor < connections; oHor++) {
					if (board[row][(col + oHor) % size] == 
							CellStatus.O)
						numToWin++;
				}
				if (numToWin == connections)
					return GameStatus.O_WON;
			}
		}


		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {

				int connectToWin = 0;

				//Checks X for vertical win
				for (int xVert = 0; xVert < connections; xVert++) {
					if (board[(row + xVert) % size][col] ==
							CellStatus.X)
						connectToWin++;
				}
				if (connectToWin == connections)
					return GameStatus.X_WON;

				connectToWin = 0;

				//Checks O for vertical win
				for (int oVert = 0; oVert < connections; oVert++) {
					if (board[(row + oVert) % size][col] == 
							CellStatus.O)
						connectToWin++;
				}
				if (connectToWin == connections)
					return GameStatus.O_WON;
			}
		}

		//Checks for a no-win situation
		int count = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (board[row][col] != CellStatus.EMPTY)
					count++;
			}
		}
		if (count == size * size)
			return GameStatus.CATS;

		// If nothing else returned, the game is still in progress
		return GameStatus.IN_PROGRESS;
	}

	/******************************************************************
	 * Resets the TicTacToe Board
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	public void reset() {
		for (int r = 0; r < size; r++) 
			for (int c = 0; c < size; c++) 
				board[r][c] = CellStatus.EMPTY;

		status = isWinner();
	}

	/******************************************************************
	 * Returns whatever the GameStatus is for the TicTacToe game
	 * @param none
	 * @return GameStatus status of the game
	 * @throws none
	 *****************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 * Returns the CellStatus for a given row and column on the board
	 * @param row of the TicTacToe board
	 * @param col of the TicTacToe board
	 * @return The CellStatus of the given cell - X, O, or EMPTY
	 *****************************************************************/
	public CellStatus getCell (int row, int col) {
		return board[row][col];
	}

	/******************************************************************
	 * Checks if the cell is empty
	 * @param row of the TicTacToe board
	 * @param col of the TicTacToe board
	 * @return true or false whether cell is empty
	 * @throws none
	 *****************************************************************/
	public boolean isEmpty(int row, int col) {
		return board[row][col] == CellStatus.EMPTY;
	}

	/******************************************************************
	 * Undoes whatever action the user and AI just did
	 * @param row of the TicTacToe board
	 * @param col of the TicTacToe board
	 * @return none
	 * @throws none
	 *****************************************************************/
	public void undo (int row, int col) {
		board[row][col] = CellStatus.EMPTY;
		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;
	}

	/******************************************************************
	 * Determines the logic for how the AI interacts with the game
	 * @param none
	 * @return Integer Array of the the coordinates that the AI selects
	 * @throws none
	 *****************************************************************/
	public int [] commandAI() {

		//Creates the array that holds coordinates of AI's move
		int selection [] = {0,0};
		if (turn == CellStatus.O && status == GameStatus.IN_PROGRESS){
			Random r = new Random();

			//Checks if there are no other O cells on the board
			int count = 0;
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					if (board[row][col] != CellStatus.O) {
						count++;
					}
				}
			}

			//Selects place to initially go if no other O cells
			if (count == size * size) {
				int row = r.nextInt(size);
				int col = r.nextInt(size);
				select(row, col);
				selection[0] = row;
				selection[1] = col;
				return selection;
			}
			count = 0;
		}

		//The logic that checks if the AI can win
		if (turn == CellStatus.O && status == GameStatus.IN_PROGRESS){


			//Checks if O (AI) is about to win horizontally
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {

					int numToWin = 0;

					for (int oHor = 0; oHor < connections - 1; oHor++){
						if (board[row][(col + oHor) % size] == 
								CellStatus.O) 
							numToWin++;
					}

					if ((numToWin == connections - 1) && 
							status == GameStatus.IN_PROGRESS) {

						//Looks at the space to the right to win
						if (isEmpty(row, 
								(col + connections - 1) % size)){
							select(row, 
									(col + connections - 1) % size);
							selection[0] = row;
							selection[1] = (col + connections - 1)
									% size;
							return selection;

						}

						//Looks at the space to the left to win
						else if (isEmpty(row,
								(col + size - 1) % size)){
							select(row, (col + size - 1) % size);
							selection[0] = row;
							selection[1] = (col + size - 1) % size;
							return selection;

						}
					}

				}
			}

			//Looks if going in the middle horizontally will win
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {

					//Checks if current cell is O, if the end of the 
					//connections link is O, if there is an empty
					//spot in the middle, and if the cell to the
					//right is not O
					if (board[row][col] == CellStatus.O && 
							board[row][(col + connections - 1) % size] 
									== CellStatus.O && isEmpty(row, 
											(col + connections - 2)
											% size) && board[row]
													[(col + size + 1)
													 % size] != 
													 CellStatus.O){
						int attemptToWin = 0;

						//Checks middle going left horizontally to win
						for (int con = 1; 
								con < connections - 1; con++) {
							if (isEmpty(row,
									(col + size - con) % size))
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty(row, (col + size - 1) % size)){
							select(row, (col + size - 1) % size);
							attemptToWin = 0;
							selection[0] = row;
							selection[1] = (col + size - 1) % size;
							return selection;
						}
						else
							attemptToWin = 0;

						//Checks middle going right horizontally to win
						for (int con = 1; 
								con < connections - 1; con++) {
							if (isEmpty(row, 
									(col + size + con) % size)) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty(row, (col + size + 1) % size)){
							select(row, (col + size + 1) % size);
							attemptToWin = 0;
							selection[0] = row;
							selection[1] = (col + size + 1) % size;
							return selection;
						}
						else
							attemptToWin = 0;
					}
				}
			}


			//Checks if O about to win vertically
			for (int col = 0; col < size; col++) {
				for (int row = 0; row < size; row++) {

					int connectToWin = 0;

					for (int oVert = 0; oVert < connections; oVert++){
						if (board[(row + oVert) % size][col] == 
								CellStatus.O)
							connectToWin++;
					}

					if ((connectToWin == connections - 1) &&
							status == GameStatus.IN_PROGRESS) {

						//Looks at the space above to win
						if (isEmpty((row + size % size), col)){
							select((row + size % size), col);
							selection[0] = (row + size % size);
							selection[1] = col;
							return selection;
						}

						//Looks at the space below to win
						else if (isEmpty((row + connections - 1)
								% size, col))  {
							select((row + connections - 1) % size,
									col);
							selection[0] = (row + connections - 1)
									% size;
							selection[1] = col;
							return selection;
						}
					}
				}
			}

			//Looks if going in the middle vertically will win
			for (int col = 0; col < size; col++) {
				for (int row = 0; row < size; row++) {

					//Checks if current cell is O, if the end of the 
					//connections link is O, if there is an empty
					//spot in the middle, and if the cell above
					//is not O
					if (board[row][col] == CellStatus.O && 
							board[(row + connections - 1) % size][col]
									== CellStatus.O &&
									isEmpty((row + connections - 2) 
											% size, col) &&	
									board[(row + size + 1) % size][col]
											!= CellStatus.O) {
						int attemptToWin = 0;

						//Checks middle going up vertically to win
						for (int con = 1;
								con < connections - 1; con++){
							if (isEmpty(
									(row + size - con) % size, col)) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty((row + size - 1) % size, col)){
							select((row + size - 1) % size, col);
							attemptToWin = 0;
							selection[0] = (row + size - 1) % size;
							selection[1] = col;
							return selection;
						}
						else
							attemptToWin = 0;

						//Checks middle going down vertically to win
						for (int con = 1;
								con < connections - 1; con++){
							if (isEmpty(
									(row + size + con) % size, col))
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty((row + size + 1) % size, col)){
							select((row + size + 1) % size, col);
							attemptToWin = 0;
							selection[0] = (row + size + 1) % size;
							selection[1] = col;
							return selection;
						}
						else
							attemptToWin = 0;
					}
				}
			}
		}

		//Logic for AI to block the User from winning
		if (turn == CellStatus.O && status == GameStatus.IN_PROGRESS) {

			//Checks if user is about to win horizontally
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {

					int numToWin = 0;

					for (int xHor = 0; xHor < connections - 1; xHor++){
						if (board[row][(col + xHor) % size] == 
								CellStatus.X)
							numToWin++;
					}

					if ((numToWin == connections - 1) && 
							status == GameStatus.IN_PROGRESS) {

						// Looks at the space to the right to block
						if (isEmpty(row, 
								(col + connections - 1) % size)) {
							select(row, 
									(col + connections - 1) % size);
							selection[0] = row;
							selection[1] = (col + connections - 1)
									% size;
						}

						// Looks at the space to the left to block
						else if (isEmpty(row, 
								(col + size - 1) % size)) {
							select(row, (col + size - 1) % size);
							selection[0] = row;
							selection[1] = (col + size - 1) % size;
						}
					}
				}
			}
			// Looks if going in the middle horizontally will block
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {

					if (board[row][col] == CellStatus.X && 
							board[row][(col + connections - 1) % size]
									== CellStatus.X && isEmpty
									(row, (col + connections - 2) 
											% size) && 
									board[row][(col	+ size + 1) % size]
											!= CellStatus.X) {
						int attemptToWin = 0;

						// Checks middle going left 
						// horizontally to block
						for (int con = 1; 
								con < connections - 1; con++) {
							if (isEmpty(row, 
									(col + size - con) % size)) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty(row, 
										(col + size - 1) % size)) {
							select(row, (col + size - 1) % size);
							attemptToWin = 0;
							selection[0] = row;
							selection[1] = (col + size - 1) % size;
							return selection;
						}
						else
							attemptToWin = 0;

						// Checks middle going right 
						// horizontally to block
						for (int con = 1; 
								con < connections - 1; con++) {
							if (isEmpty(row, 
									(col + size + con) % size)) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty(row, 
										(col + size + 1) % size)) {
							select(row, (col + size + 1) % size);
							attemptToWin = 0;
							selection[0] = row;
							selection[1] = (col + size + 1) % size;
							return selection;
						}
						else
							attemptToWin = 0;
					}
				}
			}


			//Checks if the user is about to win vertically
			for (int col = 0; col < size; col++) {
				for (int row = 0; row < size; row++) {

					int connectToWin = 0;

					for (int xVert = 0; xVert < connections; xVert++){
						if (board[(row + xVert) % size][col] == 
								CellStatus.X)
							connectToWin++;
					}

					if ((connectToWin == connections - 1) &&
							status == GameStatus.IN_PROGRESS) {

						//Looks at the space above to block
						if (isEmpty((row + size % size), col)) {
							select((row + size % size), col);
							selection[0] = (row + size % size);
							selection[1] = col;
							return selection;
						}
						//Looks at the space below to block
						else if (isEmpty((row + connections - 1)
								% size, col)) {
							select((row + connections - 1) % size, 
									col);
							selection[0] = (row + connections - 1)
									% size;
							selection[1] = col;
							return selection;
						}

					}
				}
			}

			//Looks if going in the middle vertically will block
			for (int col = 0; col < size; col++) {
				for (int row = 0; row < size; row++) {

					if (board[row][col] == CellStatus.X && 
							board[(row + connections - 1) % size][col] 
									== CellStatus.X &&
									isEmpty((row + connections - 2)
											% size, col) && 
									board[(row + size + 1) % size][col]
											!= CellStatus.X) {
						int attemptToWin = 0;

						//Checks middle going up vertically to block
						for (int con = 1; 
								con < connections - 1; con++) {
							if (isEmpty((row + size - con) % size,
									col)) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty((row + size - 1) % size,
										col)) {
							select((row + size - 1) % size, col);
							attemptToWin = 0;
							selection[0] = (row + size - 1) % size;
							selection[1] = col;
							return selection;
						}
						else
							attemptToWin = 0;

						//Checks middle going down vertically to block
						for (int con = 1; 
								con < connections - 1; con++) {
							if (isEmpty((row + size + con) % size,
									col)) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 2 &&
								isEmpty((row + size + 1) % size, col)){
							select((row + size + 1) % size, col);
							attemptToWin = 0;
							selection[0] = (row + size + 1) % size;
							selection[1] = col;
							return selection;
						}
						else
							attemptToWin = 0;
					}
				}
			}

		}

		//Logic for the AI if it can't win or block the user
		if (turn == CellStatus.O && status == GameStatus.IN_PROGRESS) {

			//Tactically moves up or down
			for (int col = 0; col < size; col++) {
				for (int row = 0; row < size; row++) {

					if (board[row][col] == CellStatus.O) {
						int attemptToWin = 0;

						//Attempts to move down
						for (int con = 1; con < connections; con++) {
							if (getCell((row + con) % size, col)
									!= CellStatus.X) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 1 &&
								isEmpty((row + 1) % size, col)) {
							select((row + 1) % size, col);
							attemptToWin = 0;
							selection[0] = (row + 1) % size;
							selection[1] = col;
							return selection;
						}
						else
							attemptToWin = 0;

						//Attempts to move up
						for (int con = 1; con < connections; con++) {
							if (getCell((row + size - con - 1) % size,
									col) != CellStatus.X) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 1 &&
								isEmpty((row + size - 1) % size, 
										col)) {
							select((row + size - 1) % size, col);
							attemptToWin = 0;
							selection[0] = (row + size - 1) % size;
							selection[1] = col;
							return selection;
						}
						else
							attemptToWin = 0;
					}
				}
			}

			//Tactically moves left or right
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {

					if (board[row][col] == CellStatus.O) {
						int attemptToWin = 0;

						//Attempts to move left
						for (int con = 1; con < connections; con++) {
							if (getCell(row, (col + size - con) % size)
									!= CellStatus.X) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 1 &&
								isEmpty(row, 
										(col + size - 1) % size)) {
							select(row, (col + size - 1) % size);
							attemptToWin = 0;
							selection[0] = row;
							selection[1] = (col + size - 1) % size;
							return selection;
						}
						else
							attemptToWin = 0;

						//Attempts to move right
						for (int con = 1; con < connections; con++) {
							if (getCell(row, (col + size + con) % size)
									!= CellStatus.X) 
								attemptToWin++;							
						}
						if (attemptToWin == connections - 1 &&
								isEmpty(row, 
										(col + size + 1) % size)) {
							select(row, (col + size + 1) % size);
							attemptToWin = 0;
							selection[0] = row;
							selection[1] = (col + size + 1) % size;
							return selection;
						}
						else
							attemptToWin = 0;
					}
				}
			}

			//If no tactical move exists, randomly move
			Random rand = new Random();

			int randR = (rand.nextInt(size));
			int randC = (rand.nextInt(size));

			//Makes sure the random cell will be empty
			while( !isEmpty(randR, randC)) {
				randR = (rand.nextInt(size));
				randC = (rand.nextInt(size));
			}
			select(randR,randC);
			selection[0] = randR;
			selection[1] = randC;
			return selection;
		}

		//Returns the coordinates of the selection the AI made
		return selection;
	}

	/******************************************************************
	 * Sets the turn for the game to X (the user)
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	public void setTurnX () {
		turn = CellStatus.X;
	}

	/******************************************************************
	 * Sets the turn for the game to O (the AI)
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	public void setTurnO () {
		turn = CellStatus.O;
	}

}
