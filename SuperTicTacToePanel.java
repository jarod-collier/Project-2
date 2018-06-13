package package1;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**********************************************************************
 * Creates the Panel for the TicTacToe GUI
 * 
 * @author Jarod Collier and Ben Burger
 * @version 6/12/2018
 *********************************************************************/
public class SuperTicTacToePanel extends JPanel {

	/** Declares the JButton for the 2D Array board */
	private JButton[][] board;

	/** Declares the JButton for the quit button */
	private JButton quitButton;

	/** Declares the JButton for the undo button */
	private JButton undoButton;

	/** Declares the JButton for the reset button */
	private JButton resetButton;

	/** Declares the JLabel for the label of X wins */
	private JLabel xWon;

	/** Declares the JLabel for the label of O wins */
	private JLabel oWon;

	/** Declares the JLabel for the label that stores X win total */
	private JLabel labxWins;

	/** Declares the JLabel for the label that stores O win total */
	private JLabel laboWins;

	/** Declares a sub-panel named bottom */
	private JPanel bottom;

	/** Declares a sub-panel named bottom */
	private JPanel center;

	/** Declares the ImageIcon for the empty space in the board */
	private ImageIcon emptyIcon;

	/** Declares the ImageIcon for the X space in the board */
	private ImageIcon xIcon;

	/** Declares the ImageIcon for the O space in the board */
	private ImageIcon oIcon;

	/** Declares a button listener */
	private ButtonListener listener;

	/** Checks the status of each cell */
	private CellStatus iCell;

	/** Declares a new TicTactoe game */
	private SuperTicTacToeGame game;

	/** Integer for the desired board size */
	private int size;

	/** Integer for the desired connections needed to win */
	private int connectionsToWin;

	/** Integer to keep track of who's turn it is */
	private int turn;

	/** 2D Array to keep track of all the moves on the board */
	private int [][] turnSelection;

	/** Integer that keeps track of who moves first */
	private int moveFirst;

	/** Integer that is set to 1 or 2 depending who goes first */
	private int firstTurn;

	/** Boolean to check if the game has started */
	private boolean gameStart = true; 

	/** Boolean to check if the cancel button was pressed */
	private boolean cancel = false;


	public SuperTicTacToePanel() {

		// create bottom panel 
		// (contains undo, quit, reset buttons and win totals)
		bottom = new JPanel();


		// set images for the icons
		emptyIcon = new ImageIcon("blank.png");
		xIcon = new ImageIcon("x.png");
		oIcon = new ImageIcon("o.png");

		// create action listener
		listener = new ButtonListener();

		// create and add undo, quit, reset buttons
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(listener);

		// method call to create board
		createBoard();

		// method call to display board
		displayBoard();

		// set layout for bottom panel 
		bottom.setLayout (new GridLayout(4,2,0,0));

		// setting text for labels
		labxWins = new JLabel ("X Wins: ");
		laboWins = new JLabel ("O Wins: ");
		xWon = new JLabel ("0");
		oWon = new JLabel ("0");

		// adding labels and buttons to bottom panel
		bottom.add(labxWins);
		bottom.add(xWon);
		bottom.add(laboWins);
		bottom.add(oWon);
		bottom.add(quitButton);
		bottom.add(undoButton);
		bottom.add(resetButton);

		// adding bottom and center panels
		add (bottom);
		add (center);
	}


