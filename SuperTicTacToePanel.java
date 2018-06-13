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
 * @version 5/31/18
 *********************************************************************/
public class SuperTicTacToePanel extends JPanel {

	private JButton[][] board;
	private CellStatus iCell;
	private JLabel xWon;
	private JLabel oWon;
	private JLabel labxWins;
	private JLabel laboWins;
	private JButton quitButton;
	private JButton undoButton;
	private JButton resetButton;

	private SuperTicTacToeGame game;

	private ImageIcon emptyIcon;
	private ImageIcon xIcon;
	private ImageIcon oIcon;

	private int size;
	private int connectionsToWin;
	private int turn;
	private int [][] turnSelection;
	
	JPanel bottom;
	JPanel center;
	ButtonListener listener;
	
	private boolean gameStart = true; 
	private boolean cancel = false;
	
	
	public SuperTicTacToePanel() {

		
		bottom = new JPanel();


		//Sets images for the icons
		emptyIcon = new ImageIcon("blank.png");
		xIcon = new ImageIcon("x.png");
		oIcon = new ImageIcon("o.png");

		// create game, listeners
		listener = new ButtonListener();

		// create Undo, quit, and reset buttons.
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(listener);

		createBoard();

		displayBoard();

		bottom.setLayout (new GridLayout(4,2,0,0));
		
		labxWins = new JLabel ("X Wins: ");
		laboWins = new JLabel ("O Wins: ");
		xWon = new JLabel ("0");
		oWon = new JLabel ("0");

		bottom.add(labxWins);
		bottom.add(xWon);
		bottom.add(laboWins);
		bottom.add(oWon);
		bottom.add(quitButton);
		bottom.add(undoButton);
		bottom.add(resetButton);

		add (bottom);
		add (center);
		
		

	}
	/******************************************************************
	 * Displays the TicTacToe board by setting the image in each cell.
	 *****************************************************************/
	private void displayBoard() {

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


	public void createBoard () {
		
	
		
		// Asks user for the desired board size
		boolean goodNum = false;

		while (!goodNum && !cancel) {
			try {
				String boardSize = JOptionPane.showInputDialog(null, "Enter" +
						" in the size of the board: \n (Must be 2 < n < 10)");
	
				size = Integer.parseInt(boardSize);
			}
			catch(Exception e) {
				 if (gameStart)
					 System.exit(0);
				 else 
					 cancel = true;
			}
			if (size > 2 && size < 10)
				goodNum = true;
			else
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid board size.");
		}


		// Asks user for the desired number of connections
		boolean goodConnection = false;

		while (!goodConnection && !cancel) {
			try {
				String connections = JOptionPane.showInputDialog(null,
						"Enter number of connections needed to win: "
								+ "\n (Must be >2 and less than the size of the board)");
	
				connectionsToWin = Integer.parseInt(connections);
			}
			catch (Exception e) {
				if (gameStart)
					 System.exit(0);
				 else 
					 cancel = true;
			}
			if (connectionsToWin > 2 && connectionsToWin <= size)
				goodConnection = true;
			else
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid amount of connections.");
		}
		if (!cancel) {
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
	
			try {
				String turn = JOptionPane.showInputDialog(null,
						"Enter '1' to go first or '2' to got second:");
				if (turn.equals("1")) {
					game.setTurnX();
				} 
				else if (turn.equals("2")) {
					game.setTurnO();
				}
				else {
					throw new IllegalArgumentException();
				}
			}
			catch (IllegalArgumentException e) {
				if (gameStart)
					JOptionPane.showMessageDialog(null, "Enter valid number");
			}
			catch (Exception e) {
				if (gameStart)
					 System.exit(0);
				 else 
					 cancel = true;
			}
			
			
			center = new JPanel();
			center.setLayout(new GridLayout(size,size,3,2));
			board = new JButton[size][size];
	
			for (int row = 0; row < game.getBoard().length; row++)
				for (int col = 0; col < game.getBoard().length; col++) {
	
					Border thickBorder = new LineBorder(Color.blue, 2);
	
					board[row][col] = new JButton ("", emptyIcon);
					board[row][col].setBorder(thickBorder);
	
					board[row][col].addActionListener(listener);
					center.add(board[row][col]);
				}
		}
		cancel = false;
	}


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (quitButton == e.getSource())
				System.exit(0);

			if (resetButton == e.getSource()) {
				JOptionPane.showMessageDialog(null, "The game will reset");
				game.reset();
				remove(center);
				createBoard();
				add (center);
				
				game.startMoveAI();
				
				displayBoard();
				revalidate();
				repaint();		
			}


			//Presses a button on the board
			for (int row = 0; row < game.getBoard().length; row++) {
				for (int col = 0; col < game.getBoard().length; col++) {
					if (board[row][col] == e.getSource() && game.isEmpty(row,col)) {
						game.select (row,col);
						turnSelection [turn][0] = row;
						turnSelection [turn][1] = col;
						turn++;
						
						game.startMoveAI();
						
						game.tryToWinAI();
						
//						game.blockUserAI();
//						
//						game.tacticsAI();
					}
				}
			}

			displayBoard();

			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null, "O won and "
						+ "X lost!\n The game will reset");
				game.reset();
				
				game.startMoveAI();
				
				displayBoard();
				oWon.setText("" + 
						(Integer.parseInt(oWon.getText()) + 1));
			}

			if (game.getGameStatus() == GameStatus.X_WON) {
				JOptionPane.showMessageDialog(null, "X won and "
						+ "O lost!\n The game will reset");
				game.reset();
			
				game.startMoveAI();
				
				displayBoard();	
				xWon.setText("" + 
						(Integer.parseInt(xWon.getText()) + 1));
			}

			if (game.getGameStatus() == GameStatus.CATS) {
				JOptionPane.showMessageDialog(null, "Tie game!" + 
						"\n The game will reset");
				game.reset();
				
				game.startMoveAI();
				
				displayBoard();
			}


			if (undoButton == e.getSource()) {
				if (turn >= 1) {
					game.undo(turnSelection[turn-1][0],turnSelection[turn-1][1]);
					turn--;
				}
				else {
					JOptionPane.showMessageDialog(null, "Cannot undo");
				}
				
				displayBoard();
			}
		}
	}
}
