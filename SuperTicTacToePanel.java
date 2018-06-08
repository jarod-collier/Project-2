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
	private JButton quitButton;
	private JButton undoButton;
	private JButton resetButton;

	private SuperTicTacToeGame game;

	private ImageIcon emptyIcon;
	private ImageIcon xIcon;
	private ImageIcon oIcon;

	private int size;
	private int connectionsToWin;

	public SuperTicTacToePanel() {

		JPanel bottom = new JPanel();
		JPanel center = new JPanel();

		//Sets images for the icons
		emptyIcon = new ImageIcon("blank.png");
		xIcon = new ImageIcon("x.png");
		oIcon = new ImageIcon("o.png");

		// create game, listeners
		ButtonListener listener = new ButtonListener();


		// Asks user for the desired board size
		boolean goodNum = false;

		while (!goodNum) {
			String boardSize = JOptionPane.showInputDialog(null, "Enter" +
					" in the size of the board: \n (Must be 2 < n < 10)");

			size = Integer.parseInt(boardSize);
			if (size > 2 && size < 10)
				goodNum = true;
			else
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid board size.");
		}


		// Asks user for the desired number of connections
		boolean goodConnection = false;

		while (!goodConnection) {

			String connections = JOptionPane.showInputDialog(null,
					"Enter number of connections needed to win: "
							+ "\n (Must be >2 and less than the size of the board)");

			connectionsToWin = Integer.parseInt(connections);
			if (connectionsToWin > 2 && connectionsToWin <= size)
				goodConnection = true;
			else
				JOptionPane.showMessageDialog(null, "Enter" +
						" valid amount of connections.");
		}

		try {
			game = new SuperTicTacToeGame(size, connectionsToWin);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Enter" +
					" valid parameters.");
		}


		// create Undo, quit, and reset buttons.
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(listener);

		// create the board 
		center.setLayout(new GridLayout(size,size,3,2));
		Dimension temp = new Dimension(60,60);
		board = new JButton[size][size];

		for (int row = 0; row < game.getBoard().length; row++)
			for (int col = 0; col < game.getBoard().length; col++) {

				Border thickBorder = new LineBorder(Color.blue, 2);

				board[row][col] = new JButton ("", emptyIcon);
				board[row][col].setBorder(thickBorder);

				board[row][col].addActionListener(listener);
				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout (new GridLayout(4,2,2,2));

		JLabel labxWins = new JLabel ("X Wins: ");
		JLabel laboWins = new JLabel ("O Wins: ");
		xWon = new JLabel ("0");
		oWon = new JLabel ("0");

		bottom.add(labxWins);
		bottom.add(xWon);
		bottom.add(laboWins);
		bottom.add(oWon);
		bottom.add(quitButton);
		bottom.add(undoButton);
		bottom.add(resetButton);

		// add all to contentPane

		//		add (new JLabel("!!!!!!  Super TicTacToe  !!!!"), 
		//				BorderLayout.NORTH);
		add (center, BorderLayout.CENTER);
		add (bottom, BorderLayout.SOUTH);

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

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (quitButton == e.getSource())
				System.exit(0);

			if (resetButton == e.getSource()) {
//				JOptionPane.showMessageDialog(null, "The game will reset");
//				game.reset();
//				displayBoard();
		
				SuperTicTacToe.main(new String[]{});
				
//				System.exit(0);
				
				//see if it works
//				game = new SuperTicTacToeGame(6, 4);
//				displayBoard();
			}
			
			
			
			for (int row = 0; row < game.getBoard().length; row++)
				for (int col = 0; col < game.getBoard().length; col++)
					if (board[row][col] == e.getSource() && game.isEmpty(row,col))
						game.select (row,col);


			displayBoard();

			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null, "O won and "
						+ "X lost!\n The game will reset");
				game.reset();
				displayBoard();
				oWon.setText("" + 
						(Integer.parseInt(oWon.getText()) + 1));
			}

			if (game.getGameStatus() == GameStatus.X_WON) {
				JOptionPane.showMessageDialog(null, "X won and "
						+ "O lost!\n The game will reset");
				game.reset();
				displayBoard();
				xWon.setText("" + 
						(Integer.parseInt(xWon.getText()) + 1));
			}

			if (game.getGameStatus() == GameStatus.CATS) {
				JOptionPane.showMessageDialog(null, "Tie game!" + 
						"\n The game will reset");
				game.reset();
				displayBoard();
			}

			
			if (undoButton == e.getSource()) {
				
				

			}
		}
	}
}
