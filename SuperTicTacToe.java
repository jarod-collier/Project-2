package package1;

import javax.swing.JFrame;

/**********************************************************************
Creates the GUI for the TicTacToe board

@author Jarod Collier and Ben Burger
@version 6/12/2018
**********************************************************************/

public class SuperTicTacToe {
	
	/******************************************************************
	 * Main method that sets up and displays TicTacToe GUI.
	 * @param none
	 * @return none
	 * @throws none
	 *****************************************************************/
	public static void main (String args[]) {

		// creating new frame
		JFrame frame = new JFrame ("Jarod and Ben's Super TicTacToe!");
		frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		// creating new TicTacToe panel and adding it to the frame
		SuperTicTacToePanel panel = new SuperTicTacToePanel();
		frame.getContentPane().add(panel);
		
		// setting frame size and making it visible
		frame.setSize(1000, 1000);
		frame.setVisible(true);
    }
}
