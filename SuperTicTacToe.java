package package1;

import javax.swing.JFrame;

import java.awt.event.WindowEvent;

/**********************************************************************
Creates the GUI for the TicTacToe board

@author Jarod Collier and Ben Burger
@version 5/31/2018
**********************************************************************/

public class SuperTicTacToe {
	
	public static void main (String[] args) {

		JFrame frame = new JFrame ("Jarod and Ben's Super TicTacToe!");
		frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		SuperTicTacToePanel panel = new SuperTicTacToePanel();
		frame.getContentPane().add(panel);
		
		frame.setSize(1000, 1000);
		frame.setVisible(true);
    }
}