	/******************************************************************
	 * Displays the TicTacToe board by setting the image in each cell.
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	private void displayBoard() {

		// nested for loop, sets image in each cell of the board
		for (int row = 0; row < game.getBoard().length; row++)
			for (int col = 0; col < game.getBoard().length; col++) {

				board[row][col].setIcon(emptyIcon);
				iCell = game.getCell(row, col);

				if (iCell == CellStatus.O)
					board[row][col].setIcon(oIcon);

				else if (iCell == CellStatus.X)
					board[row][col].setIcon(xIcon);

				else if (iCell == CellStatus.EMPTY)
					board[row][col].setIcon(emptyIcon);
			}		
	}

	/******************************************************************
	 * Creates a TicTacToe board.
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	public void createBoard () {

		// asks user for the desired board size
		boolean goodNum = false;

		// stays in loop until valid size is entered or
		// user hits cancel
		while (!goodNum && !cancel) {
			try {
				String boardSize = JOptionPane.showInputDialog(null, 
						"Enter in the size of the board: \n "
								+ "(Must be 2 < n < 10)");

				size = Integer.parseInt(boardSize);
			}
			catch(Exception e) {
				// if the game just started
				if (gameStart)
					System.exit(0);   // exit the program
				else 
					cancel = true;    // set cancel to true
			}
			if (size > 2 && size < 10)
				goodNum = true;
			else
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid board size.");
		}

		// asks user for the desired number of connections
		boolean goodConnection = false;

		// stays in loop until valid number of connections
		// is entered or user hits cancel
		while (!goodConnection && !cancel) {
			try {
				String connections = JOptionPane.showInputDialog(null,
						"Enter number of connections needed to win: "
								+ "\n (Must be >2 and less than the "
								+ "size of the board)");

				connectionsToWin = Integer.parseInt(connections);
			}
			catch (Exception e) {
				// if the game just started
				if (gameStart)
					System.exit(0);  	// exit the program
				else 
					cancel = true;     // set cancel to true
			}
			if (connectionsToWin > 2 && connectionsToWin <= size)
				goodConnection = true;
			else
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid amount of connections.");
		}

		// asks user who should start
		boolean goodFirstTurn = false;

		// stays in loop until valid first turn
		// is entered or user hits cancel
		while (!goodFirstTurn && !cancel) {

			// prompt user if they want to go first or second
			try {
				String firstMove = JOptionPane.showInputDialog(null,
						"Enter '1' to go first or '2' to got second:");

				moveFirst = Integer.parseInt(firstMove);
			}
			catch (Exception e) {
				// if the game just started
				if (gameStart)
					System.exit(0); 	// exit the program
				else {
					cancel = true;  	// set cancel to true
				}
			}
			if (moveFirst == 1) {
				goodFirstTurn = true;
			} 
			else if (moveFirst == 2) {
				goodFirstTurn = true;
			}
			else {
				JOptionPane.showMessageDialog(null, 
						"Enter valid number");
			}
		}	

		// if the user didn't cancel
		if (!cancel) {
			// try to create a new game
			try {
				game = new SuperTicTacToeGame(size, connectionsToWin);
				turn = 0;
				turnSelection = new int [size*size][2];
				gameStart = false;
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid parameters.");
			}

			if (moveFirst == 1) {
				firstTurn = 1;
				game.setTurnX();
			} 
			else if (moveFirst == 2) {
				firstTurn = 2;
				game.setTurnO();
			}

			// create new panel for the board, set layout
			center = new JPanel();
			center.setLayout(new GridLayout(size,size,3,2));

			// create new button board
			board = new JButton[size][size];

			// nested for loops: adds buttons, sets board to blank,
			// adds listeners
			for (int row = 0; row < game.getBoard().length; row++)
				for (int col = 0; col < game.getBoard().length; col++){

					Border thickBorder = new LineBorder(Color.blue, 2);

					board[row][col] = new JButton ("", emptyIcon);
					board[row][col].setBorder(thickBorder);

					board[row][col].addActionListener(listener);
					center.add(board[row][col]);
				}
		}

		// set cancel to false
		cancel = false;

		game.commandAI();
	}


	private class ButtonListener implements ActionListener {

		/**************************************************************
		 * Determines what happens when the User clicks something
		 * @param ActionEvent e used to tell computer the User clicked
		 * @return none
		 * @throws none
		 *************************************************************/
		public void actionPerformed(ActionEvent e) {

			// if quit button is clicked, exit program 
			if (quitButton == e.getSource())
				System.exit(0); 

			// if reset button is clicked
			if (resetButton == e.getSource()) {
				JOptionPane.showMessageDialog(null, 
						"The game will reset");

				// reset game, create new board, add new center panel
				game.reset();

				if (firstTurn == 1) {
					game.setTurnX();
				}
				if (firstTurn == 2) {
					game.setTurnO();
				}

				remove(center);
				createBoard();
				add (center);



				// display new board
				displayBoard();
				revalidate();
				repaint();		

				game.commandAI();
				displayBoard();
			}


			// nested for loops, determines which cell was selected
			for (int row = 0; row < game.getBoard().length; row++) {
				for (int col = 0; 
						col < game.getBoard().length; col++) {
					if (board[row][col] == e.getSource() 
							&& game.isEmpty(row,col)) {

						// select method
						game.select (row,col);

						// storing coordinates of the selected cell
						turnSelection [turn][0] = row;
						turnSelection [turn][1] = col;

						// increment turn
						turn++;
						displayBoard();
						checkForEndGame();

						int selection [];
						selection = game.commandAI();

						// store coordinates of AI selected cell
						turnSelection [turn][0] = selection[0];
						turnSelection [turn][1] = selection[1];

						// increment turn
						turn++;

						displayBoard();
						checkForEndGame();
					}
				}
			}

			displayBoard();


			// undo button is clicked 
			if (undoButton == e.getSource()) {
				if (turn >= 2) {
					// undo the last user move and AI move
					game.undo(turnSelection[turn-1][0],
							turnSelection[turn-1][1]);
					game.undo(turnSelection[turn-2][0],
							turnSelection[turn-2][1]);
					turn = turn - 2;
				}
				else {
					JOptionPane.showMessageDialog(null, 
							"Cannot undo");
				}

				displayBoard();
			}
		}
	}

	/******************************************************************
	 * Checks whether the game has ended by X or O winning or a tie
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	public void checkForEndGame () {

		// checking if O (AI) won
		if (game.getGameStatus() == GameStatus.O_WON) {
			JOptionPane.showMessageDialog(null, "O won and "
					+ "X lost!\n The game will reset");
			game.reset();

			if (firstTurn == 1) {
				game.setTurnX();
			}
			if (firstTurn == 2) {
				game.setTurnO();
			}
			turn = 0;
			turnSelection = new int [size*size][2];
			game.commandAI();

			displayBoard();
			oWon.setText("" + 
					(Integer.parseInt(oWon.getText()) + 1));
		}


		// checking if X (user) won
		if (game.getGameStatus() == GameStatus.X_WON) {
			JOptionPane.showMessageDialog(null, "X won and "
					+ "O lost!\n The game will reset");
			game.reset();

			if (firstTurn == 1) {
				game.setTurnX();
			}
			if (firstTurn == 2) {
				game.setTurnO();
			}
			turn = 0;
			turnSelection = new int [size*size][2];
			game.commandAI(); 

			displayBoard();	
			xWon.setText("" + 
					(Integer.parseInt(xWon.getText()) + 1));
		}

		// checking if game is a tie
		if (game.getGameStatus() == GameStatus.CATS) {
			JOptionPane.showMessageDialog(null, "Tie game!" + 
					"\n The game will reset");
			game.reset();


			if (firstTurn == 1) {
				game.setTurnX();
			}
			if (firstTurn == 2) {
				game.setTurnO();
			}
			turn = 0;
			turnSelection = new int [size*size][2];
			game.commandAI();

			displayBoard();
		}
	}
}