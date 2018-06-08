package package1;

import java.awt.*;
import java.util.ArrayList;

/**********************************************************************
 * This class handles all of the game logic for Tic Tac Toe
 * 
 * @author Jarod Collier and Ben Burger
 * @version 5/31/18
 *********************************************************************/

public class SuperTicTacToeGame {

	/** Creates the 2D array */
	private CellStatus[][] board;

	/** Tells the status of the game */
	private GameStatus status;

	/** Creates an int called size for the board */
	private int size;

	/** Used for the cells specific turn on the board */
	private CellStatus turn;

	/** Number of connections needed to win the game */
	private int connections;

	/******************************************************************
	 * Default constructor for SuperTicTacToeGame class
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
		
		status = GameStatus.IN_PROGRESS;

		this.connections = connections;

		this.size = size;
		board = new CellStatus[size][size];

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++)
				board[row][col] = CellStatus.EMPTY;
		}

		turn = CellStatus.X;
	}

	/******************************************************************
	 * Returns the TicTacToe board
	 * @return board for the TicTacToe game
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
		if (board[row][col] != CellStatus.EMPTY)
			return;

		board[row][col] = turn;

		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;
		status = isWinner();
	}

	/******************************************************************
	 * Creates the logic on how to win the TicTacToe game
	 * @return GameStatus.IN_PROGRESS enum variable
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
				if (board[row][col] == CellStatus.EMPTY)
					count++;
			}
		}
		if (count == 0)
			return GameStatus.CATS;

		return GameStatus.IN_PROGRESS;
	}

	/******************************************************************
	 * Resets the TicTacToe Board
	 * @return void - the board just needs to be reset
	 *****************************************************************/
	public void reset() {
		for (int r = 0; r < size; r++) 
			for (int c = 0; c < size; c++) 
				board[r][c] = CellStatus.EMPTY;
		
		status = isWinner();
	}

	/******************************************************************
	 * Called from the SuperTicTacToePanel class and indicates
	 * if a player has won the game
	 * @return
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
	 *****************************************************************/
	public boolean isEmpty(int row, int col) {
		return board[row][col] == CellStatus.EMPTY;
	}
}
